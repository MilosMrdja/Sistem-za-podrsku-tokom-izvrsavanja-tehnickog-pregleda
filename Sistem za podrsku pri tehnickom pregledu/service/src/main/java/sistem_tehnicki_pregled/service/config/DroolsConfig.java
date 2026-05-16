package sistem_tehnicki_pregled.service.config;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.StringReader;
import java.util.Collections;

/**
 * Spring configuration for the Drools KieContainer.
 *
 * The kjar module must be on the classpath (e.g. as a Maven dependency).
 * kmodule.xml inside the kjar declares the KieBase and KieSession named here.
 *
 * Expected kmodule.xml layout:
 * <pre>
 * &lt;kmodule xmlns="http://www.drools.org/xsd/kmodule"&gt;
 *   &lt;kbase name="InspectionKBase" packages="rules"&gt;
 *     &lt;ksession name="InspectionKSession"/&gt;
 *   &lt;/kbase&gt;
 * &lt;/kmodule&gt;
 * </pre>
 */
@Configuration
public class DroolsConfig {

    @Bean
    public KieContainer kieContainer() {
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        KieModuleModel kmoduleModel = ks.newKieModuleModel();

        kmoduleModel.newKieBaseModel("InspectionKBase")
                .addPackage("rules")
                .setDefault(true)
                .newKieSessionModel("InspectionKSession")
                .setDefault(true);

        kfs.writeKModuleXML(kmoduleModel.toXML());

        InputStream excel = getClass().getResourceAsStream("/rules/templateable/template-inspection-issue.xlsx");
        InputStream dtable = getClass().getResourceAsStream("/rules/templateable/template-inspection-issue.drt");

        ExternalSpreadsheetCompiler compiler = new ExternalSpreadsheetCompiler();
        String drl = compiler.compile(excel, dtable, 2, 1);
        System.out.println("==== DRL START ====");
        System.out.println(drl);
        System.out.println("==== DRL END ====");
        kfs.write("src/main/resources/rules/dynamic.drl",
                ks.getResources().newReaderResource(new StringReader(drl)));

        kfs.write("src/main/resources/rules/vehicle-identification.drl",
                ks.getResources().newClassPathResource("rules/vehicle-identification.drl"));

        kfs.write("src/main/resources/rules/pre-inspection-conditions.drl",
                ks.getResources().newClassPathResource("rules/pre-inspection-conditions.drl"));

        kfs.write("src/main/resources/rules/brake-fluid.drl",
                ks.getResources().newClassPathResource("rules/facts/brake-fluid.drl"));

        kfs.write("src/main/resources/rules/exhaust-system.drl",
                ks.getResources().newClassPathResource("rules/facts/exhaust-system.drl"));

        kfs.write("src/main/resources/rules/chassis-suspension.drl",
                ks.getResources().newClassPathResource("rules/facts/chassis-suspension.drl"));

        kfs.write("src/main/resources/rules/electrical-system.drl",
                ks.getResources().newClassPathResource("rules/facts/electrical-system.drl"));

        kfs.write("src/main/resources/rules/engine-system.drl",
                ks.getResources().newClassPathResource("rules/facts/engine-system.drl"));

        kfs.write("src/main/resources/rules/lighting-system.drl",
                ks.getResources().newClassPathResource("rules/facts/lighting-system.drl"));

        kfs.write("src/main/resources/rules/mandatory-equipment.drl",
                ks.getResources().newClassPathResource("rules/facts/mandatory-equipment.drl"));

        kfs.write("src/main/resources/rules/wheels-tyres.drl",
                ks.getResources().newClassPathResource("rules/facts/wheels-tyres.drl"));

        kfs.write("src/main/resources/rules/system-status.drl",
                ks.getResources().newClassPathResource("rules/system-status.drl"));

        kfs.write("src/main/resources/rules/final-decision.drl",
                ks.getResources().newClassPathResource("rules/final-decision.drl"));

        KieBuilder builder = ks.newKieBuilder(kfs).buildAll();

        KieContainer container = ks.newKieContainer(builder.getKieModule().getReleaseId());
        KieBase base = container.getKieBase();
        for (KiePackage kp : base.getKiePackages()) {
            System.out.println("PACKAGE: " + kp.getName());
            for (Rule r : kp.getRules()) {
                System.out.println("RULE: " + r.getName());
            }
        }
        return container;
    }
}
