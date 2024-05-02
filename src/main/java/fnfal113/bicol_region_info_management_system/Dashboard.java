package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.components.DrawableCard;
import main.java.fnfal113.bicol_region_info_management_system.db.SQLRepository;
import main.java.fnfal113.bicol_region_info_management_system.handlers.ButtonHandler;
import main.java.fnfal113.bicol_region_info_management_system.handlers.TextFieldHandler;
import main.java.fnfal113.bicol_region_info_management_system.utils.JTableUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.util.Map;
import java.util.HashMap;

public class Dashboard {
    
    private JFrame window;
    
    private JPanel panel;

    private Map<String, JTable> tables = new HashMap<>();

    public final static String[] tableNames = { "Provinces", "Municipalities", "Barangays" };

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
        GridLayout layout = new GridLayout(1, 3);

        layout.setHgap(8);

        layout.setVgap(8);

        JPanel widgetsPanel = new JPanel(layout);

        widgetsPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        for (JTable table : this.tables.values()) {
            String tableName = table.getClientProperty("name").toString();

            DrawableCard infoCard = new DrawableCard(tableName, "#6874E8", 64, 64, -56, 4);

            infoCard.addLabel("<html><p style=\"font-weight: 400;\">" + tableName + "<br/>" + "<p style=\"margin-top: 8px;\">" + table.getRowCount() + "</p>" + "</html>", BorderLayout.CENTER);

            widgetsPanel.add(infoCard);
        }

        return widgetsPanel;
    }

    private JPanel createTables() {
        JPanel tablesPanel = new JPanel(new GridBagLayout());

        tablesPanel.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));

        GridBagConstraints gbc = new GridBagConstraints();

        for (String tableName : tableNames) {
            JTable table = JTableUtils.createTableFromDbTable(tableName, new SQLRepository());

            table.getTableHeader().setFont(new Font("Inter Bold", Font.PLAIN, 12));

            table.addFocusListener(JTableUtils.createFocusIdHandler(table));

            table.getModel().addTableModelListener(JTableUtils.createModelUpdateHandler(table, tableName));

            JScrollPane jsp = new JScrollPane(table);

            jsp.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

            // scrollpane table constraints
            gbc.gridx = 0;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            this.tables.put(tableName, table);

            tablesPanel.add(jsp, gbc);

            // table options panel constraints
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
         
            tablesPanel.add(createTableOptions(table), gbc);
        }
        
        return tablesPanel;
    }

    public JPanel createTableOptions(JTable table) {
        JPanel optionsPanel = new JPanel();
           
        // search field
        optionsPanel.add(createTableFilter(table, -1));

        // barangay locator
        if(table.getClientProperty("name") != "Provinces") optionsPanel.add(createTableOptionBarangayLocator(table));

        return optionsPanel;
    }

    public JPanel createTableFilter(JTable table, int columnIndex) {
        JPanel filterPanel = new JPanel();

        JLabel filterLabel = new JLabel(table.getClientProperty("name") + " Search: ");

        filterLabel.setFont(new Font("Inter Bold", Font.PLAIN, 12));
        
        JTextField filterField = new JTextField();

        filterField.setPreferredSize(new Dimension(150, 30));

        filterField.getDocument().addDocumentListener(new TextFieldHandler() {
            @Override
            protected void handler() {
                String text = filterField.getText();
                
                // unchecked cast, row sorter is set on table creation
                TableRowSorter<TableModel> tableRowSorter = (TableRowSorter<TableModel>) table.getRowSorter();

                if (text.length() > 0) {
                    // case insensitive
                    tableRowSorter.setRowFilter(columnIndex >= 0 ? RowFilter.regexFilter("(?i)" + text, columnIndex) : RowFilter.regexFilter("(?i)" + text));
                } else {
                    tableRowSorter.setRowFilter(null);
                }
            }
        });

        filterPanel.add(filterLabel);

        filterPanel.add(filterField);

        return filterPanel;
    }

    public JButton createTableOptionBarangayLocator(JTable table) {
        JButton locateBtn = new JButton();

        locateBtn.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        locateBtn.setText("Locate Selected Row");

        locateBtn.setFont(new Font("Inter Regular", Font.BOLD, 12));

        locateBtn.setMargin(new Insets(6, 6, 6, 6));

        locateBtn.addMouseListener(new ButtonHandler() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(table.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(locateBtn, "Please select a row first", "Info", 0);

                    return;
                }

                final Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;

                if(desktop != null) {
                    try {
                        String latitude = table.getValueAt(table.getSelectedRow(), 3).toString();
                        String longitude = table.getValueAt(table.getSelectedRow(), 4).toString();

                        desktop.browse(URI.create("https://coordinates-converter.com/en/decimal/" + latitude + "," + longitude + "?karte=OpenStreetMap&zoom=16"));
                    } catch (IOException ex) {
                        ex.printStackTrace();

                        JOptionPane.showMessageDialog(locateBtn, ex.getMessage(), "Info", 0);
                    }
                } else {
                    JOptionPane.showMessageDialog(locateBtn, "No browsers detected! Please install a web browser to use this functionality", "Info", 0);
                }
            }
        });

        return locateBtn;
    }

    public JFrame getWindow() {
        return this.window;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public Map<String, JTable> getTables() {
        return this.tables;
    }

}
