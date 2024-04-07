package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.db.SQLRepository;
import main.java.fnfal113.bicol_region_info_management_system.handlers.TextFieldHandler;
import main.java.fnfal113.bicol_region_info_management_system.utils.JTableUtils;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import java.util.List;
import java.util.ArrayList;

public class Dashboard {
    
    private JFrame window;
    private JPanel panel;
    private List<JTable> tables = new ArrayList<JTable>();
    private String[] tableNames = {"Provinces", "Municipalities", "Barangays"};

    public Dashboard(JFrame window) {
        this.window = window;
        this.panel = new JPanel(new BorderLayout());

        initializePanel();
    }

    private void initializePanel() {
        this.panel.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        this.panel.add(createTables(), BorderLayout.CENTER);
        this.panel.add(createWidgets(), BorderLayout.NORTH);
    }

    private JPanel createWidgets() {
        GridLayout layout = new GridLayout(1, 4);

        layout.setHgap(8);

        JPanel panel = new JPanel(layout);

        panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        for (int i = 0; i < this.tables.size(); i++) {
            panel.add(createWidget(tableNames[i], tableNames[i] + " ", this.tables.get(i).getRowCount()));
        }

        return panel;
    }

    public JPanel createWidget(String tableName, String header, Object obj) {
        JPanel widgetPanel = new JPanel();

        widgetPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        JLabel label = new JLabel();

        label.setText("<html>" + header + "<br/>" + obj.toString() + "</html>");
        label.setFont(new Font("", Font.BOLD, 14));
        
        ImageIcon icon = new ImageIcon(
            new ImageIcon(App.class.getResource("./assets/" + tableName + ".png")).getImage().getScaledInstance(36, -1, Image.SCALE_SMOOTH)
        );

        label.setIcon(icon);

        label.setIconTextGap(8);

        label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        widgetPanel.setBackground(Color.decode("#64BAAA"));
    
        widgetPanel.add(label);
        
        return widgetPanel;
    }

    private JPanel createTables() {
        JPanel tablesPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < this.tableNames.length; i++) {
            JTable table = JTableUtils.createTableFromTableName(tableNames[i], new SQLRepository());

            table.getTableHeader().setFont(new Font("", Font.BOLD, 12));

            final int index = i;

            table.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    table.putClientProperty("currentRowId", table.getValueAt(table.getSelectedRow(), 0));
                }

                @Override
                public void focusLost(FocusEvent e) {} 
            });

            table.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    SQLRepository repository = new SQLRepository();

                    Object[] tableValuesArr = { 
                        table.getValueAt(e.getFirstRow(), e.getColumn()), // new value
                        table.getClientProperty("currentRowId")
                    };

                    repository.update("UPDATE " + tableNames[index].toLowerCase() + 
                        " SET " + table.getColumnName(e.getColumn()) + " = ? WHERE id = ?;", tableValuesArr);

                    for (int j = 0; j < tables.size(); j++) {
                        if(getTables().get(j).equals(table)) continue;

                        getTables().get(j).setModel(JTableUtils.generateTableModel(tableNames[j], new SQLRepository()));
                    }
                }
            });

            JScrollPane jsp = new JScrollPane(table);

            jsp.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

            // scrollpane table constraints
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            this.tables.add(table);

            tablesPanel.add(jsp, gbc);

            // filter panel constraints
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
         
            tablesPanel.add(createTableFilters(tableNames[i], table), gbc);
        }
        
        return tablesPanel;
    }

    public JPanel createTableFilters(String tableName, JTable table) {
        TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(table.getModel());

        table.setRowSorter(tableRowSorter);

        JPanel filterPanel = new JPanel();
            
        JLabel filterLabel = new JLabel(tableName + " search: ");

        filterLabel.setFont(new Font("", Font.BOLD, 14));
        
        JTextField filterField = new JTextField();

        filterField.getDocument().addDocumentListener(new TextFieldHandler() {
            @Override
            protected void handler() {
                String text = filterField.getText();

                if (text.length() > 0) {
                    // case insensitive
                    tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                } else {
                    tableRowSorter.setRowFilter(null);
                }
            }
        });

        filterField.setPreferredSize(new Dimension(150, 28));

        filterPanel.add(filterLabel);
        filterPanel.add(filterField);

        return filterPanel;
    }

    public JFrame getWindow() {
        return window;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public List<JTable> getTables() {
        return this.tables;
    }

}