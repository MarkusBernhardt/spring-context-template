package com.github.markusbernhardt.springcontexttemplate;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertyResolver;
import org.springframework.util.StringValueResolver;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.UUID;

public class ImportBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private final String propPrefixReplacement="----";
    private final String propPostfixReplacement="____";
	/**
	 * Central template method to actually parse the supplied {@link Element}
	 * into one or more {@link BeanDefinition BeanDefinitions}.
	 * 
	 * @param element
	 *            the element that is to be parsed into one or more
	 *            {@link BeanDefinition BeanDefinitions}
	 * @param parserContext
	 *            the object encapsulating the current state of the parsing
	 *            process; provides access to a
	 *            {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}
	 * @return the primary {@link BeanDefinition} resulting from the parsing of
	 *         the supplied {@link Element}
	 * @see #parse(org.w3c.dom.Element, ParserContext)
	 * @see #postProcessComponentDefinition(org.springframework.beans.factory.parsing.BeanComponentDefinition)
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		Map<String, String> variables = getVariables(element);
		ConfigurableEnvironment environment = (ConfigurableEnvironment) parserContext.getDelegate().getEnvironment();
		StringValueResolver resolver = new ImportStringValueResolver(variables, environment);
		BeanDefinitionVisitor visitor = new ImportBeanDefinitionVisitor(resolver);
		Map<String, Tuple<BeanDefinition,Boolean>> beanDefinitions = loadBeanDefinitions(element, visitor, resolver);
		registerBeans(element, parserContext, beanDefinitions);
		return null;
	}

	/**
	 * Return a map of all defined variables
	 * 
	 * @param element
	 *            the import XML DOM element
	 * @return the created map
	 * @throws BeanCreationException
	 *             in case of specifying no, ambiguous or invalid variables
	 */
	protected Map<String, String> getVariables(Element element) {
		Map<String, String> map = new HashMap<String, String>();

		String attributeName = element.getAttribute("name");
		String attributeValue = element.getAttribute("value");
		if (attributeName.length() != 0 || attributeValue.length() != 0) {
			if (attributeName.length() == 0) {
				throw new BeanCreationException("Missing attribute 'name'");
			}
			if (attributeValue.length() == 0) {
				throw new BeanCreationException("Missing attribute 'value'");
			}
			map.put(attributeName, attributeValue);
		}

		List<Element> elements = DomUtils.getChildElementsByTagName(element, "variable");
		for (Element variable : elements) {
			attributeName = variable.getAttribute("name");
			attributeValue = variable.getAttribute("value");
			if (map.containsKey(attributeName)) {
				throw new BeanCreationException(String.format("Ambiguous declaration of varaible '%s'", attributeName));
			}
			map.put(attributeName, attributeValue);
		}

		if (map.size() == 0) {
			throw new BeanCreationException("No variable defined");
		}
		return map;
	}

	/**
	 * Load bean definitions from the specified resource.
	 * 
	 * @param element
	 *            the import XML DOM element containing the resource
	 * @param visitor
	 *            the visitor to resolve the template variables
	 * @param valueResolver
	 *            the resolver to resolve the bean names
	 * @return the loaded and resolved beans
	 * @throws BeanCreationException
	 *             in case of loading non template beans
	 * @throws BeanDefinitionStoreException
	 *             in case of loading or parsing errors
	 */
	protected Map<String, Tuple<BeanDefinition,Boolean>> loadBeanDefinitions(Element element, BeanDefinitionVisitor visitor,
			StringValueResolver valueResolverIn) {
		ImportStringValueResolver valueResolver = (ImportStringValueResolver)valueResolverIn;
		// load bean definitions to the registry
		BeanDefinitionRegistry registry = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(registry);
		String resource = element.getAttribute("resource");
        PropertyResolver propertyResolver = valueResolver.getPropertyResolver();
		resource = propertyResolver.resolvePlaceholders(resource);
		reader.loadBeanDefinitions(resource);

		// resolve bean names
		Map<String, Tuple<BeanDefinition,Boolean>> beans = new HashMap<String, Tuple<BeanDefinition,Boolean>>();
		for (String beanNameIteration : registry.getBeanDefinitionNames()) {
		    BeanDefinition beanDefinition = registry.getBeanDefinition(beanNameIteration);
            String beanName = beanNameIteration
                            .replace(propPrefixReplacement,"${")
                            .replace(propPostfixReplacement,"}");
			visitor.visitBeanDefinition(beanDefinition);

			String resolvedBeanName = valueResolver.resolveStringValue(beanName);
            boolean isAnonymous = false;
			if (resolvedBeanName.matches("^.*#\\d+$")) {
				/**
				 * If it's anonymous bean, then generate unique name for it.
				 */
//				resolvedBeanName += "--" + UUID.randomUUID().toString();
                isAnonymous=true;
			}
            /**
             * Now anonymous beans are imported only once
             */
//			if (resolvedBeanName.equals(beanName)) {
//				throw new BeanCreationException(String.format("The bean '%s' is not a template", beanName));
//			}
			beans.put(resolvedBeanName,new Tuple<BeanDefinition,Boolean>(beanDefinition,isAnonymous) );
		}
		return beans;
	}

	/**
	 * Register the given beans to the parser context
	 * 
	 * @param element
	 *            the import XML DOM element
	 * @param parserContext
	 *            the parser context
	 * @param beanDefinitions
	 *            the loaded bean definitions
	 */
	protected void registerBeans(Element element, ParserContext parserContext,
                                 Map<String, Tuple<BeanDefinition,Boolean>> beanDefinitions) {
		CompositeComponentDefinition compositeDef = new CompositeComponentDefinition(element.getTagName(),
				parserContext.extractSource(element));
		parserContext.pushContainingComponent(compositeDef);

        BeanDefinitionRegistry registry =  parserContext.getRegistry();
		for (Map.Entry<String, Tuple<BeanDefinition,Boolean>> entry : beanDefinitions.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition bean =entry.getValue().getKey();
            if(!registry.containsBeanDefinition(beanName)) {
                registry.registerBeanDefinition(beanName, bean);
                parserContext.registerComponent(new BeanComponentDefinition(bean, beanName));
            }
		}

		parserContext.popAndRegisterContainingComponent();
	}
}