
import javax.swing.*;
import controller.OS;


public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            try {
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
            new OS();
        });
    }
}
