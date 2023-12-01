package view;

import javax.swing.*;
import java.awt.*;

public class LibraryItemPanel extends JPanel {
    private JButton deleteButton;
    private JButton viewButton;
    private JLabel paperName;
    private JPanel MainPanel;
    private final String title;
    private final String id;
    private final String url;
    public LibraryItemPanel(String title, String id, String url){
        this.title =title;
        this.id = id;
        this.url = url;
        setPreferredSize(new Dimension(250,50));
        setVisible(true);
        setLayout(new BorderLayout());
        add(MainPanel);
        paperName.setText(title);
        MainPanel.setLayout(new GridLayout());
        MainPanel.add(paperName);
        MainPanel.add(viewButton);
        MainPanel.add(deleteButton);
    }
}
