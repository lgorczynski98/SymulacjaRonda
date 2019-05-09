package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import static java.lang.Thread.sleep;

public class Controller implements Initializable
{
    @FXML
    public Pane panel;
    @FXML
    public Text textField;

    private int carCount;

    public Controller()
    {
        carCount = 50;
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

        Thread carsLeft = new Thread(new Task<Void>() {

            @Override
            public Void call() {
                for (int i = 0; i < 1000; i++) {
                    try
                    {
                        sleep(100);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> textField.setText("Samochodow: " + Car.getCarsLeft()));
                }
                return null;
            }
        });

        for (int i = 0; i < carCount; i++)
        {
            cars[i] = new Thread(new Car());
        }

        for (int i = 0; i < carCount; i++)
        {
            drive(cars[i]);
        }

        carsLeft.start();
    }
}

