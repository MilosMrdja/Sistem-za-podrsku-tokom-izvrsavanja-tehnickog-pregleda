package sistem_tehnicki_pregled.service.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sistem_tehnicki_pregled.model.cep.BrakeMeasurementEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class BrakeKafkaConsumer {

    private final KieSession cepSession;

    @KafkaListener(topics = "brake-measurements", groupId = "cep-group")
    public void consume(BrakeMeasurementEvent event) {

        cepSession.insert(event);

        log.info("CEP event inserted: {}", event);
    }
}
