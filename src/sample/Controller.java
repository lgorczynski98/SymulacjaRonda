package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Controller implements Initializable
{
    @FXML
    public Pane panel;

    private int carCount;

    public Controller()
    {
        carCount = 15;
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

