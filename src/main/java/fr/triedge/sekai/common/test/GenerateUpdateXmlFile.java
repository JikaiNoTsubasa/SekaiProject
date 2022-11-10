package fr.triedge.sekai.common.test;

import java.io.File;
import java.io.IOException;

import fr.triedge.sekai.common.model.Update;
import fr.triedge.sekai.common.model.UpdateElement;
import fr.triedge.sekai.common.utils.JSonHelper;

public class GenerateUpdateXmlFile {

	public static void main(String[] args) {
		String VERSION = "0.2";
		
		Update update = new Update(VERSION);
		
		UpdateElement el1 = new UpdateElement("http://triedge.fr/sekai/client_0.2/SekaiClient.jar","SekaiClient.jar");
		UpdateElement el3 = new UpdateElement("http://triedge.fr/sekai/client_0.2/data/img/Dummy.png","data/img/Dummy.png");
		UpdateElement el5 = new UpdateElement("http://triedge.fr/sekai/client_0.2/data/characters/zeldap.png","data/characters/zeldap.png");
		UpdateElement el6 = new UpdateElement("http://triedge.fr/sekai/client_0.2/data/characters/link_32x16_transparent.png","data/characters/link_32x16_transparent.png");
		UpdateElement el7 = new UpdateElement("http://triedge.fr/sekai/client_0.2/data/characters/link_32x16.png","data/characters/link_32x16.png");
		
		update.getElements().add(el3);
		update.getElements().add(el5);
		update.getElements().add(el6);
		update.getElements().add(el7);
		update.getElements().add(el1);
		
		try {
			JSonHelper.storeToFile(update, new File("dev/update.json"));
			//ZipHelper.zipFolder("D:\\Dev\\GIT\\DCTMUI", "dev/client_"+VERSION+".zip");
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
