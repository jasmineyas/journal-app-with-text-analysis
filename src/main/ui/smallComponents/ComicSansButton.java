package ui.smallComponents;

import java.awt.Font;
import javax.swing.JButton;

public class ComicSansButton extends JButton {

    public ComicSansButton(String text, int fontStyle, int size) {
        super(text);
        super.setFont(new Font("Comic Sans MS", fontStyle, size));
    }

}
