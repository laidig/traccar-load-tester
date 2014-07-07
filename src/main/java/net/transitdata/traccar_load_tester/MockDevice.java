package net.transitdata.traccar_load_tester;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class MockDevice {

	ClientController clientController = null;
	Location location = null; 
	int deviceId = 0;
	String serverAddress = null;
	int port = 0;

	Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);

	public static final String LOG_TAG = "Traccar.Connection";
	public static final int SOCKET_TIMEOUT = 10 * 1000;

	public MockDevice(int deviceId, String serverAddress, int port){
		deviceId = this.deviceId;
		serverAddress = this.serverAddress;
		port = this.port;
	}

	public void sendLocation(){
		long mTime = calendar.getTime().getTime();

		//return coordinates centered roughly around New York City.
		double mLatitude = 40.7127 + Math.random();
		double mLongitude =  74.0059 + Math.random();

		location = new Location(mTime, mLatitude, mLongitude, 0, 0, 0);

		String locMessage = Protocol.createLocationMessage(false, location, 100.0);

		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(serverAddress, port));
			socket.setSoTimeout(SOCKET_TIMEOUT);
			OutputStream socketStream = socket.getOutputStream();
			socketStream.write(locMessage.getBytes());
			socketStream.flush();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}


}



