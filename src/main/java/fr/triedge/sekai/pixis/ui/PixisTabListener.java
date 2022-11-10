package fr.triedge.sekai.pixis.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class PixisTabListener implements MouseListener{

	private JTabbedPane tabPane;
	
	public PixisTabListener(JTabbedPane tabPane) {
		super();
		this.tabPane = tabPane;
	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(JTabbedPane tabPane) {
		this.tabPane = tabPane;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			PixisUI.queue(new Runnable() {
				
				@Override
				public void run() {
					JPopupMenu pop = new JPopupMenu();
					JMenuItem itemClose = new JMenuItem("Close this tab");
					JMenuItem itemCloseAll = new JMenuItem("Close all tabs");
					itemClose.addActionListener(e -> closeTab(tabPane.getSelectedIndex()));
					itemCloseAll.addActionListener(e -> closeAllTabs());
					pop.add(itemClose);
					pop.add(itemCloseAll);
					pop.show(tabPane, e.getX(), e.getY());
					
				}

				private void closeTab(int selectedIndex) {
					tabPane.remove(selectedIndex);
				}
				
				private void closeAllTabs() {
					tabPane.removeAll();
				}
			});
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
}
