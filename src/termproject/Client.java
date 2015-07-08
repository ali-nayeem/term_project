/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class Client
{

    NetworkUtil nc;

    Client(String serverAddress)
    {

        try
        {

            nc = new NetworkUtil(serverAddress, 33333);
			//new ReadThread(nc);
            //new WriteThread(nc,"Client");
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }

}

class showProcess implements Runnable
{

    public boolean contd;
    JTextArea jt1;
    Thread t;
    private NetworkUtil nc;
    String d = "";
    String de = null;
    alert a;
    ArrayList<String> al;

    showProcess(JTextArea j, NetworkUtil n, alert a)
    {
        this.a = a;
        jt1 = j;
        nc = n;
        contd = true;
        t = new Thread(this);
        t.setPriority(10);
        t.start();

    }

    public void run()
    {
        try
        {
            while (contd)
            {
                String r;
                if (a.contd)
                {
                    r = a.s;
                } else
                {
                    r = (String) nc.read();

                    FileReader fin = null;
                    fin = new FileReader("Exe.dat");
                    BufferedReader br = new BufferedReader(fin);
                    String line;
                    al = new ArrayList<>();
                    while ((line = br.readLine()) != null)
                    {
                        al.add(line);
                    }
                    if (de != null)
                    {
                        al.remove(de);
                    }
                    fin.close();

                 // s=(String)nc.read();
                    if (r != null)
                    {
                        if (de != null && !r.contains(de))
                        {
                            de = null;
                        }
                        for (int i = 0; i < al.size(); i++)
                        {
                            if (r.contains(al.get(i)))
                            {
                                // s=(String)nc.read();
                                int response = JOptionPane.showConfirmDialog(null, al.get(i) + " is running on " + nc.IP + "\nDo you want to kill it?", "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                                if (response == JOptionPane.YES_OPTION)
                                {
                                    nc.write("kill#" + al.get(i));
                                }
                                de = al.get(i);
                            }
                            System.out.println(al.get(i));
                        }
                    }
                    System.out.println("now works");
                    System.out.println(r);

                }
                //String s=(String)nc.read();

                if (r != null)
                {
                    if (r.length() != d.length())
                    {
                        d = r;
                        jt1.setText(d);
                        System.out.println("showpro");
                    }

                    //if(t != null) System.out.println(t);
                }
                Thread.sleep(200);
            }
        } catch (Exception e)
        {
            System.out.println("client");
            System.out.println(e);
        }
        //  nc.closeConnection();

    }
}

class alert implements Runnable
{

    Thread t;
    NetworkUtil nc;
    ArrayList<String> al;
    boolean contd;
    String s;
    String d = null;

    alert(NetworkUtil n)
    {
        t = new Thread(this);
        System.out.println("starting alert");
        nc = n;
        contd = true;
        t.start();

    }

    public void run()
    {
        while (contd)
        {
            try
            {
                FileReader fin = null;
                fin = new FileReader("Exe.dat");
                BufferedReader br = new BufferedReader(fin);
                String line;
                al = new ArrayList<>();
                while ((line = br.readLine()) != null)
                {
                    al.add(line);
                }
                if (d != null)
                {
                    al.remove(d);
                }
                fin.close();

                s = (String) nc.read();

                if (s != null)
                {
                    if (d != null && !s.contains(d))
                    {
                        d = null;
                    }
                    for (int i = 0; i < al.size(); i++)
                    {
                        if (s.contains(al.get(i)))
                        {
                            // s=(String)nc.read();
                            int response = JOptionPane.showConfirmDialog(null, al.get(i) + " is running on " + nc.IP + "\nDo you want to kill it?", "Alert", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                            if (response == JOptionPane.YES_OPTION)
                            {
                                nc.write("kill#" + al.get(i));
                            }
                            d = al.get(i);
                        }
                        System.out.println(al.get(i));
                    }
                }
                Thread.sleep(200);
            } catch (Exception error)
            {
                try
                {
                    System.out.println("retriction" + error);
                    FileOutputStream fout;
                    fout = new FileOutputStream ("Exe.dat",true);
                    fout.close();
                } catch (IOException ex)
                {
                    Logger.getLogger(alert.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        }
    }
}
