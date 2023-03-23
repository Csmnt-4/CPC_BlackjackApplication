import ui.GameFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class BlackJackApplication {
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		changeAllFonts(new FontUIResource("Cascadia mono", Font.TRUETYPE_FONT, 12));
		try {
			GameFrame dialog = new GameFrame();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void changeAllFonts(Font font)
	{
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get (key);
			if (value instanceof javax.swing.plaf.FontUIResource)
				UIManager.put (key, font);
		}
	}
}