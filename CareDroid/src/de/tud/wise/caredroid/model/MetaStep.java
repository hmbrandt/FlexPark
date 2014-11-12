package de.tud.wise.caredroid.model;

import java.util.ArrayList;

/**
 * Die Klasse, die alle Teile eines Pfades beschreibt, unabhängig ihres Typs. Fungiert als Superklasse für diese Typen
 * gibt jedem MetaStep Objekt die Fähigkeit eigene Kinder zu speichern.
 * 
 * @author Benni
 * 
 */
public abstract class MetaStep extends MetaObject {
	private Integer id;
	private String name;
	private final ArrayList<MetaStep> children;

	/**
	 * Standard Konstruktor. Setzt die nötigen Parameter
	 * @param pre Vorgänger des Schritts
	 * @param id Id des Schritts
	 * @param name Name des Schritts
	 */
	public MetaStep(String cpModId, Integer id, String name) {
		super(cpModId);
		this.id = id;
		this.name = name;
		this.children = new ArrayList<MetaStep>();
	}

	/**
	 * Gibt alle angehängten Elemente dieses Knotens zurück
	 * 
	 * @return Gibt eine ArrayList von MetaSteps zurück. Müssen gecastet werden!
	 */
	public ArrayList<MetaStep> getChildren() {
		return children;
	}

	/**
	 * Fügt ein neues Kind hinzu.
	 * 
	 * @param child Ein MetaStep Objekt
	 * @return true wenn das Hinzufügen erfolgreich war, false wenn nicht
	 */
	public void addChildren(MetaStep child) {
		children.add(child);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void printChildren() {
		System.out.println(this);
		if (this instanceof VariableFlow) {
			VariableFlow flow = (VariableFlow)this;
			for (Step part : flow.getParts()) {
				System.out.print("Part of ID" + flow.getId() + ":");
				part.printChildren();
			}
		}
		if(!getChildren().isEmpty()){
			for (MetaStep child : getChildren()) {
				System.out.print("Child of ID" + this.getId() + ":");
				child.printChildren();
			}
		}
	}

}
