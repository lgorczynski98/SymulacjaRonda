package sample;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Controller implements Initializable
{
    @FXML
    public Pane panel;
    @FXML
    public Text textField;

    volatile private int carCount;

    volatile private List cars = Collections.synchronizedList(new ArrayList<>());
    volatile private static Semaphore creatingSemaphore = new Semaphore(1);

    public Controller()
    {
        /*Random rand = new Random();
        carCount = rand.nextInt(60) + 10;*/
        carCount = 30;
    }

    private void drive(Object o)
    {
        Thread th = (Thread)o;
        try
        {
            if(th != null)th.start();
            sleep(30);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("th.start Error");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Car.setPane(panel);
        Car.setCreatingSemaphore(creatingSemaphore);

        Thread carsLeft = new Thread(new Task<Void>() {

            @Override
            public Void call()
            {
                for (int i = 0; i < 1000; i++)
                {
                    try
                    {
                        sleep(100);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        System.out.println("carsLeftError");
                        return null;
                    }
                    Platform.runLater(() -> textField.setText("Samochodow: " + Car.getCarsLeft()));
                }
                return null;
            }
        });

        synchronized(cars)
        {
            for (int i = 0; i < carCount; i++)
            {
                try
                {
                    creatingSemaphore.acquire();
                    cars.add(new Thread(new Car()));
                    //sleep(30);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

            for (Object car : cars) {
                ((Thread)car).setDaemon(true);
                //drive(car);
                //Platform.runLater(() -> drive(car));
                Platform.runLater(((Thread)car)::start);
            }

            carsLeft.setDaemon(true);
            carsLeft.start();
        }
    }
}

