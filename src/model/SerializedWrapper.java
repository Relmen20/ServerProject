package model;

import java.io.Serializable;
import java.util.HashMap;

public class SerializedWrapper implements Serializable {

    private static final long serialVersionUID = 1234567L;
    private HashMap<String, Object> mapWrapper;

    public SerializedWrapper(HashMap<String, Object> mapWrapper) {
        this.mapWrapper = mapWrapper;
    }

    public HashMap<String, Object> getMapWrapper() {
        return mapWrapper;
    }
}
