package repository;

import model.EntityUser;

import java.io.*;

public class UserRepository {
    private final static String ACTUAL_ID_FILE_PATH = "/home/andrew/IdeaProjects/NativeCSR/repo/src/data/Object_0.txt";
    private static final String FILE_PATH = "/home/andrew/IdeaProjects/NativeCSR/repo/src/data/";
    private int actualID;

    public UserRepository() {
        this.actualID = getActualIDFromFile();
    }

    public void objectToFile(EntityUser obj) {

        actualID++;

        obj.setID(actualID);

        try {
            String fullFileName = "Object_" + actualID + ".ser";
            FileOutputStream fileOut = new FileOutputStream(new File(FILE_PATH, fullFileName));
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
            System.out.printf("Object %s was saved successfully\n", obj.getName());
            setActualIDToFile(actualID);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public EntityUser getObject(String filepath) {
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            EntityUser entityUser = (EntityUser) objectIn.readObject();

            objectIn.close();
            return entityUser;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFILEPATH() {
        return FILE_PATH;
    }

    public String[] getListOfFiles() {
        return (new File(FILE_PATH)).list();
    }

    public int getActualIDFromFile() {
        int ID = 0;
        try {
            File file = new File(ACTUAL_ID_FILE_PATH);
            if(file.exists() && !file.isDirectory()) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                ID = Integer.parseInt(String.valueOf(in.readLine()));
            }else{
                createFileActualID(ID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ID;
    }

    public void createFileActualID(int actualID) {

        File file = new File(ACTUAL_ID_FILE_PATH);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file);
            fr.write(String.valueOf(actualID));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close resources
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setActualIDToFile(int ID) {
        try {
            File f = new File(ACTUAL_ID_FILE_PATH);
            if(f.delete()){
                createFileActualID(ID);
            }else{
                System.out.println("Failure");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}










