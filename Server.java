import java.io.*;
import java.net.*;
import utilities.*;

import java.sql.ResultSet;
import java.util.*;

public class Server {

    private ServerSocket serverSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;



    public void start() {
        System.out.println("Running server on port 5000....");
        try {
            serverSocket = new ServerSocket(5000);

            boolean run = true;

            while (true) {
                Socket socket = null;
                socket = serverSocket.accept();

                System.out.println("Connected to client : " + socket);
                InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                Thread clientHandler = new ClientHandler(socket, inputStreamReader, printWriter, bufferedReader);

                clientHandler.start();

            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
