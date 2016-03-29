package com.dangerducks.cookit.utils;
import com.dangerducks.cookit.kitchen.*;

import android.content.Context;
import android.util.Log;
import java.util.Vector;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;

/**
 * Created by alex on 3/26/16.
 */
public class FileManager {

    public static String DIR = "C:\\Users\\rogeliomiguel\\workspace\\SaveRecipe\\bin\\";

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

    public static String[] openFolder(String path){//DIR

        File folder = new File(path);

        String[] recipes = folder.list(new FilenameFilter(){
            public boolean accept(File path, String name){
                return name.toLowerCase().endsWith(".obj");
            }
        });

        return recipes;
    }

    public static Recipe loadRecipe(String name){

        Recipe aux = new Recipe();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(DIR + name ));
            aux = (Recipe)in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("Receta inexistente");
        }

        return aux;
    }

    public static boolean saveRecipe(Recipe recipe){

        String fileName = recipe.name + ".obj";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DIR + fileName));
            out.writeObject(recipe);
            out.close();
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        return false;

    }

    public static Vector<Recipe> getRecipes(){
        Vector<Recipe> recipes = new Vector<Recipe>();
        String[] objects = openFolder(DIR);
        Recipe aux;

        for(String name: objects){
            aux = FileManager.loadRecipe(name);
            recipes.add(aux);
        }

        return recipes;

    }



}
