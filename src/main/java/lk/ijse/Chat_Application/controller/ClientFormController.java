package lk.ijse.Chat_Application.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClientFormController extends Thread{
    @FXML
    private ScrollPane ScorllMassage;

    @FXML
    private AnchorPane clientAnchor;

    @FXML
    private VBox clientVBox;

    @FXML
    private AnchorPane imojiPane;

    @FXML
    private AnchorPane nameAnchor;

    @FXML
    private Label lblName;

    @FXML
    private TextField txtClientMessage;



    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    private FileChooser fileChooser;
    private File filePath;


    public void initialize(){
        String userName = LoginFormController.userName;
        lblName.setText(userName);

        try {
            socket = new Socket("localhost", 3002);//4500
            System.out.println("Connected...");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imojiPane.setVisible(false);
    }

    @Override
    public void run() {
        try {
            while (true) {

                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fullMsg.append(tokens[i]+" ");
                }

                String[] msgToAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgToAr.length - 1; i++) {
                    st += msgToAr[i + 1] + " ";
                }

                Text text = new Text(st);
                String firstChars = "";
                if (st.length() > 3) {
                    firstChars = st.substring(0, 3);
                }

                if (firstChars.equalsIgnoreCase("img")) {
                    //for the Images

                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblName.getText())) {

                        clientVBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);

                        Text text1 = new Text("  " + cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);

                    } else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text("");
                        hBox.getChildren().add(text1);

                    }
                    Platform.runLater(() -> clientVBox.getChildren().addAll(hBox));

                } else {

                    TextFlow tempFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        tempFlow.getChildren().add(txtName);

                        tempFlow.setStyle("-fx-font-size: 14px; -fx-color: rgb(239,242,255); -fx-background-color: rgb(149,208,253); -fx-background-radius: 10px");
                        tempFlow.setPadding(new Insets(3, 10, 3, 10));
                    }

                    tempFlow.getChildren().add(text);
                    tempFlow.setMaxWidth(200);

                    TextFlow flow = new TextFlow(tempFlow);

                    HBox hBox = new HBox(12);

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")) {

                        clientVBox.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(flow);
                        hBox.setPadding(new Insets(2,5,2,10));

                    } else {

                        Text text2 = new Text(fullMsg + " ");
                        TextFlow flow2 = new TextFlow(text2);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(flow2);
                        hBox.setPadding(new Insets(2,5,2,10));


                        flow2.setStyle("-fx-font-size: 14px; -fx-color: rgb(239,242,255); -fx-background-color: rgb(184,248,184); -fx-background-radius: 10px");
                        flow2.setPadding(new Insets(3,10,3,10));

                    }

                    Platform.runLater(() -> clientVBox.getChildren().addAll(hBox));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Mouse_On_Click_Hide_Emogi(MouseEvent event) {
        imojiPane.setVisible(false);

    }

    @FXML
    void green_sad(MouseEvent event) {
        String emoji = new String(Character.toChars(128560));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void img_cam_onMouse(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.filePath = fileChooser.showOpenDialog(stage);
        writer.println(lblName.getText() + " " + "img" + filePath.getPath());

    }

    @FXML
    void img_massage_send_onMouse(MouseEvent event) {
        String msg = txtClientMessage.getText();
        writer.println(lblName.getText() + ": " + msg);

        txtClientMessage.clear();

        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    @FXML
    void imojiOnMouse(MouseEvent event) {
        imojiPane.setVisible(true);
    }
    @FXML
    void imojiOnMouseDrg(MouseEvent event) {
        imojiPane.setVisible(false);
    }

    @FXML
    void love(MouseEvent event) {
        String emoji = new String(Character.toChars(128525));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void sad(MouseEvent event) {
        String emoji = new String(Character.toChars(128546));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void txtClientMessageOnAction(ActionEvent event) {
        String msg = txtClientMessage.getText();
        writer.println(lblName.getText() + ": " + msg);

        txtClientMessage.clear();

        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    @FXML
    void cry_head(MouseEvent event) {
        String emoji = new String(Character.toChars(128550));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void lot_sad(MouseEvent event) {
        String emoji = new String(Character.toChars(128554));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void real_smaile(MouseEvent event) {
        String emoji = new String(Character.toChars(128514));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void smile(MouseEvent event) {
        String emoji = new String(Character.toChars(128578));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void smile_normal(MouseEvent event) {
        String emoji = new String(Character.toChars(128513));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void smile_one_eyy(MouseEvent event) {
        String emoji = new String(Character.toChars(128540));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void tuin(MouseEvent event) {
        String emoji = new String(Character.toChars(128519));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }

    @FXML
    void woow(MouseEvent event) {
        String emoji = new String(Character.toChars(128559));
        txtClientMessage.setText(emoji);
        imojiPane.setVisible(false);
    }
}
