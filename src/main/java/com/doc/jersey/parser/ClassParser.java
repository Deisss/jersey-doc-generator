package com.doc.jersey.parser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.doc.jersey.content.ClassContent;
import com.doc.jersey.content.MethodContent;

/**
 * Class check annotation
 * 
 * @author Deisss
 * @version 0.1
 */
public class ClassParser {
	/**
	 * Parse a class to get method from
	 * 
	 * @param cls The class to check annotation from
	 * @return The class parsed
	 */
	public static ClassContent parse(Class<?> cls) {
		ClassContent cc = new ClassContent();
		Annotation[] annotationList = cls.getAnnotations();
		boolean found = false;

		cc.setName(cls.getName());

		// Parsing class annotation
		for(Annotation annotation : annotationList) {
			found = BaseParser.complete(annotation, cc);
		}

		// Parsing method annotation
		// Try/catch: skip unknow class (important)
		try {
			Method[] methodList = cls.getMethods();
			for(Method method : methodList) {
				MethodContent mc = MethodParser.parse(method);
				if(mc != null) {
					found = true;
					cc.getMethodList().add(mc);
				}
			}
		} catch(NoClassDefFoundError e) {}

		if(!found) {
			return null;
		} else {
			return cc;
		}
	}
}
