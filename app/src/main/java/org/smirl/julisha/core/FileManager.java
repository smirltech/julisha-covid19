package org.smirl.julisha.core;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;

import java.io.*;
import java.util.Scanner;

public class FileManager {
    public static final String ASSET_HOME_DIR = "file:///android_asset/";

    public static File getBaseDir(Context context, String dirname){
        return context.getExternalFilesDir(dirname);
    }

    public static String readFromRaw(Context context, int resId) {
        Resources res = context.getResources();
        StringBuffer buffer = new StringBuffer();
        //String txt = "";
        try {
            Scanner reader = new Scanner(res.openRawResource(resId));
            while (reader.hasNextLine()) {
                //   txt+=reader.nextLine()+"\n";
                buffer.append(reader.nextLine());
                buffer.append("\n");
            }
            reader.close();
        } catch (Exception e) {
        }
        return buffer.toString();
    }

    public static void playAudio(Context context, int resId) {
        if (resId != 0) {
            MediaPlayer player = MediaPlayer.create(context, resId);
            player.start();
        }
    }

    public static void writeToFile(String path, String content) {
        writeToFile(new File(path), content);
    }


    public static String writeToFile(File file, String content) {

        FileWriter writer = null;
        try {
            if (!file.exists())
                file.createNewFile();

            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            return e.toString();
        }

    }

    public static String readFromFile(String path) {
        return readFromFile(new File(path));
    }


    public static String readFromFile(File file) {
        BufferedReader reader = null;
        StringBuilder buffer = new StringBuilder();
        String line = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
        } catch (IOException ioe) {
        }
        return buffer.toString();
    }

    public static String readFromAssets(Context ctx, String path) throws IOException {
        InputStream in = ctx.getAssets().open(path);
        byte[] buffer = new byte[in.available()];
        while (in.read(buffer) > -1)
            System.out.println(buffer);
        return new String(buffer);
    }
}
