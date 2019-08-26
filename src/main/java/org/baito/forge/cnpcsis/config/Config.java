package org.baito.forge.cnpcsis.config;

import org.json.JSONObject;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

public class Config {
    public static HashMap<String, SaveItem> items = new HashMap<>();
    public static String fs = System.getProperty("file.separator");
    public static Path conf;

    public static void setup() {
        if (!conf.resolve("cnsis").toFile().exists()) {
            conf.resolve("cnsis").toFile().mkdirs();
        }
    }

    public static void load() {
        File[] files = conf.resolve("cnsis").toFile().listFiles();
        for (File i : files) {
            JSONObject j = loadFile(i);
            items.put(j.getString("mappingName"), new SaveItem(j));
        }
    }

    public static void createFile(SaveItem s) {
        File f = new File(conf.resolve("cnsis").resolve(s.mappingName + ".json").toString());
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            PrintWriter pw = new PrintWriter(f);
            pw.print(s.toJson().toString(4));
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject loadFile(File conf) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(conf));
            String line = null;
            String ls = System.getProperty("line.separator");
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(ls);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject(sb.toString());
    }
}
