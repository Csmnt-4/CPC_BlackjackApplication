package ui;

import entity.Deck;
import manager.PlayerInteractionManager;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Enumeration;

public class GameFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = -7277346550704819216L;
    private final ImageTableModel dealerTableModel = new ImageTableModel();
    private final ImageTableModel playerTableModel = new ImageTableModel();
    private JPanel dealerBalancePane;
    private JLabel dealerBalanceValueLabel;
    private JLabel dealerCardsValueLabel;
    private JTable dealerCardsTable;
    private JLabel tableBetLabel;
    private JTable playerCardsTable;


    private JPanel playerBalancePane;

    private JLabel playerBalanceValueLabel;

    private JTextField playerBetTextField;
    private JButton playerBetButton;

    private JButton hitButton;
    private JButton standButton;

    private JLabel playerCardsValueLabel;

    private boolean bet;
    private boolean hit;
    private boolean pass;

    public GameFrame() throws Exception {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 350);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 550 / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 350 / 2);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());

        initializeTablePane();
        initializePlayerPane();
        initializeDealerPane();

        try {
            PlayerInteractionManager manager = new PlayerInteractionManager();

            setAlwaysOnTop(true);
            setVisible(true);

            manager.gameLoop(this);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void changeAllFonts(Font font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, font);
        }
    }

    private void initializeTablePane() {
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(new Color(0, 100, 0));
        tablePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        getContentPane().add(tablePanel, BorderLayout.CENTER);

        dealerCardsTable = initializeTable();
        tablePanel.add(dealerCardsTable);

        JSeparator upperTableSeparator = new JSeparator();
        upperTableSeparator.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        upperTableSeparator.setForeground(new Color(169, 169, 169));
        tablePanel.add(upperTableSeparator);

        tableBetLabel = new JLabel("Current bet:");
        tableBetLabel.setForeground(new Color(245, 255, 250));
        tableBetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tableBetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(tableBetLabel);

        JSeparator lowerTableSeparator;
        lowerTableSeparator = new JSeparator();
        lowerTableSeparator.setAlignmentY(Component.TOP_ALIGNMENT);
        lowerTableSeparator.setToolTipText("");
        lowerTableSeparator.setForeground(new Color(169, 169, 169));
        tablePanel.add(lowerTableSeparator);

        playerCardsTable = initializeTable();
        tablePanel.add(playerCardsTable);
    }

    private JTable initializeTable() {
        ImageTableModel model = new ImageTableModel() {
            @Override
            public Class<?> getColumnClass(int column) {
                if (getRowCount() > 0) {
                    return Icon.class;
                }
                return super.getColumnClass(column);
            }
        };

        JTable table = new JTable(model);

        table.setDefaultRenderer(BufferedImage.class, new ImageRenderer());
        table.setRowHeight(100);
        table.setBackground(new Color(0, 100, 0));
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setFocusable(false);
        table.setRowSelectionAllowed(false);
        table.setVisible(true);

        return table;
    }

    private void initializePlayerPane() {
        JPanel playerPanel = new JPanel();
        FlowLayout fl_playerPane = new FlowLayout(FlowLayout.CENTER);
        playerPanel.setLayout(fl_playerPane);
        getContentPane().add(playerPanel, BorderLayout.SOUTH);

        playerBalancePane = initializePane();
        playerPanel.add(playerBalancePane);

        JLabel playerBalanceLabel = new JLabel("Balance:");
        playerBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerBalancePane.add(playerBalanceLabel);

        playerBalanceValueLabel = new JLabel("0");
        playerBalancePane.add(playerBalanceValueLabel);

        JPanel playerBetPane = new JPanel();
        playerPanel.add(playerBetPane);
        playerBetPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        playerBetTextField = new JTextField();
        playerBetTextField.setPreferredSize(new Dimension(50,21));
        playerBetPane.add(playerBetTextField);

        playerBetButton = new JButton("Bet");
        playerBetButton.setPreferredSize(new Dimension(55,23));
        playerBetButton.setHorizontalAlignment(SwingConstants.LEFT);
        playerBetButton.addActionListener(e -> setBet(true));
        playerBetPane.add(playerBetButton);

        JPanel hitOrPassPane = new JPanel();
        playerPanel.add(hitOrPassPane);
        hitOrPassPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        hitButton = new JButton("Hit");
        hitButton.setPreferredSize(new Dimension(55,23));
        hitButton.addActionListener(e -> setHit(true));
        hitButton.setEnabled(false);
        hitButton.setHorizontalAlignment(SwingConstants.LEADING);
        hitButton.setActionCommand("OK");
        hitOrPassPane.add(hitButton);

        standButton = new JButton("Stand");
        standButton.setPreferredSize(new Dimension(70,23));
        standButton.setHorizontalAlignment(SwingConstants.LEFT);
        standButton.setActionCommand("Cancel");
        standButton.addActionListener(e -> setPass(true));
        standButton.setEnabled(false);
        hitOrPassPane.add(standButton);

        JPanel playerCardsValuePane = initializePane();
        playerCardsValuePane.setPreferredSize(new Dimension(130,30));
        playerPanel.add(playerCardsValuePane);

        JLabel playerCardsLabel = new JLabel("Cards value:");
        playerCardsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerCardsLabel.setPreferredSize(new Dimension(90,15));
        playerCardsValuePane.add(playerCardsLabel);

        playerCardsValueLabel = new JLabel("0");
        playerCardsValuePane.add(playerCardsValueLabel);
    }

    private void initializeDealerPane() {
        JPanel dealerPane = new JPanel();
        getContentPane().add(dealerPane, BorderLayout.NORTH);

        dealerBalancePane = initializePane();
        dealerPane.add(dealerBalancePane);

        JLabel dealerBalanceLabel = new JLabel("Balance:");
        dealerBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dealerBalancePane.add(dealerBalanceLabel);

        dealerBalanceValueLabel = new JLabel("0");
        dealerBalancePane.add(dealerBalanceValueLabel);

        JPanel dealerCardsValuePane = initializePane();
        dealerCardsValuePane.setPreferredSize(new Dimension(150,30));
        dealerPane.add(dealerCardsValuePane);

        JLabel dealerCardsLabel = new JLabel("Cards value:");
        dealerCardsLabel.setPreferredSize(new Dimension(90,15));
        dealerCardsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dealerCardsValuePane.add(dealerCardsLabel);

        dealerCardsValueLabel = new JLabel("0");
        dealerCardsValuePane.add(dealerCardsValueLabel);
    }

    private JPanel initializePane() {
        JPanel panel = new JPanel();
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        return panel;
    }

    public JPanel getDealerBalancePane() {
        return dealerBalancePane;
    }

    public JLabel getDealerBalanceValueLabel() {
        return dealerBalanceValueLabel;
    }

    public JLabel getDealerCardsValueLabel() {
        return dealerCardsValueLabel;
    }

    public JTable getDealerCardsTable() {
        return dealerCardsTable;
    }

    public JLabel getTableBetLabel() {
        return tableBetLabel;
    }

    public JTable getPlayerCardsTable() {
        return playerCardsTable;
    }

    public ImageTableModel getDealerTableModel() {
        return dealerTableModel;
    }

    public ImageTableModel getPlayerTableModel() {
        return playerTableModel;
    }

    public JLabel getPlayerBalanceValueLabel() {
        return playerBalanceValueLabel;
    }

    public JPanel getPlayerBalancePane() {
        return playerBalancePane;
    }

    public JTextField getPlayerBetTextField() {
        return playerBetTextField;
    }

    public JButton getPlayerBetButton() {
        return playerBetButton;
    }

    public JButton getHitButton() {
        return hitButton;
    }

    public JButton getStandButton() {
        return standButton;
    }

    public JLabel getPlayerCardsValueLabel() {
        return playerCardsValueLabel;
    }

    public boolean isBet() {
        return bet;
    }

    public void setBet(boolean bet) {
        this.bet = bet;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public void setInvisibleCardToTable(Deck deck) throws Exception{
        this.getPlayerTableModel().add(deck.getCardIcon(deck.getCards().get(0).getCardName()));
        this.getPlayerCardsTable().setModel(this.getPlayerTableModel());
        this.getPlayerCardsTable().setMaximumSize(new Dimension(0, 100));

        this.getDealerTableModel().add(deck.getCardIcon(deck.getCards().get(0).getCardName()));
        this.getDealerCardsTable().setModel(this.getDealerTableModel());
        this.getDealerCardsTable().setMaximumSize(new Dimension(0, 100));
    }
}
