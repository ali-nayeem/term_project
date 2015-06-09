/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package termproject;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Administrator
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic herez
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (ClassNotFoundException e) {
            }
            catch (InstantiationException e) {
            }
            catch (IllegalAccessException e) {
            }
            catch (UnsupportedLookAndFeelException e) {
            }
                new JFrame().setVisible(true);
            }
        });
    }
}
