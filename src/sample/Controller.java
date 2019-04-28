package sample;

import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    public Circle car;
    @FXML
    public Pane panel;

    private void jazda(Circle cir, float startAngle, float endAngle)
    {
        Arc angle = new Arc();
        angle.setRadiusX(132);
        angle.setRadiusY(132);
        angle.setStartAngle(startAngle);
        angle.setLength(endAngle);
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(4));
        transition.setNode(cir);
        transition.setPath(angle);
        //transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        jazda(car, 0, 270);

        Circle car2 = new Circle();
        car2.setLayoutX(300);
        car2.setLayoutY(300);
        car2.setRadius(15);
        car2.setFill(Color.RED);
        panel.getChildren().add(car2);
        jazda(car2, 90, 180);
    }
}

