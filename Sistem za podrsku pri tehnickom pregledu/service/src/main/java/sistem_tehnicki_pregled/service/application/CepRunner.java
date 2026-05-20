package sistem_tehnicki_pregled.service.application;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CepRunner {

    private final KieSession kieSession;

    @PostConstruct
    public void start() {
        new Thread(kieSession::fireUntilHalt).start();
    }
}