package main.java.fnfal113.bicol_region_info_management_system;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;

public class App {

    private static MainWindow window;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Font customFont = Font.createFont(Font.TRUETYPE_FONT, App.class.getResourceAsStream("assets/Inter-Regular.ttf"));
                    Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, App.class.getResourceAsStream("assets/Inter-Bold.ttf"));
                    
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

                    //register the font
                    ge.registerFont(customFont);
                    ge.registerFont(customFont2);

                    FlatIntelliJLaf.setup();

                    UIManager.put( "Button.arc", 10 );
                    UIManager.put( "ScrollBar.showButtons", true );
                    UIManager.put( "Table.showHorizontalLines", true );
                    UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Inter Regular", Font.PLAIN, 12));

                    window = new MainWindow();

                    window.show();
                } catch (IOException | FontFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MainWindow getWindow() {
        return window;
    }
}
