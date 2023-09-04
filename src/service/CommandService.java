package service;

import model.CommandList;
import model.EntityUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class CommandService {

    private final UserService userService = new UserService();


    public HashMap<String, Object> processComand(HashMap<String, Object> receivedData) {

        HashMap<String, Object> dataForRespond = new HashMap<>();

        try{
            CommandList command = CommandList.valueOf(receivedData.keySet().iterator().next());

            switch (Objects.requireNonNull(command)) {

                case CREATE:

                    String info = userService.createUser(receivedData.get(command.name()));
                    dataForRespond.put(command.name(), info);
                    break;

                case READ:

                    int ID = (int) receivedData.get(command.name());
                    EntityUser user = userService.readUser(ID);
                    dataForRespond.put(command.name(), user != null ? user : "No such user");

                    break;

                case READ_ALL:

                    ArrayList<EntityUser> users = userService.readAllUsers();
                    dataForRespond.put(command.name(), users);

                    break;

                case DELETE:

                    int deleteID = (int) receivedData.get(command.name());
                    boolean deleteResult = userService.deleteUser(deleteID);
                    dataForRespond.put(command.name(), deleteResult);
                    break;

                case DELETE_ALL:

                    ArrayList<Integer> deletedID = userService.deleteAllUsers();
                    dataForRespond.put(command.name(), deletedID);
                    break;

                case UPDATE:

                    String updateInfo = userService.updateUser(receivedData.get(command.name()));
                    dataForRespond.put(command.name(), updateInfo);

                    break;
            }
        }catch (Exception e){}

        return dataForRespond;
    }
}