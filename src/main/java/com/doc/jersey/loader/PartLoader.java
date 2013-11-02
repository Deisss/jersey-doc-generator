package com.doc.jersey.loader;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * Dynamic load class regarding their name
 * 
 * @author Deisss
 * @version 0.1
 */
public class PartLoader {
	private static ArrayList<URLClassLoader> loaderList;

	/**
	 * Set the loader used to run system
	 * @param ucl The loader
	 */
	public static void addLoader(URLClassLoader ucl) {
		if(loaderList == null) {
			loaderList = new ArrayList<URLClassLoader>();
		}
		loaderList.add(ucl);
	}

	/**
	 * Dynamic load a class file regarding it's name
	 * (need to load resource first)
	 * 
	 * @param name The class name to load
	 * @return The class loaded
	 */
	public static Class<?> load(String name) {
		if(loaderList == null) {
			return null;
		}

		String error = "";
		boolean found = false;

		for(URLClassLoader loader : loaderList) {
			try {
				Class<?> cls = loader.loadClass(name);
				found = true;
				return cls;
			} catch (ClassNotFoundException e) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				e.printStackTrace(pw);
				error = sw.toString();
			}
		}

		if(!found) {
			System.err.println(error);
		}

		return null;
	}
}
