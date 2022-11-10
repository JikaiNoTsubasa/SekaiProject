package fr.triedge.sekai.pixis.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import fr.triedge.sekai.pixis.utils.Icons;

public class PixisTreeRenderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = -6484838419926434290L;
	//private static Logger log = LogManager.getLogger(PixisTreeRenderer.class);

	ImageIcon leafIcon;
	ImageIcon paletteIcon;
	ImageIcon fileIcon;
	ImageIcon mapIcon;
	ImageIcon spriteIcon;
	ImageIcon layerIcon;
	ImageIcon projectIcon;
	ImageIcon folderClosedIcon;
	ImageIcon folderOpenedIcon;

	public PixisTreeRenderer() {
		setIconTextGap(5);
		spriteIcon = Icons.spriteIcon;
		paletteIcon = Icons.paletteIcon;
		layerIcon = Icons.layerIcon;
		mapIcon = Icons.mapIcon;
		fileIcon = Icons.fileIcon;
		projectIcon = Icons.projectIcon;
		folderClosedIcon = Icons.folderClosedIcon;
		folderOpenedIcon = Icons.folderOpenedIcon;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean isLeaf, int row, boolean focused) {
		Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, isLeaf, row, focused);
		Node node = (Node) value;
		switch (node.type) {
		case ALL:
			break;
		case FOLDER:
			if (expanded)
				setIcon(folderOpenedIcon);
			else
				setIcon(folderClosedIcon);
			break;
		case MAP:
			setIcon(mapIcon);
			break;
		case PALETTE:
			setIcon(paletteIcon);
			break;
		case PROJECT:
			setIcon(projectIcon);
			break;
		case ROOT:
			if (expanded)
				setIcon(folderOpenedIcon);
			else
				setIcon(folderClosedIcon);
			break;
		case SPRITE:
			setIcon(spriteIcon);
			break;
		default:
			setIcon(fileIcon);
			break;

		}
		/*
		 * if (isLeaf) { if (value.toString().endsWith(Const.EXT_SPRITE))
		 * setIcon(spriteIcon); else if (!value.toString().contains("."))
		 * setIcon(folderClosedIcon); else setIcon(fileIcon); } else { if
		 * (value.toString().endsWith(Const.EXT_PROJECT)) { setIcon(projectIcon); }else
		 * { if(expanded) { setIcon(folderOpenedIcon); }else {
		 * setIcon(folderClosedIcon); } } }
		 */
		return c;
	}
}
