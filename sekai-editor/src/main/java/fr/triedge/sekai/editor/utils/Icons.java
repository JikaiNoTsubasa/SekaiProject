package fr.triedge.sekai.editor.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Icons {

	public static ImageIcon spriteIcon;
	public static ImageIcon paletteIcon;
	public static ImageIcon layerIcon;
	public static ImageIcon mapIcon;
	public static ImageIcon fileIcon;
	public static ImageIcon projectIcon;
	public static ImageIcon folderClosedIcon;
	public static ImageIcon folderOpenedIcon;
	public static ImageIcon saveIcon;
	public static ImageIcon refreshIcon;
	public static ImageIcon colorWheelIcon;
	
	static {
		try {
			spriteIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_male.png")));
			colorWheelIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_colorWheel.png")));
			refreshIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_refresh.png")));
			saveIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_save.png")));
			paletteIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_palette.png")));
			layerIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_layer.png")));
			mapIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_map.png")));
			fileIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_file.png")));
			projectIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_project.png")));
			folderClosedIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_folderClosed.png")));
			folderOpenedIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_folderOpened.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
