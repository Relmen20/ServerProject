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

        try{
            CommandList cmd = CommandList.valueOf(receivedData.keySet().iterator().next());

            switch (Objects.requireNonNull(cmd)) {

                case CREATE:

                    String info = userService.createUser(receivedData.get(cmd.name()));
                    data.put(cmd.name(), info);
                    break;

                case READ:

                    int ID = (int) receivedData.get(cmd.name());
                    EntityUser user = userService.readUser(ID);
                    data.put(cmd.name(), user != null ? user : "No such user");

                    break;

                case READ_ALL:

                    ArrayList<EntityUser> users = userService.readAllUsers();
                    data.put(cmd.name(), users);

                    break;

                case DELETE:

                    int deleteID = (int) receivedData.get(cmd.name());
                    boolean deleteResult = userService.deleteUser(deleteID);
                    data.put(cmd.name(), deleteResult);
                    break;

                case DELETE_ALL:

                    ArrayList<Integer> deletedID = userService.deleteAllUsers();
                    data.put(cmd.name(), deletedID);
                    break;

                case UPDATE:

                    String updateInfo = userService.updateUser(receivedData.get(cmd.name()));
                    data.put(cmd.name(), updateInfo);

                    break;
            }
        }catch (Exception e){}

        return data;
    }
}
