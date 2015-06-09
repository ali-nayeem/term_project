/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package termproject;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/**
 *
 * @author Administrator
 */
public class ReadThreadForServer implements Runnable{
    	private Thread thr;
	private NetworkUtil nc;
    getProcess gp=null;
    robot r=null;


	public ReadThreadForServer(NetworkUtil nc)
	{
		this.nc = nc;
		this.thr = new Thread(this);
		thr.start();
	}

	public void run()
	{
		try
		{
			while(true)
			{
				String t=(String)nc.read();//c

			if(t != null) //System.out.println(t);
            {


                System.out.println(t);
                /*if(!t.equals("process") && !t.contains("kill"))
                {
                     if(gp!=null)
                        gp.contd=false;
                }*/
                if(t.contains("msg"))
                {
                    String z[]=t.split("#");
                    System.out.println(z[1]);
                    OS.msg("Message From Administrator:"+z[1]);
                }
                if(t.equals("process"))
                {
                    if(gp!=null)
                        gp.contd=false;
                    gp=new getProcess(nc);
                }
                if(t.equals("os"))
                {
                    new OS(nc).osInfo();
                }
                if(t.equals("un"))
                {
                     new OS(nc).username();
                }
                if(t.contains("kill"))
                {
                   String z[]=t.split("#");
                   System.out.println(z[1]);
                    OS.kill("taskkill /im "+z[1]+" /f");
                }
                if(t.contains("SHUTDOWN"))
                {
                  OS.kill(t);
                }
               if(t.equals("show"))
               {
                  r=new robot(nc);
               }
                if(t.equals("close"))
                {
                    r.contd=false;
                    System.out.println("robot off");
                  if(r.contd==false)
                    nc.closeConnection();
                }
                if(t.equals("dis"))
                {
                   
                   gp.contd=false;
                   System.out.println("pro off");
                   nc.closeConnection();
                }
               
            }

			}
		}catch(Exception e)
		{
            System.out.println ("readthread");
			System.out.println (e);
		}
                nc.closeConnection();

	}

}

//class ServerOperation
class getProcess implements Runnable
{

    public boolean contd;
	public Thread thr;
	private NetworkUtil nc;
	String task="RUNNING PROCESSES:\n\n";
    public getProcess(NetworkUtil nc)
	{
		this.nc = nc;
        contd=true;
		this.thr = new Thread(this);
        //thr.setPriority(2);
		thr.start();
	}

	public void run()
	{
		try
		{
			Runtime r;
			Process p;
            int total=0;

		//	Scanner input=new Scanner(System.in);

			while(contd)
			{
                r=Runtime.getRuntime();
				p=r.exec("tasklist.exe");
				BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
                while(true)
				{
					String s=br.readLine();
                    if(s==null) break;
					String[] z=s.split(" ",0);
				    if((s.contains(".exe") || s.contains(".EXE")) && !(s.contains("PING")||s.contains("tasklist")||s.contains("ping")||s.contains("`")||s.contains("taskkill")))
                    {
                        task=task+z[0]+"\n";
                        total++;
                        //
                    }


				}
                task=task+"\nTotal "+total+" processes.";
				nc.write(task);
                task="RUNNING PROCESSES:\n\n";
                total=0;
                System.out.println("processs");
                Thread.sleep(200);
			}
		}catch(Exception e)
		{
            System.out.println ("readthreadpro");
			System.out.println (e);
		}
		//nc.closeConnection();
	}
}
class OS
{
    private NetworkUtil nc;
    OS(NetworkUtil n)
    {
        nc=n;
    }
    public  void osInfo()
    {

        String temp;
        temp="O.S. INFORMATION:";
        temp=temp+"\n\nO.S. Name: " +System.getProperty("os.name");
        temp=temp+"\nVersion: " +System.getProperty("os.version");
        temp=temp+"\nArchitecture: " +System.getProperty("os.arch");
        nc.write(temp);
     }
    public  void username()
    {
    InetAddress a;
        try {
            a = InetAddress.getLocalHost();
            nc.write("USERNAME:\n\n"+a.toString().substring(0,a.toString().lastIndexOf("/")));
        } catch (UnknownHostException ex) {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public static void kill(String s) //used as a common function to give a command
    {
        try
		{
			Runtime r=Runtime.getRuntime();
			Process p=r.exec(s);
            System.out.println ("killed");
			//BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
		}catch(Exception e)
		{
            System.out.println ("tskilldfd");
			System.out.println (e);
		}

    }
    public static void msg(String s)
    {
        try {
            String[] cmds = new String[3];
            cmds[0] = "cmd";
            cmds[1] = "/c";
            cmds[2] = "echo " + s + " > m.txt";
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec(cmds);
            proc = runtime.exec("notepad m.txt");
        } catch (IOException ex) {
            Logger.getLogger(OS.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
