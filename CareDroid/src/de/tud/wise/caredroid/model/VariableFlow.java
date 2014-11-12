package de.tud.wise.caredroid.model;

import java.util.ArrayList;

import android.content.Context;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.VariableFlowDataSource;

/**
 * Die Klasse, die den Meta-Schritt "Variable-Flow" beschreibt. Ein VariableFlow ist Teil eines Pfades. Er kann einzelne Schritte beinhalten.
 * 
 * @author Benni
 * 
 */
public class VariableFlow extends MetaStep {
	private Operator operator;
	private ArrayList<Step> parts;

	public static class Builder {
		private final String cpModId;
		private final Integer id;
		private final String name;

		private Operator operator = null;

		public Builder(String cpModId, Integer id, String name) {
			this.cpModId = cpModId;
			this.id = id;
			this.name = name;
		}

		public Builder operator(Operator val) {
			operator = val;
			return this;
		}

		public VariableFlow build() {
			return new VariableFlow(this);
		}
	}

	/**
	 * Konstruktor ruft einen Builder auf, der optionale Parameter zur Verfügung stellt und notwendige Parameter einfordert
	 * 
	 * Schema: VariableFlow des1 = new Decision.Builder(NötigerVorgänger, NötigeID, NötigerSchritt).operator(optionalerOperator).build();
	 * Beispiel: VariableFlow des1 = new Decision.Builder(otherStepObj, 3, "NameDesSchritts").operator(anotherOperator).build();
	 * 
	 */
	public VariableFlow(Builder builder) {
		super(builder.cpModId, builder.id, builder.name);
		this.operator = builder.operator;
		parts = new ArrayList<Step>();
	}

	public Operator getOperator() {
		return operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	/**
	 * Enumeration mit möglichen Operatoren für VariableFlows
	 * @author Benni
	 *
	 */
	public enum Operator {
		XOR("XOR"), AND("AND"), ORSTAR("ORSTAR"), ORPLUS("ORPLUS"), PARALLEL("PARALLEL");

		private final String s;

		private Operator(String s) {
			this.s = s;
		}

		@Override
		public String toString() {
			return s;
		}
	}

	public void addPart(Step s) {
		parts.add(s);
	}

	public ArrayList<Step> getParts() {
		return parts;
	}

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new VariableFlowDataSource(context);
	}

	@Override
	public String toString() {
		return "ID: " + getId() + " Name: " + getName() + " Operator: " + getOperator();
	}
}
