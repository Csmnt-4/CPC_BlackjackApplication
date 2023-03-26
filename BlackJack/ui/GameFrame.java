package ui;

import entity.Deck;
import manager.PlayerInteractionManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.border.*;

public class GameFrame extends JFrame {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -7277346550704819216L;
    private JPanel dealerPane;
    private JPanel dealerBalancePane;
    private JLabel dealerBalanceLabel;
    private JLabel dealerBalanceValueLabel;

    private JPanel dealerCardsValuePane;
    private JLabel dealerCardsLabel;
    private JLabel dealerCardsValueLabel;

    private JPanel tablePanel;
    private JTable dealerCardsTable;
    private ImageTableModel dealerTableModel = new ImageTableModel();
    private JLabel tableBetLabel;
    private JTable playerCardsTable;
    private ImageTableModel playerTableModel = new ImageTableModel();

    private JPanel playerPanel;


    private JPanel playerBalancePane;

    private JLabel playerBalanceLabel;
    private JLabel playerBalanceValueLabel;

    private JPanel playerBetPane;
    private JTextField playerBetTextField;
    private JButton playerBetButton;

    private JPanel hitOrPassPane;
    private JButton hitButton;
    private JButton passButton;

    private JPanel playerCardsValuePane;
    private JLabel playerCardsLabel;
    private JLabel playerCardsValueLabel;

    private boolean bet;
    private boolean hit;
    private boolean pass;

    /**
     * Launch the application.
     */

