package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class MainWindow {

    private final JFrame window;
    
    private final SideBar sideBar;
    
    private final Dashboard dashboard;
    
    private final ManageData manageData;

    private final About about;
    
    private JScrollPane mainViewScrollPane = new JScrollPane();

    public MainWindow() {
        this.window = new JFrame();
        this.sideBar = new SideBar(getWindow());
        this.dashboard = new Dashboard(getWindow());
        this.manageData = new ManageData(getWindow());
        this.about = new About(getWindow());

        init();
    }

    public void init() {
        this.window.setTitle("Bicol Region Info Management System");
        
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.window.setSize(1000, 720);
        
        this.window.setLocationRelativeTo(null);
        
        this.window.add(getSideBar().getPanel(), BorderLayout.WEST);   
        
        this.mainViewScrollPane = new JScrollPane(getDashboard().getPanel());

        this.mainViewScrollPane.getVerticalScrollBar().setUnitIncrement(16);

        this.window.add(getMainViewScrollPane(), BorderLayout.CENTER);
    }

    public void show() {
        this.window.setVisible(true);
    }

    public JFrame getWindow() {
        return this.window;
    }

    public JScrollPane getMainViewScrollPane() {
        return this.mainViewScrollPane;
    }

    public SideBar getSideBar() {
        return this.sideBar;
    }

    public Dashboard getDashboard() {
        return this.dashboard;
    }

    public ManageData getManageData() {
        return this.manageData;
    }

    public About getAbout() {
        return this.about;
    }
}
