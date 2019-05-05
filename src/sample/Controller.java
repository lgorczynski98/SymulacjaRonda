package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Controller implements Initializable
{
    @FXML
    public Pane panel;

    private int carCount;

    private LinkedList<Car> Entrance1;
    private LinkedList<Car> Entrance2;
    private LinkedList<Car> Entrance3;
    private LinkedList<Car> Entrance4;

    public Controller()
    {
        carCount = 1;
        Entrance1 = new LinkedList<>();
        Entrance2 = new LinkedList<>();
        Entrance3 = new LinkedList<>();
        Entrance4 = new LinkedList<>();
    }

    private void drive(Thread th)
    {
        th.start();
        try
        {
            sleep(30);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private void addToEntrance(Car c, int entrance)
    {
        switch(entrance)
        {
            case 1:
            {
                Entrance1.addLast(c);
                break;
            }
            case 2:
            {
                Entrance2.addLast(c);
                break;
            }
            case 3:
            {
                Entrance3.addLast(c);
                break;
            }
            case 4:
            {
                Entrance4.addLast(c);
                break;
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        Car.setPane(panel);
        Thread []cars = new Thread[carCount];

        for (int i = 0; i < carCount; i++)
        {
            cars[i] = new Thread(new Car(i));
        }

        for (int i = 0; i < carCount; i++)
        {
            drive(cars[i]);
        }
    }
}

