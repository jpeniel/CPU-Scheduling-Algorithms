
package view;

import java.awt.*;
import javax.swing.JTextField;

public class HintTextField extends JTextField{
    private String hint;
    public HintTextField(String hint) {
        this(hint, 10);
    }
    public HintTextField(String hint, int size) {
        super(size);
        this.hint = hint;
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(getText().isEmpty()) {
            int padding = (getHeight() - getFont().getSize())/2;
            g.setColor(Color.GRAY);
            g.drawString(hint, padding, getHeight() - padding - 1);
        }
    }
}
