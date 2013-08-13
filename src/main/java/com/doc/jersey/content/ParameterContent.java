package com.doc.jersey.content;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Describe a parameter associated to a method
 * 
 * @author Deisss
 * @version 0.1
 */
@XmlRootElement
public class ParameterContent extends AbstractEmptyContent {
	// If it's a PathParam, QueryParam, ...
	private String context;

	private String type;

	// If there is default value associated or not
	private String defaultValue;

	public ParameterContent() {
		this.setContext("");
		this.setDefaultValue("");
	}


	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
