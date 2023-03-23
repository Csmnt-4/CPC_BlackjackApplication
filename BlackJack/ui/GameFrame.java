package ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class GameFrame extends JFrame {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -7277346550704819216L;
    private final JPanel tablePanel = new JPanel();
    private JTextField betTextField;
    private JTable dealerCardsTable;
    private JTable playerCardsTable;

    /**
     * Launch the application.
     */

    public GameFrame() {
        /**
         * Create fields, buttons and tables.
         */

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 280);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tablePanel, BorderLayout.CENTER);
        tablePanel.setBackground(new Color(0, 100, 0));
        tablePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        initializeTablePane();  //TODO: Put away parts of initialization
                                // + set up repeating code as functions
        initializeDealerPane();
        initializePlayerPane();

        {
            ImageTableModel model = new ImageTableModel() {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (getRowCount() > 0) {
                        return getValueAt(0, column).getClass();
                    }
                    return super.getColumnClass(column);
                }
            };

            try {
                BufferedImage im = ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_diamonds_A.png"));
                model.add(im);
                im = ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_back.png"));
                model.add(im);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //TODO: Repeating code
            dealerCardsTable = new JTable(model);
            dealerCardsTable.setDefaultRenderer(BufferedImage.class, new ImageRenderer());
            dealerCardsTable.setRowHeight(((ImageIcon) model.getValueAt(0, 1)).getIconHeight());
            dealerCardsTable.setMaximumSize(new Dimension(((ImageIcon) model.getValueAt(0, 1)).getIconWidth() * 2, ((ImageIcon) model.getValueAt(0, 1)).getIconHeight()));
            dealerCardsTable.setBackground(new Color(0, 100, 0));
            dealerCardsTable.setColumnSelectionAllowed(false);
            dealerCardsTable.setCellSelectionEnabled(false);
            dealerCardsTable.setShowVerticalLines(false);
            dealerCardsTable.setShowHorizontalLines(false);
            tablePanel.add(dealerCardsTable);
        }
        {
            JSeparator tableSeparator = new JSeparator();
            tableSeparator.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            tableSeparator.setForeground(new Color(169, 169, 169));
            tablePanel.add(tableSeparator);
        }
        {
            JLabel tableBetLabel = new JLabel("Current bet:");
            tableBetLabel.setForeground(new Color(245, 255, 250));
            tableBetLabel.setHorizontalAlignment(SwingConstants.CENTER);
            tableBetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            tablePanel.add(tableBetLabel);
        }
        {
            JSeparator tableSeparator = new JSeparator();
            tableSeparator.setAlignmentY(Component.TOP_ALIGNMENT);
            tableSeparator.setToolTipText("");
            tableSeparator.setForeground(new Color(169, 169, 169));
            tablePanel.add(tableSeparator);
        }
        {
            playerCardsTable = new JTable();
            ImageTableModel playerCardsModel = new ImageTableModel() {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (getRowCount() > 0) {
                        return getValueAt(0, column).getClass();
                    }
                    return super.getColumnClass(column);
                }
            };

            try {
                BufferedImage im = ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_hearts_02.png")); //TODO: addCard function
                playerCardsModel.add(im);
                im = ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_clubs_06.png"));
                playerCardsModel.add(im);
                im = ImageIO.read(new File("C:\\Users\\csmnt\\IdeaProjects\\BlackJack\\resources\\card_spades_Q.png"));
                playerCardsModel.add(im);
                playerCardsModel.add(im);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            playerCardsTable = new JTable(playerCardsModel);
            playerCardsTable.setDefaultRenderer(BufferedImage.class, new ImageRenderer());
            playerCardsTable.setRowHeight(((ImageIcon) playerCardsModel.getValueAt(0, 1)).getIconHeight());
            playerCardsTable.setMaximumSize(
                    new Dimension(
                            ((ImageIcon) playerCardsModel.getValueAt(0, 1)).getIconWidth() * (playerCardsModel.getColumnCount() - 1),
                            ((ImageIcon) playerCardsModel.getValueAt(0, 1)).getIconHeight())); //TODO: Add to the addCard function
            playerCardsTable.setBackground(new Color(0, 100, 0));
            playerCardsTable.setColumnSelectionAllowed(false);
            playerCardsTable.setCellSelectionEnabled(false);
            playerCardsTable.setShowVerticalLines(false);
            playerCardsTable.setShowHorizontalLines(false);
            tablePanel.add(playerCardsTable);
        }
        {
            JPanel playerPane = new JPanel();
            FlowLayout fl_playerPane = new FlowLayout(FlowLayout.CENTER);
            playerPane.setLayout(fl_playerPane);
            getContentPane().add(playerPane, BorderLayout.SOUTH);
            {
                JPanel playerBalancePane = new JPanel();
                playerBalancePane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
                playerPane.add(playerBalancePane);
                playerBalancePane.setLayout(new FlowLayout(FlowLayout.CENTER));
                {
                    JLabel playerBalanceLabel = new JLabel("Balance:");
                    playerBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    playerBalancePane.add(playerBalanceLabel);
                }
                {
                    JLabel playerBalanceValueLabel = new JLabel("0");
                    playerBalancePane.add(playerBalanceValueLabel);
                }
            }
            {
                JPanel betPane = new JPanel();
                playerPane.add(betPane);
                betPane.setLayout(new FlowLayout(FlowLayout.CENTER));
                {
                    betTextField = new JTextField();
                    betPane.add(betTextField);
                    betTextField.setColumns(5);
                }
                {
                    JButton betButton = new JButton("Bet");
                    betButton.setHorizontalAlignment(SwingConstants.LEFT);
                    betButton.setActionCommand("Cancel");
                    betPane.add(betButton);
                }
            }
            {
                JPanel hitOrPassPane = new JPanel();
                playerPane.add(hitOrPassPane);
                hitOrPassPane.setLayout(new FlowLayout(FlowLayout.CENTER));
                {
                    JButton hitButton = new JButton("Hit");
                    hitButton.setHorizontalAlignment(SwingConstants.LEADING);
                    hitButton.setActionCommand("OK");
                    hitOrPassPane.add(hitButton);
                }
                {
                    JButton passButton = new JButton("Pass");
                    passButton.setHorizontalAlignment(SwingConstants.LEFT);
                    passButton.setActionCommand("Cancel");
                    hitOrPassPane.add(passButton);
                }
            }
            {
                JPanel playerCardsValuePane = new JPanel();
                playerCardsValuePane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
                playerPane.add(playerCardsValuePane);
                playerCardsValuePane.setLayout(new FlowLayout(FlowLayout.CENTER));
                {
                    JLabel playerCardsLabel = new JLabel("Cards value:");
                    playerCardsLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    playerCardsValuePane.add(playerCardsLabel);
                }
                {
                    JLabel playerCardsValueLabel = new JLabel("0");
                    playerCardsValuePane.add(playerCardsValueLabel);
                }
            }
        }
        {
            JPanel dealerPane = new JPanel();
            getContentPane().add(dealerPane, BorderLayout.NORTH);
            {
                JPanel dealerBalancePane = new JPanel();
                dealerBalancePane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
                dealerPane.add(dealerBalancePane);
                dealerBalancePane.setLayout(new FlowLayout(FlowLayout.CENTER));
                {
                    JLabel dealerBalanceLabel = new JLabel("Balance:");
                    dealerBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    dealerBalancePane.add(dealerBalanceLabel);
                }
                {
                    JLabel dealerBalanceValueLabel = new JLabel("0");
                    dealerBalancePane.add(dealerBalanceValueLabel);
                }
            }
            {
                JPanel dealerCardsValuePane = new JPanel();
                dealerCardsValuePane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
                dealerPane.add(dealerCardsValuePane);
                dealerCardsValuePane.setLayout(new FlowLayout(FlowLayout.CENTER));
                {
                    JLabel dealerCardsLabel = new JLabel("Cards value:");
                    dealerCardsLabel.setHorizontalAlignment(SwingConstants.LEFT);
                    dealerCardsValuePane.add(dealerCardsLabel);
                }
                {
                    JLabel dealerCardsValueLabel = new JLabel("0");
                    dealerCardsValuePane.add(dealerCardsValueLabel);
                }

            }
        }
        setAlwaysOnTop(true);
        setVisible(true);
    }

    private void initializeTablePane() {

    }

    private void initializePlayerPane() {

    }

    private void initializeDealerPane() {

    }
}
