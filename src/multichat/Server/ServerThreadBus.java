package multichat.Server;

import multichat.Server.ServerThread;
import java.util.ArrayList;
import java.util.List;

public class ServerThreadBus {

    private ArrayList<ServerThread> severThreadBus;

    public ArrayList<ServerThread> getSeverThreadBus() {
        return severThreadBus;
    }

    public void setSeverThreadBus(ArrayList<ServerThread> severThreadBus) {
        this.severThreadBus = severThreadBus;
    }

    public ServerThreadBus() {
        this.severThreadBus = new ArrayList<ServerThread>();
    }

    public void add(ServerThread serverThread) {
        severThreadBus.add(serverThread);
        for (int i = 0; i < this.severThreadBus.size(); i++) {
            this.severThreadBus.get(i).setServerThreadBus(this);
        }

    }

    public void sendToGlobal(String message, String user) {
        for (ServerThread x : severThreadBus) {
            if (x.getUser().equals(user)) {
                continue;
            }
            x.write("message_group---" + user + "---" + message);
            System.out.println("\n" + this.getNumberOfSeverThread());
        }
    }

    public void sendToAll(String message) {
        for (ServerThread x : severThreadBus) {
            x.write(message);
        }
    }

    public void sendToPerson(String message, String sendUser, String receiver) {
        for (ServerThread x : severThreadBus) {
            if (x.getUser().equals(receiver)) {
                x.write("message_person---" + sendUser + "---" + message);
            }
        }
    }

    public int getNumberOfSeverThread() {
        return this.getSeverThreadBus().size();
    }

    public void sendOnlineList() {
        String res = "";
        for (ServerThread serverThread : this.getSeverThreadBus()) {
            res += serverThread.getUser() + "---";
        }
        this.sendToAll("update-online-list" + "---" + res);
    }

    public void remove(int clientNumber) {
        ArrayList<ServerThread> tmp = this.getSeverThreadBus();
        for (int i = 0; i < tmp.size(); i++) {
            if (tmp.get(i).getClientNumber() == clientNumber) {
                tmp.remove(i);
            }
        }

        this.setSeverThreadBus(tmp);

        for (int i = 0; i < tmp.size(); i++) {
            tmp.get(i).setServerThreadBus(this);
        }
        
        System.out.println("Number of running thread : " +tmp.size());

    }

}
