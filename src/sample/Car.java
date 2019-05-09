package sample;

import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.util.Random;
import static java.lang.Thread.sleep;

// TODO: 07.05.2019 Ogarnac zeby mozna bylo robic sleepy w javafx

public class Car implements Runnable
{
    private static Pane panel;  //pane na ktorym jest rysowane
    private Circle car;     //nasza kolka czyli samochod

    private static int IDpool = 0;
    private int ID;
    private float startAngle;   //miejsce z ktorego wjezdzamy na rondo
    private float angle;    //jaki kat zataczamy na rondzie
    private int quarter;
    private Random rand;    //pomocnicze do randomow

    private PathTransition transitionIN;
    private PathTransition transitionOUT;
    private PathTransition transitionROUNDABOUT;
    private SequentialTransition animation;

    private DriveInSemaphore driveInSemaphore;
    private DriveRoundaboutSemaphore driveRoundaboutSemaphore;

    private static int carsLeft = 0;

    public Car()
    {
        this.ID = IDpool++;
        rand = new Random();
        this.startAngle = (rand.nextInt(4)) * 90;
        this.angle = (rand.nextInt(3) + 1) * 90;
        quarter = entranceNumber();
        System.out.println("Car" + ID + " startAngle: " + startAngle + "; angle: " + angle);
        driveInSemaphore = new DriveInSemaphore(entranceNumber());
        driveRoundaboutSemaphore = new DriveRoundaboutSemaphore();
        carsLeft++;
        car = new Circle();
        car.setOpacity(0);
        car.setLayoutX(300);
        car.setLayoutY(300);
        car.setRadius(15);
        car.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
        panel.getChildren().add(car);
    }

    public static void setPane(Pane p)
    {
        panel = p;
    }

    public static int getCarsLeft()
    {
        return carsLeft;
    }

    @Override
    public void run()
    {
        driveIN();
        driveRoundabout();
        driveOUT();
        animation = new SequentialTransition(transitionROUNDABOUT, transitionOUT);
        animation.setOnFinished(actionEvent -> {
            carsLeft--;
            panel.getChildren().remove(animation);
            panel.getChildren().remove(car);
        });
        driveInSemaphore.acquire();
        //animation.play();
        transitionIN.play();
        car.setOpacity(1);
        transitionIN.statusProperty().addListener(new ChangeListener<Animation.Status>() {
            @Override
            public void changed(ObservableValue<? extends Animation.Status> observableValue, Animation.Status status, Animation.Status t1)
            {
                Thread quarterTh = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try
                        {
                            sleep(750);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < angle / 90; i++)
                        {
                            //System.out.println("Car" + ID + " quarter: " + quarter);
                            driveRoundaboutSemaphore.acquire(quarter);
                            try
                            {
                                sleep(2000);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                            driveRoundaboutSemaphore.release(quarter);
                            quarter = (quarter + 1) % 5;
                            if(quarter == 0) quarter = 1;
                        }
                        quarter = 0;
                        //System.out.println("Car" + ID + " quarter: " + quarter);
                    }
                });

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        int prevoiusQuarter = quarter - 1;
                        if(prevoiusQuarter <= 0) prevoiusQuarter = 4;
                        try
                        {
                            //animation.play();
                            driveRoundaboutSemaphore.acquireMax(prevoiusQuarter);
                            driveInSemaphore.release();
                            quarterTh.start();
                            animation.play();
                            driveRoundaboutSemaphore.releaseMax(prevoiusQuarter);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });
    }

    private void driveRoundabout()
    {
        Arc arc = new Arc();
        arc.setRadiusX(132);
        arc.setRadiusY(132);
        arc.setStartAngle(startAngle + 8);
        arc.setLength(angle - 16);
        transitionROUNDABOUT = new PathTransition();
        transitionROUNDABOUT.setDuration(Duration.seconds(driveTime()));
        transitionROUNDABOUT.setNode(car);
        transitionROUNDABOUT.setPath(arc);
    }

    private void driveIN()
    {
        transitionIN = new PathTransition();
        transitionIN.setDuration(Duration.seconds(2));
        transitionIN.setNode(car);
        Line line = new Line();

        switch((int)startAngle)
        {
            case 0:     //jazda z prawej
            {
                line.setStartX(300);
                line.setStartY(-20);
                line.setEndX(165);
                line.setEndY(-20);
                break;
            }
            case 90:    //jazda z gory
            {
                line.setStartX(-20);
                line.setStartY(-300);
                line.setEndX(-20);
                line.setEndY(-165);
                break;
            }
            case 180:       //jazda z lewej
            {
                line.setStartX(-300);
                line.setStartY(20);
                line.setEndX(-165);
                line.setEndY(20);
                break;
            }
            case 270:       //jazda z dolu
            {
                line.setStartX(20);
                line.setStartY(300);
                line.setEndX(20);
                line.setEndY(165);
                break;
            }
        }
        transitionIN.setPath(line);
    }

    private void driveOUT()
    {
        transitionOUT = new PathTransition();
        transitionOUT.setDuration(Duration.seconds(2));
        transitionOUT.setNode(car);
        Line line = new Line();
        float end = ((startAngle + angle) / 90) % 4 ;     //miejsce gdzie wyjezdzamy z ronda
        switch((int)end)
        {
            case 0:     //jazda z prawej
            {
                line.setStartX(165);
                line.setStartY(20);
                line.setEndX(300);
                line.setEndY(20);
                break;
            }
            case 1:    //jazda z gory
            {
                line.setStartX(20);
                line.setStartY(-165);
                line.setEndX(20);
                line.setEndY(-300);
                break;
            }
            case 2:       //jazda z lewej
            {
                line.setStartX(-165);
                line.setStartY(-20);
                line.setEndX(-300);
                line.setEndY(-20);
                break;
            }
            case 3:       //jazda z dolu
            {
                line.setStartX(-20);
                line.setStartY(165);
                line.setEndX(-20);
                line.setEndY(300);
                break;
            }
        }
        transitionOUT.setPath(line);
    }

    private int driveTime()
    {
        return (int) (2 * (angle/90));
    }

    public int entranceNumber()
    {
        switch((int) startAngle)
        {
            case 0:
                return 1;
            case 90:
                return 2;
            case 180:
                return 3;
            case 270:
                return 4;
        }
        return 0;
    }
}
