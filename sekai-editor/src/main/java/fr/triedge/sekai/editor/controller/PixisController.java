package fr.triedge.sekai.editor.controller;

import fr.triedge.sekai.editor.model.*;
import fr.triedge.sekai.editor.ui.*;
import fr.triedge.sekai.editor.utils.*;
import fr.triedge.sekai.sdk.ui.GUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// https://github.com/qxo/eclipse-metrics-plugin
public class PixisController {

	
	private MainWindow mainWindow;
	private DefaultTreeModel treeModel;
	private static Logger log;
	private ArrayList<Project> projects = new ArrayList<>();

	public static void main(String[] args) {
		PixisController ctrl = new PixisController();
		try {
			ctrl.init();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		MainWindow win = new MainWindow(ctrl);
		ctrl.setMainWindow(win);
		win.build();
		win.setVisible(true);
	}
	
	public void init() throws FileNotFoundException, IOException {
		// Configure logging
		configureLogging();

		log.debug("Starting initialization...");


		log.debug("Initialization completed");
	}

	public void actionQuit() {
		log.debug("ACTION CALLED: Quit");
		PixisUI.queue(new Runnable() {

			@Override
			public void run() {
				getMainWindow().dispose();
			}
		});
		log.debug("ACTION DONE: Quit");
	}

	public void actionNewProject() {
		log.debug("ACTION CALLED: New Project");
		Project prj = PixisUI.showNewProject();
		if (prj != null) {
			getProjects().add(prj);
			try {
				prj.save();
				PixisUI.queue(new Runnable() {

					@Override
					public void run() {
						Node prjNode = PixisUI.createProjectNode(prj);
						Node root = (Node)getTreeModel().getRoot();
						root.add(prjNode);
						getTreeModel().nodeStructureChanged(PixisUI.sortNodes(root));
					}
				});
				log.debug("Project created: "+ prj.getName());
			} catch (IOException e) {
				GUI.error("Cannot save project "+prj.getName(), e);
				log.error("Cannot save project "+prj.getName(),e);
			}
		}else {
			log.warn("Project is null");
		}
		log.debug("ACTION DONE: New Project");
	}

	public void actionOpenProject() {
		log.debug("ACTION CALLED: Open Project");
		File file = PixisUI.showProjectChooser();
		if (file == null)
			return;
		if (file.getName().endsWith(Const.EXT_PROJECT)) {
			try {
				Project prj = Storage.loadProject(file);
				getProjects().add(prj);
				PixisUI.queue(new Runnable() {

					@Override
					public void run() {
						Node prjNode = PixisUI.createProjectNode(prj);
						Node root = (Node)getMainWindow().getTree().getModel().getRoot();
						root.add(prjNode);
						getTreeModel().nodeStructureChanged(PixisUI.sortNodes(root));
					}
				});
			} catch (IOException e) {
				GUI.error("Cannot load project", e);
				log.error("Cannot load project",e);
			}
		}
		log.debug("ACTION DONE: Open Project");
	}

	public void actionNewSprite(Node node) {
		log.debug("ACTION CALLED: New Sprite");
		SpriteSheet sprite = PixisUI.showNewSprite();
		if (sprite != null) {
			// Get current project and it's corresponding node
			Node projectNode = PixisUI.getProjectFromChild(node);
			String projectName = projectNode.getUserObject().toString();
			Project prj = getProjectByName(projectName);
			// Add sprite to project
			prj.getSprites().add(sprite);

			// Create node
			Node spriteNode = new Node(sprite.getName(), NodeType.SPRITE, NodeType.SPRITE);
			placeNodeInTree(projectNode, spriteNode);
		}else {
			log.warn("Sprite is null");
		}
		log.debug("ACTION DONE: New Sprite");
	}

	public void actionNewMap(Node node) {
		log.debug("ACTION CALLED: New Map");
		EditableMap map = PixisUI.showNewMap();
		if (map != null) {
			// Get current project and it's corresponding node
			Node projectNode = PixisUI.getProjectFromChild(node);
			String projectName = projectNode.getUserObject().toString();
			Project prj = getProjectByName(projectName);
			// Add sprite to project
			prj.getMaps().add(map);

			// Create node
			Node mapNode = new Node(map.getMapName(), NodeType.MAP, NodeType.MAP);
			placeNodeInTree(projectNode, mapNode);
		}else {
			log.warn("Map is null");
		}
		log.debug("ACTION DONE: New Map");
	}

	public void actionNewPalette(Node node) {
		log.debug("ACTION CALLED: New Palette");
		Palette pal = PixisUI.showNewPalette();
		if (pal != null) {
			// Get current project and it's corresponding node
			Node projectNode = PixisUI.getProjectFromChild(node);
			String projectName = projectNode.getUserObject().toString();
			Project prj = getProjectByName(projectName);
			// Add sprite to project
			prj.getPalettes().add(pal);

			// Create node
			Node paletteNode = new Node(pal.getName(), NodeType.PALETTE, NodeType.PALETTE);
			placeNodeInTree(projectNode, paletteNode);
		}else {
			log.warn("Palette is null");
		}
		log.debug("ACTION DONE: New Palette");
	}

	public void actionNewNode(Node node, NodeType type) {
		log.debug("ACTION CALLED: New Node of type: "+type);
		// Get current project and it's corresponding node
		Node projectNode = PixisUI.getProjectFromChild(node);
		String projectName = projectNode.getUserObject().toString();
		Project prj = getProjectByName(projectName);
		Node createdNode = null;

		switch(type){
		case PALETTE:
			Palette pal = PixisUI.showNewPalette();
			if (pal == null)
				return;
			prj.getPalettes().add(pal);
			createdNode = new Node(pal.getName(), NodeType.PALETTE, NodeType.PALETTE);
			break;
		case SPRITE:
			SpriteSheet sheet = PixisUI.showNewSprite();
			if (sheet == null)
				return;
			prj.getSprites().add(sheet);
			createdNode = new Node(sheet.getName(), NodeType.SPRITE, NodeType.SPRITE);
			break;
		case MAP:
			EditableMap map = PixisUI.showNewMap();
			if (map == null)
				return;
			prj.getMaps().add(map);
			createdNode = new Node(map.getMapName(), NodeType.MAP, NodeType.MAP);
			break;
		default:
			break;

		}
		try {
			prj.save();
		} catch (IOException e) {
			log.error("Cannot save project",e);
		}
		placeNodeInTree(projectNode, createdNode);
		log.debug("ACTION DONE: New Node of type: "+type);
	}

	public void placeNodeInTree(Node projectNode, Node node) {
		PixisUI.queue(new Runnable() {

			@Override
			public void run() {
				Node targetNode = PixisUI.placeNode(projectNode, node);
				getTreeModel().nodeStructureChanged(PixisUI.sortNodes(targetNode));
				getMainWindow().getTree().expandPath(new TreePath(targetNode.getPath()));
			}
		});
	}

	public void actionDisplaySpriteSheet(String projectName, String spriteName) {
		JTabbedPane tabs = getMainWindow().getTabPane();
		// Check if tab already exists
		int tabCount = tabs.getTabCount();
		for (int i = 0; i < tabCount; ++i) {
			String title = tabs.getTitleAt(i);
			if (title.equals(spriteName)) {
				tabs.setSelectedIndex(i);
				return;
			}
		}
		Project prj = getProjectByName(projectName);
		if (prj == null) {
			log.warn("Tried to open sprite but project is null");
			return;
		}
		SpriteSheet sp = getSpriteSheetByName(prj, spriteName);
		if (sp == null) {
			log.warn("Tried to open sprite but sprite is null");
			return;
		}

		SpriteEditor editor = new SpriteEditor(this,sp,prj);
		editor.build();
		tabs.addTab(spriteName, Icons.spriteIcon, editor);
		tabs.setSelectedComponent(editor);
	}
	
	public void actionDisplayPalette(String projectName, String paletteName) {
		JTabbedPane tabs = getMainWindow().getTabPane();
		int tabCount = tabs.getTabCount();
		for (int i = 0; i < tabCount; ++i) {
			String title = tabs.getTitleAt(i);
			if (title.equals(paletteName)) {
				tabs.setSelectedIndex(i);
				return;
			}
		}
		Project prj = getProjectByName(projectName);
		if (prj == null) {
			log.warn("Tried to open palette but project is null");
			return;
		}
		Palette pal = getPaletteByName(prj, paletteName);
		if (pal == null) {
			log.warn("Tried to open palette but sprite is null");
			return;
		}
		
		PaletteEditor editor = new PaletteEditor(this,pal,prj);
		editor.build();
		tabs.addTab(paletteName, Icons.paletteIcon, editor);
		tabs.setSelectedComponent(editor);
	}
	
	public void actionDisplayMap(String projectName, String mapName) {
		JTabbedPane tabs = getMainWindow().getTabPane();
		int tabCount = tabs.getTabCount();
		for (int i = 0; i < tabCount; ++i) {
			String title = tabs.getTitleAt(i);
			if (title.equals(mapName)) {
				tabs.setSelectedIndex(i);
				return;
			}
		}
		Project prj = getProjectByName(projectName);
		if (prj == null) {
			log.warn("Tried to open map but project is null");
			return;
		}
		EditableMap map = getMapByName(prj, mapName);
		if (map == null) {
			log.warn("Tried to open map but sprite is null");
			return;
		}
		
		MapEditor editor = new MapEditor(this,map,prj);
		editor.build();
		tabs.addTab(mapName,Icons.mapIcon, editor);
		tabs.setSelectedComponent(editor);
	}

	private void configureLogging() throws FileNotFoundException, IOException {
		// Set configuration file for log4j2
		ConfigurationSource source = new ConfigurationSource(new FileInputStream(Const.CONFIG_LOG_LOCATION));
		Configurator.initialize(null, source);
		log = LogManager.getLogger(PixisController.class);
	}

	public Project getProjectByName(String name) {
		for(Project p : getProjects())
			if (p.getName().equals(name))
				return p;
		return null;
	}

	public Palette getPaletteByName(Project project, String name) {
		for(Palette p : project.getPalettes())
			if (p.getName().equals(name))
				return p;
		return null;
	}
	
	public EditableMap getMapByName(Project project, String name) {
		for(EditableMap p : project.getMaps())
			if (p.getMapName().equals(name))
				return p;
		return null;
	}

	public SpriteSheet getSpriteSheetByName(Project project, String name) {
		for (SpriteSheet s : project.getSprites())
			if (s.getName().equals(name))
				return s;
		return null;
	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public void setTreeModel(DefaultTreeModel model) {
		this.treeModel = model;
	}

	public DefaultTreeModel getTreeModel() {
		return this.treeModel;
	}

	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}

}
