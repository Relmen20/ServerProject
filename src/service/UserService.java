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
        try{
            EntityUser user = (EntityUser) entityUser;
            userRepository.objectToFile(user);
            return "Success";
        }catch(Exception e){
            return "Failure";
        }
    }

    public EntityUser readUser(int ID) {
        String[] ls = userRepository.getListOfFiles();

        EntityUser entityUser = null;

        if (ID == 0) {
            return null;

        } else if(userRepository.getActualIDFromFile() >= ID){

            try {
                for (String s : ls) {
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(s);
                    if (matcher.find()) {
                        String extractedNumberStr = matcher.group();
                        int extractedNumber = Integer.parseInt(extractedNumberStr);
                        if (extractedNumber == ID) {
                            entityUser = userRepository.getObject(userRepository.getFILEPATH() + s);
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
        for(String fileName: ls){
            int length = fileName.length();
            String lastPart = fileName.substring(length - 4, length);
            if(lastPart.equals(".ser")) {
                entityUser = userRepository.getObject(userRepository.getFILEPATH() + fileName);
                users.add(entityUser);
            }
        }
        return users;
    }

    public boolean deleteUser(int deleteID) {

        return false;
    }
}