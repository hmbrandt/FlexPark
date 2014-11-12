package de.tud.wise.caredroid.test;

import de.tud.wise.caredroid.model.Decision;
import de.tud.wise.caredroid.model.Path;
import de.tud.wise.caredroid.model.Role;
import de.tud.wise.caredroid.model.Step;
import de.tud.wise.caredroid.model.VariableFlow;

public class BenniHelper {

	public Path getCubettoExamplePath() {

		//roles
		Role doctor = new Role("r1", null, "Zahnarzt");
		Role helper = new Role("r2", null, "Zahnarzthelfer/in");
		Role chrirurg = new Role("r3", null, "Kieferchirurg");
		Role anes = new Role("r4", null, "Anästhesist");

		//steps
		Step s1 = new Step.Builder("s1", null, "Anamnese").proofIndicator("A").type("Diagnose").role(doctor).build();
		Step s2 = new Step.Builder("s2", null, "Röntgenuntersuchung").proofIndicator("B").type("Diagnose").role(helper).build();
		Step s3 = new Step.Builder("s3", null, "Computertomographie").type("Diagnose").role(doctor).build();
		Step s4 = new Step.Builder("s4", null, "Biopsie").type("Diagnose").role(helper).build();
		Step s5 = new Step.Builder("s5", null, "Antibiotika-Therapie").type("Therapie").role(helper).build();
		Step s6 = new Step.Builder("s6", null, "Chirurgische Entfernung").type("Therapie").role(chrirurg).build();
		Step s7 = new Step.Builder("s7", null, "Anästhesie").type("Support").role(anes).build();
		Step s8 = new Step.Builder("s8", null, "Blutbild erstellen").type("Diagnose").role(helper).build();
		Step s9 = new Step.Builder("s9", null, "Entlassung").type("Support").role(doctor).build();
		Step s10 = new Step.Builder("s10", null, "Medikation").type("Therapie").role(helper).build();

		//Decisions
		Decision d1 = new Decision.Builder("d1", null, "Therapieentscheidung").proofIndicator("A").role(doctor).build();
		Decision d2 = new Decision.Builder("d2", null, "Medikationsentscheidung").proofIndicator("C").role(doctor).build();

		//flows
		VariableFlow v1 = new VariableFlow.Builder("v1", null, "Obligatorische Untersuchungen").operator(VariableFlow.Operator.AND).build();
		VariableFlow v2 = new VariableFlow.Builder("v2", null, "Mögliche Zusatzuntersuchungen").operator(VariableFlow.Operator.ORPLUS).build();
		VariableFlow v3 = new VariableFlow.Builder("v3", null, "Chirurgischer Eingriff").operator(VariableFlow.Operator.PARALLEL).build();

		//Flow Connections
		v1.addPart(s1);
		v1.addPart(s2);
		v2.addPart(s3);
		v2.addPart(s4);
		v3.addPart(s6);
		v3.addPart(s7);

		//Connections
		v1.addChildren(v2);
		v2.addChildren(d1);
		d1.addChildren(s5);
		d1.addChildren(v3);
		v3.addChildren(s8);
		s5.addChildren(s9);
		s8.addChildren(d2);
		d2.addChildren(s9);
		d2.addChildren(s10);

		//Path
		return new Path(null, null, "Chirurgische Entfernung von Weisheitszähnen", "Patient mit Zahnbeschwerden", "Patient entlassen", v1);
	}
}