    public GameFrame() {
        /* Create fields, buttons and tables. */
//        setTitle("Blackjack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 345);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());

        initializeTablePane();
        initializePlayerPane();
        initializeDealerPane();

        PlayerInteractionManager manager = null;
        try {
            manager = new PlayerInteractionManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        setAlwaysOnTop(true);
        setVisible(true);

        manager.gameLoop(this);
    }

    private void addCardToField() {
        // TODO: Create options to add by name, add by adding the card, etc;
    }
    private void initializeTablePane() {
        tablePanel = new JPanel();
        tablePanel.setBackground(new Color(0, 100, 0));
        tablePanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        getContentPane().add(tablePanel, BorderLayout.CENTER);

        dealerCardsTable = initializeTable(dealerTableModel);
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

        playerCardsTable = initializeTable(playerTableModel);
        tablePanel.add(playerCardsTable);
    }

    private JTable initializeTable(ImageTableModel model) {
        model = new ImageTableModel() {
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
        table.setBackground(new Color(0, 100, 0));
        table.setRowHeight(100);
        table.setMaximumSize(new Dimension(0, 100)); //TODO: Add to the addCard function
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);
        table.setVisible(true);

        return table;
    }

    private void initializePlayerPane() {
        playerPanel = new JPanel();
        FlowLayout fl_playerPane = new FlowLayout(FlowLayout.CENTER);
        playerPanel.setLayout(fl_playerPane);
        getContentPane().add(playerPanel, BorderLayout.SOUTH);

        playerBalancePane = initializePane();
        playerPanel.add(playerBalancePane);

        playerBalanceLabel = new JLabel("Balance:");
        playerBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerBalancePane.add(playerBalanceLabel);

        playerBalanceValueLabel = new JLabel("0");
        playerBalancePane.add(playerBalanceValueLabel);

        playerBetPane = new JPanel();
        playerPanel.add(playerBetPane);
        playerBetPane.setLayout(new FlowLayout(FlowLayout.CENTER));

        playerBetTextField = new JTextField();
        playerBetPane.add(playerBetTextField);
        playerBetTextField.setColumns(5);

        playerBetButton = new JButton("Bet");
        playerBetButton.setHorizontalAlignment(SwingConstants.LEFT);
        playerBetButton.addActionListener(e -> {
            setBet(true);
        });
        playerBetPane.add(playerBetButton);

        hitOrPassPane = new JPanel();
        playerPanel.add(hitOrPassPane);
        hitOrPassPane.setLayout(new FlowLayout(FlowLayout.CENTER));
        hitButton = new JButton("Hit");
        hitButton.addActionListener(e -> {
            setHit(true);
        });
        hitButton.setEnabled(false);
        hitButton.setHorizontalAlignment(SwingConstants.LEADING);
        hitButton.setActionCommand("OK");
        hitOrPassPane.add(hitButton);

        passButton = new JButton("Pass");
        hitOrPassPane.add(passButton);
        passButton.setHorizontalAlignment(SwingConstants.LEFT);
        passButton.setActionCommand("Cancel");
        passButton.addActionListener(e -> {
            setPass(true);
        });
        passButton.setEnabled(false);

        playerCardsValuePane = initializePane();
        playerPanel.add(playerCardsValuePane);

        playerCardsLabel = new JLabel("Cards value:");
        playerCardsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        playerCardsValuePane.add(playerCardsLabel);

        playerCardsValueLabel = new JLabel("0");
        playerCardsValuePane.add(playerCardsValueLabel);
    }

    private void initializeDealerPane() {
        dealerPane = new JPanel();
        getContentPane().add(dealerPane, BorderLayout.NORTH);

        dealerBalancePane = initializePane();
        dealerPane.add(dealerBalancePane);

        dealerBalanceLabel = new JLabel("Balance:");
        dealerBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dealerBalancePane.add(dealerBalanceLabel);

        dealerBalanceValueLabel = new JLabel("0");
        dealerBalancePane.add(dealerBalanceValueLabel);

        dealerCardsValuePane = initializePane();
        dealerPane.add(dealerCardsValuePane);

        dealerCardsLabel = new JLabel("Cards value:");
        dealerCardsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        dealerCardsValuePane.add(dealerCardsLabel);

        dealerCardsValueLabel = new JLabel("0");
        dealerCardsValuePane.add(dealerCardsValueLabel);
    }

    private JPanel initializePane() {
        JPanel panel = new JPanel();
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED,
                null,
                null,
                null,
                null));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        return panel;
    }

    public static void changeAllFonts(Font font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put(key, font);
        }
    }

    public JPanel getDealerPane() {
        return dealerPane;
    }

    public void setDealerPane(JPanel dealerPane) {
        this.dealerPane = dealerPane;
    }

    public JPanel getDealerBalancePane() {
        return dealerBalancePane;
    }

    public void setDealerBalancePane(JPanel dealerBalancePane) {
        this.dealerBalancePane = dealerBalancePane;
    }

    public JLabel getDealerBalanceLabel() {
        return dealerBalanceLabel;
    }

    public void setDealerBalanceLabel(JLabel dealerBalanceLabel) {
        this.dealerBalanceLabel = dealerBalanceLabel;
    }

    public JLabel getDealerBalanceValueLabel() {
        return dealerBalanceValueLabel;
    }

    public void setDealerBalanceValueLabel(JLabel dealerBalanceValueLabel) {
        this.dealerBalanceValueLabel = dealerBalanceValueLabel;
    }

    public JPanel getDealerCardsValuePane() {
        return dealerCardsValuePane;
    }

    public void setDealerCardsValuePane(JPanel dealerCardsValuePane) {
        this.dealerCardsValuePane = dealerCardsValuePane;
    }

    public JLabel getDealerCardsLabel() {
        return dealerCardsLabel;
    }

    public void setDealerCardsLabel(JLabel dealerCardsLabel) {
        this.dealerCardsLabel = dealerCardsLabel;
    }

    public JLabel getDealerCardsValueLabel() {
        return dealerCardsValueLabel;
    }

    public void setDealerCardsValueLabel(JLabel dealerCardsValueLabel) {
        this.dealerCardsValueLabel = dealerCardsValueLabel;
    }

    public JPanel getTablePanel() {
        return tablePanel;
    }

    public void setTablePanel(JPanel tablePanel) {
        this.tablePanel = tablePanel;
    }

    public JTable getDealerCardsTable() {
        return dealerCardsTable;
    }

    public void setDealerCardsTable(JTable dealerCardsTable) {
        this.dealerCardsTable = dealerCardsTable;
    }

    public JLabel getTableBetLabel() {
        return tableBetLabel;
    }

    public void setTableBetLabel(JLabel tableBetLabel) {
        this.tableBetLabel = tableBetLabel;
    }

    public JTable getPlayerCardsTable() {
        return playerCardsTable;
    }

    public void setPlayerCardsTable(JTable playerCardsTable) {
        this.playerCardsTable = playerCardsTable;
    }

    public ImageTableModel getDealerTableModel() {
        return dealerTableModel;
    }

    public void setDealerTableModel(ImageTableModel dealerTableModel) {
        this.dealerTableModel = dealerTableModel;
    }

    public ImageTableModel getPlayerTableModel() {
        return playerTableModel;
    }

    public void setPlayerTableModel(ImageTableModel playerTableModel) {
        this.playerTableModel = playerTableModel;
    }

    public JPanel getPlayerPanel() {
        return playerPanel;
    }

    public void setPlayerPanel(JPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public JLabel getPlayerBalanceLabel() {
        return playerBalanceLabel;
    }

    public void setPlayerBalanceLabel(JLabel playerBalanceLabel) {
        this.playerBalanceLabel = playerBalanceLabel;
    }

    public JLabel getPlayerBalanceValueLabel() {
        return playerBalanceValueLabel;
    }

    public void setPlayerBalanceValueLabel(JLabel playerBalanceValueLabel) {
        this.playerBalanceValueLabel = playerBalanceValueLabel;
    }

    public JPanel getPlayerBalancePane() {
        return playerBalancePane;
    }

    public void setPlayerBalancePane(JPanel playerBalancePane) {
        this.playerBalancePane = playerBalancePane;
    }

    public JPanel getPlayerBetPane() {
        return playerBetPane;
    }

    public void setPlayerBetPane(JPanel playerBetPane) {
        this.playerBetPane = playerBetPane;
    }

    public JTextField getPlayerBetTextField() {
        return playerBetTextField;
    }

    public void setPlayerBetTextField(JTextField playerBetTextField) {
        this.playerBetTextField = playerBetTextField;
    }

    public JButton getPlayerBetButton() {
        return playerBetButton;
    }

    public void setPlayerBetButton(JButton playerBetButton) {
        this.playerBetButton = playerBetButton;
    }

    public JPanel getHitOrPassPane() {
        return hitOrPassPane;
    }

    public void setHitOrPassPane(JPanel hitOrPassPane) {
        this.hitOrPassPane = hitOrPassPane;
    }

    public JButton getHitButton() {
        return hitButton;
    }

    public void setHitButton(JButton hitButton) {
        this.hitButton = hitButton;
    }

    public JButton getPassButton() {
        return passButton;
    }

    public void setPassButton(JButton passButton) {
        this.passButton = passButton;
    }

    public JPanel getPlayerCardsValuePane() {
        return playerCardsValuePane;
    }

    public void setPlayerCardsValuePane(JPanel playerCardsValuePane) {
        this.playerCardsValuePane = playerCardsValuePane;
    }

    public JLabel getPlayerCardsLabel() {
        return playerCardsLabel;
    }

    public void setPlayerCardsLabel(JLabel playerCardsLabel) {
        this.playerCardsLabel = playerCardsLabel;
    }

    public JLabel getPlayerCardsValueLabel() {
        return playerCardsValueLabel;
    }

    public void setPlayerCardsValueLabel(JLabel playerCardsValueLabel) {
        this.playerCardsValueLabel = playerCardsValueLabel;
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

    public void setInvisibleCardToTable(Deck deck) {
        this.getPlayerTableModel().getCards().add(deck.getCards().get(0));
        this.getPlayerTableModel().add(deck.getCardIcon(deck.getCards().get(0).getCardName()));
        this.getPlayerCardsTable().setModel(this.getPlayerTableModel());
        this.getPlayerCardsTable().setMaximumSize(new Dimension(0,100));

        this.getDealerTableModel().getCards().add(deck.getCards().get(0));
        this.getDealerTableModel().add(deck.getCardIcon(deck.getCards().get(0).getCardName()));
        this.getDealerCardsTable().setModel(this.getDealerTableModel());
        this.getDealerCardsTable().setMaximumSize(new Dimension(0,100));
    }
}
