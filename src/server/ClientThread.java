/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import entity.Champion;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nhat
 */
public class ClientThread extends Thread {

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }



    public ClientThread(Socket socket) {
        System.out.println(socket.getInetAddress().getHostAddress() + " đã kết nối.");
        this.socket = socket;
        try {

            this.ois = new ObjectInputStream(this.socket.getInputStream());
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Champion b = (Champion) this.ois.readObject();
                System.out.println(b.toString());
//                String line = this.br.readLine();
//                if (null == line) {
//                    break;
//                }
//                System.out.println(this.socket.getInetAddress().getHostAddress() + " said: " + line);
                ChatServer.publicObject(b);
            } catch (IOException e) {
                System.out.println(socket.getInetAddress().getHostAddress() + " đã thoát.");
                break;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
