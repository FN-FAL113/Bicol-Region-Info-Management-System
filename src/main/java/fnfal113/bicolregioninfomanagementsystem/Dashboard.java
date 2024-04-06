package main.java.fnfal113.bicolregioninfomanagementsystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicolregioninfomanagementsystem.db.SQLRepository;
import main.java.fnfal113.bicolregioninfomanagementsystem.utils.JTableUtils;

import javax.swing.BorderFactory;

import java.util.List;
import java.util.ArrayList;

public class Dashboard {
    
    private JFrame window;
    private JPanel panel;
    private List<JTable> tables = new ArrayList<JTable>();
    private String[] tableNames = {"Provinces", "Municipalities", "Barangays"};

    public Dashboard(JFrame window) {
        this.window = window;

        initializePanel();
    }

    private void initializePanel() {
        this.panel = new JPanel(new BorderLayout());

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(createTables(), BorderLayout.CENTER);
        panel.add(createWidgets(), BorderLayout.NORTH);
    }

    private JPanel createWidgets() {
        GridLayout layout = new GridLayout(1, 4);

        layout.setHgap(8);

        JPanel panel = new JPanel(layout);

        panel.setBorder(BorderFactory.createEmptyBorder(4, 0, 4, 0));

        for (int i = 0; i < tables.size(); i++) {
            panel.add(createWidget("# of "+ tableNames[i], this.tables.get(i).getRowCount()));
        }

        return panel;
    }

    public JPanel createWidget(String header, Object obj) {
        JPanel widgetPanel = new JPanel();

        widgetPanel.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

        JLabel label = new JLabel();

        label.setText("<html>" + header + "<br/>" + obj.toString() + "</html>");
        label.setFont(new Font("", Font.BOLD, 14));
        
        widgetPanel.setBackground(Color.gray);
    
        widgetPanel.add(label);
        
        return widgetPanel;
    }

    private JPanel createTables() {
        JPanel tablePanel = new JPanel(new GridLayout(0, 1, 4, 4));

        for (int i = 0; i < this.tableNames.length; i++) {
            JTable table = JTableUtils.createTableFromResultSet(tableNames[i], new SQLRepository());

            final int index = i;
            table.getModel().addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    
                    SQLRepository repository = new SQLRepository();

                    Object[] objArr = { table.getValueAt(table.getSelectedRow(), e.getColumn()), table.getValueAt(table.getSelectedRow(), 0) };

                    repository.update("UPDATE " + tableNames[index].toLowerCase() 
                        + " SET " + table.getColumnName(e.getColumn()) + "= ? WHERE id = ?;", objArr);

                    JOptionPane.showMessageDialog(table, "Successfully updated!", "Info", 1);
                }
            });

            this.tables.add(table);

            JScrollPane jsp = new JScrollPane(table);

            jsp.putClientProperty(FlatClientProperties.STYLE, "arc: 10");

            tablePanel.add(jsp);
        }
        
        return tablePanel;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    public List<JTable> getTables() {
        return this.tables;
    }

}
