package com.doc.external;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;

import com.doc.jersey.content.ClassContent;

/**
 * Manage Jackson output
 * 
 * @author Deisss
 * @version 0.1
 */
public class Output {
	/**
	 * Print content to file
	 * 
	 * @param file The file to put content inside
	 * @param content The content to output
	 * 
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void toFile(File file, ClassContent content) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(Feature.INDENT_OUTPUT, true);
		mapper.writeValue(file, content);
	}

	/**
	 * Print content to given stream
	 * 
	 * @param stream The stream to put content
	 * @param content The content to output
	 * 
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static void toStream(OutputStream stream, ClassContent content) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure(Feature.INDENT_OUTPUT, true);
		mapper.writeValue(stream, content);
	}
}
