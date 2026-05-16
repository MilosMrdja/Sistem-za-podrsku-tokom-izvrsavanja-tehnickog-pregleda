package sistem_tehnicki_pregled.service.listeners;

import org.kie.api.event.rule.*;

public class DroolsDebugListener implements AgendaEventListener {

    @Override
    public void matchCreated(MatchCreatedEvent event) {
        System.out.println("MATCH: " + event.getMatch().getRule().getName());
    }

    @Override
    public void afterMatchFired(AfterMatchFiredEvent event) {
        System.out.println("FIRED: " + event.getMatch().getRule().getName());
    }

    @Override public void matchCancelled(MatchCancelledEvent event) {}
    @Override public void beforeMatchFired(BeforeMatchFiredEvent event) {}
    @Override public void agendaGroupPopped(AgendaGroupPoppedEvent event) {}
    @Override public void agendaGroupPushed(AgendaGroupPushedEvent event) {}
    @Override public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {}
    @Override public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {}
    @Override public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {}
    @Override public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {}
}
