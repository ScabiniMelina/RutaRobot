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
    private JTable metricsTable;
    private DefaultTableModel metricsTableModel;
    private JComboBox<String> routeSelector;
    private int currentRouteIndex;

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
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        titleLabel = new JLabel(ALGORITHM_RESULT);
        titleLabel.setFont(LABEL.getFont());
        titleLabel.setForeground(TEXT_WHITE_SOFT.getColor());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        leftPanel.add(titleLabel);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        rightPanel.add(createRouteSelector());

        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }

    private JPanel createRouteSelector() {
        JPanel selectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        selectorPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());

        JLabel selectorLabel = new JLabel(ROUTE_SELECTOR_LABEL);
        selectorLabel.setFont(COMBO.getFont());
        selectorLabel.setForeground(TEXT_WHITE_SOFT.getColor());

        routeSelector = new JComboBox<>();
        setupRouteSelectorProperties();
        addRouteSelectorAction();

        selectorPanel.add(selectorLabel);
        selectorPanel.add(routeSelector);

        return selectorPanel;
    }

    private void setupRouteSelectorProperties() {
        routeSelector.setFont(COMBO.getFont());
        routeSelector.setBackground(TEXT_WHITE_SOFT.getColor());
        routeSelector.setForeground(BACKGROUND_DARK_BLUE.getColor());
        routeSelector.setPreferredSize(new Dimension(120, 30));
        routeSelector.setFocusable(false);
    }

    private void addRouteSelectorAction() {
        routeSelector.addActionListener(e -> {
            int selectedIndex = routeSelector.getSelectedIndex();
            if (selectedIndex >= 0) {
                currentRouteIndex = selectedIndex;
                updateSelectedRoute();
            }
        });
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());

        contentPanel.add(createTablePanel(), BorderLayout.CENTER);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BACKGROUND_DARK_BLUE.getColor());
        rightPanel.add(createMetricsPanel(), BorderLayout.NORTH);
        
        contentPanel.add(rightPanel, BorderLayout.EAST);

        return contentPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(CARD_COLOR.getColor());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_GRAY.getColor(), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        tableModel = new DefaultTableModel();
        gridTable = new JTable(tableModel);
        
        setupTableAppearance();

        JScrollPane scrollPane = new JScrollPane(gridTable);
        scrollPane.setBackground(CARD_COLOR.getColor());
        scrollPane.getViewport().setBackground(CARD_COLOR.getColor());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(350, 280));

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
        metricsPanel.setPreferredSize(new Dimension(400, 300));

        metricsPanel.add(createMetricsHeader(), BorderLayout.NORTH);
        metricsPanel.add(createMetricsContent(), BorderLayout.CENTER);

        return metricsPanel;
    }

    private JLabel createMetricsHeader() {
        JLabel headerLabel = new JLabel(METRICS_TITLE);
        headerLabel.setFont(BOLD_LABEL.getFont());
        headerLabel.setForeground(TEXT_WHITE_SOFT.getColor());
        headerLabel.setHorizontalAlignment(JLabel.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        return headerLabel;
    }

    private JPanel createMetricsContent() {
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR.getColor());
        
        createMetricsTable();
        setupMetricsTableAppearance();
        
        JScrollPane scrollPane = new JScrollPane(metricsTable);
        scrollPane.setBackground(CARD_COLOR.getColor());
        scrollPane.getViewport().setBackground(CARD_COLOR.getColor());
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(360, 160));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        return contentPanel;
    }

    private void createMetricsTable() {
        String[] columnNames = {"Métrica", "Valor"};
        String[][] data = {
            {EXPLORED_PATHS_METRIC, "0"},
            {VALID_ROUTES_METRIC, "0"},
            {RECURSIVE_CALLS_METRIC, "0"},
            {EXECUTION_TIME_METRIC, "0.00"},
            {GRID_SIZE_METRIC, "0x0"}
        };
        
        metricsTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };
        
        metricsTable = new JTable(metricsTableModel);
    }

    private void setupMetricsTableAppearance() {
        metricsTable.setFont(COMBO.getFont());
        metricsTable.setBackground(TEXT_WHITE_SOFT.getColor());
        metricsTable.setForeground(BACKGROUND_DARK_BLUE.getColor());
        metricsTable.setSelectionBackground(BUTTON_BLUE.getColor());
        metricsTable.setSelectionForeground(TEXT_WHITE_SOFT.getColor());
        metricsTable.setRowHeight(25);
        metricsTable.setShowGrid(true);
        metricsTable.setGridColor(TEXT_GRAY.getColor());
        metricsTable.setEnabled(false);
        
        // Configurar el header
        metricsTable.getTableHeader().setFont(BOLD_LABEL.getFont());
        metricsTable.getTableHeader().setBackground(BUTTON_BLUE.getColor());
        metricsTable.getTableHeader().setForeground(TEXT_WHITE_SOFT.getColor());
        
        // Configurar el ancho de las columnas
        metricsTable.getColumnModel().getColumn(0).setPreferredWidth(260);
        metricsTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        
        // Renderer personalizado para la columna de valores
        DefaultTableCellRenderer valueRenderer = new DefaultTableCellRenderer();
        valueRenderer.setHorizontalAlignment(JLabel.CENTER);
        valueRenderer.setFont(BUTTON.getFont());
        valueRenderer.setForeground(BUTTON_BLUE.getColor());
        metricsTable.getColumnModel().getColumn(1).setCellRenderer(valueRenderer);
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
        this.currentRouteIndex = 0;
        titleLabel.setText(RESULT + algorithmType);
        robotController.addGridObserver(this);
        setupRouteSelector();
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
        int validRoutes = getRouteCount();
        String gridSize = getGridSize();
        
        // Actualizar valores en la tabla
        metricsTableModel.setValueAt(String.valueOf(exploredPaths), 0, 1);
        metricsTableModel.setValueAt(String.valueOf(validRoutes), 1, 1);
        metricsTableModel.setValueAt(String.valueOf(recursiveCalls), 2, 1);
        metricsTableModel.setValueAt(String.format("%.2f", executionTime), 3, 1);
        metricsTableModel.setValueAt(gridSize, 4, 1);
        
        repaint();
    }

    private String getGridSize() {
        int[][] grid = robotController.getRobotGrid();
        if (grid != null && grid.length > 0) {
            int rows = grid.length;
            int cols = grid[0].length;
            return rows + "x" + cols;
        } else {
            return "0x0";
        }
    }

    private void setupRouteSelector() {
        routeSelector.removeAllItems();
        
        int routeCount = getRouteCount();
        
        for (int i = 0; i < routeCount; i++) {
            routeSelector.addItem("Ruta " + (i + 1));
        }
        
        if (routeCount > 0) {
            routeSelector.setSelectedIndex(0);
            routeSelector.setEnabled(routeCount > 1);
        }
    }

    private int getRouteCount() {
        if (currentAlgorithmType != null) {
            if (currentAlgorithmType.equals(PRUNING_ALGORITM)) {
                return robotController.getPruningValidRoutesCount();
            } else if (currentAlgorithmType.equals(NO_PRUNING_ALGORITM)) {
                return robotController.getNonPruningValidRoutesCount();
            }
        }
        return 0;
    }

    private void updateSelectedRoute() {
        try {
            List<Point> newPath;
            if (currentAlgorithmType.equals(PRUNING_ALGORITM)) {
                newPath = robotController.getBestRoutesWithPruning(currentRouteIndex);
            } else {
                newPath = robotController.getBestRouteWithoutPruning(currentRouteIndex);
            }
            
            this.highlightedPath = newPath;
            updateGridTable();
        } catch (IndexOutOfBoundsException e) {
            // Si el índice no es válido, mantener la ruta actual
            System.err.println("Índice de ruta no válido: " + currentRouteIndex);
        }
    }
}

