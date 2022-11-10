package fr.triedge.sekai.server.storage;

import fr.triedge.sekai.common.model.Model;

public interface Storage {

	public void openStorage(String path);
	public Model loadModel();
}
