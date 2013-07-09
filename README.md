spring-context-template
=======================

This library provides a templating mechanism for creating XML-based Spring context definitions. It is heavily based on the 
[spring-import-template](https://github.com/hikage/spring-import-template/blob/master/pom.xml) library by Gildas Cuisinier.

Introduction
------------

Especially when using spring-batch you are very often defining a large number of very similar beans in the context. When
you think for example about a job that needs to read multiple files, you end up with context definitions like:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- address -->
	<bean id="addressReader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="addressLineMapper"/>
	    <property name="resource" value="${address-input-file}"/>
	</bean>
	
	<bean id="addressWriter" class="org.springframework.batch.item.file.FlatFileItemWriter">
        <property name="lineAggregator" ref="addressLineAggregator"/>
        <property name="resource" value="${address-output-file}" />
    </bean>
	
    <!-- address -->
	<bean id="addressReader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="addressLineMapper"/>
	    <property name="resource" value="${address-input-file}"/>
	</bean> 
	
	<bean id="addressWriter" class="org.springframework.batch.item.file.FlatFileItemWriter">
        <property name="lineAggregator" ref="addressLineAggregator"/>
        <property name="resource" value="${address-output-file}" />
    </bean>
	
    <!-- contract -->
	<bean id="contractReader" class="org.springframework.batch.item.file.FlatFileItemReader">
	    <property name="lineMapper" ref="contractLineMapper"/>
	    <property name="resource" value="${contract-input-file}"/>
	</bean> 

	<bean id="contractWriter" class="org.springframework.batch.item.file.FlatFileItemWriter">
        <property name="lineAggregator" ref="contractLineAggregator"/>
        <property name="resource" value="${contract-output-file}" />
    </bean>
	
</beans>
```

This gets quite annoying very fast. 




Dependencies
------------

If you are using maven, you can use this library by adding the following dependency to your pom.xml:

    <dependency>
        <groupId>com.github.markusbernhardt</groupId>
        <artifactId>spring-context-template</artifactId>
        <version>1.0.0</version>
    </dependency>

Without maven you can use this [jar](http://search.maven.org/remotecontent?filepath=com/github/markusbernhardt/spring-context-template/1.0.0/spring-context-template-1.0.0.jar) and provide all required libraries from this [list](DEPENDENCIES.md) on your own.
