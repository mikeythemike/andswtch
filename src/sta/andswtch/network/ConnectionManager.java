package sta.andswtch.network;

import java.io.IOException;

import sta.andswtch.extensionLead.Config;
import sta.andswtch.extensionLead.ExtensionLead;
import android.util.Log;

public class ConnectionManager implements IConnectionManager {

	private Config config;
	private Sender sender;
	private Receiver receiver;
	private ExtensionLead extLead;

	private static final String TAG = ConnectionManager.class.getName();
	private static final String GET_STATUS = "wer da?";
	private static final String ON = "on";
	private static final String OFF = "off";

	public ConnectionManager(Config config, ExtensionLead extLead) {
		this.config = config;
		this.extLead = extLead;
		this.sender = new Sender();
		this.receiver = new Receiver(this);
	}

	public void errorAlert() {
		Log.e(TAG, "Server timed out...");
	}

	public void sendUpdateRequest() {
		this.sendAndReceive(GET_STATUS);
	}

	public void sendAndReceive(String command) {

		Log.v(TAG, "try to send a packet with the command: "
				+ command + "to " + config.getHost() + ":"
				+ config.getExtensionLeadReceiverPort());
		try {
			this.sender.send(this.config.getHost(),
					this.config.getExtensionLeadReceiverPort(), command);
		} catch (IOException e) {
			// TODO better error handling
			e.printStackTrace();
			Log.e(TAG, "failed to send the packet with the following command : "
					+ command + "to " + config.getHost() + ":"
					+ config.getExtensionLeadReceiverPort());
		}
		
		Log.d(TAG, "start a server to receive the response");
		try {
			this.receiver.start(this.config.getExtensionLeadSenderPort());
		} catch (IOException e) {
			Log.e(TAG, "Error: could not start the server, exception: "
					+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void updateDatastructure(String response) {
		this.extLead.updateDatastructure(response);
	}
	
	public void sendState(int id, boolean on) {
		sendState(id, on, 0);
	}
	
	public void sendState(int id, boolean on, int time) {
		String status;
		if (on) {
			status = ON;
		} else {
			status = OFF;
		}
		
		sendAndReceive("Sw_" + status + Integer.toString(id)
				+ this.config.getUser() + this.config.getPassword());
		
		// time does not work right now. - has to be evaluated, how to build the
		// command string
	}

}
