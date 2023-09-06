package service;

import model.EntityUser;
import repository.UserRepository;

import java.util.ArrayList;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public String createUser(Object entityUser) {
        try {
            EntityUser user = (EntityUser) entityUser;
            userRepository.createUser(user);
            return "Success";
        } catch (Exception e) {
            return "Failure";
        }
    }

    public EntityUser readUser(int ID) {
        String[] listOfFileNames = userRepository.getListOfFiles();
        EntityUser entityUser = null;

        if (userRepository.getActualIDFromFile() >= ID && listOfFileNames != null) {
            for (String fileName : listOfFileNames) {
                if (ID == Integer.parseInt(fileName.substring(7, fileName.length() - 4))) {
                    entityUser = userRepository.getUser(UserRepository.DIRECTORY_PATH + "/"  + fileName);
                }
            }
        }
        return entityUser;
    }

    public ArrayList<EntityUser> readAllUsers() {
        ArrayList<EntityUser> allUsers = new ArrayList<>();
        String[] listOfFileNames = userRepository.getListOfFiles();

        for (String fileName : listOfFileNames) {
            if (fileName.endsWith(".ser")) {
                allUsers.add(userRepository.getUser(UserRepository.DIRECTORY_PATH + "/"  + fileName));
            }
        }
        return allUsers;
    }

    public boolean deleteUser(int idToDelete) {
        String[] listOfFileNames = userRepository.getListOfFiles();

        if (userRepository.getActualIDFromFile() >= idToDelete && listOfFileNames != null) {
            for (String fileName : listOfFileNames) {
                if (idToDelete == Integer.parseInt(fileName.substring(7, fileName.length() - 4))) {
                    return userRepository.deleteUser(fileName);
                }
            }
        }
        return false;
    }

    public ArrayList<Integer> deleteAllUsers() {
        ArrayList<Integer> arrayOfDeletedID = new ArrayList<>();
        String[] listOfFileNames = userRepository.getListOfFiles();

        if (listOfFileNames != null) {
            for (String fileName : listOfFileNames) {
                if (fileName.endsWith(".ser") && userRepository.deleteUser(fileName)) {
                    arrayOfDeletedID.add(Integer.parseInt(fileName.substring(7, fileName.length() - 4)));
                }
            }
        } else {
            return null;
        }
        userRepository.setActualIDToFile(0);
        return arrayOfDeletedID;
    }

    public String updateUser(Object object) {
        try {
            EntityUser user = (EntityUser) object;
            if(deleteUser(user.getID())) {
                userRepository.createUser(user);
                return "Success";
            }
            return "Failure";
        } catch (Exception e) {
            return "Failure";
        }
    }
}