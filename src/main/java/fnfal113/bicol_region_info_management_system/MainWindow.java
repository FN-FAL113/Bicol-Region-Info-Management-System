package main.java.fnfal113.bicolregioninfomanagementsystem;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainWindow {

    private final JFrame window;
    private final SideBar sideBar;
    private final Dashboard dashboard;

    public MainWindow() {
        this.window = new JFrame();
        this.sideBar = new SideBar(getWindow());
        this.dashboard = new Dashboard(getWindow());
        
        init();
    }

    public void init() {
        this.window.setTitle("Bicol Region Info Management System");
        
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.window.setSize(800, 600);
        
        this.window.setLocationRelativeTo(null);
        
        getWindow().add(getSideBar().getPanel(), BorderLayout.WEST);       
        
        getWindow().add(getDashboard().getPanel(), BorderLayout.CENTER);       
    }

    public void show() {
        // this.window.pack(); // this will auto adjust contents to fit
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
