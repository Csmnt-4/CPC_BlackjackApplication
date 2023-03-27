import ui.GameFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;


public class BlackJackApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            GameFrame.changeAllFonts(new FontUIResource("Cascadia mono", Font.TRUETYPE_FONT, 12));
            GameFrame blackjackGame = new GameFrame();
        } catch (Exception e) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter("ErrorLog.txt", "UTF-8");
                writer.println(e.getMessage());
                writer.println(Arrays.toString(e.getStackTrace()));
                writer.close();
            } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
            }
        }
    }
}