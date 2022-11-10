package fr.triedge.sekai.pixis.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;
import javax.swing.SwingUtilities;

import fr.triedge.sekai.pixis.controller.PixisController;

public class PixisTreeListener implements MouseListener {

	private JTree tree;
	private PixisController controller;
	private MainWindow mainWindow;

	public PixisTreeListener(MainWindow win, JTree tree, PixisController controller) {
		setTree(tree);
		setController(controller);
		setMainWindow(win);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			int row = tree.getClosestRowForLocation(e.getX(), e.getY());
			tree.setSelectionRow(row);
			getMainWindow().openTreeMenu(tree, e.getX(), e.getY());
		}

		if (e.getClickCount() == 2) {
			int row = tree.getClosestRowForLocation(e.getX(), e.getY());
			tree.setSelectionRow(row);
			getMainWindow().doubleClicked();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public PixisController getController() {
		return controller;
	}

	public void setController(PixisController controller) {
		this.controller = controller;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

}
