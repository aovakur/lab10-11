package client;

import java.net.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CalculatorClient {
    private Socket clientSocket;
    private PrintWriter out;
    private ObjectInputStream in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new ObjectInputStream(clientSocket.getInputStream());
    }

    public void sendMessage(String msg) throws IOException, ClassNotFoundException {
        out.println(msg);
        Double result = (Double) in.readObject();
        System.out.println(result.toString());
    }

    public void getLogs() throws IOException, ClassNotFoundException {
        out.println("get_logs");
        List<String> log = (LinkedList<String>) in.readObject();
        System.out.println(log);
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        CalculatorClient client = new CalculatorClient();
        client.startConnection("127.0.0.1", 8888);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputLine = scanner.nextLine();
            if ("end".equals(inputLine)) break;
            else if ("get_logs".equals(inputLine)) client.getLogs();
            else client.sendMessage(inputLine);
        }
    }
}
