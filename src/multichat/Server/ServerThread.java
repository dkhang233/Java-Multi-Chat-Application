package multichat.Server;

import multichat.Server.ServerThreadBus;
import multichat.DatabaseConfig.DatabaseConnection;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerThread implements Runnable {

    private Connection connection = null;
    private Statement statement = null;
    private int clientNumber;
    private Socket s;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerThreadBus serverThreadBus;
    private String user;

    public ServerThread(Socket s, int clientNumber) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            connection = databaseConnection.getConnection();
            statement = connection.createStatement();
            this.clientNumber = clientNumber;
            this.s = s;
            System.out.println("Sever run ");
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public String getUser() {
        return user;
    }

    public void setServerThreadBus(ServerThreadBus serverThreadBus) {
        this.serverThreadBus = serverThreadBus;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());
            while (true) {
                String result = in.readUTF();
                if (result == null) {
                    break;
                }

                String[] resultArray = result.split("---");
                if (resultArray[0].equals("user")) {
                    String userString = resultArray[1];
                    String passString = resultArray[2];
                    ResultSet resultSet = statement.executeQuery("select * from myaccounts where Username = '"
                            + userString + "' and Password ='" + passString + "'" + "and Remarks <> 'Used' ");

                    if (resultSet.next()) {
                        this.user = userString;
                        statement.executeUpdate("update myaccounts set Remarks = 'Used' where Username = '"
                                + userString + "' and Password ='" + passString + "'");
                        out.writeUTF("true");
                        serverThreadBus.sendOnlineList();

                    } else {
                        out.writeUTF("false");

                    }

                }

                if (resultArray[0].equals("message")) {
                    String timeMessage = time();
                    if (resultArray[1].equals("send_to_group")) {
                        String sb = resultArray[2];
                        serverThreadBus.sendToGlobal(sb, user);
                        statement.executeUpdate("insert into mychat values('" + user + "','" + sb + "','" + timeMessage + "','','group','')");
                    } else {
                        String receiver = resultArray[2];
                        String sb = resultArray[3];
                        serverThreadBus.sendToPerson(sb, user, receiver);
                        statement.executeUpdate("insert into mychat values('" + user + "','" + sb + "','" + timeMessage + "','','" + receiver + "','')");
                    }

                }

                if (resultArray[0].equals("exit")) {
                    statement.executeUpdate("update myaccounts set Remarks = '' where Username = '"
                            + user + "'");
                    serverThreadBus.remove(clientNumber);
                    serverThreadBus.sendOnlineList();
                    serverThreadBus.sendToAll("-------" + user + " has exited--------");
                }

                if (resultArray[0].equals("reload_message")) {
                    if (resultArray[1].equals("group")) {
                        String result2 = "";
                        ResultSet resultSet2 = statement.executeQuery("select * from mychat where ChatGroup = 'group' order by Time");
                        /*
                        if (resultSet2.next()) {
                            if (resultSet2.getString(1).equals(user)) {
                                result2 += "You : " + resultSet2.getString(2);
                                resultSet2.next();
                            } else {
                                result2 += resultSet2.getString(1) + " : " + resultSet2.getString(2);
                                resultSet2.next();
                            }
                         */
                        while (resultSet2.next()) {
                            if (resultSet2.getString(1).equals(user)) {
                                result2 += "You : " + resultSet2.getString(2);
                            } else {
                                result2 += resultSet2.getString(1) + " : " + resultSet2.getString(2);
                            }

                            if (!resultSet2.isLast()) {
                                result2 += "\n";
                            }

                        }
                        //}

                        out.writeUTF("reload_message---" + result2);

                    } else {
                        String result3 = "";
                        ResultSet resultSet3 = statement.executeQuery("select * from mychat where Sender in ('" + user + "','" + resultArray[1] + "')and ChatGroup in( '" + resultArray[1] + "','" + user + "') order by Time");

                        while (resultSet3.next()) {
                            if (resultSet3.getString(1).equals(user)) {
                                result3 += "You : " + resultSet3.getString(2);
                            } else {
                                result3 += resultSet3.getString(1) + " : " + resultSet3.getString(2);
                            }

                            if (!resultSet3.isLast()) {
                                result3 += "\n";
                            }

                        }

                        if (result3.equals("")) {
                            out.writeUTF("reload_message---null");
                        } else {
                            out.writeUTF("reload_message---" + result3);
                        }
                    }

                }

                if (resultArray[0].equals("registeruser")) {
                    String userString1 = resultArray[1];
                    String passString1 = resultArray[2];
                    ResultSet resultSet = statement.executeQuery("select * from myaccounts where Username = '"
                            + userString1 + "'");
                    if (resultSet.next()) {
                        out.writeUTF("already exist");
                    } else {
                        statement.executeUpdate("insert into myaccounts values('" + userString1 + "','" + passString1 + "','')");
                        out.writeUTF("sucess");
                    }
                }
            }

        } catch (IOException ex) {
            serverThreadBus.remove(clientNumber);
        } catch (SQLException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void write(String message) {
        try {

            out.writeUTF(message);

        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String time() {
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }

}
