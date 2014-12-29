package org.appdynamics.appdiscovery.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppDiscoveryHistoryFileProcessor {
	
	private String historyFileName; 
    private static Logger logger=Logger.getLogger(AppDiscoveryHistoryFileProcessor.class.getName());
	
	public AppDiscoveryHistoryFileProcessor(String historyFileName) {
		this.historyFileName = historyFileName;
	}
	
	public int readLastApplicationId() {
		BufferedReader br = null;
		
		try {
	        FileReader fr = new FileReader(historyFileName);
			br = new BufferedReader(fr);
	        return Integer.parseInt(br.readLine());
		}
		catch (Exception e) {
	           logger.log(Level.SEVERE,"File could not be read." + e.toString());
	           return 0; 
        }
		finally {
			if (br != null) {
				try { br.close(); } catch (Exception e) {}
			}
		}
	}
	
	public void writeLastApplicationId(int lastApplicationId)  {
		BufferedWriter bw = null;
		
		try {
	        FileWriter fw = new FileWriter(historyFileName);
			bw = new BufferedWriter(fw);
			bw.write(String.valueOf(lastApplicationId));
			return;
		}
		catch (Exception e) {
            logger.log(Level.SEVERE,"File could not be written." + e.toString());
        }
		finally {
			if (bw != null) {
				try { bw.close(); } catch (Exception e) {}
			}
		}	
	}
}
