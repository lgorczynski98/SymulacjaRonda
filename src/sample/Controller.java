package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.*;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class Controller implements Initializable
{
    @FXML public Pane panel;
    @FXML public Text textField;

    volatile private int carCount;

    volatile private List cars = Collections.synchronizedList(new ArrayList<>());
    volatile private static Semaphore creatingSemaphore = new Semaphore(1);

    public Controller()
    {
        carCount = 20;
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
        init();
        Car.setPane(panel);
        Car.setCreatingSemaphore(creatingSemaphore);

        Thread carsLeft = new Thread(new Task<Void>() {

            @Override
            public Void call()
            {
                while(Car.getCarsLeft() >= 0)
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
                    if(Car.getCarsLeft() == 0)
                        break;
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

    private void init()
    {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Przygotowywanie ronda");
        dialog.setHeaderText("Proszę podać parametry symulacji ronda");
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150,10,10));
        TextField iloscText = new TextField();
        Slider rateSlider = new Slider();
        rateSlider.setMin(0);
        rateSlider.setMax(4);
        rateSlider.setValue(1);
        rateSlider.setShowTickLabels(true);
        rateSlider.setShowTickMarks(true);
        rateSlider.setMajorTickUnit(0.5);
        grid.add(new Label("Ilość samochodów: "), 0, 0);
        grid.add(iloscText, 1, 0);
        grid.add(new Label("Przyśpieszenie samochodów: "), 0, 1);
        grid.add(rateSlider, 1, 1);
        Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
        okButton.setDisable(true);
        iloscText.textProperty().addListener(((observableValue, oldVal, newVal) -> {
            okButton.setDisable(newVal.trim().isEmpty());
        }));
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> iloscText.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == okButtonType)
                return new Pair<>(iloscText.getText(), String.valueOf(rateSlider.getValue()));
            return null;
        });
        Optional<Pair<String, String>> result = dialog.showAndWait();
        result.ifPresent(pair -> {
            carCount = Integer.valueOf(pair.getKey());
            Car.setRate(Double.valueOf(pair.getValue()));
        });
    }
}

