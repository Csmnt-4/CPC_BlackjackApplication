package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

public class ImageRenderer extends DefaultTableCellRenderer {
    @Serial
    private static final long serialVersionUID = -8404338743784775292L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof BufferedImage) {
            setIcon(new ImageIcon((BufferedImage) value));
            setText(null);
        } else if (value instanceof ImageIcon) {
            setIcon((ImageIcon) value);
            setText(null);
        }
        return this;
    }
}
