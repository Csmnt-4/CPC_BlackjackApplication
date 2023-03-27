package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class ImageTableModel extends DefaultTableModel {

    private final List<ImageIcon> icons = new ArrayList<>();

    public void add(ImageIcon image) throws Exception {
        icons.add(image);
        this.addColumn(image.toString(),
                new ImageIcon[]{image});
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        if (icons != null) {
            return icons.size();
        }
        return 1;
    }

    @Override
    public Object getValueAt(int columnIndex, int rowIndex) {
        return icons.get(rowIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return ImageIcon.class;
    }

    @Override
    public String getColumnName(int column) {
        return "Card";
    }

    public List<ImageIcon> getIcons() {
        return icons;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
