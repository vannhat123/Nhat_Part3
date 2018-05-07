/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import entity.Champion;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.SPACE;
import javafx.stage.Stage;

/**
 *
 * @author nhat
 */
public class ChatClient1 extends Application {

    private long clientId;
    private final String HOST = "localhost";
    private final int PORT = 6000;
    private Socket socket;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private GraphicsContext gc;
    private Image face;
    private Image bullet;

    private int minX = 230;
    private int minY = 230;
    private int width = 80;
    private int height = 80;
    private final int step = 7;
    private int jump = 20;
    private int bump = 20;
    private String garendownImageUrl = "https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/31945448_369001026941518_4433915926181576704_n.jpg?_nc_cat=0&oh=3cf24cbe7278adcb71bb0a763a655bba&oe=5B59A099";
    private String garenUpImageUrl = "https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/31958277_368999303608357_5498406111453118464_n.jpg?_nc_cat=0&oh=20573ce07f057efeab6222d78be7e21e&oe=5B8ED06C";
    private String garenLeftImageUrl = "https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/31960356_368999343608353_8049889278261460992_n.jpg?_nc_cat=0&oh=e16519f57c2e7b2f32f7b93d10428c31&oe=5B862F4A";
    private String garenRightImageUrl = "https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/31957044_368999326941688_2803832136333787136_n.jpg?_nc_cat=0&oh=c8327cfa2dbd0d2625f65792312153ba&oe=5B88B2F1";
    private String garenNormalImageUrl = "https://scontent.fsgn2-1.fna.fbcdn.net/v/t1.0-9/31945448_369001026941518_4433915926181576704_n.jpg?_nc_cat=0&oh=3cf24cbe7278adcb71bb0a763a655bba&oe=5B59A099";

    @Override
    public void start(Stage theStage) throws Exception {
        this.clientId = System.currentTimeMillis();
        this.socket = new Socket(HOST, PORT);
        try {

            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            this.ois = new ObjectInputStream(this.socket.getInputStream());
            ChatClientReaderThread ccrt = new ChatClientReaderThread();
            ccrt.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(1000, 500);
        root.getChildren().add(canvas);

        gc = canvas.getGraphicsContext2D();
        face = new Image(
                garenNormalImageUrl,
                this.width, this.height, false, false);
        bullet = new Image(
                garenNormalImageUrl,
                this.width, this.height, false, false);
        gc.drawImage(bullet, this.minX, this.minY);
        gc.drawImage(face, this.minX, this.minY);

//        Champion h = new Champion(this.clientId,
//                bulletImageUrl, this.minX, this.minY, this.width, this.height, this.minX + 100, this.minY + 100);
        Champion p = new Champion(this.clientId,
                garendownImageUrl, this.minX, this.minY, this.width, this.height, this.minX, this.minY);
        //    this.oos.writeObject(h);
        this.oos.writeObject(p);
        this.oos.flush();

        theScene.setOnKeyPressed((event) -> {
            if (null != event.getCode()) {
                int ox = this.minX;
                int oy = this.minY;
                switch (event.getCode()) {
                    // tốc biến
//                    case D:
//                        gc.clearRect(this.minX, this.minY, this.width, this.height);
//                        if (this.garenNormalImageUrl.equals(this.garenRightImageUrl)) {
//                            this.minY += this.step * 10;
//                        } else if (this.garenNormalImageUrl.equals(this.garenRightImageUrl)) {
//                            this.minX += this.step * 10;
//                        } else if (this.garenNormalImageUrl.equals(this.garendownImageUrl)) {
//                            this.minX -= this.step * 10;
//                        } else if (this.garenNormalImageUrl.equals(this.garenUpImageUrl)) {
//                            this.minY -= this.step * 10;
//                        }
//                        gc.drawImage(face, this.minX, this.minY);
//                        break;
                    case RIGHT:
                        this.face = new Image(
                                this.garenRightImageUrl,
                                this.width, this.height, false, false);
                        gc.drawImage(face, this.minX, this.minY);
                        this.minX += this.step;
                        break;
                    case LEFT:
                        this.face = new Image(
                                this.garenLeftImageUrl,
                                this.width, this.height, false, false);
                        gc.drawImage(face, this.minX, this.minY);
                        this.minX -= this.step;
                        break;
                    case UP:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.face = new Image(
                                this.garenUpImageUrl,
                                this.width, this.height, false, false);
                        gc.drawImage(face, this.minX, this.minY);
                        this.minY -= this.step;
                        break;
                    case DOWN:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.face = new Image(
                                this.garendownImageUrl,
                                this.width, this.height, false, false);
                        gc.drawImage(face, this.minX, this.minY);
                        this.minY += this.step;
                        break;
                    default:
                        break;
                }
                try {
                    this.oos.writeObject(new Champion(this.clientId,
                            garenNormalImageUrl, this.minX, this.minY, this.width, this.height, ox, oy));
                    this.oos.flush();
                } catch (IOException e) {

                }
            }
        });
        theStage.show();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    class ChatClientReaderThread extends Thread {

        @Override
        public void run() {
//            while (true) {
//                try {
//                    String line = br.readLine();
//                    if (null != line) {
//                        System.out.println("Server said: " + line);
//                    }
//                } catch (IOException e) {
//                }
//
//            }

            while (true) {
                try {
                    Champion b = (Champion) ois.readObject();
                    if (b.getId() == clientId) {
                        gc.clearRect(b.getOx(), b.getOy(), b.getW(), b.getH());
                        gc.drawImage(face, b.getX(), b.getY());
                    } else {
                        face = new Image(
                                b.getImg(),
                                b.getW(), b.getH(), false, false);
                        gc.clearRect(b.getOx(), b.getOy(), b.getW(), b.getH());
                        gc.drawImage(face, b.getX(), b.getY());
                    }
                } catch (IOException e) {
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ChatClient1.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {
//        new ChatClient(new Socket("localhost", 6000));
        launch(args);
    }
}
