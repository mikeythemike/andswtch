package sta.andswtch.extensionLead;

import java.util.List;

import sta.andswtch.gui.AndSwtch;
import sta.andswtch.network.ConnectionManager;

public class ExtensionLead implements IExtensionLead {

	private List<PowerPoint> powerPoints;
	private Config config;
	private ConnectionManager connectionManager;
	private ResponseProcessor responseProcessor;
	private AndSwtch andSwtch;
	private int powerPointsCount;
	
	//is only for prototype v0.0 :), will be removed
	private String response = "nothing";

	// test static final variables
	private static final int testPowerPointsCount = 3;
	private static final String testHost = "192.168.178.21";
	private static final int testPortIn = 1132;
	private static final int testPortOut = 1133;
	private static final String testUser = "admin";
	private static final String testPassword = "anel";

	public ExtensionLead(AndSwtch andSwtch) {
		this.andSwtch = andSwtch;
		init();
	}

	private void addPowerPoint(int id, String name, boolean enable, boolean on) {
		this.powerPoints.add(new PowerPoint(id, name, enable, on));
	}


	public void errorAlert() {
		// TODO Auto-generated method stub

	}


	public String getHost() {
		return this.config.getHost();
	}


	public String getPassword() {
		return this.config.getPassword();
	}


	public int getPortIn() {
		return this.config.getPortIn();
	}


	public int getPortOut() {
		return this.config.getPortOut();
	}


	public String getPowerPointName(int id) {
		return this.powerPoints.get(id).getName();
	}


	public int getPowerPointsCount() {
		return this.powerPointsCount;
	}


	public String getUser() {
		return this.config.getUser();
	}


	public void init() {
		this.powerPointsCount = testPowerPointsCount;
		this.config = new Config(testHost, testPortIn, testPortOut, testUser,
				testPassword);
		this.connectionManager = new ConnectionManager(this.config, this);

//		for (int id = 0; id < this.powerPointsCount; id++) {
//			this.addPowerPoint(id, "pP_0" + id, true, false);
//		}
		this.responseProcessor = new ResponseProcessor(this.powerPoints);

		this.sendUpdateMessage();
	}


	public boolean isPowerPointEnable(int id) {
		return this.powerPoints.get(id).isEnabled();
	}


	public boolean isPowerPointOn(int id) {
		return this.powerPoints.get(id).isOn();
	}


	public void sendState(int id, boolean on) {
		// TODO depends on the response something like that
		// this.powerPoints.get(id).setState(on);

	}


	public void sendState(int id, boolean on, int time) {
		// TODO depends on the response something like that
		// after the specified time
		// this.powerPoints.get(id).setState(on);

	}


	public void sendStateAll(boolean on) {
		// TODO depends on the response something like that
		// for(int id = 0; id < this.powerPointsCount; id++) {
		// this.powerPoints.get(id).setState(on);
		// }

	}

	public void sendStateAll(boolean on, int time) {
		// TODO depends on the response something like that
		// after the specified time
		// for(int id = 0; id < this.powerPointsCount; id++) {
		// this.powerPoints.get(id).setState(on);
		// }

	}


	public void sendUpdateMessage() {
		connectionManager.getUpdate();

	}


	public void setConfig(String host, int portIn, int portOut, String user,
			String password) {

		if (this.config == null) {
			this.config = new Config(host, portIn, portOut, user, password);
		} else {
			this.config.setConfig(host, portIn, portOut, user, password);
		}
	}


	public void setPowerPointName(int id, String name) {
		this.powerPoints.get(id).setName(name);
	}


	public void updateDatastructure(String response) {
//		if (this.responseProcessor == null) {
//			this.responseProcessor = new ResponseProcessor(this.powerPoints);
//		}
//		this.responseProcessor.processResponse(response);
		
		//for now, will be changed later, that the responseprocessor is processing the data.
		this.response = response;
		
		andSwtch.updateActivity();
		
		
		
	}
	
	
	public String getResponse() {
		return response;
		
	}

}
