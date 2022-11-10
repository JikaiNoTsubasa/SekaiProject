package fr.triedge.sekai.pixis.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import fr.triedge.sekai.pixis.controller.PixisController;
import fr.triedge.sekai.pixis.model.Palette;
import fr.triedge.sekai.pixis.model.Project;
import fr.triedge.sekai.pixis.utils.Icons;

public class PaletteEditor extends JPanel{

	private static final long serialVersionUID = 4466139872135350762L;
	private PixisController controller;
	private Palette palette;
	private Project project;
	
	private JToolBar toolBar;
	private JButton btnNewColor;
	
	public PaletteEditor(PixisController controller, Palette pal, Project prj) {
		setController(controller);
		setPalette(pal);
		setProject(prj);
	}

	public void build() {
		setLayout(new BorderLayout());
		setToolBar(new JToolBar());
		getToolBar().setFloatable(false);
		setBtnNewColor(new JButton(Icons.colorWheelIcon));
		getBtnNewColor().setToolTipText("Add color");
		getBtnNewColor().addActionListener(e -> addColorToPalette());
		getToolBar().add(getBtnNewColor());
		
		add(getToolBar(), BorderLayout.NORTH);
	}
	
	public void addColorToPalette() {
		Color res = JColorChooser.showDialog(null, "Add color to "+getPalette().getName(), Color.white);
		getPalette().getColors().add(res.getRGB());
		//UI.info("Color added to Palette");
		try {
			getProject().save();
		} catch (IOException e) {
			PixisUI.error("Cannot save project", e);
		}
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public JButton getBtnNewColor() {
		return btnNewColor;
	}

	public void setBtnNewColor(JButton btnNewColor) {
		this.btnNewColor = btnNewColor;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Palette getPalette() {
		return palette;
	}

	public void setPalette(Palette palette) {
		this.palette = palette;
	}

	public PixisController getController() {
		return controller;
	}

	public void setController(PixisController controller) {
		this.controller = controller;
	}
}
