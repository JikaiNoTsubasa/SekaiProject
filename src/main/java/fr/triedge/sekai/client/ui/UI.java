package fr.triedge.sekai.client.ui;

import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import fr.triedge.sekai.common.utils.ExceptionDialog;
import fr.triedge.sekai.common.utils.LoginToken;

public class UI {

	public static void queue(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}
	
	public static LoginToken showLoginScreen() {
		JTextField textName = new JTextField("");
		JPasswordField textPassword = new JPasswordField("");

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Login:"));
		panel.add(textName);
		panel.add(new JLabel("Password:"));
		panel.add(textPassword);

		int result = JOptionPane.showConfirmDialog(null, panel, "Login",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			LoginToken token = new LoginToken();
			token.username = textName.getText();
			token.password = new String(textPassword.getPassword());
			return token;
		}
		return null;
	}
	
	public static void warn(String content) {
		JOptionPane.showMessageDialog(null,
			    content,
			    "WARNING",
			    JOptionPane.WARNING_MESSAGE);
	}
	
	public static void error(String content, Exception e) {
		ExceptionDialog ld = new ExceptionDialog(
				"Unexpected Error!",
				content, e);

		ld.setVisible(true);
	}
	/*
	public static void errorfx(String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("ERROR");
		alert.setContentText(content);

		alert.showAndWait();
	}
	
	public static void errorfx(String content, Exception ex) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Exception Dialog");
		alert.setHeaderText("ERROR");
		alert.setContentText(content);


		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}
	*/
	
	public static void error(String content) {
		JOptionPane.showMessageDialog(null,
			    content,
			    "ERROR",
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static void info(String content) {
		JOptionPane.showMessageDialog(null,
			    content,
			    "INFO",
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static byte[] imageToBytes(BufferedImage img) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( img, "png", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}
	
	public static BufferedImage bytesToImage(byte[] imageData) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
		return ImageIO.read(bais);
	}
}
