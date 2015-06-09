/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package termproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Observable;

/**
 *
 * @author Administrator
 */
public class AliveCheck extends Observable implements Runnable
{
   //boolean cond;
    Thread t;
    int i;
    String ip1;
   String s;
   MyTableModel m;
     AliveCheck(int i,String ip,MyTableModel m)
    {
        this.i = i;
        this.m=m;
        ip1=ip;
        t =new Thread(this);
        t.start();
        this.addObserver(m);
    }
     public void run()
     {

         try
         {
           while(m.c)
             {

                if(DoPing(ip1))
                    s="ALIVE";
                else
                    s="dead";
                setChanged();
                notifyObservers(new String(s+"#"+i));
                Thread.sleep(5000);
            }

         }
         catch(Exception e)
         {
             System.out.println("in alive"+e);
         }
         System.out.println("end");
      }
     public boolean DoPing(String ip)
     {
        Runtime rt = Runtime.getRuntime();
        Process pt = null;
        String s = "";
        try{
            pt = rt.exec("ping -n 1 -w 2000 "+ip);
           	BufferedReader br=new BufferedReader(new InputStreamReader(pt.getInputStream()));
            while(true)
            {
            	 s=br.readLine();
                 if(s==null) break;
                 if(s.contains("bytes=32"))  //bcoz if a ip is ON then the messege shown in cmd promt start with reply from
                        return true;
            }
            return false;
            }
        catch(Exception e)
        {
            System.out.println("in alivec"+e+"\n"+ip);
            return false;
        }
     }

}
 



