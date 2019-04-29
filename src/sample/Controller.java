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

    private void drive(Thread th)
    {
        th.start();
        try
        {
            sleep(300);
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
        Thread th1 = new Thread(new Car());
        Thread th2 = new Thread(new Car());
        Thread th3 = new Thread(new Car());

        drive(th1);
        drive(th2);
        drive(th3);

    }
}

