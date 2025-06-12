package view;

import controller.RobotController;
import observer.IObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static view.util.ColorPalette.*;
import static view.util.FontPalette.*;
import static view.util.Texts.*;

public class BoardView extends BaseView implements IObserver {
    private final RobotController robotController;
    private JTable gridTable;
    private DefaultTableModel tableModel;
    private JLabel selectedBoardLabel;
    private JLabel messageLabel;
    private BaseView parentMainView;

    public BoardView(RobotController robotController, BaseView parentView) {
        super();
        this.robotController = robotController;
        setupGameView();
        this.parentMainView = parentView;
    }

    private void setupGameView() {
        add(createGamePanel(), BorderLayout.CENTER);
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        gamePanel.add(createHeaderPanel(), BorderLayout.NORTH);
        gamePanel.add(createContentPanel(), BorderLayout.CENTER);

        return gamePanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        contentPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        return contentPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        selectedBoardLabel = new JLabel(BOARD_SELECTED + NO_BOARD_SELECTED);
        selectedBoardLabel.setFont(LABEL.getFont());
        selectedBoardLabel.setForeground(TEXT_WHITE_SOFT.getColor());

        headerPanel.add(selectedBoardLabel);
        return headerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_COLOR.getColor());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_GRAY.getColor(), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        tableModel = new DefaultTableModel();
        gridTable = new JTable(tableModel);
        
        setupTableAppearance();

        JScrollPane scrollPane = new JScrollPane(gridTable);
        scrollPane.setBackground(CARD_COLOR.getColor());
        scrollPane.getViewport().setBackground(CARD_COLOR.getColor());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JPanel tableContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tableContainer.setBackground(CARD_COLOR.getColor());
        tableContainer.add(scrollPane);

