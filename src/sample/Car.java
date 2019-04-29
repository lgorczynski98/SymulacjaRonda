package sample;

import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Car implements Runnable
{
    private static Pane panel;
    private Circle car;
    private float startAngle;
    private float angle;
    private Random rand;

    public Car()
    {
        rand = new Random();
        this.startAngle = rand.nextInt(3) * 90;
        this.angle = (rand.nextInt(3) + 1) * 90;
    }

    public static void setPane(Pane p)
    {
        panel = p;
    }

    @Override
    public void run()
    {
        car = new Circle();
        car.setLayoutX(300);
        car.setLayoutY(300);
        car.setRadius(15);
        car.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
        panel.getChildren().add(car);
        drive();
    }

    private void drive()
    {
        Arc arc = new Arc();
        arc.setRadiusX(132);
        arc.setRadiusY(132);
        arc.setStartAngle(startAngle);
        arc.setLength(angle);
        PathTransition transition = new PathTransition();
        transition.setDuration(Duration.seconds(driveTime()));
        transition.setNode(car);
        transition.setPath(arc);
        transition.play();
        try
        {
            sleep(1000 * driveTime());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
        car.setOpacity(0);
    }

    private int driveTime()
    {
        return (int) (2 * (angle/90));
    }
}
