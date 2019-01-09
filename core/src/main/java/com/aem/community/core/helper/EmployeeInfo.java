package com.aem.community.core.helper;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = Resource.class)
public class EmployeeInfo {
	@Inject
	String userName;
	
	@Inject
	String gender;
	
	@Inject
	String profileImagePath;

	public String getUserName() {
		return userName;
	}

	public String getGender() {
		return gender;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

}
