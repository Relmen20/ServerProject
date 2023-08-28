package server;

import entity.SerializedEntity;
import service.Commands;

import java.io.EOFException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Server implements Runnable {

    private final Commands command;

    private final Socket socket;

    public Server(Commands commands, Socket socket) {
        this.socket = socket;
        this.command = commands;
    }

    @Override
    public void run() {
        try {

            HashMap<String, Object> receivedData;

            receivedData = catcher();
            System.out.printf("key --> %s  value --> %s\n", receivedData.keySet().iterator().next(), receivedData.get(receivedData.keySet().iterator().next()));

            receivedData = command.comandHandler(receivedData);

            sender(receivedData);

            System.out.printf("key --> %s  value --> %s\n", receivedData.keySet().iterator().next(), receivedData.get(receivedData.keySet().iterator().next()));

        } catch (Exception e) {
            System.out.println("run Exception : " + e);
        }
    }

    public void sender(HashMap<String, Object> sendHashMap) {
        try {
            SerializedEntity sendData;

            OutputStream south = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(south);

            sendData = new SerializedEntity(sendHashMap);
            out.writeObject(sendData);

            out.flush();
            out.close();
        } catch (Exception e) {
            System.out.println("sender Exception : " + e);
        }
    }

    public HashMap<String, Object> catcher() {

        HashMap<String, Object> sendHashMap = null;

        try {
            InputStream sin = socket.getInputStream();
            ObjectInputStream in = new ObjectInputStream(sin);

            SerializedEntity receivedSerializedData;
            receivedSerializedData = (SerializedEntity) in.readObject();
            sendHashMap = receivedSerializedData.sendMsg;
        } catch (Exception e) {
            System.out.println("catcher Exception : " + e);
        }

        return sendHashMap;
    }

}