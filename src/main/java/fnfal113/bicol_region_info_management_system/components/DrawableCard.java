package main.java.fnfal113.bicol_region_info_management_system.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

import main.java.fnfal113.bicol_region_info_management_system.App;

/**
 * an extended JPanel since component graphic cannot be accessed onced its rendered
 */
public class DrawableCard extends JPanel {
    
    private String text;
    
    private String iconFileName;
    
    private String backgroundColor;

    private int width;

    private int height;

    private int imageOffsetX;

    private int imageOffsetY;

    public DrawableCard(String iconFileName, String backgroundColor, int width, int height, int imageOffsetX, int imageOffsetY) {
        this.setLayout(new BorderLayout());
        this.iconFileName = iconFileName;
        this.backgroundColor = backgroundColor;
        this.width = width;
        this.height = height;
        this.imageOffsetX = imageOffsetX;
        this.imageOffsetY = imageOffsetY;

        init();
    }
    
    public DrawableCard(String iconFileName, String backgroundColor, int width, int height) {
        this.setLayout(new BorderLayout());
        this.iconFileName = iconFileName;
        this.backgroundColor = backgroundColor;
        this.width = width;
        this.height = height;
        this.imageOffsetX = 0;
        this.imageOffsetY = 0;

        init();
    }

    public DrawableCard(String iconFileName, String backgroundColor) {
        this.setLayout(new BorderLayout());
        this.iconFileName = iconFileName;
        this.backgroundColor = backgroundColor;
        this.width = 0;
        this.height = 0;
        this.imageOffsetX = 0;
        this.imageOffsetY = 0;

        init();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        try {
            Image img;

            if(this.width > 0 || this.height > 0) {
                img = ImageIO.read(App.class.getResourceAsStream("assets" + File.separator + this.iconFileName + ".png")).getScaledInstance(this.width, this.height, Image.SCALE_SMOOTH);
            } else {
                img = ImageIO.read(App.class.getResourceAsStream("assets" + File.separator + this.iconFileName + ".png"));
            }
           
            g.drawImage(img, this.getSize().width + imageOffsetX, this.imageOffsetY, this);
        } catch (IOException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(this, e.getMessage(), "Info", 1);
        }
    }

    // make this extendable if needed
    public void init() {
        this.setBackground(Color.decode(backgroundColor));

        this.setPreferredSize(new Dimension(this.width, this.height));

        this.putClientProperty(FlatClientProperties.STYLE, "arc: 10");
    }

    public void addLabel(String text, String borderLayoutConstraint) {
        JLabel label = new JLabel(text);

        label.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 0));

        label.setFont(new Font("Inter Bold", Font.BOLD, 16));

        label.setForeground(Color.WHITE);

        label.setVerticalAlignment(JLabel.CENTER);

        label.setIconTextGap(8);
        
        this.add(label, borderLayoutConstraint);
    }

}
