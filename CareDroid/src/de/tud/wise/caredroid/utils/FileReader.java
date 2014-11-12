package de.tud.wise.caredroid.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import android.app.Activity;
import android.content.res.AssetManager;

/**
 * 
 * @author Mario
 *
 */
public class FileReader {

	/**
	 * A method that reads an asset located in the assets Folder of this app
	 * 
	 * @param activity The main activity of the app
	 * @param path The path to the file, within in the assets folder
	 * @return the inputstream
	 */
	public InputStream readAsset(Activity activity, String path) {

		InputStream inputStream = null;

		AssetManager asset = activity.getAssets();

		try {
			inputStream = asset.open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return inputStream;
	}

	/**
	 * Read an input stream and return its content as a string
	 * 
	 * @param inputStream The input stream that will be read
	 * @return The content
	 */
	public String convertStreamToString(InputStream inputStream) {

		Scanner scanner = new Scanner(inputStream);
		scanner.useDelimiter("\\A");
		String content = scanner.hasNext() ? scanner.next() : "";
		scanner.close();

		return content;
	}

}
