package me.med.ans;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Main extends Application {

    private static final String ANSI_CODE = "windows-1251";
    private static final Charset ANSI_CHARSET = Charset.forName(ANSI_CODE);

    private ArrayList<Answer> loadAnswers() {
        ArrayList<Answer> mAnswers = new ArrayList<>();
        try {
            Path path = Paths.get("Vopros.txt");
            Stream<String> stream = Files.lines(path, ANSI_CHARSET);
            stream.forEach((line) -> {
                int lastPosition = 0;
                int length = line.length();
                if (length > 4) {
                    for (int i = 0; i < length; i++) {
                        if (line.charAt(i) != ' ') {
                            lastPosition++;
                        } else {
                            if (length >= lastPosition) {
                                Answer answer = new Answer(line.substring(0, lastPosition), line.substring(lastPosition).toLowerCase());
                                mAnswers.add(answer);
                            }
                            break;
                        }
                    }
                }
            });
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не удалось загрузить ответы. Удостоверьтесь, что в папке с данной программой есть файл Vopros.txt", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.exit(0);
            }
        }
        System.out.println("Ответов загружено: " + mAnswers.size());
        return mAnswers;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("sample.fxml").openStream());
        Controller appController = fxmlLoader.getController();
        primaryStage.setTitle("TAOX ответы");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.show();
        ArrayList<Answer> answers = loadAnswers();
        appController.setMainApp(this, answers);
    }


    public static void main(String[] args) {

        launch(args);
    }
}
