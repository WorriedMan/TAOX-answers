package me.med.ans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;

public class Controller {
    ArrayList<Answer> mAnswers;
    @FXML
    private TableView<Answer> mAnswerTable;
    @FXML
    private TableColumn<Answer, String> mPartColumn;
    @FXML
    private TableColumn<Answer, String> mPositionColumn;

    @FXML
    private Label totalCardsLabel;
    @FXML
    private TextField mPartNameEdit;
    @FXML
    private Button mFindButton;

    private Main mainApp;

    ObservableList<Answer> mResultsList = FXCollections.observableArrayList();

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

    }

    void setMainApp(Main mainApp, ArrayList<Answer> answers) {
        this.mainApp = mainApp;
        mAnswers = answers;
    }

    private void findPart(String text) {
        mResultsList.clear();
        int[] found = {0};
        mAnswers.forEach(answer -> {
            if (answer.getPart().contains(text)) {
                found[0]++;
                mResultsList.add(answer);
                System.out.println("Answer: " + answer.getPart() + " | " + answer.getPosition());
            }
        });
        if (found[0] == 0) {
            System.out.println("Поиск заверщен, ответы не найдены");
        } else {
            System.out.println("Поиск заверщен, ответы выведены");
        }
        mAnswerTable.setItems(mResultsList);
    }
}
