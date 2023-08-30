package service;

import model.CommandList;
import model.EntityUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

public class CommandService {

    final static Pattern IsIntegerPattern = Pattern.compile("^\\d+$");

    private final UserService userService = new UserService();


    public HashMap<String, Object> processComand(HashMap<String, Object> receivedData) {

        HashMap<String, Object> data = new HashMap<>();

        CommandList cmd = CommandList.fromString(receivedData.keySet().iterator().next());

        switch (Objects.requireNonNull(cmd)) {

            case CREATE:

                String info = userService.createUser(receivedData.get(cmd.getCommand()));
                data.put(cmd.getCommand(), info);
                break;

            case READ:

                int ID = (int) receivedData.get(cmd.getCommand());
                EntityUser user = userService.readUser(ID);
                data.put(cmd.getCommand(), user != null ? user : "No such user");

                break;

            case READ_ALL:

                ArrayList <EntityUser> users = userService.readAllUsers();
                data.put(cmd.getCommand(), users);

                break;

            case DELETE:

                int deleteID = (int) receivedData.get(cmd.getCommand());
                boolean deleteResult = userService.deleteUser(deleteID);
                data.put(cmd.getCommand(), deleteResult);
                break;
//            case UPDATE:
//
//
//
//            default:

        }

        return data;
    }

    private boolean isInteger(String str) {
        return IsIntegerPattern.matcher(str).matches();
    }
}
