package util;

import interfaces.SocketListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketThread implements Runnable {
    private boolean isInterrupted = false;
    private ServerSocket serverSocket;
    private BufferedReader reader;
    private InputStreamReader stream;
    private Socket socket;
    private SocketListener listener;
    private int port;

    public SocketThread(SocketListener listener, int port) {
        this.listener = listener;
        this.port = port;
        new Thread(this).start();
    }

    public void interrupt() {
        isInterrupted = true;
    }

    @Override
    public void run() {
        try {

            serverSocket = new ServerSocket(port);
            listener.socketCreated(serverSocket);
            socket = serverSocket.accept();
            listener.socketAccepted(socket);
            stream = new InputStreamReader(socket.getInputStream());
            listener.inputStreamCreated(stream);
            reader = new BufferedReader(stream);
            while (!isInterrupted) {
                listener.messageGet(reader.readLine());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
