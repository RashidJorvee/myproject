/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.aem.community.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.settings.SlingSettingsService;
import org.slf4j.LoggerFactory;

import com.aem.community.core.helper.EmployeeDetailsPojo;


@Model(adaptables=Resource.class)
public class HelloWorldModel {
//	final static org.slf4j.Logger LOGGER=LoggerFactory.getLogger(HelloWorldModel.class);
    @Inject
    private SlingSettingsService settings;

    @Inject @Named("sling:resourceType") @Default(values="No resourceType")
    protected String resourceType;
    @Inject @Named("employee")
    private Resource employee;
    private String message;

    List<EmployeeDetailsPojo> employeeList;
    
    public List<EmployeeDetailsPojo> getEmployeeList() {
		return employeeList;
	}

	@PostConstruct
    protected void init() {
        message = "\tHello World!\n";
        message += "\tThis is instance: " + settings.getSlingId() + "\n";
        message += "\tResource type is: " + resourceType + "\n";
        
        employeeList = new ArrayList<EmployeeDetailsPojo>();
        if(employee != null) {
//        	LOGGER.error("value of header list is " +employeeList);
            employeeList = populateModel(employeeList, employee);
        }
    }

    private List<EmployeeDetailsPojo> populateModel(List<EmployeeDetailsPojo> array, Resource resource) {
    	if(resource != null) {
            Iterator<Resource> linkResource = resource.listChildren();
            while(linkResource.hasNext()) {
            	EmployeeDetailsPojo header = linkResource.next().adaptTo(EmployeeDetailsPojo.class);
                if(header != null) {
                    array.add(header);
                }
            }
        }
        return array;
	}

	public String getMessage() {
        return message;
    }

	public Resource getEmployee() {
		return employee;
	}

}
