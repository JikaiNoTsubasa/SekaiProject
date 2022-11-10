package fr.triedge.sekai.client.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import fr.triedge.sekai.common.utils.JSonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;


import fr.triedge.sekai.client.config.ClientConfig;
import fr.triedge.sekai.client.ui.LoginScreen;
import fr.triedge.sekai.client.ui.UI;
import fr.triedge.sekai.common.model.User;
import fr.triedge.sekai.common.net.MSGClientAskLoginServer;
import fr.triedge.sekai.common.net.MSGCode;
import fr.triedge.sekai.common.net.MSGServerAnswerLogin;
import fr.triedge.sekai.common.utils.Utils;

public class SekaiClient {

	private static Logger log;

	private static String CONFIG_FILE							= "client/config/client.json";
	private static String CONFIG_LOG_LOCATION					= "client/config/log4j2.xml";

	private ClientConfig config;
	//private Client session;
	private LoginScreen loginScreen;

	public static void main(String[] args) {
		SekaiClient client = new SekaiClient();
		//client.init();
	}
/*
	public void init(){
		// Configure logging
		try {
			configureLogging();
		} catch (IOException e) {
			UI.error("Cannot create logging, is "+CONFIG_LOG_LOCATION+" missing?", e);
		}

		// Load client configuration
		try {
			loadClientConfig();
		} catch (IOException e) {
			UI.error("Cannot load config file "+CONFIG_FILE,e);
			log.error("Cannot load config file "+CONFIG_FILE,e);
			System.exit(-1);
		}

		// Contacting server and registering classes
		try {
			contactServer();
			//registerClasses();
		} catch (IOException e) {
			UI.error("Server unavailable at "+getConfig().getServerHost()+":"+getConfig().getServerPort(),e);
			log.error("Server unavailable at "+getConfig().getServerHost()+":"+getConfig().getServerPort(),e);
		}

		// Register listener
		registerClientListener();

		// Login screen
		setLoginScreen(new LoginScreen(this));
		getLoginScreen().build();
	}


	private void registerClientListener() {
		getSession().addListener(new Listener() {
			public void received (Connection connection, Object msg) {
				receivedMessage(connection,msg);
			}
		});
	}

	public void receivedMessage(Connection connection, Object msg) {
		if (msg instanceof MSGServerAnswerLogin) {
			MSGServerAnswerLogin mes = (MSGServerAnswerLogin)msg;
			if (mes.code == MSGCode.OK) {
				log.debug("Login is OK");
				if (mes.getUser() == null) {
					UI.error("Received user object is null, try login again");
					log.error("User object is null");
					return;
				}
				startGameWithUser(mes.getUser());
			}else if (mes.code == MSGCode.NOK) {
				log.debug("Login is FAILED");
				UI.warn("Login or password is wrong");
			}
		}
	}

	private void startGameWithUser(User user) {
		
	}

	private void registerClasses() {
		Kryo kryo = getSession().getKryo();
		Utils.registerClasses(kryo);
	}

	private void contactServer() throws IOException {
		log.debug("START: contactServer()");
		setSession(new Client());
		registerClasses();
		getSession().start();
		log.info("Connecting to "+config.getServerHost()+":"+config.getServerPort());
		getSession().connect(5000, getConfig().getServerHost(), getConfig().getServerPort());
		log.info("Connected");
		log.debug("END: contactServer()");
	}

	public void loginToServer(String username, String password) {
		log.info("Login to server with account: "+username+"...");
		MSGClientAskLoginServer msg = new MSGClientAskLoginServer(username, password);
		getSession().sendTCP(msg);
		log.debug("Login message sent");
	}

	private void loadClientConfig() throws IOException {
		log.debug("START: loadClientConfig()");
		log.info("Loading configuration file from: "+CONFIG_FILE);
		config = JSonHelper.loadFromFile(new File(CONFIG_FILE));
		log.info("Configuration file loaded");
		log.debug("END: loadClientConfig()");
	}

	private void configureLogging() throws FileNotFoundException, IOException {
		// Set configuration file for log4j2
		ConfigurationSource source = new ConfigurationSource(new FileInputStream(CONFIG_LOG_LOCATION));
		Configurator.initialize(null, source);
		log = LogManager.getLogger(SekaiClient.class);
	}

	public ClientConfig getConfig() {
		return config;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}

	public Client getSession() {
		return session;
	}

	public void setSession(Client session) {
		this.session = session;
	}

	public LoginScreen getLoginScreen() {
		return loginScreen;
	}

	public void setLoginScreen(LoginScreen loginScreen) {
		this.loginScreen = loginScreen;
	}

 */
}
