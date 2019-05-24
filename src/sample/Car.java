package sample;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Car implements Runnable
{
    volatile private static Pane panel;  //pane na ktorym jest rysowane
    volatile private Circle car;     //nasza kolka czyli samochod

    volatile private static int IDpool = 0;
    volatile private int ID;
    volatile private float startAngle;   //miejsce z ktorego wjezdzamy na rondo
    volatile private float angle;    //jaki kat zataczamy na rondzie
    volatile private int quarter;

    volatile private PathTransition transitionIN;
    volatile private PathTransition transitionOUT;
    volatile private PathTransition transitionROUNDABOUT;
    volatile private SequentialTransition animation;

    volatile private DriveInSemaphore driveInSemaphore;
    volatile private static DriveRoundaboutSemaphore driveRoundaboutSemaphore;
    volatile private static Semaphore creatingSemaphore;

    private static int carsLeft = 0;
    private int quarterTime;      //czas przejazdu przez cwiartke ronda oraz dojazd w milisekundach
    private static double rate = 1;

    public Car()
    {
        try
        {
            quarterTime = 2000;
            this.ID = IDpool++;
            Random rand = new Random();
            this.startAngle = (rand.nextInt(4)) * 90;
            this.angle = (rand.nextInt(3) + 1) * 90;
            quarter = entranceNumber();
            //System.out.println("Car" + ID + " startAngle: " + startAngle + "; angle: " + angle);
            driveInSemaphore = new DriveInSemaphore(entranceNumber());
            driveRoundaboutSemaphore = new DriveRoundaboutSemaphore();
            carsLeft++;
            car = new Circle();
            car.setOpacity(0);
            car.setLayoutX(300);
            car.setLayoutY(300);
            car.setRadius(15);
            car.setStroke(Color.LIGHTGREY);
            car.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
            panel.getChildren().add(car);
            driveIN();
            driveRoundabout();
            driveOUT();
            animation = new SequentialTransition(transitionROUNDABOUT, transitionOUT);

            animation.setRate(rate);
            transitionIN.setRate(rate);
            quarterTime = (int)(quarterTime / rate);

            animation.setOnFinished(actionEvent -> {
                carsLeft--;
                panel.getChildren().remove(animation);
                panel.getChildren().remove(car);
            });
            creatingSemaphore.release();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void setPane(Pane p)
    {
        panel = p;
    }

    public static void setCreatingSemaphore(Semaphore sem)
    {
        creatingSemaphore = sem;
    }

    public static int getCarsLeft()
    {
        return carsLeft;
    }

    public static void setRate(double rate) {
        Car.rate = rate;
    }

    @Override
    public void run()
    {
        Random rand = new Random();
        driveInSemaphore.acquire();
        try
        {
            sleep(rand.nextInt((int)(0.2 * quarterTime)) + (int)(0.05 * quarterTime));
            Platform.runLater(transitionIN::play);
            Platform.runLater(() -> car.setOpacity(1));
        }
        catch(IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            panel.getChildren().remove(car);
            driveInSemaphore.release();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        //car.setOpacity(1);
        transitionIN.setOnFinished(actionEvent -> {

            Thread t = new Thread(() -> {
                int previousQuarter = quarter - 1;
                if(previousQuarter <= 0) previousQuarter = 4;
                try
                {
                    driveRoundaboutSemaphore.acquireMaxRelease(previousQuarter);
                    driveInSemaphore.release();
                    transitionIN.setNode(null);
                    Thread quarterTh = new Thread(() -> {
                        try
                        {
                            sleep((int)(0.375 * quarterTime));
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                            System.out.println("quarterTh first wait Error");
                        }
                        for (int i = 0; i < angle / 90; i++)
                        {
                            //System.out.println("Car" + ID + " quarter: " + quarter);
                            driveRoundaboutSemaphore.acquire(quarter);
                            try
                            {
                                sleep(quarterTime);
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                System.out.println("quarterTh Error");
                            }
                            driveRoundaboutSemaphore.release(quarter);
                            int q  = (quarter + 1) % 5;
                            if(q == 0) q = 1;
                            quarter = q;
                        }
                        quarter = 0;
                        //System.out.println("Car" + ID + " quarter: " + quarter);
                    });
                    quarterTh.setDaemon(true);
                    //quarterTh.start();
                    Platform.runLater(quarterTh::start);
                    if(animation != null) Platform.runLater(animation::play);      //tu jakis blad czasem : IndexOutOfBounds ???
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    System.out.println("t Error");
                }
            });
            t.setDaemon(true);
            //t.start();
            Platform.runLater(t::start);
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
