package com.github.markusbernhardt.springcontexttemplate.beans;

public class Container {

	private Simple bean;
	private Object innerAnonymous;

	public Simple getBean() {
		return bean;
	}

	public void setBean(Simple bean) {
		this.bean = bean;
	}

	public Object getInnerAnonymous() {
		return innerAnonymous;
	}

	public void setInnerAnonymous(Object innerAnonymous) {
		this.innerAnonymous = innerAnonymous;
	}
}
