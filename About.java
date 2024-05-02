import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class About extends JFrame {
    public About()
    {
        super("About");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new VerticalLayout());
        ImageIcon icon = new ImageIcon("picture/applications-graphics.png");
        add(new JLabel(icon));
        JLabel programName = new JLabel("Pixelize 1.0");
        programName.setFont(new Font("Monospaced", Font.BOLD, 18));
        add(programName);
        
        add(new JLabel("Copyright (c) 2011 Rafael Rendon Pablo"));
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
