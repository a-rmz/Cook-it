package com.dangerducks.cookit.utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by alex on 3/26/16.
 */
public class FileManager {

    public static boolean saveUserData(Context context, String user, String pass, String email) {
        String buffer = user + "\t" + email + "\n" + pass;

        File path = context.getFilesDir();
        File credentials = new File(path, "usr.crd");
        Log.v("dir: ", "cred dir: " + credentials.toString());

        try {
            FileOutputStream stream = new FileOutputStream(credentials);
            stream.write(buffer.getBytes());
            stream.close();
            Log.v("dir: ", "Saved data successfully");
        } catch (Exception e) {
            Log.v("saveData: ", "Failed to save data");
            return false;
        }

        return true;
    }

    public static String[] loadUserData(Context context) {
        String cred;
        File path = context.getFilesDir();
        File credentials = new File(path, "usr.crd");

        int length = (int) credentials.length();
        byte [] bytes = new byte[length];

        try {
            FileInputStream inputStream = new FileInputStream(credentials);
            inputStream.read(bytes);
            inputStream.close();

            cred = new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("loadData: ", "Failed to load data");
            return null;
        }

        String user = cred.substring(0, cred.indexOf("\t"));
        String email = cred.substring(cred.indexOf("\t") + 1, cred.indexOf("\n"));
        String pass = cred.substring(cred.indexOf("\n") + 1, cred.length());

        return new String[] {user, pass, email};
    }

    public static boolean deleteUserData(Context context) {
        File path = context.getFilesDir();
        File credentials = new File(path, "usr.crd");

        return credentials.delete();
    }
}
