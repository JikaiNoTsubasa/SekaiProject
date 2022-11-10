package fr.triedge.sekai.common.test;

import fr.triedge.sekai.common.utils.DefaultFactory;

public class GenerateAllDefaultClientFiles {

	public static void main(String[] args) {
		DefaultFactory.generateDefaultClientConfigFile("client/config/client.json");
		System.out.println("Generated client/config/client.json");
		
		DefaultFactory.generateDefaultUpdateFile("dev/update.json");
		System.out.println("Generated dev/update.json");
	}

}
