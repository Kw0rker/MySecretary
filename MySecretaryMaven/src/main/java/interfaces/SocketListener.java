package interfaces;

import util.SocketThread;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public interface SocketListener {
    SocketThread socketThreadCreated(Thread thread);

    ServerSocket socketCreated(ServerSocket socket);

    Socket socketAccepted(Socket socket);

    InputStreamReader inputStreamCreated(InputStreamReader inputStreamReader);

    String messageGet(String message);
}
