package org.appdynamics.appdiscovery;

import java.util.logging.Logger;
import java.util.logging.Level;

import org.appdynamics.appdiscovery.utils.AppDiscoveryOptions;
import org.appdynamics.appdiscovery.utils.AppDiscoveryEventProcessor;

public class AppDiscovery {

    private static Logger logger=Logger.getLogger(AppDiscovery.class.getName());

	public static void main(String[] args) {

		try {
	        AppDiscoveryOptions opts =new AppDiscoveryOptions(args);
	        if (opts.parse() == false) {
				System.exit(-1);
	        }
	        
			AppDiscoveryEventProcessor ep = new AppDiscoveryEventProcessor();
			ep.processApplications();
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.toString());
		}
	}
		
}
