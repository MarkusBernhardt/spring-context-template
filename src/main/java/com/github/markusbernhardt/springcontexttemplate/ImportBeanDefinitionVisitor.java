package com.github.markusbernhardt.springcontexttemplate;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.util.StringValueResolver;

public class ImportBeanDefinitionVisitor extends
		BeanDefinitionVisitor {

	/**
	 * Create a new DependsOnVisitingBeanDefinitionVisitor, applying the
	 * specified value resolver to all bean metadata values.
	 * 
	 * @param valueResolver
	 *            the StringValueResolver to apply
	 */
	public ImportBeanDefinitionVisitor(
			StringValueResolver valueResolver) {
		super(valueResolver);
	}

	/**
	 * Traverse the given BeanDefinition object and the MutablePropertyValues
	 * and ConstructorArgumentValues contained in them.
	 * 
	 * @param beanDefinition
	 *            the BeanDefinition object to traverse
	 */
	@Override
	public void visitBeanDefinition(BeanDefinition beanDefinition) {
		if (beanDefinition instanceof AbstractBeanDefinition) {
			visitDependsOn((AbstractBeanDefinition) beanDefinition);
		}
		super.visitBeanDefinition(beanDefinition);
	}

	protected void visitDependsOn(AbstractBeanDefinition beanDefinition) {
		String[] allDependsOn = beanDefinition.getDependsOn();
		if (allDependsOn == null || allDependsOn.length == 0) {
			return;
		}
		String[] allResolved = new String[allDependsOn.length];
		for (int i = 0; i < allDependsOn.length; i++) {
			allResolved[i] = resolveStringValue(allDependsOn[i]);
		}
		beanDefinition.setDependsOn(allResolved);
	}
}
