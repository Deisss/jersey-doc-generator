package com.doc.jersey.content;

/**
 * Most basic content
 * 
 * @author Deisss
 * @version 0.1
 */
public abstract class AbstractEmptyContent {
	private String name;

	public AbstractEmptyContent() {
		this.setName("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
