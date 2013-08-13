package com.doc.jersey.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import com.doc.jersey.content.ClassContent;
import com.doc.jersey.content.EmptyContent;
import com.doc.jersey.content.MethodContent;
import com.doc.jersey.content.ParameterContent;

/**
 * Method check annotation
 * 
 * @author Deisss
 * @version 0.1
 */
public class MethodParser {
	/**
	 * Parse a method to get annotation from
	 * 
	 * @param method
	 * @return The method content parsed
	 */
	public static MethodContent parse(Method method) {
		MethodContent mc = new MethodContent();
		Annotation[] annotationMethodList = method.getDeclaredAnnotations();
		boolean found = false;

		mc.setName(method.getName());

		for(Annotation annotationMethod : annotationMethodList) {

			// HTTP Verb check
			if(
					annotationMethod instanceof GET     ||
					annotationMethod instanceof POST    ||
					annotationMethod instanceof PUT     ||
					annotationMethod instanceof DELETE  ||
					annotationMethod instanceof HEAD    ||
					annotationMethod instanceof OPTIONS
			) {
				found = true;
				mc.setType(annotationMethod.annotationType().getName());
			}

			// HTTP Verb check (another way)
			if(annotationMethod instanceof HttpMethod) {
				found = true;
				HttpMethod hm = (HttpMethod) annotationMethod;
				String value = hm.value();
				if(value != null) {
					value = value.toUpperCase();
				}
				mc.setType("javax.ws.rs." + value);
			}

			// Complete already parsed annotation
			boolean baseFound = BaseParser.complete(annotationMethod, mc);
			if(baseFound) {
				found = true;
			}
		}

		// With the return type, we try to check if it's a sub resource or not
		if(found) {
			/*
			 * 
			 * PARSING INPUT TYPE(S)
			 * 
			 */
			Class<?>[] parameterList = method.getParameterTypes();
			Annotation[][] annotationParameterList = method.getParameterAnnotations();
			int parameterLength = parameterList.length;
			if(parameterLength > 0) {
				for(int i=0; i<parameterLength; ++i) {
					ParameterContent pctmp = ParameterParser.parse(parameterList[i], annotationParameterList[i]);
					if(pctmp != null) {
						mc.getInputList().add(pctmp);
					}
				}
			}

			/*
			 * 
			 * PARSING RETURN TYPE
			 * 
			 */
			Class<?> rt = method.getReturnType();
			// If it's not void type return, or not a basic "Response" type
			if(rt != null && !rt.equals(Void.TYPE)) {
				ClassContent cc = ClassParser.parse(rt);
				if(cc != null) {
					found = true;
					mc.setSubResource(true);
					mc.setOutput(cc);
				} else {
					EmptyContent ec = new EmptyContent();
					ec.setName(rt.getName());
					mc.setOutput(ec);
				}
			}
		}

		if(!found) {
			return null;
		} else {
			return mc;
		}
	}
}
