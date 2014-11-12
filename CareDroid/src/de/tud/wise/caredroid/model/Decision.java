package de.tud.wise.caredroid.model;

import java.util.ArrayList;

import android.content.Context;
import de.tud.wise.caredroid.db.DecisionDataSource;
import de.tud.wise.caredroid.db.GeneralDataSource;

/**
 * Die Klasse, die den Meta-Schritt Typ "Entscheidung" beschreibt. Entscheidungen sind Teil eines Pfades.
 * 
 * @author Benni
 * 
 */
public class Decision extends MetaStep {
	private Role role;
	private String proofIndicator;
	private final ArrayList<Document> relevantDocuments;

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

		private String proofIndicator = null;
		private Role role = null;

		public Builder(String cpModId, Integer id, String name) {
			this.cpModId = cpModId;
			this.id = id;
			this.name = name;
		}

		public Builder proofIndicator(String val) {
			proofIndicator = val;
			return this;
		}

		public Builder role(Role val) {
			role = val;
			return this;
		}

		public Decision build() {
			return new Decision(this);
		}
	}

	/**
	 * Konstruktor ruft einen Builder auf, der optionale Parameter zur Verfügung stellt und notwendige Parameter einfordert
	 * 
	 * Schema: Decision des1 = new Decision.Builder(NötigerVorgänger, NötigeID, NötigerSchritt).role(optionaleRolle).build();
	 * Beispiel: Decision des1 = new Decision.Builder(otherStepObj, 3, "NameDesSchritts").role(anotherRoleObj).build();
	 * 
	 */
	public Decision(Builder builder) {
		super(builder.cpModId, builder.id, builder.name);
		this.setProofIndicator(builder.proofIndicator);
		this.role = builder.role;
		this.relevantDocuments = new ArrayList<Document>();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ArrayList<Document> getRelevantDocuments() {
		return relevantDocuments;
	}

	/**
	 * Fügt Dokumente zur Dokumentensammlung des Schritts hinzu
	 * 
	 * @param doc ein Dokument-Objekt
	 * @return true wenn erfolgreich, false wenn nicht
	 */
	public boolean addRelevantDocument(Document doc) {
		if (relevantDocuments.add(doc)) {
			return true;
		}
		return false;
	}

	public String getProofIndicator() {
		return proofIndicator;
	}

	public void setProofIndicator(String proofIndicator) {
		this.proofIndicator = proofIndicator;
	}

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new DecisionDataSource(context);
	}

	@Override
	public String toString() {
		return "ID: " + getId() + " Name: " + getName() + " Role: " + getRole();
	}

}
