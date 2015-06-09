package termproject;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

class robot implements Runnable
{

    Robot r = null;
    Thread t = null;
    NetworkUtil nc;
    boolean contd;
    //Graphics2D g =null;
    public BufferedImage img = null;

    final static int Height = 600;
    final static int Width = 900;

    public robot(NetworkUtil n)
    {

        try
        {
            nc = n;
            r = new Robot();
            t = new Thread(this);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        contd = true;

        t.start();
    }

    public void run()
    {
        try
        {

            while (contd)
            {

                Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                img = r.createScreenCapture(new Rectangle((int) d.getWidth(), (int) d.getHeight()));
                img = resize(img, robot.Width, robot.Height);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
			//JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
                //encoder.encode(img);
                ImageIO.write(img, "jpeg", os);
                byte a[] = os.toByteArray();
                myDesktop md = new myDesktop(a);
                nc.write(md);
                System.out.println("robottttttttt");
                Thread.sleep(500);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public BufferedImage resize(BufferedImage img, int newW, int newH)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
}
