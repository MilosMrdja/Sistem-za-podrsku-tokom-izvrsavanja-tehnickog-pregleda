package sistem_tehnicki_pregled.service.factories;

import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Component;
import sistem_tehnicki_pregled.service.listeners.DroolsDebugListener;

@Component
@RequiredArgsConstructor
public class DroolsSessionFactory {

    private final KieContainer kieContainer;

    public KieSession createSession() {
        KieSession session = kieContainer.newKieSession("InspectionKSession");
        session.addEventListener(new DroolsDebugListener());
        return session;
    }
}