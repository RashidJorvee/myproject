package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import com.aem.community.core.helper.EmployeeDetailsPojo;


@Model(adaptables=Resource.class)
public class EmployeeDetails {

	@Inject @Named("employee")
	private Resource employee;
	private String message;

	List<EmployeeDetailsPojo> employeeList;

	public List<EmployeeDetailsPojo> getEmployeeList() {
		return employeeList;
	}

	@PostConstruct
	protected void init() {
		employeeList = new ArrayList<EmployeeDetailsPojo>();
		if(employee != null) {
			employeeList = populateModel(employeeList, employee);
		}
	}

	private List<EmployeeDetailsPojo> populateModel(List<EmployeeDetailsPojo> employList, Resource resource) {
		if(resource != null) {
			Iterator<Resource> iterator = resource.listChildren();
			while(iterator.hasNext()) {
				EmployeeDetailsPojo employeeDetailsPojo = iterator.next().adaptTo(EmployeeDetailsPojo.class);
				if(employeeDetailsPojo != null) {
					employList.add(employeeDetailsPojo);
				}
			}
		}
		return employList;
	}

	public Resource getEmployee() {
		return employee;
	}
}
