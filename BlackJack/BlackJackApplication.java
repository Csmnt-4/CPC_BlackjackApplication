import ui.GameFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class BlackJackApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            GameFrame.changeAllFonts(new FontUIResource("Cascadia mono", Font.TRUETYPE_FONT, 12));
            new GameFrame();
        } catch (Exception e) {
            PrintWriter writer;
            try {
                writer = new PrintWriter("ErrorLog.txt", StandardCharsets.UTF_8);
                writer.println(e.getMessage());
                writer.println(Arrays.toString(e.getStackTrace()));
                writer.close();
            } catch (IOException ignored) {
            }
        }
    }
}