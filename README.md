spring-context-template
=======================

This library provides a templating mechanism for creating XML-based Spring context definitions. It is heavily based on the 
[spring-import-template](https://github.com/hikage/spring-import-template/blob/master/pom.xml) library by Gildas Cuisinier.

Introduction
------------

We are, especially when using spring-batch, very often defining a large number of very similar beans in the spring
context. This violates the DRY-principle and makes maintenance sometimes a nightmare. The spring-context-tmeplate library
helps to avoid most of the boilerplate code:

*context-reader.xml*
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:template="http://www.github.bom/markusbernhardt/schema/context-template"
	xsi:schemaLocation="
		http://www.springframework.org/schema/beans
		http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.github.bom/markusbernhardt/schema/context-template
		http://www.github.bom/markusbernhardt/schema/context-template/spring-context-template.xsd">

	<template:import resource="classpath:context-reader-template.xml" 
	    name="record-type" value="person" />
	    
	<template:import resource="classpath:context-reader-template.xml" 
	    name="record-type" value="address" />
	    
	<template:import resource="classpath:context-reader-template.xml" 
	    name="record-type" value="contract" />

</beans>
```

*context-reader-template.xml*
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
	    http://www.springframework.org/schema/beans
	    http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- template -->
	<bean id="${record-type}Reader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="${record-type}LineMapper"/>
	    <property name="resource" value="${${record-type}-input-file}"/>
	</bean>
	
	<bean id="${record-type}LineMapper" class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
        <property name="lineTokenizer" ref="${record-type}LineTokenizer"/>
        <property name="fieldSetMapper" ref="${record-type}FieldSetMapper"/>
	</bean>
        
    <bean id="${record-type}LineTokenizer" class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer">
	    <property name="delimiter" value="${${record-type}-input-file-delimiter}"/>
	    <property name="names" value="${${record-type}-input-file-field-names}"/>
	</bean>
    
    <bean id="${record-type}FieldSetMapper" class="org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper">
	    <property name="targetType" value="${${record-type}-bean-type}"/>
    </bean>
            
</beans>
```

Without this library you would have to define all beans by hand:

*context-reader.xml*
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="
	    http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- person -->
	<bean id="personReader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="personLineMapper"/>
	    <property name="resource" value="${person-input-file}"/>
	</bean>
	
	<bean id="personLineMapper" class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
        <property name="lineTokenizer" ref="personLineTokenizer"/>
        <property name="fieldSetMapper" ref="personFieldSetMapper"/>
	</bean>
        
    <bean id="personLineTokenizer" class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer">
	    <property name="delimiter" value="${person-input-file-delimiter}"/>
	    <property name="names" value="${person-input-file-field-names}"/>
	</bean>
    
    <bean id="personFieldSetMapper" class="org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper">
	    <property name="targetType" value="${person-bean-type}"/>
    </bean>
            
    <!-- address -->
	<bean id="addressReader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="addressLineMapper"/>
	    <property name="resource" value="${address-input-file}"/>
	</bean>
	
	<bean id="addressLineMapper" class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
        <property name="lineTokenizer" ref="addressLineTokenizer"/>
        <property name="fieldSetMapper" ref="addressFieldSetMapper"/>
	</bean>
        
    <bean id="addressLineTokenizer" class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer">
	    <property name="delimiter" value="${address-input-file-delimiter}"/>
	    <property name="names" value="${address-input-file-field-names}"/>
	</bean>
    
    <bean id="addressFieldSetMapper" class="org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper">
	    <property name="targetType" value="${address-bean-type}"/>
    </bean>
            
    <!-- contract -->
	<bean id="contractReader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="contractLineMapper"/>
	    <property name="resource" value="${contract-input-file}"/>
	</bean>
	
	<bean id="contractLineMapper" class="org.springframework.batch.item.file.mapping.DefaultLineMapper">
        <property name="lineTokenizer" ref="contractLineTokenizer"/>
        <property name="fieldSetMapper" ref="contractFieldSetMapper"/>
	</bean>
        
    <bean id="contractLineTokenizer" class="org.springframework.batch.item.file.transform.DelimitedLineTokenizer">
	    <property name="delimiter" value="${contract-input-file-delimiter}"/>
	    <property name="names" value="${contract-input-file-field-names}"/>
	</bean>
    
    <bean id="contractFieldSetMapper" class="org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper">
	    <property name="targetType" value="${contract-bean-type}"/>
    </bean>
            
</beans>
```

Dependencies
------------

If you are using maven, you can use this library by adding the following dependency to your pom.xml:

    <dependency>
        <groupId>com.github.markusbernhardt</groupId>
        <artifactId>spring-context-template</artifactId>
        <version>1.0.4</version>
    </dependency>

Without maven you can use this [jar](http://search.maven.org/remotecontent?filepath=com/github/markusbernhardt/spring-context-template/1.0.0/spring-context-template-1.0.0.jar) and provide all required libraries from this [list](DEPENDENCIES.md) on your own.
