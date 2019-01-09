package com.aem.community.core.servlets;

import java.util.Iterator;

import javax.inject.Inject;
import javax.servlet.Servlet;

import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.community.core.helper.EmployeeInfo;


@Component(service=Servlet.class,
property={
        Constants.SERVICE_DESCRIPTION + "=Employee info API",
        "sling.servlet.methods=" + HttpConstants.METHOD_GET,
        "sling.servlet.paths="+ "/bin/private/employeeInfo",
        "sling.servlet.extensions=" + "json"
})
public class SlingModelServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = -2343488695356747563L;
	Logger log=LoggerFactory.getLogger(SlingModelServlet.class);
	@Reference 
	private ResourceResolverFactory resourceResolverFactory;
	ResourceResolver resourceResolver;
	
	public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) {
		res.setContentType("text/html");
		try{
//			resourceResolver=resourceResolverFactory.getServiceResourceResolver(null);
			resourceResolver=req.getResourceResolver();
			Resource resource=resourceResolver.getResource("/content/rashid0/jcr:content/par");
			Iterator<Resource> iterator=resource.listChildren();
			JSONArray jsonAray=new JSONArray();
			while(iterator.hasNext()){
				log.error("Inside iterator 1");
				Resource childNode=iterator.next();
				JSONObject jsonObject=new JSONObject();
				if(childNode.getResourceType().equalsIgnoreCase("myproject/components/content/employee")) {
		            
					Resource employeeComp=resourceResolver.getResource(childNode.getPath()).getChild("employee");
					JSONArray employArray=new JSONArray();
					Iterator<Resource> items=employeeComp.listChildren();
					while(items.hasNext()) {
			            JSONObject employObj=new JSONObject();
						log.error("Inside iterator 2");
						Resource itemRes=resourceResolver.getResource(items.next().getPath());
						log.error("ItemRes::"+itemRes);
						EmployeeInfo empInfo = itemRes.adaptTo(EmployeeInfo.class);
						log.error("empInfo:"+empInfo);
						employObj.put("Name", empInfo.getUserName());
						employObj.put("Gender", empInfo.getGender());
						employObj.put("Image", empInfo.getProfileImagePath());
			            employArray.put(employObj);
			             
					}
					jsonObject.put("Emplooyee:", employArray);
				} 
				jsonAray.put(jsonObject);
			}
			res.getWriter().write(jsonAray.toString());
			
		} catch(Exception e) {
			log.error("Exception SlingModelServlet: {}" ,e);
		}
	}
}
