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

    public static String DIR;

    public static void setDIR(Context context) {
        DIR = context.getFilesDir().getPath();
    }

    public static boolean saveUserData(Context context, String user, String pass, String email) {
        String buffer = user + "\t" + email + "\n" + pass;

        File path = context.getFilesDir();
        path = new File(path.getPath() + "private");
        if(!path.mkdir()) path.mkdir();
        File credentials = new File(path, "usr.crd");

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
        path = new File(path.getPath() + "private");
        if(!path.mkdir()) path.mkdir();
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

    public static String[] openFolder(String path){
        path += "/rec/";
        File folder = new File(path);
        if(!folder.exists()) folder.mkdir();

        String[] recipes = folder.list(new FilenameFilter(){
            public boolean accept(File path, String name){
                return name.toLowerCase().endsWith(".obj");
            }
        });

        return recipes;
    }

    public static Recipe loadRecipe(String name, String dir){
        dir = dir + "/rec/";

        Recipe aux = new Recipe();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(dir + name ));
            aux = (Recipe)in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Receta inexistente");
        }

        return aux;
    }

    public static void clearRecipes(String dir) {
        dir += "/rec/";
        File path = new File(dir);
        try {
            for (File recipe:
                 path.listFiles()) {
                recipe.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean saveRecipe(Recipe recipe, String dir){
        dir += "/rec/";
        File path = new File(dir);
        if(!path.exists()) path.mkdir();

        String fileName = recipe.getName() + ".obj";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dir + fileName));
            out.writeObject(recipe);
            out.close();
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        return false;

    }

    public static Vector<Recipe> getRecipes(String dir){
        Vector<Recipe> recipes = new Vector<Recipe>();
        String[] objects = openFolder(dir);
        Recipe aux;

        for(String name: objects){
            aux = FileManager.loadRecipe(name, dir);
            recipes.add(aux);
        }

        return recipes;

    }

////////////////////////////////////////////////////////////////

    public static Category loadCategory(String name, String dir){
        dir = dir + "/rec/";

        Category aux = new Category();
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(dir + name ));
            aux = (Category)in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("Receta inexistente");
        }

        return aux;
    }


    public static boolean saveCategory(Category category, String dir){
        dir += "/rec/";
        File path = new File(dir);
        if(!path.exists()) path.mkdir();

        String fileName = category.getName() + ".obj";
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dir + fileName));
            out.writeObject(category);
            out.close();
            return true;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        return false;

    }

    public static Vector<Category> getCategories(String dir){
        Vector<Category> categories = new Vector<Category>();
        String[] objects = openFolder(dir);
        Category aux;

        for(String name: objects){
            aux = FileManager.loadCategory(name, dir);
            categories.add(aux);
        }

        return categories;

    }

    public static void createBasicCategories(String dir){
        String[] names = {"Mexican", "Oriental", "Italian", "French", "Soup", "Seafood", "Desserts","Beverages"};
        String[] descriptions ={
                "Salsa, Tacos, Guacamole",
                "Rice, Rats, Dog",
                "Pizza, Spaghetti, Mamma mia",
                "Baguette, Lots of cheese and wine",
                "A lot of soups ",
                "Fish, Shrimps, Whale",
                "Sweet desserts for you",
                "Alcohol and no-alcohol"
        };

        for(int i = 0 ; i < 8; i++){
            Category aux = new Category(names[i]);
            aux.setDescription(descriptions[i]);

            FileManager.saveCategory(aux,dir);
        }
    }
}
