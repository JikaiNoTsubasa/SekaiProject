package fr.triedge.sekai.common.test;

import java.io.File;
import java.io.IOException;


import fr.triedge.sekai.common.model.Character;
import fr.triedge.sekai.common.model.Model;
import fr.triedge.sekai.common.model.User;
import fr.triedge.sekai.common.utils.JSonHelper;

public class GenerateAllDefaultServerFiles {

	public static void main(String[] args) {
		Model model = new Model();
		User user = new User();
		user.setId(8888);
		user.setUsername("test");
		user.setPassword("test");
		
		Character cha = new Character();
		cha.setName("Jikai");
		
		user.getCharacters().add(cha);
		
		model.getUsers().add(user);
		
		try {
			JSonHelper.storeToFile(model, new File("server/storage/FileStorage/model.json"));
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
