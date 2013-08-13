package com.doc.jersey.content;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract default class content
 * 
 * @author Deisss
 * @version 0.1
 */
public abstract class AbstractBaseContent extends AbstractEmptyContent {
	private String path;

	// @ApiDoc
	private String doc;

	// @ApiAuthor
	private String author;

	// @ApiVersion
	private String version;
	
	private List<String> produceList;
	private List<String> consumeList;

	// @ApiDeprecated
	private boolean isDeprecated;
	// @ApiUnimplemented
	private boolean isUnimplemented;

	public AbstractBaseContent() {
		this.setPath("");
		this.setDoc("");
		this.setAuthor("");
		this.setVersion("");
		this.setProduceList(new ArrayList<String>());
		this.setConsumeList(new ArrayList<String>());
		this.setDeprecated(false);
		this.setUnimplemented(false);
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<String> getProduceList() {
		if(produceList == null) {
			this.setProduceList(new ArrayList<String>());
		}
		return produceList;
	}

	public void setProduceList(List<String> produceList) {
		this.produceList = produceList;
	}

	public List<String> getConsumeList() {
		if(consumeList == null) {
			this.setConsumeList(new ArrayList<String>());
		}
		return consumeList;
	}

	public void setConsumeList(List<String> consumeList) {
		this.consumeList = consumeList;
	}

	public boolean isDeprecated() {
		return isDeprecated;
	}

	public void setDeprecated(boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}

	public boolean isUnimplemented() {
		return isUnimplemented;
	}

	public void setUnimplemented(boolean isUnimplemented) {
		this.isUnimplemented = isUnimplemented;
	}
}
