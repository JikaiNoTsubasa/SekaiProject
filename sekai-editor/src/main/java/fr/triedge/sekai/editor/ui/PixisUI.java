package fr.triedge.sekai.editor.ui;

import fr.triedge.sekai.editor.model.*;
import fr.triedge.sekai.editor.utils.Const;
import fr.triedge.sekai.editor.utils.MapFactory;
import fr.triedge.sekai.sdk.ui.GUI;
import fr.triedge.sekai.sdk.utils.ExceptionDialog;
import fr.triedge.sekai.sdk.utils.Utils;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PixisUI {

	public static void queue(Runnable runnable) {
		SwingUtilities.invokeLater(runnable);
	}
	
	public static Project showNewProject() {
		Project project = null;
		JTextField projectName = new JTextField("project1");
		projectName.requestFocus();

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Project Name:"));
		panel.add(projectName);

		int result = JOptionPane.showConfirmDialog(null, panel, "New Project",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			project = new Project();
			project.setName(projectName.getText().replace(" ", "_")+ Const.EXT_PROJECT);
		}
		return project;
	}
	
	public static SpriteSheet showNewSprite() {
		SpriteSheet spriteSheet = null;
		JTextField textName = new JTextField("sprite1");
		JTextField numCharacterHeight = new JTextField("32");
		JTextField numCharacterWidth = new JTextField("16");
		ComboImageType[] comboTypes = new ComboImageType[4];
		comboTypes[0] = new ComboImageType("TYPE_INT_RGB", BufferedImage.TYPE_INT_RGB);
		comboTypes[1] = new ComboImageType("TYPE_INT_ARGB", BufferedImage.TYPE_INT_ARGB);
		comboTypes[2] = new ComboImageType("TYPE_4BYTE_ABGR", BufferedImage.TYPE_4BYTE_ABGR);
		comboTypes[3] = new ComboImageType("TYPE_BYTE_GRAY", BufferedImage.TYPE_BYTE_GRAY);
		JComboBox<ComboImageType> comboImageType = new JComboBox<>(comboTypes);
		comboImageType.setSelectedIndex(2);

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Sprite Name:"));
		panel.add(textName);
		panel.add(new JLabel("Character Height(px):"));
		panel.add(numCharacterHeight);
		panel.add(new JLabel("Character Width(px):"));
		panel.add(numCharacterWidth);
		panel.add(new JLabel("Image Type:"));
		panel.add(comboImageType);

		int result = JOptionPane.showConfirmDialog(null, panel, "New Sprite",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			spriteSheet = new SpriteSheet();
			spriteSheet.setName(textName.getText().replace(" ", "_")+Const.EXT_SPRITE);
			spriteSheet.setCharacterHeight(Integer.valueOf(numCharacterHeight.getText()));
			spriteSheet.setCharacterWidth(Integer.valueOf(numCharacterWidth.getText()));
			spriteSheet.setImageType(((ComboImageType)comboImageType.getSelectedItem()).value);
			// Create first sprite
			spriteSheet.getLayers().add(new SpriteLayer());
			BufferedImage img = new BufferedImage(spriteSheet.getCharacterWidth(), spriteSheet.getCharacterHeight(), spriteSheet.getImageType());
			Sprite sp = new Sprite();
			spriteSheet.getLayers().get(0).getSprites().add(sp);
			sp.setPosXonSheet(0);
			sp.setPosYonSheet(0);
			try {
				sp.setImageData(Utils.imageToString(img));
			} catch (IOException e) {
				error("Cannot convert default image to bytes", e);
				return null;
			}
		}
		return spriteSheet;
	}
	
	public static EditableMap showNewMap() {
		EditableMap map = null;
		JTextField textName = new JTextField("map1");
		JButton btnSelectChipset = new JButton("??????");
		JTextField numCharacterHeight = new JTextField("50");
		JTextField numCharacterWidth = new JTextField("50");
		JTextField numTileSize = new JTextField("16");

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Map Name:"));
		panel.add(textName);
		panel.add(new JLabel("Chipset"));
		panel.add(btnSelectChipset);
		panel.add(new JLabel("Map Height(blocs):"));
		panel.add(numCharacterHeight);
		panel.add(new JLabel("Map Width(blocs):"));
		panel.add(numCharacterWidth);
		panel.add(new JLabel("Tile Size(px):"));
		panel.add(numTileSize);
		
		btnSelectChipset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = showChipsetChooser();
				if (file != null) {
					btnSelectChipset.setText(file.getName());
					try {
						btnSelectChipset.setToolTipText(Utils.imageToString(ImageIO.read(file)));
					} catch (IOException e1) {
						GUI.error("Cannot load chipset", e1);
					}
				}
			}
		});

		int result = JOptionPane.showConfirmDialog(null, panel, "New Map",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			map = MapFactory.generateDefaultEditableMap(
					textName.getText().replace(" ", "_")+Const.EXT_MAP, 
					Integer.valueOf(numCharacterHeight.getText()), 
					Integer.valueOf(numCharacterWidth.getText()), 
					btnSelectChipset.getToolTipText());
			map.setTileSize(Integer.valueOf(numTileSize.getText()));
		}
		return map;
	}
	
	public static Palette showNewPalette() {
		Palette palette = null;
		JTextField textName = new JTextField("palette1");

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JLabel("Palette Name:"));
		panel.add(textName);

		int result = JOptionPane.showConfirmDialog(null, panel, "New Palette",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			palette = new Palette();
			palette.setName(textName.getText().replace(" ", "_")+Const.EXT_PALETTE);
		}
		return palette;
	}
	
	public static File showSaveFile(String name) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(Const.EXPORT_LOCATION));
		chooser.setSelectedFile(new File(name));
		int res = chooser.showSaveDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	public static File showProjectChooser() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter prjFilter = new FileNameExtensionFilter("Project files", "prj");
		chooser.setFileFilter(prjFilter);
		chooser.setCurrentDirectory(new File(Const.PROJECTS_LOCATION));
		int res = chooser.showDialog(null, "Open Project");
		if (res == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	public static File showChipsetChooser() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter prjFilter = new FileNameExtensionFilter("Chipset", "png");
		chooser.setFileFilter(prjFilter);
		chooser.setCurrentDirectory(new File(Const.CHIPSET_LOCATION));
		int res = chooser.showDialog(null, "Open Chipset");
		if (res == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}
		return null;
	}
	
	public static Node getChildByName(Node projectNode, String name) {
		int count = projectNode.getChildCount();
		for (int i = 0 ; i < count ; ++i) {
			Node node = (Node)projectNode.getChildAt(i);
			if (node.getUserObject().toString().equals(name))
				return node;
		}
		return null;
	}
	
	public static Node getChildByAllowedType(Node projectNode, NodeType type) {
		int count = projectNode.getChildCount();
		for (int i = 0 ; i < count ; ++i) {
			Node node = (Node)projectNode.getChildAt(i);
			if (node.allowedChildrenType == type)
				return node;
		}
		return null;
	}
	
	public static Node getProjectFromChild(Node childNode) {
		if (childNode.getUserObject().toString().endsWith(Const.EXT_PROJECT))
			return childNode;
		else {
			if (childNode.getParent() == null)
				return null;
			else {
				return getProjectFromChild((Node)childNode.getParent());
			}
		}
	}
	
	public static Node sortNodes(Node node) {
	    //sort alphabetically
	    for(int i = 0; i < node.getChildCount() - 1; i++) {
	        Node child = (Node) node.getChildAt(i);
	        String nt = child.getUserObject().toString();

	        for(int j = i + 1; j <= node.getChildCount() - 1; j++) {
	            Node prevNode = (Node) node.getChildAt(j);
	            String np = prevNode.getUserObject().toString();

	            if(nt.compareToIgnoreCase(np) > 0) {
	                node.insert(child, j);
	                node.insert(prevNode, i);
	            }
	        }
	        if(child.getChildCount() > 0) {
	            sortNodes(child);
	        }
	    }

	    //put folders first - normal on Windows and some flavors of Linux but not on Mac OS X.
	    for(int i = 0; i < node.getChildCount() - 1; i++) {
	        Node child = (Node) node.getChildAt(i);
	        for(int j = i + 1; j <= node.getChildCount() - 1; j++) {
	            Node prevNode = (Node) node.getChildAt(j);

	            if(!prevNode.isLeaf() && child.isLeaf()) {
	                node.insert(child, j);
	                node.insert(prevNode, i);
	            }
	        }
	    }
	    return node;
	}
	
	public static Node createProjectNode(Project prj) {
		if (prj == null)
			return null;
		Node prjNode = new Node(prj.getName(), NodeType.PROJECT, NodeType.ALL);
		Node spritesNode = new Node(Const.SPRITES, NodeType.FOLDER, NodeType.SPRITE);
		Node palettesNode = new Node(Const.PALETTES, NodeType.FOLDER, NodeType.PALETTE);
		Node mapsNode = new Node(Const.MAPS, NodeType.FOLDER, NodeType.MAP);
		for (SpriteSheet sp : prj.getSprites()) {
			spritesNode.add(new Node(sp.getName(), NodeType.SPRITE, NodeType.SPRITE));
		}
		for (EditableMap sp : prj.getMaps()) {
			mapsNode.add(new Node(sp.getMapName(), NodeType.MAP, NodeType.MAP));
		}
		for (Palette sp : prj.getPalettes()) {
			palettesNode.add(new Node(sp.getName(), NodeType.PALETTE, NodeType.PALETTE));
		}
		prjNode.add(spritesNode);
		prjNode.add(palettesNode);
		prjNode.add(mapsNode);
		return prjNode;
	}

	public static Node placeNode(Node projectNode, Node node) {
		Node targetNode = getChildByAllowedType(projectNode, node.type);
		targetNode.add(node);
		return targetNode;
	}
	
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
}
