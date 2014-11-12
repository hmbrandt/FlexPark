package de.tud.wise.caredroid;

<<<<<<< .mine
import java.util.List;

import de.tud.wise.caredroid.db.GeneralDataSource;
import de.tud.wise.caredroid.db.PathsDataSource;
import de.tud.wise.caredroid.model.MetaObject;
import de.tud.wise.caredroid.model.Path;
=======
import android.app.Activity;
>>>>>>> .r71
import android.os.Bundle;
import android.view.Menu;
import de.tud.wise.caredroid.model.Case;
import de.tud.wise.caredroid.model.Path;
import de.tud.wise.caredroid.model.Patient;
import de.tud.wise.caredroid.test.BenniHelper;

/**
 * zentrale Aktivit‰t und Start-Aktivit‰t
 * @author Benni
 *
 */
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

<<<<<<< .mine
		GeneralDataSource<Path> source = new PathsDataSource(this);
=======
		//		Cubetto BeispielPfad als Objekt		//
		//////////////////////////////////////////////
>>>>>>> .r71

<<<<<<< .mine
		source.open();
		Log.d("Insert: ", "Inserting ..");
		source.add(new Path(null, "Zahn1", "krank", "gesund"));
		source.add(new Path(null, "Zahn2", "krank", "gesund"));
		source.add(new Path(null, "Zahn3", "krank", "gesund"));
		source.add(new Path(null, "Zahn4", "krank", "gesund"));
=======
		BenniHelper help = new BenniHelper();
>>>>>>> .r71

<<<<<<< .mine
		Log.d("Reading: ", "Reading all paths..");
		List<Path> paths = source.getAll();
=======
		Path cubettoPath = help.getCubettoExamplePath();
>>>>>>> .r71

		Patient pat = new Patient(null, null, "CubettoPatient", "Bonnerstraﬂe 52, 01823 Dresden", "038738123");

		new Case(null, null, "Cubetto Zahnbeschwerden 1", cubettoPath, pat);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
