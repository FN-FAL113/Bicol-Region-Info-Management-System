package main.java.fnfal113.bicol_region_info_management_system;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class App {

    private static MainWindow window;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FlatIntelliJLaf.setup();

                UIManager.put( "Button.arc", 10 );
                UIManager.put( "ScrollBar.showButtons", true );
                UIManager.put( "Table.showHorizontalLines", true );
                
                window = new MainWindow();

                window.show();
            }
        });
    }

    public static MainWindow getWindow() {
        return window;
    }
}
