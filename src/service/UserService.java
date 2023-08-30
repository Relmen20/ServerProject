package service;

import model.EntityUser;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String[] ls = userRepository.getListOfFiles();

        EntityUser entityUser = null;

        if (userRepository.getActualIDFromFile() >= ID) {

            try {
                for (String s : ls) {
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        String extractedNumberStr = matcher.group();
                        int extractedNumber = Integer.parseInt(extractedNumberStr);
                        if (extractedNumber == ID) {
                            entityUser = userRepository.getUser(UserRepository.FILE_PATH + s);
                        }
                    } else {
                        System.out.println("No number found in the input string.");
                    }

                }

            } catch (Exception e) {
            }
        }

        return entityUser;
    }

    public ArrayList<EntityUser> readAllUsers() {
        ArrayList<EntityUser> users = new ArrayList<>();
        EntityUser entityUser;

        String[] ls = userRepository.getListOfFiles();
        for (String fileName : ls) {
            int length = fileName.length();
            String lastPart = fileName.substring(length - 4, length);
            if (lastPart.equals(".ser")) {
                entityUser = userRepository.getUser(UserRepository.FILE_PATH + fileName);
                users.add(entityUser);
            }
        }
        return users;
    }

    public boolean deleteUser(int deleteID) {

        String[] ls = userRepository.getListOfFiles();

        if ((userRepository.getActualIDFromFile() >= deleteID) && (ls != null)) {

            for (String str : ls) {
                int ID = Integer.parseInt(str.substring(7, str.length() - 4));
                if (ID == deleteID) {
                    return userRepository.deleteUser(str);
                }
            }

        }
        return false;
    }

    public ArrayList<Integer> deleteAllUsers() {

        ArrayList<Integer> deletedID = new ArrayList<>();

        String[] ls = userRepository.getListOfFiles();

        if (ls != null) {
            for (String str : ls) {
                String lastPart = str.substring(str.length() - 4, str.length());
                if (lastPart.equals(".ser")) {
                    if (userRepository.deleteUser(str)) {
                        int ID = Integer.parseInt(str.substring(7, str.length() - 4));
                        deletedID.add(ID);
                    }
                }
            }
        } else {
            return null;
        }
        userRepository.setActualIDToFile(0);
        return deletedID;
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