package de.tud.wise.caredroid.model;

import android.content.Context;
import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.PathDataSource;

import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.PathsDataSource;

import android.content.Context;

/**
 * Die Klasse, die den gesamten Behandlungspfad beschreibt und die einzelnen Meta-Schritte dieses PFades vorhält.
 * 
 * @author Benni
 * 
 */
<<<<<<< .mine
public class Path extends MetaObject{
=======
public class Path extends MetaObject {
>>>>>>> .r71
	private Integer id;
	private String name;
	private String beginState;
	private String endState;
	private MetaStep node;

	public Path(String cpModId, Integer id, String name, String beginState, String endState, MetaStep node) {
		super(cpModId);
		this.id = id;
		this.name = name;
		this.beginState = beginState;
		this.endState = endState;
		this.node = node;
	}

	public MetaStep getNode() {
		return node;
	}

	public void setNode(MetaStep node) {
		this.node = node;
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

	public String getBeginState() {
		return beginState;
	}

	public void setBeginState(String beginState) {
		this.beginState = beginState;
	}

	public String getEndState() {
		return endState;
	}

	public void setEndState(String endState) {
		this.endState = endState;
	}

<<<<<<< .mine
	@Override
	public GeneralDataSource accept(Visitor visitor, Context context) {
		
		return visitor.visit(this, context);
		// TODO Auto-generated method stub
		
	}

=======
	public void printPath() {
		System.out.println(this.toString());
		getNode().printChildren();
	}

	@Override
	public GeneralDataSource getDataSource(Context context) {
		return new PathDataSource(context);
	}

	@Override
	public String toString() {
		return "ID: " + getId() + ", Name: " + getName() + ", BeginState: " + getBeginState() + ", EndState: " + getEndState() + ", \nNode: " + getNode().getId();
	}

>>>>>>> .r71
}
