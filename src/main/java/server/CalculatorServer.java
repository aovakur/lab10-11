package server;

import java.net.*;
import java.io.*;

public class CalculatorServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private Calcualtor calcualtor;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            calcualtor = new Calcualtor();
            while (true) {
                clientSocket = serverSocket.accept();
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String inputLine = in.readLine();
                    if ("end".equals(inputLine)) break;
                    else out.println(calcualtor.calc(inputLine));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CalculatorServer server = new CalculatorServer();
        server.start(8888);
    }
}

