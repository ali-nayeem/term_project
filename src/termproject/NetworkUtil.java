/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package termproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class NetworkUtil
{

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    boolean isconnected = true;
    String IP;

    public NetworkUtil(String s, int port)
    {
        try
        {
            IP = s;
            this.socket = new Socket(s, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (ConnectException e)
        {
            isconnected = false;
            JOptionPane.showMessageDialog(null, "Failed to establish connection.\n         IP out of control.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e)
        {
            isconnected = false;
            System.out.println("In NetworkUtil : " + e.toString());
            JOptionPane.showMessageDialog(null, "Failed to establish connection.\n        IP out of control.", "Error", JOptionPane.ERROR_MESSAGE);
            //System.exit(0);
        }
    }

    public NetworkUtil(Socket s)
    {
        try
        {
            this.socket = s;
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (Exception e)
        {
            System.out.println("In NetworkUtil : " + e.toString());
            //System.exit(0);

        }
    }

    public Object read()
    {
        Object o = null;
        try
        {
            o = ois.readObject();

        } catch (Exception e)
        {
            //System.out.println("Reading Error in network : " + e.toString());

        }
        return o;
    }

    public void write(Object o)
    {
        try
        {
            oos.writeObject(o);
        } catch (IOException e)
        {
            System.out.println("Writing  Error in network : " + e.toString());

        }
    }

    public void closeConnection()
    {
        try
        {

            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e)
        {
            System.out.println("Closing Error in network : " + e.toString());

        }
    }

    public Socket getSocket()
    {
        return this.socket;
    }
}
