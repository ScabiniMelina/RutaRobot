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

public class ReportView extends BaseView implements IObserver {
    private final RobotController robotController;
    private JTable gridTable;
    private DefaultTableModel tableModel;
    private JLabel titleLabel;
    private List<Point> highlightedPath;
    private BaseView parentBoardView;
    private String currentAlgorithmType;
    private JLabel exploredPathsLabel;
    private JLabel recursiveCallsLabel;
    private JLabel executionTimeLabel;

    public ReportView(RobotController robotController, BaseView parentBoardView) {
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
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        titleLabel = new JLabel(ALGORITHM_RESULT);
        titleLabel.setFont(LABEL.getFont());
        titleLabel.setForeground(TEXT_WHITE_SOFT.getColor());

        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        contentPanel.add(createMetricsPanel(), BorderLayout.EAST);

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

    private JPanel createMetricsPanel() {
        JPanel metricsPanel = new JPanel(new BorderLayout());
        metricsPanel.setBackground(CARD_COLOR.getColor());
        metricsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_GRAY.getColor(), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        metricsPanel.setPreferredSize(new Dimension(300, 0));

        metricsPanel.add(createMetricsHeader(), BorderLayout.NORTH);
        metricsPanel.add(createMetricsContent(), BorderLayout.CENTER);

        return metricsPanel;
    }

    private JLabel createMetricsHeader() {
        JLabel headerLabel = new JLabel(METRICS_TITLE);
        headerLabel.setFont(LABEL.getFont());
        headerLabel.setForeground(TEXT_WHITE_SOFT.getColor());
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        return headerLabel;
    }

    private JPanel createMetricsContent() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(CARD_COLOR.getColor());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        gbc.gridy = 0;
        contentPanel.add(createMetricLabel(EXPLORED_PATHS_METRIC), gbc);
        gbc.gridy = 1;
        exploredPathsLabel = createMetricValueLabel();
        contentPanel.add(exploredPathsLabel, gbc);

        gbc.gridy = 2;
        contentPanel.add(createMetricLabel(RECURSIVE_CALLS_METRIC), gbc);
        gbc.gridy = 3;
        recursiveCallsLabel = createMetricValueLabel();
        contentPanel.add(recursiveCallsLabel, gbc);

        gbc.gridy = 4;
        contentPanel.add(createMetricLabel(EXECUTION_TIME_METRIC), gbc);
        gbc.gridy = 5;
        executionTimeLabel = createMetricValueLabel();
        contentPanel.add(executionTimeLabel, gbc);

        return contentPanel;
    }

    private JLabel createMetricLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(COMBO.getFont());
        label.setForeground(TEXT_WHITE_SOFT.getColor());
        return label;
    }

    private JLabel createMetricValueLabel() {
        JLabel label = new JLabel("0");
        label.setFont(BUTTON.getFont());
        label.setForeground(BUTTON_BLUE.getColor());
        return label;
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
                
                boolean isInPath = highlightedPath != null &&
                    highlightedPath.stream().anyMatch(point -> point.x == row && point.y == column);
                
                if (!isSelected) {
                    if (isInPath) {
                        setBackground(BUTTON_STATIONS_PURPLE.getColor());
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

        JButton backButton = new JButton(BACK);
        setupBackButtonProperties(backButton);
        addButtonHoverEffect(backButton);
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

    private void addBackButtonAction(JButton button) {
        button.addActionListener(e -> {
            // Volver al BoardView
            if (parentBoardView != null) {
                parentBoardView.setVisible(true);
            }
            this.dispose();
        });
    }

    public void setPathAndGrid(List<Point> path, String algorithmType) {
        this.highlightedPath = path;
        this.currentAlgorithmType = algorithmType;
        titleLabel.setText(RESULT + algorithmType);
        robotController.addGridObserver(this);
        updateGridTable();
        updateMetrics();
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

    private void updateMetrics() {
        if (currentAlgorithmType != null) {
            if (currentAlgorithmType.equals(PRUNING_ALGORITM)) {
                updatePruningMetrics();
            } else if (currentAlgorithmType.equals(NO_PRUNING_ALGORITM)) {
                updateNonPruningMetrics();
            }
        }
    }

    private void updatePruningMetrics() {
        int exploredPaths = robotController.getPruningExploredPaths();
        int recursiveCalls = robotController.getPruningRecursiveCalls();
        double executionTime = robotController.getPruningExecutionTimeMs();

        updateMetricLabels(exploredPaths, recursiveCalls, executionTime);
    }

    private void updateNonPruningMetrics() {
        int exploredPaths = robotController.getNonPruningExploredPaths();
        int recursiveCalls = robotController.getNonPruningRecursiveCalls();
        double executionTime = robotController.getNonPruningExecutionTimeMs();

        updateMetricLabels(exploredPaths, recursiveCalls, executionTime);
    }

    private void updateMetricLabels(int exploredPaths, int recursiveCalls, double executionTime) {
        exploredPathsLabel.setText(String.valueOf(exploredPaths));
        recursiveCallsLabel.setText(String.valueOf(recursiveCalls));
        executionTimeLabel.setText(String.format("%.2f", executionTime));
        
        repaint();
    }
}
