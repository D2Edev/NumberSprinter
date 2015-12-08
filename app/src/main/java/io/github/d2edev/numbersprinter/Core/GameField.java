package io.github.d2edev.numbersprinter.Core;

/**
 * Created by druzhyni on 05.07.2015.
 */
public class GameField {
    private int fieldNumber;
    private int fieldColor;
    private int fieldTextColor;


    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public void setFieldTextColor(int fieldTextColor) { this.fieldTextColor = fieldTextColor; }

    public void setFieldColor(int fieldColor) {
        this.fieldColor = fieldColor;
    }

    public GameField(int fieldNumber, int fieldColor, int fieldTextColor) {
        this.fieldNumber = fieldNumber;
        this.fieldColor = fieldColor;
        this.fieldTextColor = fieldTextColor;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public int getFieldColor() {
        return fieldColor;
    }

    public int getFieldTextColor() {return fieldTextColor; }
}
