package fr.triedge.sekai.server.storage;

import java.io.File;
import java.io.IOException;

import fr.triedge.sekai.common.model.Model;
import fr.triedge.sekai.common.utils.JSonHelper;

public class XmlStorage implements Storage{
	
	private File file;

	@Override
	public void openStorage(String path) {
		setFile(new File(path));
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public Model loadModel() {
		try {
			Model model = JSonHelper.loadFromFile(getFile());
			return model;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
