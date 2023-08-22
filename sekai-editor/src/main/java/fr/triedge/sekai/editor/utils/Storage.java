package fr.triedge.sekai.editor.utils;

import fr.triedge.sekai.editor.model.Project;
import fr.triedge.sekai.sdk.utils.JSonHelper;

import java.io.File;
import java.io.IOException;


public class Storage {

	public static void storeProject(Project prj, String path) throws IOException {
		JSonHelper.storeToFile(prj, new File(path));
	}
	
	public static void storeProjectToDefault(Project prj, String projectName) throws IOException {
		storeProject(prj, Const.PROJECTS_LOCATION+File.separator+projectName);
	}
	
	public static Project loadProject(File file) throws IOException {
		return JSonHelper.loadFromFile(file);
	}
}
