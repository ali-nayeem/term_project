/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package termproject;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Administrator
 */
class handler extends WindowAdapter{

    showDesktop s;
    public handler(showDesktop b) {
        s=b;
    }
    @Override
    public void windowClosing(WindowEvent e)
    {
       // s.j.a=new alert(s.j.pc.nc);
       
        s.contd=false;
        s.nc.write("close");
        s.nc.closeConnection();
        //s.g.dispose();


    }


}
