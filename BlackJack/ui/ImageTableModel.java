package ui;

import entity.Card;
import entity.Deck;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageTableModel extends AbstractTableModel {

    private List<Icon> icons = new ArrayList<>();
    private List<Card> cards = new ArrayList<>();

    public void add(ImageIcon image) {
        icons.add(image);
        fireTableRowsInserted(icons.size() - 1, icons.size() - 1);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return icons.size();
    }

    @Override
    public Object getValueAt(int columnIndex, int rowIndex) {
        return icons.get(rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return Icon.class;
    }

    @Override
    public String getColumnName(int column) {
        return "Card";
    }
    public List<Icon> getIcons() {
        return icons;
    }

    public void setIcons(List<Icon> icons) {
        this.icons = icons;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
