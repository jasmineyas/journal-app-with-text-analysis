package ui.smallComponents;

import java.awt.Font;

import javax.swing.JLabel;

public class ComicSansLabel extends JLabel {

    public ComicSansLabel(String text, int fontStyle, int fontSize) {
        super(text);
        setFont(new Font("Comic Sans Ms", fontStyle, fontSize));
    }

}
