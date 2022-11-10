package fr.triedge.sekai.pixis.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import fr.triedge.sekai.common.model.Map;
import fr.triedge.sekai.common.model.TileEvent;
import fr.triedge.sekai.common.model.TileEventType;
import fr.triedge.sekai.common.model.TileInfo;
import fr.triedge.sekai.common.utils.JSonHelper;
import fr.triedge.sekai.common.utils.Utils;
import fr.triedge.sekai.pixis.controller.PixisController;
import fr.triedge.sekai.pixis.model.EditableMap;
import fr.triedge.sekai.pixis.model.Project;
import fr.triedge.sekai.pixis.model.Tile;

public class MapEditor extends JPanel{

	private static final long serialVersionUID = -2436162250351121940L;
	private static final String LAYER_GROUND				= "Ground";
	private static final String LAYER_OBJECTS				= "Objects";
	private static final String LAYER_EVENTS				= "Events";
	private static final String LAYER_WALK					= "Walkable";

	private Project project;
	private EditableMap editableMap;
	private PixisController controller;
	private MapDisplayer mapDisplayer;
	private ChipsetDisplayer chipsetDisplayer;

	private JButton btnSelectChipset, btnExportMap;
	private JToolBar toolbar;
	private JComboBox<Integer> comboZoomFactor;
	private JComboBox<String> comboCurrentLayer;
	private JCheckBox checkGrid;

	private BufferedImage currentChipset;
	private String currentLayer;
	private Tile selectedTile;
	private JSplitPane splitPane;
	private JScrollPane scrollMapDisplayer, scrollChipsetDisplayer;


	private int zoomFactor = 1;

	public MapEditor(PixisController controller, EditableMap map, Project project) {
		setController(controller);
		setEditableMap(map);
		setProject(project);
	}

