package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class CalculatorServerWithSessions {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) new Session(serverSocket.accept()).start();
    }

    public static void main(String[] args) throws IOException {
        CalculatorServerWithSessions server = new CalculatorServerWithSessions();
        server.start(8888);
    }

    private static class Session extends Thread {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private Calcualtor calcualtor;

        public Session(Socket socket) throws SocketException {
            this.calcualtor = new Calcualtor();
            this.clientSocket = socket;
            clientSocket.setSoTimeout(60000);
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String inputLine = in.readLine();
                    if ("end".equals(inputLine)) break;
                    else out.println(calcualtor.calc(inputLine));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

