import ui.GameFrame;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/** BlackJackApplication class - entry-point-class responsible for instantiating the game window frame,
 * initial style adjustments and top-level (as in the entry-point of the program) exceptions handling.
 *
 * @author Victor Anisimov
 * @since 3/8/2023                                                                          <p></p>
 * Revision history:                                                                        <p>
 *  3/08/2023 - Initialise the class along with other core classes.                         <p>
 *  3/23/2023 - Create the main window, add changeAllFonts() function.                      <p>
 *  3/26/2023 - Edit exception handling, format and edit main() method.                     <p>
 *  3/31/2023 - Add comments, small changes in the main() method.                           <p>
 */

public class BlackJackApplication {
    /** General method main(String[] args) for calling main functions in a single point (allowing
     * processing all the errors in a unified way), and creating a new instance of a UI for the program.
     *
     * @author Victor Anisimov
     * @param args String[]
     * @since 3/8/2023                                                                          <p></p>
     * Revision history:                                                                        <p>
     *  3/08/2023 - Initialise the core classes and "Hello world" output.                       <p>
     *  3/23/2023 - Create a mockup UI with ImageBuilder, add changeAllFonts() function.        <p>
     *  3/26/2023 - Format the code into a single try-catch block.                              <p>
     *  3/26/2023 - Add an "error processor" - print last exception message to the .txt file.   <p>
     *  3/31/2023 - Add comments.                                                               <p>
     *  3/31/2023 - Add possible alteration between default ruleset and "player-chasing".
     */
    public static void main(String[] args) {
        try {
            // Setting the unified Windows LookAndFeel for the GUI.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Setting a single font for all the text in every component available.
            GameFrame.changeAllFonts(new FontUIResource("Cascadia mono", Font.TRUETYPE_FONT, 12));
            // Creating a new object of GameFrame class, which, due to being
            //  an extension to JFrame class, allows us to create a new window.
            new GameFrame(false);
        } catch (Exception e) {
            // Creating simple flag to detect successful exception handling, i.e. output to the file.
            boolean successfulOutput = false;
            // Try repeating the process until all the steps are done successfully.
            while (!successfulOutput) {
                try {
                    // Create a formatting for the timestamp.
                    SimpleDateFormat formatter = new SimpleDateFormat("ErrorLog_MM/dd/yyyy_HH:mm:ss.txt");
                    // Get current timestamp.
                    Date date = new Date();
                    // Creating a writer to output the text into a file.
                    PrintWriter writer;
                    // Creating a new file for the output.
                    writer = new PrintWriter( formatter.format(date), StandardCharsets.UTF_8);
                    // Printing the exception message.
                    writer.println(e.getMessage());
                    writer.println(Arrays.toString(e.getStackTrace()));
                    // Closing the file.
                    writer.close();
                    successfulOutput = true;
                } catch (IOException ignored) {
                    // Anything that happens to the PrintWriter or the file is out of control, as we could
                    //  only try to repeat the process.
                }
            }
        }
    }
}