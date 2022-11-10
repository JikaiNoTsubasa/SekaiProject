package fr.triedge.sekai.server.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;


import fr.triedge.sekai.common.model.Model;
import fr.triedge.sekai.common.model.User;
import fr.triedge.sekai.common.net.MSGClientAskLoginServer;
import fr.triedge.sekai.common.net.MSGCode;
import fr.triedge.sekai.common.net.MSGServerAnswerLogin;
import fr.triedge.sekai.common.utils.Utils;
import fr.triedge.sekai.server.config.ServerConfig;
import fr.triedge.sekai.server.storage.Storage;
import fr.triedge.sekai.server.storage.XmlStorage;

public class SekaiServer{ //implements Runnable{
	
	private static String CONFIG_FILE							= "server/config/server.json";
	private static String CONFIG_LOG_LOCATION					= "server/config/log4j2.xml";
	private static String STORAGE_LOG_LOCATION					= "server/storage/FileStorage/model.json";
	//private Server server;
	private ServerConfig config;
	private static Logger log;
	private boolean running = true;
	private Storage storage;
	private Model model;
	private long serverStartingTime;
	/*
	private void init() {
		setServerStartingTime(System.currentTimeMillis());
		// Init logs
		try {
			configureLogging();
		} catch (IOException e1) {
			log.error("Cannot initiate logging", e1);
		}
		
		try {
			// Init config
			config = XmlHelper.loadXml(ServerConfig.class, new File(CONFIG_FILE));
			log.info("Configuration file loaded");
			server = new Server();
			registerClasses(server.getKryo());
			log.debug("Kryo classes registered");
			server.start();
			server.bind(config.getPort());
			server.addListener(new Listener() {
				public void received (Connection connection, Object message) {
					messageReceived(connection, message);
			       }
			});
			log.info("Server configured for port: "+config.getPort());
		} catch (JAXBException | IOException e) {
			log.error("Cannot initiate server", e);
		}
		
		// Init storage
		storage = new XmlStorage();
		storage.openStorage(STORAGE_LOG_LOCATION);
		log.info("Storage type: File, located on "+STORAGE_LOG_LOCATION);
		
		// Load storage
		model = storage.loadModel();
		log.debug("Model loaded");
		
		if (model == null) {
			log.error("Model is null");
			return;
		}
		
		log.debug("Server initialized");
	}

	private void registerClasses(Kryo kryo) {
		Utils.registerClasses(kryo);
	}

	@Override
	public void run() {
		// Init server
		init();
		
		double res = (System.currentTimeMillis() - getServerStartingTime()) / 1000;
		log.info("Server started in "+res+" seconds");
		while (isRunning()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void messageReceived(Connection source, Object mes) {
		log.debug("START: messageReceived()");
		if (mes instanceof MSGClientAskLoginServer) {
			manageLogin(source, (MSGClientAskLoginServer)mes);
		}
		log.debug("END: messageReceived()");
	}
	
	private void manageLogin(Connection source, MSGClientAskLoginServer msg) {
		log.debug("START: manageLogin()");
		String username = msg.getUsername();
		String password = msg.getPassword();
		
		User user = retrieveUser(username, password);
		MSGServerAnswerLogin mes = new MSGServerAnswerLogin();
		if (user == null) {
			// Reply wrong user
			mes.code = MSGCode.NOK;
		}else {
			// Login user
			mes.code = MSGCode.OK;
			mes.setUser(user);
		}
		server.sendToTCP(source.getID(), mes);
		log.debug("END: manageLogin()");
	}
	
	private User retrieveUser(String username, String password) {
		for (User user : getModel().getUsers()) {
			if (user.getUsername().equals(username) && user.getPassword().equals(password))
				return user;
		}
		return null;
	}
	
	private void configureLogging() throws FileNotFoundException, IOException {
		// Set configuration file for log4j2
		ConfigurationSource source = new ConfigurationSource(new FileInputStream(CONFIG_LOG_LOCATION));
		Configurator.initialize(null, source);
		log = LogManager.getLogger(SekaiServer.class);
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public ServerConfig getConfig() {
		return config;
	}

	public void setConfig(ServerConfig config) {
		this.config = config;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public long getServerStartingTime() {
		return serverStartingTime;
	}

	public void setServerStartingTime(long serverStartingTime) {
		this.serverStartingTime = serverStartingTime;
	}


	 */
}
