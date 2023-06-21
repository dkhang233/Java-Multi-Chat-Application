package multichat.Client;

import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Chat_GUI extends javax.swing.JFrame {

    private DataOutputStream os;
    private DataInputStream is;
    private ArrayList<String> onlineList = new ArrayList<String>();
    private String user;

    public Chat_GUI(DataInputStream is, DataOutputStream os, String user) {
        initComponents();
        this.setVisible(true);
        this.user = user;
        System.out.println("this is " + user);
        setUpSocket(is, os);
        this.userName.setText("User Name: " + user);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        sendButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        messageToDisplay = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        messageToSend = new javax.swing.JTextArea();
        sendOption = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        userName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        sendButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multichat/Static/send.png"))); // NOI18N
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        messageToDisplay.setEditable(false);
        messageToDisplay.setColumns(20);
        messageToDisplay.setRows(5);
        jScrollPane3.setViewportView(messageToDisplay);

        messageToSend.setColumns(20);
        messageToSend.setRows(5);
        jScrollPane4.setViewportView(messageToSend);

        sendOption.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        sendOption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendOptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sendOption, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jScrollPane3)
                .addGap(0, 0, 0)
                .addComponent(sendOption, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4)
                    .addComponent(sendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jTabbedPane2.addTab("Message", jPanel1);

        jTextArea3.setEditable(false);
        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane5.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addComponent(userName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(userName, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane2.addTab("Online List", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            os.writeUTF("exit");
            os.close();
            is.close();
        } catch (IOException ex) {
            Logger.getLogger(Chat_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosing

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        try {
            // TODO add your handling code here:

            String contentMessage = messageToSend.getText();
            if (contentMessage.equals("")) {
                JOptionPane.showMessageDialog(null, "Message is empty ");
            } else {
                String contentAll = messageToDisplay.getText();
                if (!contentAll.isEmpty()) {
                    contentAll += "\nYou : " + contentMessage;
                } else {
                    contentAll += "You : " + contentMessage;
                }
                messageToDisplay.setText(contentAll);
                if (this.sendOption.getSelectedIndex() == 0) {
                    os.writeUTF("message---send_to_group---" + contentMessage);
                } else {
                    String receiver = (String) this.sendOption.getSelectedItem();
                    os.writeUTF("message---send_to_person---" + receiver + "---" + contentMessage);
                }
            }

            messageToSend.setText("");

        } catch (IOException ex) {
        }
    }//GEN-LAST:event_sendButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed

    }//GEN-LAST:event_formWindowClosed

    private void sendOptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendOptionActionPerformed
        // TODO add your handling code here:
        if (sendOption.getSelectedItem() != null) {
            if (sendOption.getSelectedItem().equals("Group")) {
                try {
                    os.writeUTF("reload_message---group");
                } catch (IOException ex) {
                    Logger.getLogger(Chat_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                String option = (String) sendOption.getSelectedItem();
                try {
                    os.writeUTF("reload_message---" + option);
                } catch (IOException ex) {
                    Logger.getLogger(Chat_GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


    }//GEN-LAST:event_sendOptionActionPerformed

    private void updateCombobox() {
        sendOption.removeAllItems();
        sendOption.addItem("Group");
        for (String e : onlineList) {
            sendOption.addItem(e);

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea messageToDisplay;
    private javax.swing.JTextArea messageToSend;
    private javax.swing.JButton sendButton;
    private javax.swing.JComboBox<String> sendOption;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables

    public void setUpSocket(DataInputStream inputStream, DataOutputStream outputStream) {
        try {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        is = inputStream;
                        os = outputStream;
                        System.out.println("Chatgui  connect ");
                        while (true) {
                            String result = is.readUTF();
                            System.out.println(result);
                            if (result == null) {
                                break;
                            }
                            String[] resultArray = result.split("---");
                            if (resultArray[0].equals("message_group")) {
                                if (sendOption.getSelectedItem().equals("Group")) {
                                    String message = messageToDisplay.getText();
                                    if (!message.isEmpty()) {
                                        message += "\n" + resultArray[1] + " : " + resultArray[2];
                                    } else {
                                        message += resultArray[1] + " : " + resultArray[2];
                                    }
                                    messageToDisplay.setText(message);
                                }

                            }

                            if (resultArray[0].equals("message_person")) {
                                if (sendOption.getSelectedItem().equals(resultArray[1])) {
                                    String message = messageToDisplay.getText();
                                    if (!message.isEmpty()) {
                                        message += "\n" + resultArray[1] + " : " + resultArray[2];
                                    } else {
                                        message += resultArray[1] + " : " + resultArray[2];
                                    }
                                    messageToDisplay.setText(message);
                                }

                            }

                            if (resultArray[0].equals("update-online-list")) {
                                String online = "";
                                onlineList.clear();
                                String idString = "" + user;
                                for (int i = 1; i < resultArray.length; i++) {
                                    if (!resultArray[i].equals(idString)) {
                                        onlineList.add(resultArray[i]);
                                        online += resultArray[i] + " đang online\n";
                                    }

                                }
                                updateCombobox();
                                jTextArea3.setText(online);

                            }

                            if (resultArray[0].equals("reload_message")) {
                                if (resultArray.length < 2 || resultArray[1].equals("null")) {
                                    messageToDisplay.setText("");
                                } else {
                                    messageToDisplay.setText(resultArray[1]);
                                }
                            }

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Chat_GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            };
            thread.start();
        } catch (Exception e) {
        }
    }
}
