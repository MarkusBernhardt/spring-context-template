package com.github.markusbernhardt.springcontexttemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IllegalTemplateTest {

	@Test
	public void testMissingTemplate() {
		try {
			new ClassPathXmlApplicationContext("illegal-template-test.xml",
					this.getClass());
			fail("Exception must be thrown");
		} catch (BeansException e) {
			assertTrue(e.getCause() instanceof BeanCreationException);
		}
	}
}
