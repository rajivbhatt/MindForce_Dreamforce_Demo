
import oscP5.OscMessage;
import oscP5.OscP5;

public class MuseOscServer_FinalDemo {

	static MuseOscServer_FinalDemo museOscServer;
	
	OscP5 museServer;
	static int recvPort = 5000;

		
	public static void main(String [] args) {
		museOscServer = new MuseOscServer_FinalDemo();
		museOscServer.museServer = new OscP5(museOscServer, recvPort);
	}
	
	void oscEvent(OscMessage msg) {
		
		// Navigation Events
		if (msg.checkAddrPattern("/muse/acc")==true) {  
		       handleNavigationEvents(msg);    	
		}
		
		// Mellow Events
		
		if ( msg.checkAddrPattern("/muse/elements/experimental/mellow")==true) {
			System.out.print("Focus Level :" + msg.get(0).floatValue()  + "\n");
			if(!disableMellow) {
				handleMellowEvent(msg);
			}
	     }

	}
	
	private void handleNavigationEvents(OscMessage msg) {
		
		
		
		if (msg.get(0).floatValue() < -1000 && msg.get(2).floatValue() < -100 ) {
			
			 SalesforceRESTAPIHandler.pushNavigationDataToSalesforce("Right");
		}

		if (msg.get(0).floatValue() > 800 && msg.get(1).floatValue() > 800 && msg.get(2).floatValue() > 800) {
			
			 SalesforceRESTAPIHandler.pushNavigationDataToSalesforce("Left");
		}


	}
	
	
	private void handleMellowEvent(OscMessage msg) {
	    	
	   	
	    	
	    	if(msg.get(0).floatValue() > 0 && msg.get(0).floatValue() < 1 && !isCalibrationDone) {
	    		isCalibrationDone = true;
	    		System.out.println("Calibration done");
	    	}
	    	
	    	if(isCalibrationDone) {
	    		if(msg.get(0).floatValue() > 0.1 && msg.get(0).floatValue() < 0.2 && !isLevel1Done) { 
		    		SalesforceRESTAPIHandler.pushDataToSalesforce(10);
		    		System.out.println("\n Level 1");
		    		isLevel1Done=true; 
		    		isLevel2Done=false;
		    		isLevel3Done=false;
		    		isLevel4Done=false;
		    		isLevel5Done=false;
		    		
		    	}
		    	else if(msg.get(0).floatValue() > 0.2 && msg.get(0).floatValue() < 0.4 && !isLevel2Done) { 
		   		SalesforceRESTAPIHandler.pushDataToSalesforce(25);
		    		System.out.println("\n Level 2");
		    		isLevel2Done=true;
		    		isLevel1Done=false;
		    		isLevel3Done=false;
		    		isLevel4Done=false;
		    		isLevel5Done=false;
		    	}
		    	else if(msg.get(0).floatValue() > 0.4 && msg.get(0).floatValue() < 0.7 && !isLevel3Done) { 
		    		SalesforceRESTAPIHandler.pushDataToSalesforce(50);
		    		System.out.println("\n Level 3");
		    		isLevel3Done = true;
		    		isLevel1Done=false;
		    		isLevel2Done=false;
		    		isLevel4Done=false;
		    		isLevel5Done=false;
		    	}
		    	else if(msg.get(0).floatValue() > 0.7 && msg.get(0).floatValue() < 0.8 && !isLevel4Done) { 
		    		SalesforceRESTAPIHandler.pushDataToSalesforce(75);
		    		System.out.println("\n Level 4");
		    		isLevel4Done=true;
		    		isLevel1Done=false;
		    		isLevel2Done=false;
		    		isLevel3Done=false;
		    		isLevel5Done=false;
		    	}
		    	else if(msg.get(0).floatValue() > 0.8 && msg.get(0).floatValue() < 0.95  && !isLevel5Done) { 
		    	    SalesforceRESTAPIHandler.pushDataToSalesforce(90);
		    		System.out.println("\n Level 5");
		    		isLevel5Done=true;
		    		isLevel1Done=false;
		    		isLevel2Done=false;
		    		isLevel3Done=false;
		    		isLevel4Done=false;
		    	}
		    	else if(msg.get(0).floatValue() == 1.0 ) { 
		    		
		    		
		    		
		    		SalesforceRESTAPIHandler.pushDataToSalesforce(100);
		    		//System.exit(0);
		    		disableMellow=true;
		    		}
		    		
		    	}
	    	}
		
	}
	static boolean isLevel1Done = false;
	static boolean isLevel2Done = false;
	static boolean isLevel3Done = false;
	static boolean isLevel4Done = false;
	static boolean isLevel5Done = false;
	static boolean disableMellow=false;
	
	static boolean isCalibrationDone = false;

}
 
		
