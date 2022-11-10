package fr.triedge.sekai.client.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import fr.triedge.sekai.client.controller.SekaiClient;

public class LoginScreen extends JFrame{

	private static final long serialVersionUID = -4636288702783216590L;
	
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JButton btnLogin;
	private SekaiClient client;
	
	public LoginScreen(SekaiClient client) {
		setClient(client);
	}

	public void build() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(600, 400);
		
		setTextUsername(new JTextField());
		setTextPassword(new JPasswordField());
		setBtnLogin(new JButton("Login"));
		getBtnLogin().addActionListener(e -> actionLogin());
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel grid = new JPanel(new GridLayout(2,0));
		
		grid.add(new JLabel("Username"));
		grid.add(getTextUsername());
		grid.add(getBtnLogin());
		grid.add(new JLabel("Password"));
		grid.add(getTextPassword());
		
		panel.add(grid, BorderLayout.SOUTH);
		setContentPane(panel);
		setVisible(true);
	}
	
	public void actionLogin() {
		String username = getTextUsername().getText();
		String password = new String(getTextPassword().getPassword());
		
		//getClient().loginToServer(username, password);
	}

	public JTextField getTextUsername() {
		return textUsername;
	}

	public void setTextUsername(JTextField textUsername) {
		this.textUsername = textUsername;
	}

	public JPasswordField getTextPassword() {
		return textPassword;
	}

	public void setTextPassword(JPasswordField textPassword) {
		this.textPassword = textPassword;
	}

	public JButton getBtnLogin() {
		return btnLogin;
	}

	public void setBtnLogin(JButton btnLogin) {
		this.btnLogin = btnLogin;
	}

	public SekaiClient getClient() {
		return client;
	}

	public void setClient(SekaiClient client) {
		this.client = client;
	}
}
