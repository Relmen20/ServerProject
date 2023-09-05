package repository;

import model.EntityUser;

import java.io.*;

public class UserRepository {
    private final static String ACTUAL_ID_FILE_NAME = "Object_0.txt";
    public static final String DIRECTORY_PATH = "/home/andrew/IdeaProjects/NativeCSR/repo/src/data/";
    private int actualID;

    public UserRepository(){
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
                                           new FileOutputStream(fullFileName)))
        {
            objectOut.writeObject(obj);
            System.out.printf("Object %s was saved successfully\n", obj.getName());
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public EntityUser getUser(String filepath) {
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filepath))){
            return (EntityUser) objectIn.readObject();
        } catch (Exception e) {
            System.out.println("Exception: " + e);
            return null;
        }
    }

    public String[] getListOfFiles() {
        return (new File(DIRECTORY_PATH)).list();
    }

    public int getActualIDFromFile() {
        int ID = 0;
        try(FileInputStream fileInputStream = new FileInputStream(DIRECTORY_PATH+ACTUAL_ID_FILE_NAME)) {
            ID = Integer.parseInt(String.valueOf(fileInputStream.read()));

        } catch (FileNotFoundException e) {
            setActualIDToFile(ID);
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }

        return ID;
    }

    public void setActualIDToFile(int ID) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(DIRECTORY_PATH+ACTUAL_ID_FILE_NAME, false)) {
            fileOutputStream.write(ID);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        actualID = ID;
    }

    public boolean deleteUser(String str) {
        return (new File(DIRECTORY_PATH + str)).delete();
    }
}