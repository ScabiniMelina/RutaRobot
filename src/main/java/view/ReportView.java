package view;

import controller.RobotController;
import model.Position;
import observer.IObserver;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import static view.util.ColorPalette.*;
import static view.util.FontPalette.*;
import static view.util.Texts.*;

public class ReportView extends BaseView implements IObserver {
    private final RobotController robotController;
    private JTable gridTable;
    private DefaultTableModel tableModel;
    private JLabel titleLabel;
    private List<Position> highlightedPath;
    private BoardView parentBoardView;

    public ReportView(RobotController robotController, BoardView parentBoardView) {
        super();
        this.robotController = robotController;
        this.parentBoardView = parentBoardView;
        setupReportView();
    }

    private void setupReportView() {
        add(createReportPanel(), BorderLayout.CENTER);
    }

    private JPanel createReportPanel() {
        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));

        reportPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        reportPanel.add(createContentPanel(), BorderLayout.CENTER);
        reportPanel.add(createBackButtonPanel(), BorderLayout.SOUTH);

        return reportPanel;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        titleLabel = new JLabel("Resultado del Algoritmo");
        titleLabel.setFont(LABEL.getFont());
        titleLabel.setForeground(TEXT_WHITE_SOFT.getColor());

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);

        return contentPanel;
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

        DefaultTableCellRenderer pathRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setHorizontalAlignment(JLabel.CENTER);
                setFont(COMBO.getFont());
                
                // Verificar si esta posición está en el camino resaltado
                boolean isInPath = highlightedPath != null && 
                    highlightedPath.stream().anyMatch(pos -> pos.getX() == row && pos.getY() == column);
                
                if (!isSelected) {
                    if (isInPath) {
                        // Resaltar en amarillo las posiciones del camino
                        setBackground(Color.YELLOW);
                        setForeground(BACKGROUND_DARK_BLUE.getColor());
                    } else if (value != null && value.toString().equals("1")) {
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
        
        gridTable.setDefaultRenderer(Object.class, pathRenderer);
    }

    private JPanel createBackButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton backButton = new JButton("Volver");
        setupBackButtonProperties(backButton);
        addBackButtonAction(backButton);

        buttonPanel.add(backButton);
        return buttonPanel;
    }

    private void setupBackButtonProperties(JButton button) {
        button.setFont(BUTTON.getFont());
        button.setPreferredSize(new Dimension(160, 40));
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

    private void addBackButtonAction(JButton button) {
        button.addActionListener(e -> {
            // Volver al BoardView
            if (parentBoardView != null) {
                parentBoardView.setVisible(true);
            }
            this.dispose();
        });
    }

    public void setPathAndGrid(List<Position> path, String algorithmType) {
        this.highlightedPath = path;
        titleLabel.setText("Resultado: " + algorithmType);
        robotController.addGridObserver(this);
        updateGridTable();
    }

    @Override
    public void update(String eventType) {
        if (eventType.equals(GRID_UPDATED)) {
            updateGridTable();
        }
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