        tablePanel.add(tableContainer, BorderLayout.CENTER);
        tablePanel.add(createMessagePanel(), BorderLayout.SOUTH);
        return tablePanel;
    }

    private JPanel createMessagePanel() {
        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setBackground(CARD_COLOR.getColor());
        
        messagePanel.add(createMessageLabel());
        
        return messagePanel;
    }

    private JLabel createMessageLabel() {
        messageLabel = new JLabel();
        messageLabel.setFont(LABEL.getFont());
        messageLabel.setForeground(TRAIL_HIGH_IMPACT.getColor());
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setVisible(false);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        return messageLabel;
    }

    private void setupTableAppearance() {
        gridTable.setFont(COMBO.getFont());
        gridTable.setBackground(TEXT_WHITE_SOFT.getColor());
        gridTable.setForeground(BACKGROUND_DARK_BLUE.getColor());
        gridTable.setSelectionBackground(BUTTON_BLUE.getColor());
        gridTable.setSelectionForeground(TEXT_WHITE_SOFT.getColor());
        gridTable.setRowHeight(40);
        gridTable.setShowGrid(true);
        gridTable.setGridColor(TEXT_GRAY.getColor());
        gridTable.setTableHeader(null);
        gridTable.setEnabled(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        centerRenderer.setFont(COMBO.getFont());
        
        centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setHorizontalAlignment(JLabel.CENTER);
                setFont(COMBO.getFont());
                
                if (!isSelected) {
                    if (value != null && value.toString().equals("1")) {
                        setBackground(GRID_LIGHT_GRAY.getColor());
                        setForeground(BACKGROUND_DARK_BLUE.getColor());
                    } else if (value != null && value.toString().equals("-1")) {
                        setBackground(GRID_DARK_GRAY.getColor());
                        setForeground(TEXT_WHITE_SOFT.getColor());
                    } else {
                        setBackground(TEXT_WHITE_SOFT.getColor());
                        setForeground(BACKGROUND_DARK_BLUE.getColor());
                    }
                }
                
                return c;
            }
        };
        
        gridTable.setDefaultRenderer(Object.class, centerRenderer);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        buttonPanel.add(createBackButton());
        buttonPanel.add(createRouteButton(PRUNING_PATH));
        buttonPanel.add(createRouteButton(NO_PRUNING_PATH));

        return buttonPanel;
    }

    private Component createBackButton() {
        JButton backButton = new JButton(BACK);
        setupButtonProperties(backButton);
        addBackButtonAction(backButton);
        return backButton;
    }

    private void addBackButtonAction(JButton button) {
        button.addActionListener(e -> {
            if (parentMainView != null) {
                parentMainView.setVisible(true);
            }
            this.dispose();
        });
    }

    private JButton createRouteButton(String text) {
        JButton button = new JButton(text);
        setupButtonProperties(button);
        addButtonHoverEffect(button);
        addButtonAction(button, text);
        
        return button;
    }

    private void setupButtonProperties(JButton button) {
        button.setFont(BUTTON.getFont());
        button.setPreferredSize(new Dimension(250, 40));
        button.setBackground(BUTTON_BLUE.getColor());
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(CARD_COLOR.getColor(), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
    }

    private void addButtonHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(BUTTON_STATIONS_PURPLE.getColor());
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(BUTTON_BLUE.getColor());
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                button.setBackground(BACKGROUND_DARK_BLUE.getColor());
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                button.setBackground(BUTTON_STATIONS_PURPLE.getColor());
            }
        });
    }

    private void addButtonAction(JButton button, String buttonText) {
        button.addActionListener(y -> {
            try {
                if (buttonText.equals(PRUNING_PATH)) {
                    List<Point> path = robotController.getBestRoutesWithPruning(0);
                    openReportView(path, PRUNING_ALGORITM);
                } else {
                    List<Point> path = robotController.getBestRouteWithoutPruning(0);
                    openReportView(path, NO_PRUNING_ALGORITM);
                }
            } catch (IndexOutOfBoundsException e){
                showMessage(NO_WAY);
            } catch (IllegalArgumentException e) {
                showMessage(ODD_BOARD);
            }
        });
    }

    private void showMessage(String message) {
        if (message != null && !message.trim().isEmpty()) {
            displayMessage(message);
            setupMessageTimer();
        } else {
            hideMessage();
        }
        
        refreshInterface();
    }

    private void displayMessage(String message) {
        messageLabel.setText(message);
        messageLabel.setVisible(true);
    }

    private void hideMessage() {
        messageLabel.setVisible(false);
        messageLabel.setText("");
    }

    private void setupMessageTimer() {
        Timer timer = new Timer(5000, e -> hideMessage());
        timer.setRepeats(false);
        timer.start();
    }

    private void refreshInterface() {
        revalidate();
        repaint();
    }

    private void openReportView(List<Point> path, String algorithmType) {
        ReportView reportView = new ReportView(robotController, this);
        reportView.setPathAndGrid(path, algorithmType);
        reportView.setVisible(true);
        this.setVisible(false);
    }

    public void setSelectedBoard(String selectedBoard) {
        selectedBoardLabel.setText(BOARD_SELECTED + selectedBoard);
        robotController.addGridObserver(this);
        robotController.setRobotGrid(selectedBoard);
    }

    @Override
    public void update(String eventType) {
        if (eventType == GRID_UPDATED)
            updateGridTable();

    }

    private void updateGridTable() {
        int[][] grid = robotController.getRobotGrid();
        
        if (grid == null || grid.length == 0) {
            return;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        tableModel.setRowCount(0);
        tableModel.setColumnCount(cols);

        for (int i = 0; i < rows; i++) {
            Object[] rowData = new Object[cols];
            for (int j = 0; j < cols; j++) {
                rowData[j] = grid[i][j];
            }
            tableModel.addRow(rowData);
        }

        for (int i = 0; i < cols; i++) {
            gridTable.getColumnModel().getColumn(i).setPreferredWidth(50);
        }

        gridTable.revalidate();
        gridTable.repaint();
    }
}

