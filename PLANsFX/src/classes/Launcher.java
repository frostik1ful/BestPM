package classes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Launcher extends Application {
    private Stage stage;
    private ServerPlayer serverPlayer;
    private ClientPlayer clientPlayer;
    @Override
    public void start(Stage primaryStage) throws Exception {
        MenuScene menu = new MenuScene(this);
        stage = primaryStage;
        primaryStage.setTitle("pLANsFX");
        primaryStage.getIcons().add(new Image("/resources/images/icon.png"));
        primaryStage.setResizable(false);
        primaryStage.setScene(menu.getMenuScene());

        primaryStage.show();
    }
    void createServerGame(int port,boolean isFpsFixActive){

        serverPlayer = new ServerPlayer(port,isFpsFixActive);
        serverPlayer.run();
        Scene scene = new Scene(serverPlayer);
        scene.setOnKeyPressed(event -> serverPlayer.setKey(event));
        scene.setOnKeyReleased(event -> serverPlayer.setKey(event));
        stage.setScene(scene);
        stage.setX(0);
        stage.setY(0);
        stage.setOnCloseRequest(event -> {
            serverPlayer.stop();
        });



    }
    void createClientGame(String ip,int port,boolean isFpsFixActive){

        clientPlayer = new ClientPlayer(ip,port,isFpsFixActive);
        clientPlayer.run();
        Scene scene = new Scene(clientPlayer);
        scene.setOnKeyPressed(event -> clientPlayer.setKey(event));
        scene.setOnKeyReleased(event -> clientPlayer.setKey(event));
        stage.setScene(scene);
        stage.setX(0);
        stage.setY(0);

    }
    public static void Main(String[] args){
        launch(args);
    }
}
