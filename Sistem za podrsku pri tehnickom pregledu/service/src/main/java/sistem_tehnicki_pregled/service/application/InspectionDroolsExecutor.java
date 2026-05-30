package sistem_tehnicki_pregled.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;
import sistem_tehnicki_pregled.model.entities.Inspection;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.model.models.FinalDecision;
import sistem_tehnicki_pregled.model.models.SystemStatus;
import sistem_tehnicki_pregled.service.dto.InspectionResponseDTO;
import sistem_tehnicki_pregled.service.factories.DroolsSessionFactory;
import sistem_tehnicki_pregled.service.factories.InspectionResponseFactory;

import java.util.ArrayList;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class InspectionDroolsExecutor {

    private final DroolsSessionFactory sessionFactory;
    private final InspectionStateService inspectionStateService;
    private final InspectionResponseFactory responseFactory;

    public InspectionResponseDTO executeVehicleSystemCheck(
            Inspection inspection,
            InspectionResult passedResult,
            String systemName,
            Object fact
    ) {
        return executeSystemCheck(
                inspection,
                passedResult,
                systemName,
                inspection.getVehicle(),
                fact
        );
    }

    public InspectionResponseDTO executeSystemCheck(
            Inspection inspection,
            InspectionResult passedResult,
            String systemName,
            Object... facts
    ) {
        SystemStatus systemStatus = new SystemStatus(systemName, false, new ArrayList<>());
        return executeSystemCheck(inspection, passedResult, systemStatus, facts);
    }

    public InspectionResponseDTO executeSystemCheck(
            Inspection inspection,
            InspectionResult passedResult,
            SystemStatus systemStatus,
            Object... facts
    ) {
        FinalDecision decision = executeRules(passedResult, session -> {
            for (Object fact : facts) {
                session.insert(fact);
            }
            session.insert(systemStatus);
        });
        inspectionStateService.applyDecision(inspection, decision);

        return responseFactory.build(inspection.getVehicle(), decision, systemStatus);
    }

    public FinalDecision executeRules(InspectionResult initialResult, Object fact) {
        return executeRules(initialResult, session -> session.insert(fact));
    }

    public FinalDecision executeRules(InspectionResult initialResult, Consumer<KieSession> factsInserter) {
        FinalDecision decision = FinalDecision.builder()
                .result(initialResult)
                .resolved(false)
                .build();

        KieSession session = sessionFactory.createSession();
        try {
            factsInserter.accept(session);
            session.insert(decision);

            int fired = session.fireAllRules();
            log.info("Rules fired: {}", fired);
        } finally {
            session.dispose();
        }

        return decision;
    }
}
