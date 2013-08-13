package com.doc.jersey.loader;

import java.net.URLClassLoader;

/**
 * Dynamic load class regarding their name
 * 
 * @author Deisss
 * @version 0.1
 */
public class PartLoader {
	private static URLClassLoader loader;

	/**
	 * Set the loader used to run system
	 * @param ucl The loader
	 */
	public static void setLoader(URLClassLoader ucl) {
		loader = ucl;
	}

	/**
	 * Dynamic load a class file regarding it's name
	 * (need to load resource first)
	 * 
	 * @param name The class name to load
	 * @return The class loaded
	 */
	public static Class<?> load(String name) {
		if(loader == null) {
			return null;
		}

		try {
			return loader.loadClass(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
}
