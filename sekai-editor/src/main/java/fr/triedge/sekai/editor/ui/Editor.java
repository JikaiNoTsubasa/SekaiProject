package fr.triedge.sekai.editor.ui;


import javax.swing.*;

public class Editor {

	private String name;
	private JPanel panel;
	
	public Editor(JPanel panel) {
		setPanel(panel);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public JPanel getPanel() {
		return panel;
	}
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}
	
	
}
