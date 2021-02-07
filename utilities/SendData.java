package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SendData {
    private Socket socket;
    private PrintWriter printWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public SendData() {
        try {
            socket = new Socket("localhost", 5000);

            System.out.println("Connected to cline");
            printWriter = new PrintWriter(socket.getOutputStream());
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedReader = new BufferedReader(inputStreamReader);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public static void main(String[] args) {
        SendData sendData = new SendData();
    }
}
