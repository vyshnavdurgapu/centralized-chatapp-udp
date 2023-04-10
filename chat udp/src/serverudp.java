
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class server {
	DatagramSocket ds;
	server()
	{
		try {
			ds=new DatagramSocket(9997);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	void sendtext(String s)
	{
		byte arr[]=new byte[1024];
		arr=s.getBytes();
		InetAddress inet;
		try {
			inet = InetAddress.getByName("localhost");
			DatagramPacket dp1=new DatagramPacket(arr,arr.length,inet,8888);
			ds.send(dp1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	String revievetext()
	{
		byte[] in_data=new byte[1024];
		DatagramPacket dp2=new DatagramPacket(in_data,in_data.length);
		try {
			ds.receive(dp2);
			String s=new String(dp2.getData());
			return s;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";		
	}
}

/* s is client socket object */
class chatserver extends JFrame implements ActionListener,Runnable
{
	JLabel heading;
	JPanel mainpanel;
	JTextArea chatscreen;
	JTextField text;
	JButton enter;
	
	server s;
	//BufferedReader in;
	//PrintWriter out;
	
	Thread t;
	
	public chatserver()
	{
		try {
			s =new server();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setLayout(null);
        this.setTitle("chatserver");
        this.setBounds(700,300,600,450);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        heading=new JLabel("CITADEL");
        heading.setBounds(0,0,600,30);
        this.add(heading);
        heading.setFont(new Font(null,Font.BOLD,25));
        heading.setHorizontalAlignment(JLabel.CENTER);
        heading.setBackground(new Color(0,255,0));
        heading.setOpaque(true);
        
        mainpanel=new JPanel();
        mainpanel.setLayout(null);
        mainpanel.setBackground(new Color(255,0,0));
        this.add(mainpanel);
        mainpanel.setBounds(0,30,600,420);
        
        chatscreen = new JTextArea();
		chatscreen.setForeground(Color.ORANGE);
        chatscreen.setBackground(new Color(162,0,255));
        chatscreen.setBounds(12,8,563,320);
//        chatscreen.setEditable(false);
        chatscreen.setFont(new Font("Roboto" ,Font.PLAIN,20));
        JScrollPane scroll = new JScrollPane(chatscreen,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(12,8,563,320);
        chatscreen.setDisabledTextColor(new Color(255,100,0));
        mainpanel.add(scroll);
        
        text = new JTextField();
        text.setBackground(new Color(0,255,255));
        text.setBounds(12,340,450,30);
        text.setFont(new Font("Roboto" ,Font.PLAIN,16));
        mainpanel.add(text);
        
        enter = new JButton("ENTER");
        mainpanel.add(enter);
        enter.setFocusable(false);
        enter.setBounds(473,340,100,30);
        enter.setBackground(new Color(70,50,50));
        enter.setForeground(new Color(0,226,250));
        enter.addActionListener(this);
        
        this.setVisible(true);
        
        t=new Thread(this);
        t.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==enter)
		{
			String str = text.getText();
			if(!str.equals(""))
			{
				text.setText("");
				chatscreen.append("\n you :"+str+"\n");
				//out.println(" user :"+str+"\n");
				s.sendtext('\n'+str);
				//out.flush();
			}
		}
	}

	@Override
	public void run() {
		while(true)
        {
        	try {
        		String t = s.revievetext();
        		chatscreen.append(t);
				Thread.sleep(100);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
	
}

public class serverudp {

	public static void main(String[] args) {
		new chatserver();
	}

}
