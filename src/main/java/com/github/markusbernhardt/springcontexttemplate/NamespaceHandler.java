package com.github.markusbernhardt.springcontexttemplate;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class NamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		registerBeanDefinitionParser("import",
				new ImportBeanDefinitionParser());
	}
}