package de.tud.wise.caredroid.model;

import java.util.ArrayList;

import android.content.Context;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.StepDataSource;

/**
 * Die Klasse, die den Meta-Schritt "Schritt" beschreibt. Schritte sind Teil eines Pfades, entweder innerhalb eines Variable Flow oder alleine.
 * 
 * @author Benni
 * 
 */
public class Step extends MetaStep {
	private Role role;
	private String proofIndicator;
	private String type;
	private final ArrayList<Document> producedDocuments;

	/**
	 * Innere Builderklasse, die optionale und notwendige Parameter empfängt und dann dynamisch zusammensetzt
	 * 
	 * @author Benni
	 *
	 */
	public static class Builder {
		private final String cpModId;
		private final Integer id;
		private final String name;

		private Role role = null;
		private String proofIndicator = null;
		private String type = null;

		public Builder(String cpModId, Integer id, String name) {
			this.cpModId = cpModId;
			this.id = id;
			this.name = name;
		}

		public Builder role(Role val) {
			role = val;
			return this;
		}

		public Builder proofIndicator(String val) {
			proofIndicator = val;
			return this;
		}

		public Builder type(String val) {
			type = val;
			return this;
		}

		public Step build() {
			return new Step(this);
		}

	}

	/**
	 * Konstruktor ruft einen Builder auf, der optionale Parameter zur Verfügung stellt und notwendige Parameter einfordert
	 * 
	 * Schema: Step step1 = new Step.Builder(NötigerVorgänger, NötigeID, NötigerSchritt).parentFlow(optionalerVariableFlow).type(optionalerTyp).role(optionaleRolle).proofIndicator(optionalerIndikator).build();
	 * Beispiel: Step step1 = new Step.Builder(otherStepObj, 3, "NameDesSchritts").variableFlow(someVariableFlowObj).type("TypDesSchritts").build();
	 * 
	 */
	private Step(Builder builder) {
		super(builder.cpModId, builder.id, builder.name);
		this.role = builder.role;
		this.proofIndicator = builder.proofIndicator;
		this.producedDocuments = new ArrayList<Document>();
		this.type = builder.type;
	}

	public ArrayList<Document> getProducedDocuments() {
		return producedDocuments;
	}

	/**
	 * Fügt Dokumente zur Dokumentensammlung des Schritts hinzu
	 * 
	 * @param doc ein Dokument-Objekt
	 * @return true wenn erfolgreich, false wenn nicht
	 */
	public void addProducedDocument(Document doc) {
		producedDocuments.add(doc);
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getProofIndicator() {
		return proofIndicator;
	}

	public void setProofIndicator(String proofIndicator) {
		this.proofIndicator = proofIndicator;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new StepDataSource(context);
	}

	@Override
	public String toString() {
		return "ID: " + getId() + " Name: " + getName() + " Role: " + getRole() + " ProofIndicator: " + getProofIndicator() + " Type: " + getType();
	}
}
