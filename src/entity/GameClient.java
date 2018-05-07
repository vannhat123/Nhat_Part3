/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author nhat
 */
public class GameClient extends Application {

    private int minX = 30;
    private int minY = 30;
    private int width = 50;
    private int height = 50;
    private final int step = 5;
    private String originalImageUrl = "http://allaboutwindowsphone.com/images/appicons/284761.png";
    private String superImageUrl = "http://vietgamedev.net/attachment/download/id_4345/";
    private String currentImageUrl = "http://allaboutwindowsphone.com/images/appicons/284761.png";
    private Image face;

    @Override
    public void start(Stage theStage) throws Exception {
        theStage.setTitle("Canvas Example");
        Group root = new Group();
        Scene theScene = new Scene(root);
        theStage.setScene(theScene);

        Canvas canvas = new Canvas(700, 350);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        face = new Image(
                this.currentImageUrl,
                this.width, this.height, false, false);
        gc.drawImage(face, this.minX, this.minY);

        theScene.setOnKeyPressed((event) -> {

            if (null != event.getCode()) {
                switch (event.getCode()) {
                    case SPACE:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
//                        this.width *= 2;
//                        this.height *= 2;
                        if (this.currentImageUrl.equals(this.superImageUrl)) {
                            this.currentImageUrl = this.originalImageUrl;
                        } else {
                            this.currentImageUrl = this.superImageUrl;
                        }

                        this.face = new Image(
                                this.currentImageUrl,
                                this.width, this.height, false, false);
                        gc.drawImage(face, this.minX, this.minY);
                    case RIGHT:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minX += this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    case LEFT:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minX -= this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    case UP:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minY -= this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    case DOWN:
                        gc.clearRect(this.minX, this.minY, this.width, this.height);
                        this.minY += this.step;
                        gc.drawImage(face, this.minX, this.minY);
                        break;
                    default:
                        break;
                }
            }
        });
        theStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
