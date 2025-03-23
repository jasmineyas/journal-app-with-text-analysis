package ui.components;

import java.awt.Font;

import javax.swing.JLabel;

/** 
 *  ComicSansLabel class is a JLabel that has CUTE Comic Sans MS font.
 */
public class ComicSansLabel extends JLabel {

    public ComicSansLabel(String text, int fontStyle, int fontSize) {
        super(text);
        setFont(new Font("Comic Sans Ms", fontStyle, fontSize));
    }

}
