package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import utils.Properties_Reader;
import client.GUI_Client;

public class Server implements Runnable {
	private ServerSocket ss;
	private  Socket s;
	private ArrayList<Socket> clientList = new ArrayList<Socket>();
	public Server_GUI gui;
	public GUI_Client client;
	
	private boolean isRunning;
	private boolean isSending;
	public int countClient;

	public boolean getStatus() {
		return isRunning;
	}
	public void setStatus(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public ArrayList<Socket> getClientList() {
		return clientList;
	}

	public ServerSocket getSs() {
		return ss;
	}
	
	public void setClientList(ArrayList<Socket> clientList) {
		this.clientList = clientList;
	}
	
	public Server_GUI getGui() {
		return gui;
	}
	public GUI_Client getClient() {
		return client;
	}
	public void setClient(GUI_Client c) {
		client = c;
	}
	public boolean isSending() {
		return isSending;
	}
	public void setSending(boolean isSending) {
		this.isSending = isSending;
	}
	public Server() {
		isSending = false;
	}
	public void run() {
		try {
			String sPort = Properties_Reader.getLine("port", "server_socket.properties");
			int port = new Integer(sPort);
			ss = new ServerSocket(port);
			while(true) {
				try {
//					System.out.println("... Waiting for clients ...");
					s = ss.accept();
					JOptionPane.showMessageDialog(gui, "Socket :\n" + s + "\n acceptee", "Nouveau Client", JOptionPane.INFORMATION_MESSAGE);
					clientList.add(s);
					countClient = clientList.size();
					gui.tableData();
				} catch(SocketException s) {
//					System.out.println("Server is not Responding");
					setStatus(false);
					break;
				} catch(Exception ex) {
					setStatus(false);
					ex.printStackTrace();
				}
			}
		} catch(Exception e) {
			setStatus(false);
			e.printStackTrace();
		} 
	}
	
	public boolean closeAll() throws IOException {
		for(int i = 0 ; i < clientList.size(); i += 1) {
			Socket tempSock = (Socket) clientList.get(i);
			OutputStream out = tempSock.getOutputStream();
			out.write("Server is not responding".getBytes());
			out.flush();
			tempSock.close();
		}
		return false;
	}
	
	public void send() throws Exception {
		for(int i = 0; i < clientList.size(); i++) {
			Server_Return sR = new Server_Return(this, clientList.get(i));
			Thread thread = new Thread(sR);
			thread.start();
		}
	}

	
}
