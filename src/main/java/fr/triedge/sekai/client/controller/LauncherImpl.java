package fr.triedge.sekai.client.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.SwingWorker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.triedge.sekai.common.utils.JSonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import fr.triedge.sekai.client.config.ClientConfig;
import fr.triedge.sekai.client.ui.Launcher;
import fr.triedge.sekai.client.ui.UI;
import fr.triedge.sekai.common.model.Update;
import fr.triedge.sekai.common.model.UpdateElement;
import fr.triedge.sekai.common.utils.Utils;

public class LauncherImpl {

	private static Logger log;

	private static String CONFIG_FILE							= "client/config/client.json";
	private static String CONFIG_LOG_LOCATION					= "client/config/log4j2.xml";
	private static String TMP_UPDATE_FILE						= "client/tmp/update.json";
	
	private ClientConfig config;
	private Launcher launcherUI;
	
	public static void main(String[] args) {
		LauncherImpl impl = new LauncherImpl();
		impl.init();
	}

	public void init() {
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
			log.error("Cannot load config file "+CONFIG_FILE,e);
			UI.error("Cannot load config file "+CONFIG_FILE,e);
		}
		
		// Start UI
		startLauncherUI();
		
	}
	public void saveConfig() {
		try {
			JSonHelper.storeToFile(config, new File(CONFIG_FILE));
		} catch (IOException e) {
			UI.error("Cannot save config file", e);
			log.error("Cannot save config file",e);
		}
	}
	
	public void startTaskCheckUpdates() {
		log.debug("START: startTaskCheckUpdates()");
		SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() 
		{
			@Override
			protected Void doInBackground() throws Exception {
				try {
					updateStatus("Downloading MANIFEST...");
					log.info("Checking for updates...");
					checkForUpdates();
					log.info("Updates done");
				} catch (IOException e) {
					UI.error("Cannot check for updates",e);
					log.error("Cannot check for updates",e);
				}
				return null;
			}
		};
		worker.execute();
		log.debug("END: startTaskCheckUpdates()");
	}
	
	public void updateStatus(String status) {
		UI.queue(new Runnable() {
			
			@Override
			public void run() {
				getLauncherUI().getLabelStatus().setText(status);
				getLauncherUI().repaint();
			}
		});
	}
	
	private void startLauncherUI() {
		log.debug("START: startLauncherUI()");
		log.info("Starting GUI...");
		setLauncherUI(new Launcher(this));
		getLauncherUI().build();
		log.info("GUI started");
		log.debug("END: startLauncherUI()");
	}

	private void checkForUpdates() throws IOException {
		log.debug("START: checkForUpdates()");
		getLauncherUI().getBtnCheckForUpdates().setEnabled(false);
		String url = config.getLinkUpdate();
		Utils.downloadFileTo(url, TMP_UPDATE_FILE);
		updateStatus("Extracting MANIFEST...");
		getLauncherUI().getProgressBar().setValue(10);
		log.info("Downloaded update.json");
		String currentDir = System.getProperty("user.dir");
		try {
			Update update = JSonHelper.loadFromFile(new File(TMP_UPDATE_FILE));
			if (!update.getVersion().equals(config.getVersion())) {
				updateStatus("Downloading new version...");
				log.info("Current version ("+config.getVersion()+") is different than actual version ("+update.getVersion()+")");
				int count = update.getElements().size();
				int step = (int)(90/count);
				for (UpdateElement ue: update.getElements()) {
					String from = ue.getSitePath();
					String to = currentDir+File.separator+ue.getLocalPath();
					log.info("Downloading file from: "+from+", to: "+to+"...");
					updateStatus("Downloading "+to+"...");
					Utils.downloadFileTo(from, to);
					getLauncherUI().getProgressBar().setValue(getLauncherUI().getProgressBar().getValue()+step);
				}
				getLauncherUI().getBtnCheckForUpdates().setEnabled(false);
				getLauncherUI().enableLogin();
				getLauncherUI().getProgressBar().setValue(100);
				updateStatus("Version up to date");
				config.setVersion(update.getVersion());
				saveConfig();
			}else {
				getLauncherUI().getBtnCheckForUpdates().setEnabled(false);
				getLauncherUI().enableLogin();
				getLauncherUI().getProgressBar().setValue(100);
				updateStatus("Version up to date");
			}
		} catch (Exception e) {
			UI.error("Exception occured", e);
			log.error("Exception occured",e);
		}
		log.debug("END: checkForUpdates()");
	}
	
	public void startSekaiServer() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		File file = new File("SekaiServer.jar");
		URL[] urls = { file.toURI().toURL() };
		log.debug("URL: "+urls[0]);
		URLClassLoader loader = new URLClassLoader(urls);
		Class<?> cls = loader.loadClass("fr.triedge.sekai.client.controller.SekaiClient");
		Method main = cls.getDeclaredMethod("main", String[].class); // get the main method using reflection
		String[] args = {"whatever"};
		main.invoke(null, new Object[] {args}); // static methods are invoked with null as first argument
		getLauncherUI().dispose();
	}
	
	private void loadClientConfig() throws IOException {
		log.debug("START: loadClientConfig()");
		log.info("Loading configuration file from: "+CONFIG_FILE);
		setConfig(JSonHelper.loadFromFile(new File(CONFIG_FILE)));
		log.info("Configuration file loaded");
		log.debug("END: loadClientConfig()");
	}

	private void configureLogging() throws FileNotFoundException, IOException {
		// Set configuration file for log4j2
		ConfigurationSource source = new ConfigurationSource(new FileInputStream(CONFIG_LOG_LOCATION));
		Configurator.initialize(null, source);
		log = LogManager.getLogger(LauncherImpl.class);
	}

	public ClientConfig getConfig() {
		return config;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}

	public Launcher getLauncherUI() {
		return launcherUI;
	}

	public void setLauncherUI(Launcher launcherUI) {
		this.launcherUI = launcherUI;
	}
}
