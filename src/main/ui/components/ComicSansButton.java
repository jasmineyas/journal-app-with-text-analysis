package ui.components;

import java.awt.Font;
import javax.swing.JButton;

/**
 * ComicSansButton class is a JButton that has CUTE Comic Sans MS font.
 */
public class ComicSansButton extends JButton {

    public ComicSansButton(String text, int fontStyle, int size) {
        super(text);
        super.setFont(new Font("Comic Sans MS", fontStyle, size));
    }

}
