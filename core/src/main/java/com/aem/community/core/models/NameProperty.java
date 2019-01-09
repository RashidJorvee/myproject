package com.aem.community.core.models;


import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=Resource.class)
public class NameProperty {
	
	@Inject @Optional
	private String name;
	
	private String message2;
	public String getMessage2() {
		return message2;
	}

	public String formettedName;
	
	@PostConstruct
	private void initModel() {
		this.formettedName = "your name is "+this.name;
		this.message2="How are you doing today";
	}
	
	public String getFormettedName() {
		return formettedName;
	}
}
