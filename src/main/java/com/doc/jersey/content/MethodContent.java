package com.doc.jersey.content;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Define a method representation
 * 
 * @author Deisss
 * @version 0.1
 */
@XmlRootElement
public class MethodContent extends AbstractBaseContent {
	// HTTP Type (GET/POST, ...)
	private String type;

	private List<AbstractEmptyContent> inputList;

	// return type name
	private AbstractEmptyContent output;

	// Indicate if the method return a subresource or not
	private boolean subResource;

	public MethodContent() {
		this.setType("");
		this.setInputList(new ArrayList<AbstractEmptyContent>());
		this.setOutput(new EmptyContent());
		this.setSubResource(false);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AbstractEmptyContent> getInputList() {
		if(inputList == null) {
			this.setInputList(new ArrayList<AbstractEmptyContent>());
		}
		return inputList;
	}

	public void setInputList(List<AbstractEmptyContent> inputList) {
		this.inputList = inputList;
	}

	public AbstractEmptyContent getOutput() {
		return output;
	}

	public void setOutput(AbstractEmptyContent output) {
		this.output = output;
	}

	public boolean isSubResource() {
		return subResource;
	}

	public void setSubResource(boolean subResource) {
		this.subResource = subResource;
	}
}
