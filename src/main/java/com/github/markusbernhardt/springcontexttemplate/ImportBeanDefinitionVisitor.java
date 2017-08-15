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
		//((CamelBeanPostProcessor)beanDefinition).getCamelContext().getRoutes()
//		if (beanDefinition instanceof CamelBeanPostProcessor) {
//			visitDependsOn((CamelBeanPostProcessor) beanDefinition);
//		}
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
//	protected void visitRoutes(CamelBeanPostProcessor beanDefinition) {
//		List<RouteDefinition> routes = beanDefinition.getCamelContext().getRouteDefinitions();
//		if (routes == null || routes.size() == 0) {
//			return;
//		}
//
//		for (int i = 0; i < routes.size(); i++) {
//			RouteDefinition item = routes.get(i);
//			if(item.getInputs()!=null){
//				List<FromDefinition> froms = item.getInputs();
//				for (int f = 0; f < froms.size(); f++) {
//					FromDefinition from = froms.get(f);
//					if (from.getUri() != null) {
//						String uri = from.getUri();
//						if (uri != null && uri.trim().length() > 0) {
//							from.setUri(resolveStringValue(uri));
//						}
//					}
//				}
//				List<ProcessorDefinition<?>> tos = item.getOutputs();
//				for (int t = 0; t < tos.size(); t++) {
//					ProcessorDefinition<?> to = tos.get(t);
//					if (to.getOtherAttributes().containsKey("uri")) {
//						String uri =  to.att().get(new QName("uri"));
//						if (uri != null && uri.trim().length() > 0) {
//							to.getOtherAttributes().remove("uri");
//							to.getOtherAttributes().put("uri", resolveStringValue(uri));
//						}
//					}
//				}
//			}
//		}
//	}
}
