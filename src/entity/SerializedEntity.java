package entity;

import java.io.Serializable;
import java.util.HashMap;

public class SerializedEntity implements Serializable {

    private static final long serialVersionUID = 1234567L;
    public HashMap<String, Object> sendMsg;

    public SerializedEntity(HashMap<String, Object> sendMsg){
        this.sendMsg = sendMsg;
    }
}
