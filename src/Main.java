import server.Server;
import service.CommandService;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {

        CommandService commandService = new CommandService();
        try(ServerSocket srvSocket = new ServerSocket(6666, 1, InetAddress.getByName("localhost"))){
            System.out.println("Server started\n\n");

            while (true) {
                try{
                    Socket socket = srvSocket.accept();
                    Server server = new Server(commandService, socket);
                    server.run();
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