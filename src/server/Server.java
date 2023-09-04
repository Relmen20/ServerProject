package server;

import model.SerializedWrapper;
import service.CommandService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Server implements Runnable {

    private final CommandService commandService;

    private final Socket socket;

    public Server(CommandService commandService, Socket socket) {
        this.socket = socket;
        this.commandService = commandService;
    }

    @Override
    public void run() {
        try {
            HashMap<String, Object> receivedData;

            while(!socket.isClosed()){
                receivedData = catchRespond();
                System.out.printf("key --> %s  value --> %s\n", receivedData.keySet().iterator().next(), receivedData.get(receivedData.keySet().iterator().next()));

                receivedData = commandService.processComand(receivedData);

                sendRequest(receivedData);

                System.out.printf("key --> %s  value --> %s\n",
                        receivedData.keySet().iterator().next(),
                        receivedData.get(receivedData.keySet().iterator().next()));
            }
        } catch (Exception e) {
            System.out.println("run Exception : " + e);
        }
    }

    public void sendRequest(HashMap<String, Object> sendHashMap) {
        try(ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            out.writeObject(new SerializedWrapper(sendHashMap));
            out.flush();
        } catch (Exception e) {
            System.out.println("sender Exception : " + e);
        }
    }

    public HashMap<String, Object> catchRespond() {

        HashMap<String, Object> sendHashMap = null;

        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            SerializedWrapper receivedSerializedData = (SerializedWrapper) in.readObject();
            sendHashMap = receivedSerializedData.getMapWrapper();

        } catch (Exception e) {
            System.out.println("catcher Exception : " + e);
        }

        return sendHashMap;
    }
}
