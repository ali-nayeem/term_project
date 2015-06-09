/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package termproject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Administrator
 */
public class Server
{
	private ServerSocket ServSock;

	Server()
	{
		try
		{
		ServSock = new ServerSocket(33333);
		while (true)
		{
			ServerThread m = new ServerThread(ServSock.accept());
		}
		}catch(Exception e)
		{
			System.out.println("Server starts:"+e);
		}
	}

 class ServerThread implements Runnable
 {
	private Socket ClientSock;
	private Thread thr;
	private NetworkUtil nc;

	ServerThread(Socket client)
	{
		this.ClientSock = client;
		this.thr = new Thread(this);
		thr.start();
        System.out.println("connected");
	}

	public void run()
	{
		this.nc=new NetworkUtil(ClientSock);
		new ReadThreadForServer(this.nc);
		//new Write(this.nc,"Server");

	}
}
}
class restrict implements Runnable
{

	private Thread thr;
	private NetworkUtil nc;
	String task="";

	public restrict(NetworkUtil nc)
	{
		this.nc = nc;

		this.thr = new Thread(this);
		thr.start();
	}

	public void run()
	{
		try
		{
			Runtime r;
			Process p;
            while(true)
			{
                r=Runtime.getRuntime();
				p=r.exec("tasklist.exe");
				BufferedReader br=new BufferedReader(new InputStreamReader(p.getInputStream()));
                while(true)
				{
					String s=br.readLine();
                    if(s==null) break;
					String[] z=s.split(" ",0);
				    if((s.contains(".exe") || s.contains(".EXE"))&& !(s.contains("PING")||s.contains("tasklist")||s.contains("ping")||s.contains("`")))
                    {
                        task=task+z[0]+"#";

                    }
                }

				nc.write(task);
                Thread.sleep(8000);
			}
		}catch(Exception e)
		{
            System.out.println ("restric");
			System.out.println (e);
		}
		//nc.closeConnection();
	}
}

