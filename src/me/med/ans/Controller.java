package me.med.ans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Controller {
    private ArrayList<Answer> mAnswers;
    @FXML
    private TableView<Answer> mAnswerTable;
    @FXML
    private TableColumn<Answer, String> mPartColumn;
    @FXML
    private TableColumn<Answer, String> mPositionColumn;

    @FXML
    private Label mTotalCardsLabel;
    @FXML
    private TextField mPartNameEdit;
    @FXML
    private Button mFindButton;
    @FXML
    private ImageView mImageView;

    private Main mainApp;

    private ObservableList<Answer> mResultsList = FXCollections.observableArrayList();

    private File mPrevImage;

    public Controller() {
    }

    @FXML
    private void initialize() {
        mPartColumn = new TableColumn<>("Часть тела");
        mPositionColumn = new TableColumn<>("Ответ #");
        mAnswerTable.getColumns().add(mPartColumn);
        mAnswerTable.getColumns().add(mPositionColumn);
        mPartColumn.prefWidthProperty().bind(mAnswerTable.widthProperty().multiply(0.7));
        mPositionColumn.prefWidthProperty().bind(mAnswerTable.widthProperty().multiply(0.29));
        mPartColumn.setResizable(false);
        mPositionColumn.setResizable(false);
        mPartColumn.setCellValueFactory(cellData -> cellData.getValue().getPartProperty());
        mPositionColumn.setCellValueFactory(cellData -> cellData.getValue().getPositionProperty());
        mFindButton.setOnAction(event -> {
            String text = mPartNameEdit.getText();
            findPart(text);
        });

        mAnswerTable.setRowFactory(tv -> {
            TableRow<Answer> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 1) {
                    setImageView(row.getItem());
                }
            });
            return row;
        });


    }

    private void setImageView(Answer item) {
        if (item.getImage() != null) {
            Path imagePath = Paths.get("files/" + item.getImage() + ".jpg");
            try {
                if (Files.exists(imagePath)) {
                    InputStream is = new FileInputStream(imagePath.toString());
                    Image image = new Image(new BufferedInputStream(is), 50, 0, true, false);
                    mImageView.setImage(null);
                    mImageView.setImage(image);
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException ignored) {
                mImageView.setImage(null);
            }
        }
    }

    void setMainApp(Main mainApp, ArrayList<Answer> answers) {
        this.mainApp = mainApp;
        mAnswers = answers;
        mTotalCardsLabel.setText("Загружено ответов: " + answers.size());
    }

    private void findPart(String text) {
        String lowerText = text.toLowerCase();
        mResultsList.clear();
        int[] found = {0};
        mAnswers.forEach(answer -> {
            if (answer.getPart().contains(lowerText)) {
                found[0]++;
                mResultsList.add(answer);
//                System.out.println("Answer: " + answer.getPart() + " | " + answer.getPosition());
            }
        });
        if (found[0] == 0) {
            mAnswerTable.setPlaceholder(new Label("Ничего не найдено"));
        }
        mAnswerTable.setItems(mResultsList);
    }
}
