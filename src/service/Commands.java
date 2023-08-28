package service;

import entity.EntityUser;
import java.util.HashMap;
import java.util.Objects;

public class Commands {

    private UserInteraction userInteraction = new UserInteraction();


    public HashMap<String, Object> comandHandler(HashMap<String, Object> receivedData) {

        HashMap<String, Object> data = new HashMap<>();

        CommandList cmd = CommandList.valueOfLabel(receivedData.keySet().iterator().next());

        switch (Objects.requireNonNull(cmd)) {

            case CREATE:

                String info = userInteraction.createUser(receivedData.get(cmd.getCommand()));
                data.put(cmd.getCommand(), info);
                break;

            case READ:

                int ID = (int) receivedData.get(cmd.getCommand());
                EntityUser user = userInteraction.readUser(ID);
                data.put(cmd.getCommand(), user != null ? user : "No such user");
                break;

//            case UPDATE:
//
//            case DELETE:
//
//            default:

        }

        return data;
    }

}
