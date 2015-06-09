package termproject;

import java.awt.Robot;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class showDesktop extends javax.swing.JFrame implements Runnable{
	Robot r = null;
	Thread t = null;
	Graphics2D g =null;
	BufferedImage img = null;
    NetworkUtil nc;
    boolean contd;
    JFrame j;

	final static int Height = 600;
	final static int Width =900;

	public showDesktop(NetworkUtil n,JFrame j){
        super();
        this.j=j;
        nc=n;
        contd=true;
		try{

			t = new Thread(this);

		}catch(Exception e){
			e.printStackTrace();
		}
		this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Remote Desktop");
		this.setSize(showDesktop.Width+6,showDesktop.Height+26);
		g = (Graphics2D)this.getRootPane().getGraphics();
		t.start();
        this.addWindowListener(new handler(this));
	}
	public void run(){
		try{

		while(contd)
		{
           // Thread.sleep(20);
            myDesktop d=(myDesktop)nc.read();
            if(d!=null)
            {
                InputStream in = new ByteArrayInputStream(d.a);
                img = javax.imageio.ImageIO.read(in);
                g = (Graphics2D)this.getRootPane().getGraphics();//links the frame with Graphic
                g.drawImage(img, null, 0, 0);
                Thread.sleep(50);
            }
			//this.seti

			this.setDefaultCloseOperation(showDesktop.HIDE_ON_CLOSE);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}


}