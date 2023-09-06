package server;

import service.CommandService;

import java.io.EOFException;
import java.io.InputStream;
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

    public void run() {
        try {
            HashMap<String, Object> receivedData;

            while (!socket.isClosed() && (receivedData = catchRespond()) != null) {
                System.out.printf("key --> %s  value --> %s\n",
                        receivedData.keySet().iterator().next(),
                        receivedData.get(receivedData.keySet().iterator().next()));

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
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(sendHashMap);
            objectOutputStream.flush();
        } catch (Exception e) {
            System.out.println("sender Exception : " + e);
        }
    }


    @SuppressWarnings("unchecked")
    public HashMap<String, Object> catchRespond() {
        HashMap<String, Object> sendHashMap = new HashMap<>();
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            sendHashMap = (HashMap<String, Object>) objectInputStream.readObject();
        } catch (EOFException e) {
            return null;
            // end of data in InputStream
        } catch (Exception e) {
            System.out.println("catcher Exception : " + e);
        }

        return sendHashMap;
    }
}