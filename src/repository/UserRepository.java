package repository;

import model.EntityUser;

import java.io.*;

public class UserRepository {
    private final static String ACTUAL_ID_FILE_PATH = "/home/andrew/IdeaProjects/NativeCSR/repo/src/data/Object_0.txt";
    public static final String FILE_PATH = "/home/andrew/IdeaProjects/NativeCSR/repo/src/data/";
    private int actualID;

    public UserRepository() {
        this.actualID = getActualIDFromFile();
    }

    public void createUser(EntityUser obj) {

        if (obj.getID() == 0) {
            actualID++;
            setActualIDToFile(actualID);
            obj.setID(actualID);
        }
        String fullFileName = "Object_" + obj.getID() + ".ser";

        try(ObjectOutputStream objectOut = new ObjectOutputStream(
                                           new FileOutputStream(
                                           new File(FILE_PATH, fullFileName))))
        {
            objectOut.writeObject(obj);
            System.out.printf("Object %s was saved successfully\n", obj.getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public EntityUser getUser(String filepath) {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath))){

            return (EntityUser) objectIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getListOfFiles() {
        return (new File(FILE_PATH)).list();
    }

    public int getActualIDFromFile() {
        int ID = 0;
        try {
            File file = new File(ACTUAL_ID_FILE_PATH);
            if (file.exists() && !file.isDirectory()) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                ID = Integer.parseInt(String.valueOf(in.readLine()));
            } else {
                createFileActualID(ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ID;
    }

    public void createFileActualID(int actualID) {
        try(FileWriter fr = new FileWriter(ACTUAL_ID_FILE_PATH)){
            fr.write(String.valueOf(actualID));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setActualIDToFile(int ID) {
        try {
            File f = new File(ACTUAL_ID_FILE_PATH);
            if (f.delete()) {
                createFileActualID(ID);
            } else {
                System.out.println("Failure");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        actualID = ID;
    }

    public boolean deleteUser(String str) {
        return (new File(FILE_PATH + str)).delete();
    }
}