package sistem_tehnicki_pregled.service.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import sistem_tehnicki_pregled.model.entities.Inspection;
import sistem_tehnicki_pregled.model.entities.Vehicle;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.service.dto.InspectionResponseDTO;
import sistem_tehnicki_pregled.service.factories.InspectionResponseFactory;

@Slf4j
@Service
@RequiredArgsConstructor
public class InspectionNotificationService {

    private final SimpMessagingTemplate messagingTemplate;
    private final InspectionResponseFactory responseFactory;

    public void notifyBrakeTestFinished(Inspection inspection) {
        Vehicle vehicle = inspection.getVehicle();
        InspectionResponseDTO payload = responseFactory.build(
                vehicle,
                inspection.getResult(),
                inspection.getFinishedAt() != null ? inspection.getFinishedAt() : java.time.LocalDateTime.now(),
                inspection.getId()
        );

        String destination = "/topic/inspection/" + inspection.getId() + "/brake-test";
        messagingTemplate.convertAndSend(destination, payload);
        log.info("WebSocket obaveštenje poslato na {} sa rezultatom {}", destination, inspection.getResult());
    }

}
