package fr.triedge.sekai.pixis.ui;

import javax.swing.tree.DefaultMutableTreeNode;

public class Node extends DefaultMutableTreeNode{

	private static final long serialVersionUID = -6985361219024797196L;

	public NodeType type = NodeType.ROOT;
	public NodeType allowedChildrenType = NodeType.FOLDER;
	
	public Node(String name) {
		super(name);
	}
	
	public Node(String name, NodeType type, NodeType allowedChildrenType) {
		super(name);
		this.type = type;
		this.allowedChildrenType = allowedChildrenType;
	}
}
