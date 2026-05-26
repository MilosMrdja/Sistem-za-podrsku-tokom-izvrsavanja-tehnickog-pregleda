package sistem_tehnicki_pregled.service.kafka;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sistem_tehnicki_pregled.model.cep.BrakeMeasurementEvent;
import sistem_tehnicki_pregled.model.cep.BrakeTestFinishedEvent;
import sistem_tehnicki_pregled.model.entities.Inspection;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.model.models.FinalDecision;
import sistem_tehnicki_pregled.model.models.SystemStatus;
import sistem_tehnicki_pregled.service.factories.DroolsSessionFactory;
import sistem_tehnicki_pregled.service.repositories.InspectionRepository;
import sistem_tehnicki_pregled.service.websocket.InspectionNotificationService;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class BrakeKafkaConsumer {

    private KieSession cepSession;
    private final DroolsSessionFactory sessionFactory;
    private final InspectionRepository inspectionRepository;
    private final InspectionNotificationService notificationService;

    @PostConstruct
    public void init() {
        this.cepSession = sessionFactory.createSession();
        log.info("KieSession uspešno kreirana preko DroolsSessionFactory.");
    }

    @KafkaListener(topics = "brake-measurements", groupId = "cep-group")
    public void consume(BrakeMeasurementEvent event) {
        synchronized (cepSession) {
            try {
                initializeBrakeSystemIfNeeded();

                cepSession.insert(event);

            } catch (Exception e) {
                log.error("❌ Greška prilikom ubacivanja merenja u Drools: {}", e.getMessage());
            }
        }

    }

    @KafkaListener(topics = "brake-test-finished", groupId = "cep-group", properties = {
            "value.deserializer=org.springframework.kafka.support.serializer.JsonDeserializer",
            "spring.json.value.default.type=sistem_tehnicki_pregled.model.cep.BrakeTestFinishedEvent",
            "spring.json.trusted.packages=sistem_tehnicki_pregled.model.cep"
    })
    public void consumeFinishSignal(
            BrakeTestFinishedEvent event) {
        synchronized (cepSession) {
            try {

                log.info(
                        "🏁 Finish signal received: {}",
                        event.getInspectionId());
                Inspection inspection = inspectionRepository.findById(event.getInspectionId()).orElse(null);
                cepSession.insert(event);

                insertDecisionIfNeeded(event);

                int fired = cepSession.fireAllRules();

                log.info("Rules fired: {}", fired);

                FinalDecision decision = getFinalDecision();

                log.info(
                        "FinalDecision: {}",
                        decision);
                updateInspectionState(inspection, decision);

                if (inspection != null) {
                    notificationService.notifyBrakeTestFinished(inspection);
                }

            } catch (Exception e) {
                log.error(
                        "Error processing finish signal",
                        e);
            }
        }

    }

    private void updateInspectionState(Inspection inspection, FinalDecision decision) {
        if (decision.getResult() == InspectionResult.NIJE_PROSAO) {
            inspection.setResult(InspectionResult.NIJE_PROSAO);
            inspection.setResolved(true);
            inspection.setFinishedAt(LocalDateTime.now());
        } else {

            inspection.setResult(decision.getResult());
        }
        inspectionRepository.save(inspection);
    }

    private void initializeBrakeSystemIfNeeded() {

        boolean exists = cepSession.getObjects(obj -> obj instanceof SystemStatus &&
                SystemStatus.BRAKE_TEST.equals(
                        ((SystemStatus) obj)
                                .getSystemName()))
                .stream()
                .findFirst()
                .isPresent();

        if (!exists) {

            cepSession.insert(
                    new SystemStatus(
                            SystemStatus.BRAKE_TEST,
                            false,
                            new ArrayList<>()));
        }
    }

    private void insertDecisionIfNeeded(
            BrakeTestFinishedEvent event) {

        boolean exists = cepSession.getObjects(obj -> obj instanceof FinalDecision).stream().findFirst().isPresent();

        if (!exists) {

            FinalDecision decision = FinalDecision.builder()
                    .result(
                            InspectionResult.BRAKE_TEST_PASSED)
                    .resolved(false)
                    .build();

            cepSession.insert(decision);
        }
    }

    private FinalDecision getFinalDecision() {

        return cepSession.getObjects(obj -> obj instanceof FinalDecision)
                .stream()
                .map(FinalDecision.class::cast)
                .filter(FinalDecision::isResolved)
                .findFirst()
                .orElse(null);
    }

}
