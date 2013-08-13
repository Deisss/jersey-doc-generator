package com.doc.jersey.parser;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.simplapi.jersey.doc.annotation.ApiAuthor;
import com.simplapi.jersey.doc.annotation.ApiDeprecated;
import com.simplapi.jersey.doc.annotation.ApiDoc;
import com.simplapi.jersey.doc.annotation.ApiUnimplemented;
import com.simplapi.jersey.doc.annotation.ApiVersion;

import com.doc.jersey.content.AbstractBaseContent;

/**
 * Provide a shared parsing check for both method parse, and class parse
 * 
 * @author Deisss
 * @version 0.1
 */
public class BaseParser {
	/**
	 * Default parse for both method and class
	 * 
	 * @param annotation The current annotation
	 * @param content The currently performed class result
	 */
	public static boolean complete(Annotation annotation, AbstractBaseContent content) {
		boolean found = false;

		// PATH combine - @Path
		if(annotation instanceof Path) {
			found = true;
			Path p = (Path) annotation;
			content.setPath(p.value());
		}

		// Input/output - @Produces / @ Consumes
		if(annotation instanceof Produces) {
			found = true;
			Produces pr = (Produces) annotation;
			ArrayList<String> produceList = new ArrayList<String>(Arrays.asList(pr.value()));
			content.setProduceList(produceList);
		}
		if(annotation instanceof Consumes) {
			found = true;
			Consumes cs = (Consumes) annotation;
			ArrayList<String> consumeList = new ArrayList<String>(Arrays.asList(cs.value()));
			content.setConsumeList(consumeList);
		}

		// jersey-doc-annotation - @ApiDoc
		if(annotation instanceof ApiDoc) {
			ApiDoc ad = (ApiDoc) annotation;
			content.setDoc(ad.value());
		}

		// jersey-doc-annotation - @ApiDeprecated
		// or java - @Deprecated
		if(annotation instanceof ApiDeprecated) {
			found = true;
			content.setDeprecated(true);
		} else if(annotation instanceof Deprecated) {
			content.setDeprecated(true);
		}

		// jersey-doc-annotation - @ApiUnimplemented
		if(annotation instanceof ApiUnimplemented) {
			found = true;
			content.setUnimplemented(true);
		}

		// jersey-doc-annotation - @ApiAuthor
		if(annotation instanceof ApiAuthor) {
			ApiAuthor aa = (ApiAuthor) annotation;
			content.setAuthor(aa.value());
		}

		// jersey-doc-annotation - @ApiVersion
		if(annotation instanceof ApiVersion) {
			ApiVersion av = (ApiVersion) annotation;
			content.setVersion(av.value());
		}

		return found;
	}
}
