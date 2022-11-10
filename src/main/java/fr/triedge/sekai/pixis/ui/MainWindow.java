package fr.triedge.sekai.pixis.ui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.triedge.sekai.pixis.controller.PixisController;
import fr.triedge.sekai.pixis.utils.Const;


public class MainWindow extends JFrame{

	private static final long serialVersionUID = -2521639473225704163L;
	private static Logger log = LogManager.getLogger(MainWindow.class);

	private JMenuBar bar;
	private JMenu menuFile;
	private JMenuItem itemQuit, itemNewProject, itemOpenProject, itemNewSprite;
	private JSplitPane splitPane;
	private JTree tree;
	private JTabbedPane tabPane;
	private JPopupMenu treePopup;

	private PixisController controller;

	public MainWindow(PixisController controller) {
		setController(controller);
	}

	public void build() {
		setTitle("Pixis");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSplitPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT));
		//getSplitPane().setDividerSize(2);
		getSplitPane().setResizeWeight(0.2);
		setContentPane(getSplitPane());
		
		// Setup tree
		setupTree();
		
		// Setup popup
		setupTreePopup();

		setTabPane(new JTabbedPane());
		getTabPane().addMouseListener(new PixisTabListener(getTabPane()));
		getSplitPane().add(new JScrollPane(getTree()));
		getSplitPane().add(getTabPane());

		// Setup menu
		setBar(new JMenuBar());

		setMenuFile(new JMenu("File"));

		setItemQuit(new JMenuItem("Quit"));
		getItemQuit().addActionListener(e -> getController().actionQuit());
		setItemNewProject(new JMenuItem("New Project"));
		getItemNewProject().addActionListener(e -> getController().actionNewProject());
		setItemOpenProject(new JMenuItem("Open Project"));
		getItemOpenProject().addActionListener(e -> getController().actionOpenProject());

		getMenuFile().add(getItemNewProject());
		getMenuFile().add(new JSeparator());
		getMenuFile().add(getItemQuit());
		getBar().add(getMenuFile());

		setJMenuBar(getBar());

		// Windows closing
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				getController().actionQuit();
			}
		});
		log.debug("Windows built");
	}
	
	private void setupTreePopup() {
		setTreePopup(new JPopupMenu("Project Menu"));
		JMenuItem itemNewSprite = new JMenuItem("New Sprite");
		getTreePopup().add(itemNewSprite);
	}
	
	public void openTreeMenu(Component c, int x, int y) {
		Node node = (Node)getTree().getLastSelectedPathComponent();
		// String name = node.getUserObject().toString();
		// Create dynamic menu
		setTreePopup(new JPopupMenu("Project Menu"));
		if (node.allowedChildrenType == NodeType.PROJECT) {
			JMenuItem itemNewP = new JMenuItem("New Project");
			itemNewP.addActionListener(e -> getController().actionNewProject());
			JMenuItem itemOpenP = new JMenuItem("Open Project");
			itemOpenP.addActionListener(e -> getController().actionOpenProject());
			getTreePopup().add(itemNewP);
			getTreePopup().add(itemOpenP);
		}
		if (node.allowedChildrenType == NodeType.SPRITE || node.allowedChildrenType == NodeType.ALL) {
			JMenuItem item = new JMenuItem("New Sprite");
			item.addActionListener(e -> getController().actionNewNode(node,NodeType.SPRITE));
			getTreePopup().add(item);
		}
		if (node.allowedChildrenType == NodeType.PALETTE || node.allowedChildrenType == NodeType.ALL) {
			JMenuItem item = new JMenuItem("New Palette");
			item.addActionListener(e -> getController().actionNewNode(node,NodeType.PALETTE));
			getTreePopup().add(item);
		}
		if (node.allowedChildrenType == NodeType.MAP || node.allowedChildrenType == NodeType.ALL) {
			JMenuItem item = new JMenuItem("New Map");
			item.addActionListener(e -> getController().actionNewNode(node,NodeType.MAP));
			getTreePopup().add(item);
		}
		getTreePopup().show(c, x, y);
	}

	private void setupTree() {
		// Setup tree http://www.informit.com/articles/article.aspx?p=26327
		Node root = new Node(Const.PROJECTS, NodeType.ROOT, NodeType.PROJECT);
		setTree(new JTree(root));
		getController().setTreeModel((DefaultTreeModel)getTree().getModel());
		getTree().setShowsRootHandles(true);
		getTree().setRowHeight(20);
		getTree().setCellRenderer(new PixisTreeRenderer());
		getTree().addMouseListener(new PixisTreeListener(this,getTree(),getController()));
	}
	
	public void doubleClicked() {
		Node node = (Node)getTree().getLastSelectedPathComponent();
		String name = node.getUserObject().toString();
		Node prjNode = PixisUI.getProjectFromChild(node);
		if (node.type == NodeType.SPRITE) {
			getController().actionDisplaySpriteSheet(prjNode.getUserObject().toString(), name);
		}
		if (node.type == NodeType.PALETTE) {
			getController().actionDisplayPalette(prjNode.getUserObject().toString(), name);
		}
		if (node.type == NodeType.MAP) {
			getController().actionDisplayMap(prjNode.getUserObject().toString(), name);
		}
	}

	public JMenuBar getBar() {
		return bar;
	}

	public void setBar(JMenuBar bar) {
		this.bar = bar;
	}

	public JMenu getMenuFile() {
		return menuFile;
	}

	public void setMenuFile(JMenu menuFile) {
		this.menuFile = menuFile;
	}

	public JMenuItem getItemQuit() {
		return itemQuit;
	}

	public void setItemQuit(JMenuItem itemQuit) {
		this.itemQuit = itemQuit;
	}

	public PixisController getController() {
		return controller;
	}

	public void setController(PixisController controller) {
		this.controller = controller;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public JMenuItem getItemNewProject() {
		return itemNewProject;
	}

	public void setItemNewProject(JMenuItem itemNewProject) {
		this.itemNewProject = itemNewProject;
	}

	public JTree getTree() {
		return tree;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public JTabbedPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(JTabbedPane tabPane) {
		this.tabPane = tabPane;
	}

	public JMenuItem getItemOpenProject() {
		return itemOpenProject;
	}

	public void setItemOpenProject(JMenuItem itemOpenProject) {
		this.itemOpenProject = itemOpenProject;
	}

	public JMenuItem getItemNewSprite() {
		return itemNewSprite;
	}

	public void setItemNewSprite(JMenuItem itemNewSprite) {
		this.itemNewSprite = itemNewSprite;
	}

	public JPopupMenu getTreePopup() {
		return treePopup;
	}

	public void setTreePopup(JPopupMenu treePopup) {
		this.treePopup = treePopup;
	}

}
