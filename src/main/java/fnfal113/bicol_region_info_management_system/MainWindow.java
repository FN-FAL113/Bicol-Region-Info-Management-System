package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainWindow {

    private JFrame window;
    private SideBar sideBar;
    private Dashboard dashboard;

    public MainWindow() {
        init();
    }

    public void init() {
        this.window = new JFrame();

        this.window.setTitle("Bicol Region Info Management System");
        
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.window.setSize(800, 600);
        
        this.window.setLocationRelativeTo(null);

        this.sideBar = new SideBar(getWindow());
        
        this.dashboard = new Dashboard(getWindow());
        
        getWindow().add(getSideBar().getPanel(), BorderLayout.WEST);   
        
        JScrollPane scrollableDashboard = new JScrollPane(getDashboard().getPanel());

        scrollableDashboard.getVerticalScrollBar().setUnitIncrement(16);
        
        getWindow().add(scrollableDashboard, BorderLayout.CENTER);       
    }

    public void show() {
        this.window.setVisible(true);
    }

    public JFrame getWindow() {
        return this.window;
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }
}
