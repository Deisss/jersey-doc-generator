package com.doc.jersey.content;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Define a class parsed result
 * 
 * @author Deisss
 * @version 0.1
 */
@XmlRootElement
public class ClassContent extends AbstractBaseContent {
	private List<MethodContent> methodList;

	public ClassContent() {
		this.setMethodList(new ArrayList<MethodContent>());
	}

	public List<MethodContent> getMethodList() {
		if(methodList == null) {
			this.setMethodList(new ArrayList<MethodContent>());
		}
		return methodList;
	}

	public void setMethodList(List<MethodContent> methodList) {
		this.methodList = methodList;
	}
}
