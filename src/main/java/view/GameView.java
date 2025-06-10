package view;

import controller.RobotController;
import observer.IObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static view.util.ColorPalette.*;
import static view.util.FontPalette.*;
import static view.util.Texts.NO_BOARD_SELECTED;

public class GameView extends BaseView implements IObserver {
    private final RobotController robotController;
    private JTable gridTable;
    private DefaultTableModel tableModel;
    private JLabel selectedBoardLabel;

    public GameView(RobotController robotController) {
        super();
        this.robotController = robotController;
        setupGameView();
    }

    private void setupGameView() {
        add(createGamePanel(), BorderLayout.CENTER);
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        gamePanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        gamePanel.add(createHeaderPanel(), BorderLayout.NORTH);
        gamePanel.add(createTablePanel(), BorderLayout.CENTER);

        return gamePanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        selectedBoardLabel = new JLabel(NO_BOARD_SELECTED);
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
        return tablePanel;
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

    public void setSelectedBoard(String selectedBoard) {
        selectedBoardLabel.setText("Tablero seleccionado: " + selectedBoard);
        robotController.addGridObserver(this);
        robotController.setRobotGrid(selectedBoard);
    }

    @Override
    public void update(String eventType) {
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
