from kafka import KafkaConsumer, KafkaProducer
import json
import time
import random
from datetime import datetime
import threading

BOOTSTRAP = "localhost:9092"

# Producer (za brake events)
producer = KafkaProducer(
    bootstrap_servers=BOOTSTRAP,
    value_serializer=lambda v: json.dumps(v).encode("utf-8")
)

# Consumer (sluša START signal)
consumer = KafkaConsumer(
    "start-brake-test",
    bootstrap_servers=BOOTSTRAP,
    value_deserializer=lambda x: x.decode("utf-8"),
    auto_offset_reset="latest",
    group_id="python-simulator"
)

WHEELS = ["FL", "FR", "RL", "RR"]

running = False


def generate_brake_event(wheel):
    return {
        "timestamp": datetime.utcnow().isoformat(),
        "wheelId": wheel,
        "axleId": "FRONT" if wheel in ["FL", "FR"] else "REAR",
        "brakeForce": round(random.uniform(50, 250), 2)
    }


def simulation_loop():
    global running

    print("🚗 Simulation STARTED")

    while running:
        for wheel in WHEELS:
            if not running:
                break

            event = generate_brake_event(wheel)

            producer.send("brake-measurements", value=event)
            print("Sent:", event)

            time.sleep(0.5)

        time.sleep(1)

    print("🛑 Simulation STOPPED")


print("⏳ Waiting for START signal from Kafka topic: start-brake-test ...")

# MAIN LOOP: čeka start signal
for msg in consumer:
    print("📩 Received START signal:", msg.value)

    if not running:
        running = True
        threading.Thread(target=simulation_loop).start()