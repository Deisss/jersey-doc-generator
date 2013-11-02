package com.doc.external;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.doc.jersey.content.ClassContent;
import com.doc.jersey.loader.JarLoader;
import com.doc.jersey.loader.PartLoader;
import com.doc.jersey.parser.ClassParser;

/**
 * Default class start
 * 
 * @author Deisss
 * @version 0.1
 */
public class Main {
	// Temporary folder && temp folder setted by user
	private static String temporaryFolder = "default";
	private static String userFolder = null;

	// Parsing type
	private static String parsingType = "class";

	/**
	 * Generate a jar file from existing content
	 * 
	 * @param folder The folder to load
	 * @return
	 * @throws IOException
	 */
	private static String generateJar(File folder) throws IOException {
		// Setup default tmp before anything else
		if(temporaryFolder.equals("default")) {
			if (System.getProperty("os.name").startsWith("Windows")) {
				temporaryFolder = "C:/";
			} else {
				temporaryFolder = "/tmp";
			}
		}

		// Test content
		if(userFolder != null) {
			temporaryFolder = userFolder;
		}

		String output = temporaryFolder + "doc.jar";

		ProcessBuilder jar = new ProcessBuilder("jar", "cf", output, ".");
		jar.directory(folder);
		Process process = jar.start();

		InputStream is = process.getErrorStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		while ((line = br.readLine()) != null) {
		  System.out.println(line);
		}

		return output;
	}

	/**
	 * Load a given class regarding it's class name
	 * 
	 * @param file The file we are loading
	 * @param className The class name we are searching to parse
	 * @return The class loaded
	 * @throws ClassNotFoundException 
	 */
	public static Class<?> preLoader(File file, String className) {
		try {
			String jar = file.getAbsolutePath();

			// We generate a jar from that class file
			if(parsingType.equals("class")) {
				jar = generateJar(file);
			}

			// Convert File to a URL
			URL url = new URL("file:" + jar);
			URL[] urls = new URL[]{url};
			PartLoader.addLoader(new URLClassLoader(urls));
			return PartLoader.load(className);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Recursive load external jar dependencies
	 * 
	 * @param folder
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static void preLoadDependencies(String folder) throws IOException {
		File[] fileList = new File(folder).listFiles();
		List<File> directoryList = new ArrayList<File>(fileList.length);

		// Load jar and get directory
		for(File file : fileList) {
			if(file.isDirectory()) {
				directoryList.add(file);
			} else if(file.getName().endsWith(".jar")) {
				System.out.println("Loading jar : " + file.getName());
				JarLoader.load(file.getAbsolutePath());
			}
		}

		// Load sub directory search
		for(File directory : directoryList) {
			preLoadDependencies(directory.getAbsolutePath());
		}
	}

	/**
	 * 
	 * @param cmd
	 * @param root
	 * @param cls
	 * 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	public static void proceed(CommandLine cmd, File root, String cls) throws JsonGenerationException, JsonMappingException, IOException {
		Class<?> loaded = preLoader(root, cls);
		ClassContent cc = ClassParser.parse(loaded);

		// We check if it's direct output or in file output
		if(cmd.hasOption("o")) {
			Output.toFile(new File(cmd.getOptionValue("o")), cc);
		} else {
			Output.toStream(System.out, cc);
		}
	}

	/**
	 * Start function
	 * 
	 * @param args Arguments passed as parameter
	 * 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException, ParseException {
		CommandLineParser parser = new PosixParser();
		Options cmdOptions = Cli.constructOptions();
		CommandLine cmd = parser.parse(cmdOptions, args);

		if(cmd.hasOption("h")) {
			Cli.printHelp();
			System.exit(0);
		}

		// Detect user temp folder
		if(cmd.hasOption("tmp")) {
			String tmpFolder = cmd.getOptionValue("tmp");
			File testFolder = new File(tmpFolder);
			if(testFolder.exists() && testFolder.isDirectory() && testFolder.canWrite()) {
				userFolder = tmpFolder;
			}
		}

		// Detect dependencies resolve
		if(cmd.hasOption("d")) {
			String[] dependencies = cmd.getOptionValues("d");
			for(String dep : dependencies) {
				preLoadDependencies(dep);
			}
		}

		// Detect parsing type
		if(cmd.hasOption("t")) {
			String parsingValue = cmd.getOptionValue("t");
			if(parsingValue.equals("war") || parsingValue.equals("jar")) {
				parsingType = parsingValue;
			}
			// The default "class" is already setted
		}

		// Printing help in case of problem
		if(!cmd.hasOption("p") && !cmd.hasOption("c")) {
			Cli.printHelp();

		// Real application start
		} else {
			String root = cmd.getOptionValue("p");
			File file = new File(root);
			if(!file.exists()) {
				System.err.println("Error: Unable to start: the root does not exist (path: " + root + ")");
				Cli.printHelp();
			} else {
				proceed(cmd, file, cmd.getOptionValue("c"));
			}
		}

		// We delete existing class if there is
		File jar = new File(temporaryFolder + "doc.jar");
		if(jar.exists()) {
			jar.delete();
		}
	}
}
