import server.Server;
import service.CommandService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {

        CommandService commandService = new CommandService();

        try(ServerSocket srvSocket = new ServerSocket(6666, 1, InetAddress.getByName("localhost"))) {
            System.out.println("Server started\n\n");

            while (true) {
                try(Socket socket = srvSocket.accept()){
                    Server server = new Server(commandService, socket);
                    server.run();
//                    Thread clientThread = new Thread(new Server(commandService, socket));
//                    clientThread.start();
                }catch (Exception e){
                    System.out.println("socket Exception " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("srvSocket Exception " + e.getMessage());
        }
        System.exit(0);
    }
}