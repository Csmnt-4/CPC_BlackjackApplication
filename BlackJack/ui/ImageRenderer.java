package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;

/** ImageRenderer class is used to give the table model renderer an idea of what does it render
 * to avoid unnecessary bugs. I am not sure whether the renderer is still needed after I switched
 * to extending DefaultTableModel from AbstractTableModel, because overriding too many functions
 * is not efficient. There *were* possibilities of both BufferedImage and Imageicon being passed
 * into the table, so both options are considered possible.
 *
 * @since 3/23/2023
 * @author Victor Anisimov
 */
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
