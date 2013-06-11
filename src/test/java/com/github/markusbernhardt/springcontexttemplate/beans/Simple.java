package com.github.markusbernhardt.springcontexttemplate.beans;

public class Simple {

	private String constructorValue;
	private String externalizedConstructorValue;
	private String propertyValue;
	private String externalizedPropertyValue;

	public Simple(String constructorValue,
			String externalizedConstructorValue) {
		super();
		this.constructorValue = constructorValue;
		this.externalizedConstructorValue = externalizedConstructorValue;
	}

	public String getConstructorValue() {
		return constructorValue;
	}

	public void setConstructorValue(String constructorValue) {
		this.constructorValue = constructorValue;
	}

	public String getExternalizedConstructorValue() {
		return externalizedConstructorValue;
	}

	public void setExternalizedConstructorValue(
			String externalizedConstructorValue) {
		this.externalizedConstructorValue = externalizedConstructorValue;
	}

	public String getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public String getExternalizedPropertyValue() {
		return externalizedPropertyValue;
	}

	public void setExternalizedPropertyValue(String externalizedPropertyValue) {
		this.externalizedPropertyValue = externalizedPropertyValue;
	}

}
