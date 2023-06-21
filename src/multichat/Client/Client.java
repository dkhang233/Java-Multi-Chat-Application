
package multichat.Client;

import multichat.Client.Login_GUI;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Client {
    private Socket socket;
    private DataInputStream is;
    private DataOutputStream os;

    public Client(int port) {
        try {
            socket = new Socket("localhost", port);
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIs() {
        return is;
    }

    public DataOutputStream getOs() {
        return os;
    }
    
    
    
    
    public static void main(String[] args) {
        Client client =  new Client(8000);
        new Login_GUI(client.getIs(),client.getOs());
    }
    
    
}
