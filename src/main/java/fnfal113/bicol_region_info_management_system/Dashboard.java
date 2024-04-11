package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.db.SQLRepository;
import main.java.fnfal113.bicol_region_info_management_system.handlers.ButtonHandler;
import main.java.fnfal113.bicol_region_info_management_system.handlers.TextFieldHandler;
import main.java.fnfal113.bicol_region_info_management_system.utils.JTableUtils;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

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

        JPanel widgetsPanel = new JPanel(layout);

        widgetsPanel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        for (int i = 0; i < this.tables.size(); i++) {
            widgetsPanel.add(createWidget(tableNames[i], tableNames[i] + " ", this.tables.get(i).getRowCount()));
        }

        return widgetsPanel;
    }

    public JPanel createWidget(String tableName, String header, Object body) {
        JPanel widgetPanel = new JPanel();

        widgetPanel.setBackground(Color.decode("#8596F4"));

        widgetPanel.setPreferredSize(new Dimension(0, 64));

        widgetPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        JLabel label = new JLabel("<html>" + header + "<br/>" + body.toString() + "</html>");
        
        label.setFont(new Font("Inter Bold", Font.BOLD, 14));
        
        ImageIcon icon = new ImageIcon(
            new ImageIcon(App.class.getResource("assets/" + tableName + ".png")).getImage().getScaledInstance(32, -1, Image.SCALE_SMOOTH)
        );

        label.setIcon(icon);

        label.setIconTextGap(6);

        label.setBorder(BorderFactory.createEmptyBorder(8, 16, 8,16));
        
        widgetPanel.add(label);
        
        return widgetPanel;
    }

    private JPanel createTables() {
        JPanel tablesPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < this.tableNames.length; i++) {
            JTable table = JTableUtils.createTableFromTableName(tableNames[i], new SQLRepository());

            String tableName = table.getClientProperty("name").toString().toLowerCase();

            table.getTableHeader().setFont(new Font("Inter Regular", Font.BOLD, 12));

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

                    ArrayList<Object> queryParameters = new ArrayList<>();
                    
                    queryParameters.add(table.getValueAt(e.getFirstRow(), e.getColumn())); // new value
                    queryParameters.add(table.getClientProperty("currentRowId")); // original row id)

                    repository.addOrUpdate("UPDATE " + tableName + 
                        " SET " + table.getColumnName(e.getColumn()) + " = ? WHERE id = ?;", queryParameters);

                    for (int j = 0; j < tables.size(); j++) {
                        if(getTables().get(j).equals(table)) continue;

                        getTables().get(j).setModel(JTableUtils.generateTableModel(tableName, new SQLRepository()));
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
        optionsPanel.add(createTableOptionFilter(table, -1));

        // barangay locator
        if(table.getClientProperty("name") != "Provinces") optionsPanel.add(createTableOptionBarangayLocator(table));

        return optionsPanel;
    }

    public JPanel createTableOptionFilter(JTable table, int columnIndex) {
        JPanel filterPanel = new JPanel();

        TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(table.getModel());

        table.setRowSorter(tableRowSorter);

        JLabel filterLabel = new JLabel(table.getClientProperty("name") + " search: ");

        filterLabel.setFont(new Font("Inter Regular", Font.BOLD, 12));
        
        JTextField filterField = new JTextField();

        filterField.setPreferredSize(new Dimension(150, 30));

        filterField.getDocument().addDocumentListener(new TextFieldHandler() {
            @Override
            protected void handler() {
                String text = filterField.getText();

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
                    JOptionPane.showMessageDialog(locateBtn, "Please select a row", "Info", 0);

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
        return window;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public List<JTable> getTables() {
        return this.tables;
    }

}
