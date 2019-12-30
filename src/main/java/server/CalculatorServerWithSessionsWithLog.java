package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

public class CalculatorServerWithSessionsWithLog {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        while (true) new Session(serverSocket.accept()).start();
    }

    public static void main(String[] args) throws IOException {
        CalculatorServerWithSessionsWithLog server = new CalculatorServerWithSessionsWithLog();
        server.start(8888);
    }

    private static class Session extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream out;
        private BufferedReader in;
        private Calcualtor calcualtor;
        private List<String> log;

        public Session(Socket socket) throws SocketException {
            this.calcualtor = new Calcualtor();
            this.log = new LinkedList<String>();
            this.clientSocket = socket;
            clientSocket.setSoTimeout(60000);
        }

        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (true) {
                    String inputLine = in.readLine();
                    log.add(inputLine);
                    if ("end".equals(inputLine)) break;
                    else if ("get_logs".equals(inputLine)) out.writeObject(log);
                    else out.writeObject(Double.valueOf(calcualtor.calc(inputLine)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

