from kafka import KafkaConsumer, KafkaProducer
import json
import time
import random
from datetime import datetime
import threading
import struct

BOOTSTRAP = "localhost:9092"
producer = KafkaProducer(bootstrap_servers=BOOTSTRAP)


consumer = KafkaConsumer(
    "start-brake-test",
    bootstrap_servers=BOOTSTRAP,
    value_deserializer=lambda x: struct.unpack('!i', x)[0] if x else None,
    auto_offset_reset="latest",
    group_id="python-simulator"
)

WHEELS = ["FL", "FR", "RL", "RR"]

active_inspections = set()

def simulation_loop(inspection_id):
    print(f"🚗 Simulacija POKRENUTA za pregled ID: {inspection_id}")
    
    start_time = time.time()
    TEST_DURATION = 10 

    while (time.time() - start_time < TEST_DURATION) and (inspection_id in active_inspections):
        for wheel in WHEELS:
            if inspection_id not in active_inspections: 
                break
            
            event = {
                "inspectionId": int(inspection_id),
                "timestamp": int(time.time() * 1000),
                "wheelId": wheel,
                "axleId": "FRONT" if wheel in ["FL", "FR"] else "REAR",
                "brakeForce": round(random.uniform(50, 250), 2)
            }
            
            # Ovde eksplicitno pretvaramo u JSON jer šaljemo objekat
            json_payload = json.dumps(event).encode("utf-8")
            producer.send("brake-measurements", value=json_payload)
            time.sleep(0.1)
            
        time.sleep(0.5)
            
    if inspection_id in active_inspections:
        active_inspections.remove(inspection_id)
        
    print(f"🛑 Simulacija završena u Pythonu za pregled {inspection_id}")
    
    finish_event = {
        "inspectionId": int(inspection_id)
    }
    finish_payload = json.dumps(finish_event).encode("utf-8")

    producer.send("brake-test-finished", value=finish_payload)

    # ISFORSIRAJ SLANJE
    producer.flush()
    print(f"📢 Poslat FINISH signal na Kafku za pregled {inspection_id}")


print("⏳ Čekam START signal sa Kafka topika: start-brake-test ...")

# MAIN LOOP
for msg in consumer:
    try:
        print(msg)
        current_inspection_id = msg.value
        print(f"📩 Primljen START signal za ID: {current_inspection_id}")

        if current_inspection_id not in active_inspections:
            active_inspections.add(current_inspection_id)
            
            t = threading.Thread(target=simulation_loop, args=(current_inspection_id,))
            t.daemon = True
            t.start()
        else:
            print(f"⚠️ Pregled {current_inspection_id} se već simulira!")
            
    except ValueError:
        print(f"❌ Greška: Primljeni ID nije broj: {msg.value}")