import ui.GameFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;


public class BlackJackApplication {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
            GameFrame.changeAllFonts(
                    new FontUIResource("Cascadia mono", Font.TRUETYPE_FONT, 12));
            GameFrame blackjackGame = new GameFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}