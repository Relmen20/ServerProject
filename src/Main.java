import server.Server;
import service.CommandService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        ServerSocket srvSocket = null;

        CommandService commandService = new CommandService();

        try {
            try {

                srvSocket = new ServerSocket(6666, 1, InetAddress.getByName("localhost"));

                System.out.println("Server started\n\n");

                while (true) {

                    Socket socket = srvSocket.accept();

                    Server server = new Server(commandService, socket);

                    server.run();
                }

            } catch (Exception e) {
                System.out.println("main Exception : " + e);
            }
        } finally {
            try {
                if (srvSocket != null)
                    srvSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}