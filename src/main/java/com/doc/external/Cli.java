package com.doc.external;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Apache CLI management
 * 
 * @author Deisss
 * @version 0.1
 */
public class Cli {
	/**
	 * Print doc help
	 */
	public static void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("jersey-doc-generator", constructOptions());
	}

	/**
	 * Build main menu system
	 * 
	 * @return The builded menu
	 */
	public static Options constructOptions() {
		final Options options = new Options();

		/*
		 * 
		 * -----------------------
		 * CORE OPTIONS
		 * -----------------------
		 * 
		 */

		// Path selector
		Option p = OptionBuilder.create("p");
		p.setLongOpt("path");
		p.setDescription("The default folder where to find project");
		p.setArgs(1);

		// Class selector
		Option c = OptionBuilder.create("c");
		c.setLongOpt("class");
		c.setDescription("The class (with package name) to parse for extracting data from");
		c.setArgs(1);

		// Type selector
		Option t = OptionBuilder.create("t");
		t.setLongOpt("type");
		t.setDescription("Project type: war, jar, or class (default is class)");
		t.setArgs(1);

		options.addOption(p);
		options.addOption(c);
		options.addOption(t);

		/*
		 * 
		 * -----------------------
		 * MISCELANEOUS OPTIONS
		 * -----------------------
		 * 
		 */
		Option h = OptionBuilder.create("h");
		h.setLongOpt("help");
		h.setDescription("Print help");

		// Path selector
		Option o = OptionBuilder.create("o");
		o.setLongOpt("out");
		o.setDescription("The file to save content, nothing = print on console");
		o.setArgs(1);

		// Class selector
		Option tmp = OptionBuilder.create("tmp");
		tmp.setDescription("The tmp folder to use (default is /tmp or C:\\ depending on OS");
		tmp.setArgs(1);

		options.addOption(h);
		options.addOption(o);
		options.addOption(tmp);


		return options;
	}
}
