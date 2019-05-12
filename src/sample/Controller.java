package sample;

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

    public Controller()
    {
        carCount = 50;
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
        //ArrayList<Thread> cars = new ArrayList<>();

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

        /*for (int i = 0; i < carCount; i++)
        {
            cars.add(new Thread(new Car()));
        }

        cars.forEach((car) -> car.setDaemon(true));

        try
        {
            sleep(2000);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        for (Thread car : cars) {
            drive(car);
        }

        carsLeft.start();*/
        synchronized(cars)
        {
            for (int i = 0; i < carCount; i++)
            {
                try
                {
                    cars.add(new Thread(new Car()));
                    //sleep(30);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

            for (Object car : cars) {
                drive(car);
            }

            carsLeft.setDaemon(true);
            carsLeft.start();
        }
    }
}

