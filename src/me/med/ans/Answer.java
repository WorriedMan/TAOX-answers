package me.med.ans;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Answer {
    private StringProperty mPositionProperty;
    private StringProperty mPartProperty;
    private String mPosition;
    private String mPart;
    private String mImage;
    Answer(String position, String part, String image) {
        mPosition = position;
        mPart = part;
        mPositionProperty = new SimpleStringProperty(mPosition);
        mPartProperty = new SimpleStringProperty(mPart);
        mImage = image;
    }

    String getPosition() {
        return mPosition;
    }

    String getPart() {
        return mPart;
    }

    StringProperty getPositionProperty() {
        return mPositionProperty;
    }

    StringProperty getPartProperty() {
        return mPartProperty;
    }

    String getImage() {
        return mImage;
    }

}
