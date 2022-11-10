package fr.triedge.sekai.client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import fr.triedge.sekai.client.controller.LauncherImpl;

public class Launcher extends JFrame{

	private static final long serialVersionUID = 7187019555370115990L;
	
	private LauncherImpl impl;
	
	private JLabel labelStatus;
	private JButton btnLogin, btnCheckForUpdates;
	private JPanel mainPanel, gridPanel;
	private JProgressBar progressBar;
	
	public Launcher(LauncherImpl laucnher) {
		setImpl(laucnher);
	}

	public void build() {
		setTitle("Launcher");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(800, 400);
		
		// Components
		setMainPanel(new JPanel(new BorderLayout()));
		setGridPanel(new JPanel(new GridLayout(3,0)));
		setLabelStatus(new JLabel("Ready"));
		setBtnLogin(new JButton("Start"));
		setBtnCheckForUpdates(new JButton("Check For Updates"));
		setProgressBar(new JProgressBar(0, 100));
		
		getBtnLogin().addActionListener(e -> actionLogin());
		getBtnCheckForUpdates().addActionListener(e -> actionCheckForUpdates());
		disableLogin();
		
		getGridPanel().add(getProgressBar());
		getGridPanel().add(getBtnCheckForUpdates());
		getGridPanel().add(getLabelStatus());
		getGridPanel().add(getBtnLogin());
		
		getMainPanel().add(getGridPanel(),BorderLayout.SOUTH);
		setContentPane(getMainPanel());
		
		setVisible(true);
	}
	
	private void actionCheckForUpdates() {
		getImpl().startTaskCheckUpdates();
	}
	
	public void disableLogin() {
		getBtnLogin().setEnabled(false);
	}
	
	public void enableLogin() {
		getBtnLogin().setEnabled(true);
	}
	
	private void actionLogin() {
		try {
			getImpl().startSekaiServer();
		} catch (MalformedURLException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	

	public JLabel getLabelStatus() {
		return labelStatus;
	}

	public void setLabelStatus(JLabel labelStatus) {
		this.labelStatus = labelStatus;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}


	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JPanel getGridPanel() {
		return gridPanel;
	}

	public void setGridPanel(JPanel gridPanel) {
		this.gridPanel = gridPanel;
	}

	public LauncherImpl getImpl() {
		return impl;
	}

	public void setImpl(LauncherImpl impl) {
		this.impl = impl;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public JButton getBtnCheckForUpdates() {
		return btnCheckForUpdates;
	}

	public void setBtnCheckForUpdates(JButton btnCheckForUpdates) {
		this.btnCheckForUpdates = btnCheckForUpdates;
	}
}
