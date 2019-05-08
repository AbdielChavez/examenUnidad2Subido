package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        final int[] contador = {0};
        final int[] resultado = {0};
        final boolean[] acerto = {false};
        AnchorPane root = new AnchorPane();

        Label label3 = new Label("Ingrese el resultado de la siguiente multiplicación");
        label3.setLayoutY(20);

        Label label1 = new Label();
        int[] numero1 = {(int)(Math.random()*20)+1};
        label1.setText(String.valueOf(numero1[0])+"x");
        label1.setLayoutX(0);
        label1.setLayoutY(40);
        int[] numero2 = {(int)(Math.random()*20)+1};
        Label label2 = new Label();
        label2.setText(String.valueOf(numero2[0]));
        label2.setLayoutX(20);
        label2.setLayoutY(40);

        resultado[0] = numero1[0]*numero2[0];

        Label label4 = new Label("Tiempo");
        label4.setLayoutX(150);
        label4.setLayoutY(60);

        Label label5 = new Label();
        label5.setLayoutX(160);
        label5.setLayoutY(75);

        TextField textField = new TextField();
        textField.setLayoutY(60);
        textField.setPrefSize(60,20);

        root.getChildren().add(label3);
        root.getChildren().add(label1);
        root.getChildren().add(label2);
        root.getChildren().add(textField);
        root.getChildren().add(label4);
        root.getChildren().add(label5);
        //this.runTask();


        Task task = new Task() {
            @Override
            protected Object call() throws Exception {

                while(!acerto[0]){

                    //se usa plataform para poder modificar un elemento de la GUI
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            label5.setText(String.valueOf(contador[0]));
                            //System.out.println(contador[0]);

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    contador[0]++;
                }

                return null;
            }
        };

        Thread thread = new Thread(task);

        thread.setDaemon(true);
        thread.start();


        Button button = new Button("Comprobar");
        button.setLayoutY(90);
        int[] result = {resultado[0]};
        button.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
             if(textField.getText().length() != 0){
                 if(result[0] == Integer.parseInt(textField.getText())){
                     acerto[0] = true;
                     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                     alert.setWidth(200);
                     alert.setHeight(100);
                     alert.setContentText("Tiempo Transcurrido: "+contador[0]);
                     alert.show();

                     numero1[0] = (int)(Math.random()*20)+1;
                     label1.setText(String.valueOf(numero1[0]));
                     numero2[0] = (int)(Math.random()*20)+1;
                     label2.setText(String.valueOf(numero2[0]));
                     label5.setText("");
                     textField.setText("");
                     result[0] = numero1[0]*numero2[0];
                     contador[0] = 0;
                     acerto[0] = false;
                     thread.run();

                 } else {
                     Alert alert = new Alert(Alert.AlertType.ERROR);
                     alert.setContentText("Incorrecto");
                     alert.show();
                 }
             }
            }
        });

        root.getChildren().add(button);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }


}