	public void build() {
		setLayout(new BorderLayout());
		setToolbar(new JToolBar());
		setBtnSelectChipset(new JButton("Chipset"));
		setBtnExportMap(new JButton("Export"));
		setCheckGrid(new JCheckBox("Grid"));
		setMapDisplayer(new MapDisplayer());
		setChipsetDisplayer(new ChipsetDisplayer());
		setSplitPane(new JSplitPane(JSplitPane.VERTICAL_SPLIT));
		setCurrentLayer(LAYER_GROUND);

		setScrollMapDisplayer(new JScrollPane(getMapDisplayer()));
		getScrollMapDisplayer().getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				redraw();
			}
		});
		setScrollChipsetDisplayer(new JScrollPane(getChipsetDisplayer()));
		getScrollChipsetDisplayer().getViewport().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				redraw();
			}
		});
		getCheckGrid().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				redraw();
			}
		});

		getSplitPane().add(getScrollMapDisplayer());
		getSplitPane().add(getScrollChipsetDisplayer());

		Integer[] comb = {1,2,3,4,5};
		setComboZoomFactor(new JComboBox<Integer>(comb));
		getComboZoomFactor().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				Integer value = (Integer)e.getItem();
				updateZoomFactor(value);
			}
		});
		String[] layers = {LAYER_GROUND, LAYER_OBJECTS, LAYER_EVENTS, LAYER_WALK};
		setComboCurrentLayer(new JComboBox<String>(layers));
		getComboCurrentLayer().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String value = (String)e.getItem();
				setCurrentLayer(value);
				redraw();
			}
		});

		getToolbar().setFloatable(false);
		getToolbar().add(getBtnSelectChipset());
		getToolbar().add(getBtnExportMap());
		getToolbar().add(getCheckGrid());
		getToolbar().add(new JLabel("Layer:"));
		getToolbar().add(getComboCurrentLayer());
		getToolbar().add(new JLabel("Zoom:"));
		getToolbar().add(getComboZoomFactor());

		getBtnSelectChipset().addActionListener(e -> actionSelectChipset());
		getBtnExportMap().addActionListener(e -> actionExportMap());

		add(getToolbar(), BorderLayout.NORTH);
		add(getSplitPane(), BorderLayout.CENTER);

		updateCurrentChipset();
		updateMapDisplayerSize();
	}

	private void actionExportMap() {
		BufferedImage img = new BufferedImage(
				getEditableMap().getMapWidth()*getEditableMap().getTileSize(), 
				getEditableMap().getMapHeight()*getEditableMap().getTileSize(), 
				BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = img.getGraphics();
		drawTilesLayer(getEditableMap().getGoundTiles(), getEditableMap().getTileSize(), g);
		drawTilesLayer(getEditableMap().getObjectTiles(), getEditableMap().getTileSize(), g);
		
		Map map = new Map();
		try {
			map.setChipset(Utils.imageToString(getCurrentChipset()));
			map.setMapImage(Utils.imageToString(img));
			map.setMapName(getEditableMap().getMapName());
			for (Tile tile : getEditableMap().getGoundTiles()) {
				if (!tile.isWalkable()) {
					TileInfo ti = new TileInfo();
					TileEvent te = new TileEvent();
					te.setEventType(TileEventType.BLOCKED);
					ti.getTileEvents().add(te);
					ti.setWalkable(false);
					ti.setX(tile.getX());
					ti.setY(tile.getY());
					map.add(ti);
				}
			}
			File file = PixisUI.showSaveFile(map.getMapName());
			if (file == null)
				return;
			JSonHelper.storeToFile(map, file);
			PixisUI.info("Map exported to "+file.getAbsolutePath());
		} catch (IOException e) {
			PixisUI.error("Cannot save map", e);
			e.printStackTrace();
		}
	}

	private void redraw() {
		this.revalidate();
		this.repaint();
	}

	private class MapDisplayer extends JPanel implements MouseListener, MouseMotionListener{

		private static final long serialVersionUID = -2823063788900209076L;

		public MapDisplayer() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			EditableMap map = getEditableMap();
			if (map == null)
				return;
			int rectWidth = map.getMapWidth()*map.getTileSize()*getZoomFactor();
			int rectHeight = map.getMapHeight()*map.getTileSize()*getZoomFactor();
			// Background color
			g.setColor(Color.darkGray);
			g.fillRect(0, 0, rectWidth, rectHeight);

			// Draw map
			drawTilesLayer(map.getGoundTiles(), map.getTileSize(), g);
			drawTilesLayer(map.getObjectTiles(), map.getTileSize(), g);

			// Draw grid
			if (getCheckGrid().isSelected())
				drawGrid(rectWidth, rectHeight, map.getTileSize(), g);

			// Draw walkable
			if (getCurrentLayer() == LAYER_WALK)
				drawWalkable(map.getGoundTiles(), map.getTileSize(), g);

			// Foreground limit rectangle
			g.setColor(Color.red);
			g.drawRect(0, 0, rectWidth, rectHeight);
		}



		private void drawWalkable(ArrayList<Tile> tiles, int tileSize, Graphics g) {
			for (Tile tile : tiles) {
				int bor = 4*getZoomFactor();
				if (tile.isWalkable()) {
					g.setColor(new Color(109,255,130,100));
					g.fillRect(
							tile.getX()*tileSize*getZoomFactor() + bor, 
							tile.getY()*tileSize*getZoomFactor() + bor, 
							tileSize*getZoomFactor() - bor*2, 
							tileSize*getZoomFactor() - bor*2);
					g.setColor(Color.green);
					g.drawRect(
							tile.getX()*tileSize*getZoomFactor() + bor, 
							tile.getY()*tileSize*getZoomFactor() + bor, 
							tileSize*getZoomFactor() - bor*2, 
							tileSize*getZoomFactor() - bor*2);
				}else {
					g.setColor(new Color(231,0,65,100));
					g.fillRect(
							tile.getX()*tileSize*getZoomFactor() + bor, 
							tile.getY()*tileSize*getZoomFactor() + bor, 
							tileSize*getZoomFactor() - bor*2, 
							tileSize*getZoomFactor() - bor*2);
					g.setColor(Color.red);
					g.drawRect(
							tile.getX()*tileSize*getZoomFactor() + bor, 
							tile.getY()*tileSize*getZoomFactor() + bor, 
							tileSize*getZoomFactor() - bor*2, 
							tileSize*getZoomFactor() - bor*2);
				}
			}
		}

		private void drawGrid(int width, int height, int tileSize, Graphics g) {
			for (int x = 0; x < width; ++x) {
				if ((x/getZoomFactor()) % tileSize == 0) {
					g.setColor(Color.lightGray);
					g.drawLine(x, 0, x, height);
				}
			}
			for (int y = 0; y < height; ++y) {
				if ((y/getZoomFactor()) % tileSize == 0) {
					g.setColor(Color.lightGray);
					g.drawLine(0, y, width, y);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e)) {
				leftClick(e);
			}else if(SwingUtilities.isRightMouseButton(e)) {
				rightClick(e);
			}
		}

		private void updateWalkableTile(int x, int y, boolean b) {
			Tile tile = getTileAt(getEditableMap().getGoundTiles(), x, y);
			if (tile == null)
				return;
			tile.setWalkable(b);
			redraw();
			saveProject();
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

		@Override
		public void mouseDragged(MouseEvent e) {
			int b1 = MouseEvent.BUTTON1_DOWN_MASK;
			int b3 = MouseEvent.BUTTON3_DOWN_MASK;
			if ((e.getModifiersEx() & (b1 | b3)) == b1) {
				leftClick(e);
			}else if((e.getModifiersEx() & (b1 | b3)) == b3) {
				rightClick(e);
			}
		}

		public void leftClick(MouseEvent e) {
			int x = Utils.roundDown((e.getX()/getEditableMap().getTileSize())/getZoomFactor());
			int y = Utils.roundDown((e.getY()/getEditableMap().getTileSize())/getZoomFactor());
			switch (getCurrentLayer()) {
			case LAYER_GROUND:
				if (getSelectedTile() != null) {
					updateTile(x,y);
				}
				break;
			case LAYER_OBJECTS:
				if (getSelectedTile() != null) {
					updateTile(x,y);
				}
				break;
			case LAYER_WALK:
				updateWalkableTile(x,y,true);
				break;
			default:
				break;
			}
		}

		public void rightClick(MouseEvent e) {
			int x = Utils.roundDown((e.getX()/getEditableMap().getTileSize())/getZoomFactor());
			int y = Utils.roundDown((e.getY()/getEditableMap().getTileSize())/getZoomFactor());
			switch (getCurrentLayer()) {
			case LAYER_GROUND:
				fillTiles(e.getX(), e.getY());
				break;
			case LAYER_OBJECTS:
				break;
			case LAYER_WALK:
				updateWalkableTile(x,y,false);
				break;
			default:
				break;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}

		private void fillTiles(int mouseX, int mouseY) {
			if (getSelectedTile() != null) {
				int x = Utils.roundDown((mouseX/getEditableMap().getTileSize())/getZoomFactor());
				int y = Utils.roundDown((mouseY/getEditableMap().getTileSize())/getZoomFactor());

				ArrayList<Tile> currentLayer = getEditableMap().getGoundTiles();
				switch (getCurrentLayer()) {
				case LAYER_OBJECTS:
					currentLayer = getEditableMap().getObjectTiles();
					break;
				}

				Tile target = getTileAt(currentLayer, x, y);
				fillTilesProcess(target, currentLayer);
			}
		}

		private void fillTilesProcess(Tile target, ArrayList<Tile> currentLayer) {
			ArrayList<Tile> tilesToUpdate = new ArrayList<>();
			fillTile(tilesToUpdate, currentLayer, target, target);
			for (Tile tile : tilesToUpdate) {
				updateTile(tile.getX(), tile.getY());
			}
		}

		private void fillTile(ArrayList<Tile> tilesToUpdate,ArrayList<Tile> currentLayer, Tile currentTile, Tile previousTile) {
			// System.out.println("");
			// System.out.println("Current: "+currentTile);
			// System.out.println("Previous: "+previousTile);
			if (tilesToUpdate.contains(currentTile))
				return;
			if (currentTile.getChipsetX() == previousTile.getChipsetX() && currentTile.getChipsetY() == previousTile.getChipsetY()) {
				tilesToUpdate.add(currentTile);
				//System.out.println("Added: "+currentTile);
			}
			// Get Tile up
			if (currentTile.getY()-1 >= 0) {
				Tile up = getTileAt(currentLayer, currentTile.getX(), currentTile.getY()-1);
				if (up != null)
					fillTile(tilesToUpdate, currentLayer, up, currentTile);
			}

			// Get Tile down
			if (currentTile.getY()+1 <= getEditableMap().getMapHeight()) {
				Tile down = getTileAt(currentLayer, currentTile.getX(), currentTile.getY()+1);
				if (down != null)
					fillTile(tilesToUpdate, currentLayer, down, currentTile);
			}

			// Get Tile right
			if (currentTile.getX()+1 <= getEditableMap().getMapHeight()) {
				Tile right = getTileAt(currentLayer, currentTile.getX()+1, currentTile.getY());
				if (right != null)
					fillTile(tilesToUpdate, currentLayer, right, currentTile);
			}

			// Get Tile right
			if (currentTile.getX()-1 >= 0) {
				Tile left = getTileAt(currentLayer, currentTile.getX()-1, currentTile.getY());
				if (left != null)
					fillTile(tilesToUpdate, currentLayer, left, currentTile);
			}
		}


		/**
		 * 
		 * @param x - X in grid not px
		 * @param y - Y in grid not px
		 */
		private void updateTile(int x, int y) {
			if (getSelectedTile() != null) {
				if (x >= getEditableMap().getMapWidth() || y >= getEditableMap().getMapHeight())
					return;
				getSelectedTile().setX(x);
				getSelectedTile().setY(y);
				Tile newTile = getSelectedTile().copy();

				switch(getCurrentLayer()) {
				case LAYER_GROUND:
					removeTilesAt(getEditableMap().getGoundTiles(), x, y);
					getEditableMap().getGoundTiles().add(newTile);
					break;
				case LAYER_OBJECTS:
					removeTilesAt(getEditableMap().getObjectTiles(), x, y);
					getEditableMap().getObjectTiles().add(newTile);
					break;
				default:
					break;
				}
				redraw();
				saveProject();
			}
		}
	}

	private void drawTilesLayer(ArrayList<Tile> tiles, int tileSize, Graphics g) {
		for (Tile tile : tiles) {
			if (getCurrentChipset() == null) {
				System.out.println("Current chipset null, cannot paint");
				break;
			}
			int size = tileSize;
			g.drawImage(
					getCurrentChipset(), 
					tile.getX() * size * getZoomFactor(), 
					tile.getY() * size * getZoomFactor(), 
					((tile.getX() * size) + size) * getZoomFactor(), 
					((tile.getY() * size) + size) * getZoomFactor(), 
					tile.getChipsetX() * size, 
					tile.getChipsetY() * size, 
					(tile.getChipsetX() * size) + size, 
					(tile.getChipsetY() * size) + size, 
					null);
		}
	}

	private Tile getTileAt(ArrayList<Tile> tiles, int x, int y) {
		for (Tile tile : tiles)
			if (tile.getX() == x && tile.getY() == y)
				return tile;
		return null;
	}

	private void removeTilesAt(ArrayList<Tile> tiles, int x, int y) {
		Iterator<Tile> it = tiles.iterator();
		while(it.hasNext()) {
			Tile tile = it.next();
			if (tile.getX() == x && tile.getY() == y)
				it.remove();
		}
	}

	private void saveProject() {
		try {
			getProject().save();
		} catch (IOException e) {
			PixisUI.error("Cannot save project", e);
		}
	}

	private class ChipsetDisplayer extends JPanel implements MouseListener{

		private static final long serialVersionUID = -3000027470876270723L;

		public ChipsetDisplayer() {
			addMouseListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			EditableMap map = getEditableMap();
			if (map == null || getCurrentChipset() == null)
				return;
			g.drawImage(getCurrentChipset(), 0, 0, null);
			if (getSelectedTile() != null) {
				int size = map.getTileSize();
				g.setColor(Color.red);
				//creates a copy of the Graphics instance
				Graphics2D g2d = (Graphics2D) g.create();

				//set the stroke of the copy, not the original 
				Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{4,2}, 0);
				g2d.setStroke(dashed);

				//gets rid of the copy
				g2d.drawRect(
						getSelectedTile().getChipsetX()*size, 
						getSelectedTile().getChipsetY()*size, 
						size, 
						size);
				g2d.dispose();
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (getEditableMap() != null) {
					int chipsetX = Utils.roundDown(e.getX() / getEditableMap().getTileSize());
					int chipsetY = Utils.roundDown(e.getY() / getEditableMap().getTileSize());
					Tile tile = new Tile();
					tile.setChipsetX(chipsetX);
					tile.setChipsetY(chipsetY);
					setSelectedTile(tile);
					redraw();
				}
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

	private void updateChipsetDisplayerSize() {
		if (getCurrentChipset() == null)
			return;
		Dimension dim = new Dimension(getCurrentChipset().getWidth(),getCurrentChipset().getHeight());
		getChipsetDisplayer().setPreferredSize(dim);
		getChipsetDisplayer().setSize(dim);
		redraw();
	}

	private void updateMapDisplayerSize() {
		if (getEditableMap() != null) {
			Dimension dim = new Dimension(
					(getEditableMap().getMapWidth()*getEditableMap().getTileSize()*getZoomFactor()) + 1, 
					(getEditableMap().getMapHeight()*getEditableMap().getTileSize()*getZoomFactor()) + 1);
			getMapDisplayer().setPreferredSize(dim);
			getMapDisplayer().setSize(dim);
			redraw();
		}
	}

	public void actionSelectChipset() {
		File chipset = PixisUI.showChipsetChooser();
		if (chipset == null)
			return;
		String chipString;
		try {
			chipString = Utils.imageToString(ImageIO.read(chipset));
			getEditableMap().setChipset(chipString);
			getProject().save();
			updateCurrentChipset();
		} catch (IOException e) {
			PixisUI.error("Cannot load chipset image", e);
		}
	}

	private void updateZoomFactor(int value) {
		setZoomFactor(value);
		updateMapDisplayerSize();
		redraw();
	}

	private void updateCurrentChipset() {
		if (getEditableMap() != null && getEditableMap().getChipset() != null) {
			try {
				setCurrentChipset(Utils.stringToImage(getEditableMap().getChipset()));
				updateChipsetDisplayerSize();
				redraw();
			} catch (IOException e) {
				PixisUI.error("Cannot load current chipset", e);
			}
		}else {
			PixisUI.warn("Current chipset is null, please select one");
		}
	}

	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public PixisController getController() {
		return controller;
	}
	public void setController(PixisController controller) {
		this.controller = controller;
	}

	public EditableMap getEditableMap() {
		return editableMap;
	}

	public void setEditableMap(EditableMap editableMap) {
		this.editableMap = editableMap;
	}

	public JButton getBtnSelectChipset() {
		return btnSelectChipset;
	}

	public void setBtnSelectChipset(JButton btnSelectChipset) {
		this.btnSelectChipset = btnSelectChipset;
	}

	public JToolBar getToolbar() {
		return toolbar;
	}

	public void setToolbar(JToolBar toolbar) {
		this.toolbar = toolbar;
	}

	public MapDisplayer getMapDisplayer() {
		return mapDisplayer;
	}

	public void setMapDisplayer(MapDisplayer mapDisplayer) {
		this.mapDisplayer = mapDisplayer;
	}

	public int getZoomFactor() {
		return zoomFactor;
	}

	public void setZoomFactor(int zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	public BufferedImage getCurrentChipset() {
		return currentChipset;
	}

	public void setCurrentChipset(BufferedImage currentChipset) {
		this.currentChipset = currentChipset;
	}

	public JComboBox<Integer> getComboZoomFactor() {
		return comboZoomFactor;
	}

	public void setComboZoomFactor(JComboBox<Integer> comboZoomFactor) {
		this.comboZoomFactor = comboZoomFactor;
	}

	public JComboBox<String> getComboCurrentLayer() {
		return comboCurrentLayer;
	}

	public void setComboCurrentLayer(JComboBox<String> comboCurrentLayer) {
		this.comboCurrentLayer = comboCurrentLayer;
	}

	public String getCurrentLayer() {
		return currentLayer;
	}

	public void setCurrentLayer(String currentLayer) {
		this.currentLayer = currentLayer;
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

	public void setSplitPane(JSplitPane splitPane) {
		this.splitPane = splitPane;
	}

	public ChipsetDisplayer getChipsetDisplayer() {
		return chipsetDisplayer;
	}

	public void setChipsetDisplayer(ChipsetDisplayer chipsetDisplayer) {
		this.chipsetDisplayer = chipsetDisplayer;
	}

	public JScrollPane getScrollMapDisplayer() {
		return scrollMapDisplayer;
	}

	public void setScrollMapDisplayer(JScrollPane scrollMapDisplayer) {
		this.scrollMapDisplayer = scrollMapDisplayer;
	}

	public JScrollPane getScrollChipsetDisplayer() {
		return scrollChipsetDisplayer;
	}

	public void setScrollChipsetDisplayer(JScrollPane scrollChipsetDisplayer) {
		this.scrollChipsetDisplayer = scrollChipsetDisplayer;
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
	}

	public JButton getBtnExportMap() {
		return btnExportMap;
	}

	public void setBtnExportMap(JButton btnExportMap) {
		this.btnExportMap = btnExportMap;
	}

	public JCheckBox getCheckGrid() {
		return checkGrid;
	}

	public void setCheckGrid(JCheckBox checkGrid) {
		this.checkGrid = checkGrid;
	}

}
