package com.doc.jersey.loader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class JarLoader {
	private static final Class<?>[] parameters = new Class[]{URL.class};

	/**
	 * Try to load a jar as comme file
	 * @param path
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public static void load(String path) throws IOException {
		/*@SuppressWarnings("resource")
		JarFile file = new JarFile(path);*/
		new JarFile(path);
		//Enumeration<JarEntry> e = file.entries();

		URL url = new URL("jar:file:" + path + "!/");
		
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class<?> sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[]{url});
		} catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
		
		/*while (e.hasMoreElements()) {
			JarEntry je = (JarEntry) e.nextElement();
			if(je.isDirectory() || !je.getName().endsWith(".class")){
				continue;
			}
			// -6 because of .class
			String className = je.getName().substring(0,je.getName().length()-6);
			className = className.replace('/', '.');
			try {
				sysloader.loadClass(className);
			}
			catch(ClassNotFoundException ex) {}
			catch(NoClassDefFoundError ez) {}
		}*/

	}
}
