package com.doc.jersey.parser;

import java.lang.annotation.Annotation;

import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import com.doc.jersey.content.ParameterContent;

/**
 * Parameter check annotation
 * 
 * @author Deisss
 * @version 0.1
 */
public class ParameterParser {
	/**
	 * Parse a paramater with their jersey annotation
	 * 
	 * @param parameter The current parameter
	 * @param annotationList The associated annotation
	 * @return
	 */
	public static ParameterContent parse(Class<?> parameter, Annotation[] annotationList) {
		ParameterContent pc = new ParameterContent();

		pc.setType(parameter.getName());

		for(Annotation annotation : annotationList) {
			// Jersey - @DefaultValue
			if(annotation instanceof DefaultValue) {
				DefaultValue dv = (DefaultValue) annotation;
				pc.setDefaultValue(dv.value());
			}

			// Jersey - @CookieParam
			if(annotation instanceof CookieParam) {
				CookieParam cp = (CookieParam) annotation;
				pc.setContext(cp.annotationType().getName());
				pc.setName(cp.value());
			}

			// Jersey - @FormParam
			if(annotation instanceof FormParam) {
				FormParam fp = (FormParam) annotation;
				pc.setContext(fp.annotationType().getName());
				pc.setName(fp.value());
			}

			// Jersey - @HeaderParam
			if(annotation instanceof HeaderParam) {
				HeaderParam hp = (HeaderParam) annotation;
				pc.setContext(hp.annotationType().getName());
				pc.setName(hp.value());
			}

			// Jersey - @MatrixParam
			if(annotation instanceof MatrixParam) {
				MatrixParam mp = (MatrixParam) annotation;
				pc.setContext(mp.annotationType().getName());
				pc.setName(mp.value());
			}

			// Jersey - @PathParam
			if(annotation instanceof PathParam) {
				PathParam pp = (PathParam) annotation;
				pc.setContext(pp.annotationType().getName());
				pc.setName(pp.value());
			}

			// Jersey - @QueryParam
			if(annotation instanceof QueryParam) {
				QueryParam qp = (QueryParam) annotation;
				pc.setContext(qp.annotationType().getName());
				pc.setName(qp.value());
			}
		}

		return pc;
	}
}
