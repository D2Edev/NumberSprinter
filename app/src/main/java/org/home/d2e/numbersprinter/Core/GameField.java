package org.home.d2e.numbersprinter.Core;

/**
 * Created by druzhyni on 05.07.2015.
 */
public class GameField {
    private int fieldNumber;
    private int fieldColor;

    public GameField(int fieldNumber, int fieldColor) {
        this.fieldNumber = fieldNumber;
        this.fieldColor = fieldColor;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public int getFieldColor() {
        return fieldColor;
    }
}
