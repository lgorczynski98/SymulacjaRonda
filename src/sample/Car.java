package sample;

import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Car implements Runnable
{
    private static Pane panel;  //pane na ktorym jest rysowane
    private Circle car;     //nasza kolka czyli samochod

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

    public Car(int ID)
    {
        this.ID = ID;
        rand = new Random();
        this.startAngle = rand.nextInt(3) * 90;
        this.angle = (rand.nextInt(3) + 1) * 90;
        quarter = entranceNumber();
        System.out.println("Car" + ID + " startAngle: " + startAngle + "; angle: " + angle);
        driveInSemaphore = new DriveInSemaphore(entranceNumber());
        driveRoundaboutSemaphore = new DriveRoundaboutSemaphore();
    }

    public static void setPane(Pane p)
    {
        panel = p;
    }

    @Override
    public void run()
    {
        car = new Circle();
        car.setOpacity(0);
        car.setLayoutX(300);
        car.setLayoutY(300);
        car.setRadius(15);
        car.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));
        panel.getChildren().add(car);
        driveIN();
        driveRoundabout();
        driveOUT();
        animation = new SequentialTransition(transitionIN, transitionROUNDABOUT, transitionOUT);
        animation.setOnFinished(actionEvent -> car.setOpacity(0));
        //animation.setCycleCount(PathTransition.INDEFINITE);
        driveInSemaphore.acquire();
        car.setOpacity(1);
        animation.play();
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
        Thread quarterTh = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < angle / 90; i++)
                {
                    System.out.println("Car" + ID + " quarter: " + quarter);
                    //driveRoundaboutSemaphore.acquire(quarter);
                    try
                    {
                        sleep(2000);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    //driveRoundaboutSemaphore.release(quarter);
                    quarter = (quarter + 1) % 5;
                    if(quarter == 0) quarter = 1;
                }
                quarter = 0;
                System.out.println("Car" + ID + " quarter: " + quarter);
            }
        });
        transitionIN.setOnFinished(actionEvent -> {
            animation.pause();
            //tu zamiast sleepa bedzie na semaforach
            try
            {
                sleep(rand.nextInt(30) + 5);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
            driveInSemaphore.release();
            quarterTh.start();
            /*while(DriveRoundaboutSemaphore.getQuarterSemaphore(quarter).availablePermits() != 10)
            {
                try
                {
                    sleep(30);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }*/
            //driveRoundaboutSemaphore.acquire(quarter);
            animation.play();
        });
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
