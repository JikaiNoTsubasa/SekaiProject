package fr.triedge.sekai.sdk.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;

public class JSonHelper {

    public static <T> T loadFromFile(File file) throws IOException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(file), new TypeToken<>(){});
    }

    public static void storeToFile(Object obj, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(gson.toJson(obj));
        bw.flush();
        bw.close();
    }
}
