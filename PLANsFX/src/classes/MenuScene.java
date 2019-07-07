package classes;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;


public class MenuScene  {
    double height = 300;
    double width = 450;
    Scene menuScene;
    Button createServer = new Button("Create Game");
    Button connectToServer = new Button("Connect To Game");
    TextField serverPortField = new TextField("9999");
    TextField clientPortField = new TextField("9999");
    TextField clientIpField = new TextField("192.168.0.103");

    Label serverPortLabel = new Label("port");
    Label clientIpLabel = new Label("ip adress");
    Label clientPortLabel = new Label("port");

    Label fpsFixLabel = new Label("if you have more than 60hz");
    CheckBox fpsFixCheckBox = new CheckBox();

    Label guidLabel = new Label("\nW - increase speed gear \n S - decrease speed gear \n A - rotate to Left \n D - rotate to Right \n" +
            " Right ctrl - shoot \n SPACE - catapult/open parachute");

    GridPane grid = new GridPane();
    //ServerPlayer main;
    Launcher application;
    public MenuScene(Launcher application) {

        this.application=application;
        clientIpField.setText("localHost");
        serverPortField.setMaxWidth(45);
        clientPortField.setMaxWidth(45);

        grid.add(createServer,0,0);
        grid.add(connectToServer,0,1);
        grid.add(serverPortLabel,1,0);
        grid.add(serverPortField,2,0);
        grid.add(clientIpLabel,1,1);
        grid.add(clientIpField,2,1);
        grid.add(clientPortLabel,3,1);
        grid.add(clientPortField,4,1);
        grid.add(fpsFixLabel,0,2);
        grid.add(fpsFixCheckBox,2,2);
        grid.add(guidLabel,0,3);


        menuScene = new Scene(grid,grid.getPrefWidth(),height);


        createServer.setOnAction(event -> {
            try {
                if (serverPortField.getText().length()<4){
                    throw new NumberFormatException();
                }
                application.createServerGame(Integer.parseInt(serverPortField.getText()),fpsFixCheckBox.isSelected());

            }
            catch (NumberFormatException e){
                serverPortLabel.setTextFill(Color.RED);
                serverPortLabel.setText("WRONG PORT");
            }
        });

        connectToServer.setOnAction(event -> {
            try {
                if (clientPortField.getText().length()<4){
                    throw new NumberFormatException();
                }
                application.createClientGame(clientIpField.getText(),Integer.parseInt(clientPortField.getText()),fpsFixCheckBox.isSelected());

            }
            catch (NumberFormatException e){
                clientPortLabel.setTextFill(Color.RED);
                clientPortLabel.setText("WRONG PORT");
            }
        });

    }

    public Scene getMenuScene() {
        return menuScene;
    }
}
