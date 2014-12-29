package org.appdynamics.appdiscovery.events;

import org.appdynamics.appdrestapi.util.PostEvent;

public class AppDiscoveryEvent extends PostEvent {

    private static String CUSTOM_EVENT_TYPE = "New Application Discovered";

	public AppDiscoveryEvent(String appName, int appID) {
		super("", null, CUSTOM_EVENT_TYPE);
		String summary = "A new application named " + appName + " has been created.  The application ID is " + appID + ".";
		this.setSummary(summary);
	}

}
