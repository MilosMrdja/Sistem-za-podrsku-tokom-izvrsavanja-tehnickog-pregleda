package sistem_tehnicki_pregled.service.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistem_tehnicki_pregled.model.entities.Inspection;
import sistem_tehnicki_pregled.model.enums.InspectionResult;
import sistem_tehnicki_pregled.model.models.FinalDecision;
import sistem_tehnicki_pregled.service.repositories.InspectionRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InspectionStateService {

    private final InspectionRepository inspectionRepository;

    public void applyDecision(Inspection inspection, FinalDecision decision) {
        decision.setDecidedAt(LocalDateTime.now());
        inspection.setResult(decision.getResult());

        if (isFinalResult(decision.getResult())) {
            inspection.setResolved(true);
            inspection.setFinishedAt(decision.getDecidedAt());
        }

        inspectionRepository.save(inspection);
    }

    private boolean isFinalResult(InspectionResult result) {
        return result == InspectionResult.PROSAO
                || result == InspectionResult.NIJE_PROSAO
                || result == InspectionResult.PREKINUT
                || result == InspectionResult.NIJE_ZAPOCET;
    }
}
