package com.doc.external;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Load maven to find the .m2 repository
 * 
 * @author Deisss
 * @version 0.1
 */
public class Maven {
	/**
	 * Find and extract .m2 repository
	 * 
	 * @return The repository path found
	 */
	public static String getRepository() {
		String repository = "";
		// Don't forget ending ' '
		String searchedLine = "[DEBUG] Using local repository at";

		try {
			// Launch maven in debug mode
			ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/k", "mvn -X");
			pb.redirectErrorStream(true);

			Process child = pb.start();

			InputStreamReader r = new InputStreamReader(child.getInputStream());
			BufferedReader in = new BufferedReader(r);

			String line;
			while ((line = in.readLine()) != null) {
				// Extract from line the repository
				if(line != null && line.startsWith(searchedLine)) {
					// We remove beginning of the line
					repository = line.substring(searchedLine.length());
					// Remove white space
					repository = repository.trim();

					// We don't need to continue
					in.close();
					break;
				}
			}
			in.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return repository;
	}
}
