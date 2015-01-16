package org.appdynamics.appdiscovery.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.appdynamics.appdiscovery.events.AppDiscoveryEvent;
import org.appdynamics.appdrestapi.RESTAccess;
import org.appdynamics.appdrestapi.data.Application;
import org.appdynamics.appdrestapi.data.Applications;
import org.appdynamics.appdrestapi.data.RESTProxy;
import org.appdynamics.appdrestapi.util.PostEvent;

public class AppDiscoveryEventProcessor {
	
    private int oldMaxApplicationId; 
	private int newMaxApplicationId;
    private RESTAccess access;
	private AppDiscoveryHistoryFileProcessor hfp;
    
	private static Logger logger=Logger.getLogger(AppDiscoveryEventProcessor.class.getName());

	public AppDiscoveryEventProcessor() {
		this.hfp = new AppDiscoveryHistoryFileProcessor(AppDiscoveryOptions.HISTORYFILE_V);
		this.oldMaxApplicationId = hfp.readLastApplicationId();
		this.newMaxApplicationId = this.oldMaxApplicationId;
		
	    if (AppDiscoveryOptions.PROXYURL_V != null && AppDiscoveryOptions.PROXYPORT_V != null) {
	    	Integer proxyPort = new Integer(AppDiscoveryOptions.PROXYPORT_V);
			RESTProxy proxy = new RESTProxy(AppDiscoveryOptions.PROXYURL_V, proxyPort);
		    access = new RESTAccess(AppDiscoveryOptions.CONTROLLER_V, AppDiscoveryOptions.PORT_V, AppDiscoveryOptions.SSL_V, AppDiscoveryOptions.USERNAME_V, AppDiscoveryOptions.PASSWD_V, AppDiscoveryOptions.ACCOUNT_V, proxy);	    	
		}
	    else {
		    access = new RESTAccess(AppDiscoveryOptions.CONTROLLER_V, AppDiscoveryOptions.PORT_V, AppDiscoveryOptions.SSL_V, AppDiscoveryOptions.USERNAME_V, AppDiscoveryOptions.PASSWD_V, AppDiscoveryOptions.ACCOUNT_V);	    	
	    }
	}
	
	public void processApplications() {
		if (oldMaxApplicationId == 0) {
			logger.log(Level.SEVERE,"The AppDiscoveryHistory.txt file is required in order to detect when new applications are added.\nExiting.");
			return;
		}
	    
	    // Get all of the applications so we can see which ones are new.
	    Applications apps=access.getApplications();
	    if(apps == null){
	        // If this is null then we had a problem connection so might as well stop.
	        logger.log(Level.SEVERE,"No applications were returned when executing the request for applications, please insure the information provided was correct.\nExiting.");
	        System.exit(1);
	    }
	    
	    for(Application appD: apps.getApplications()){
	    	if (appD.getId() > oldMaxApplicationId) {
	    		logger.log(Level.INFO, "Found new application:" + appD.getName());
	    		postCustomEvent(access, appD, AppDiscoveryOptions.APPLICATION_V);
	    		if (appD.getId() > newMaxApplicationId) {
	    			newMaxApplicationId = appD.getId();
	    		}
	    	}
	    }
	    
	    updateHistoryFile();
	}
	
	public void postCustomEvent(RESTAccess access, Application app, String applicationToPostEventTo) {
        // Create a new Custom Event (summary, comments, and custom event type)
 		AppDiscoveryEvent ade = new AppDiscoveryEvent(app.getName(), app.getId());
        access.postRESTCustomEvent(applicationToPostEventTo, ade);
	}

	public void updateHistoryFile() {
		if (oldMaxApplicationId == newMaxApplicationId) {
			// no need to update the history file since the max application id has not changed 
	        logger.log(Level.INFO, "No new applications were discovered");			
		}
		else
		{
			// write the new max application id to the history file 
	        logger.log(Level.INFO, "New max application id is:" + newMaxApplicationId);
	        hfp.writeLastApplicationId(newMaxApplicationId);
		}
	}
}
