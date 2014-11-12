package de.tud.wise.caredroid.gateways;

import android.content.Context;

/**
 * Abstract Class to unite all Gateways.
 * 
 * @author Benni
 *
 */
public abstract class Gateway {
	protected Context context;

	public Gateway(Context context) {
		this.context = context;
	}
}
