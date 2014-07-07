package net.transitdata.traccar_load_tester;

import java.io.IOException;
import java.util.ArrayList;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;


public class App 
{
	 ArrayList<MockDevice> listOfMockDevices = null;
	 
	 @Option(name="-s",usage="server address")
	 private String serverAddress;
	 
	 @Option(name="-p",usage="server port")
	 private int serverPort = 5005;
	 
	 @Option(name="-n",usage="number of mock devices")
	 private int n = 3;
	 
	 @Option(name="-i",usage="interval (in seconds) between sending mock locations, default 30")
	 private int i = 30;
	 
    public static void main( String[] args )throws IOException {
    	 new App().doMain(args);
    }
    
    public void doMain(String[] args) throws IOException{
    	CmdLineParser parser = new CmdLineParser(this);
        parser.setUsageWidth(80);

        try {
            // parse the arguments.
            parser.parseArgument(args);

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            System.err.println(e.getMessage());
            System.err.println("java traccar-load-tester -s serverAddress -r serverPort -n numDevices");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            return;
        }
    	// create n mock devices
    	for (int i=0; i <= n ; n++ ){
    		MockDevice md = new MockDevice(i, serverAddress, serverPort);
    		listOfMockDevices.add(md);
    	}
    	// set a timer, every n send in order
        
    	for (MockDevice md : listOfMockDevices){
    		md.sendLocation();
    	}
    	
    	
    }
    }

