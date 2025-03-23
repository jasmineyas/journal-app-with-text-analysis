package ui.smallComponents;

import java.awt.*;

import javax.swing.JTextField;

public class ComicSansTextField extends JTextField {

    public ComicSansTextField(int columns, int fontSize, int width, int height) {
        super(columns);
        setFont(new Font("Comic Sans Ms", Font.PLAIN, fontSize));
        setForeground(new Color(68, 101, 233));
        setPreferredSize(new Dimension(width, height));
    }

}
