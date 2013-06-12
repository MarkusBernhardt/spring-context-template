package com.github.markusbernhardt.springcontexttemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InvalidTemplateTest {

	@Test
	public void testInvalidTemplateNoVariable() {
		try {
			new ClassPathXmlApplicationContext(
					"invalid-template-test-no-variable.xml", this.getClass());
			fail("Exception must be thrown");
		} catch (BeanDefinitionStoreException e) {
			assertTrue(e.getCause() instanceof BeanCreationException);
		}
	}

	@Test
	public void testInvalidTemplateDuplicateVariable() {
		try {
			new ClassPathXmlApplicationContext(
					"invalid-template-test-duplicate-variable.xml", this.getClass());
			fail("Exception must be thrown");
		} catch (BeanDefinitionStoreException e) {
			assertTrue(e.getCause() instanceof BeanCreationException);
		}
	}

	@Test
	public void testInvalidTemplateMissingName() {
		try {
			new ClassPathXmlApplicationContext(
					"invalid-template-test-missing-name.xml", this.getClass());
			fail("Exception must be thrown");
		} catch (BeanDefinitionStoreException e) {
			assertTrue(e.getCause() instanceof BeanCreationException);
		}
	}

	@Test
	public void testInvalidTemplateMissingValue() {
		try {
			new ClassPathXmlApplicationContext(
					"invalid-template-test-missing-value.xml", this.getClass());
			fail("Exception must be thrown");
		} catch (BeanDefinitionStoreException e) {
			assertTrue(e.getCause() instanceof BeanCreationException);
		}
	}
}
