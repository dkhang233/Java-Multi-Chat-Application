package multichat.Server;

import multichat.Server.ServerThread;
import multichat.Server.ServerThreadBus;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerThreadBus serverThreadBus;
    private ServerSocket serverSocket;

    public ServerThreadBus getServerThreadBus() {
        return serverThreadBus;
    }

    public void setServerThreadBus(ServerThreadBus serverThreadBus) {
        this.serverThreadBus = serverThreadBus;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverThreadBus = new ServerThreadBus();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(8000);
        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10, // corePoolSize
                100, // maximumPoolSize
                10, // thread timeout
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8) // queueCapacity
        );

        try {
            while (true) {
                Socket s = server.getServerSocket().accept();
                ServerThreadBus serverThreadBus = server.getServerThreadBus();
                ServerThread serverThread = new ServerThread(s,clientNumber++);
                serverThreadBus.add(serverThread);
                System.out.println("Number of running thread : " + serverThreadBus.getNumberOfSeverThread());
                executor.execute(serverThread);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
