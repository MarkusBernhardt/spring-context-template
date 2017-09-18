package com.github.markusbernhardt.springcontexttemplate;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;

import org.junit.Test;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MissingTemplateTest {

    @Test
    public void testMissingTemplate() {
        try (ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
                "missing-template-test.xml", this.getClass())) {
            fail("Exception must be thrown");
        } catch (BeanDefinitionStoreException e) {
            assertTrue(e.getCause() instanceof FileNotFoundException);
        }
    }
}
