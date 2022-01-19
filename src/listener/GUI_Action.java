package listener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import server.Server;
import server.Server_GUI;
import client.GUI_Client;

public class GUI_Action implements ActionListener{
	Server server;
	Server_GUI gui;
	GUI_Client client;
	public GUI_Action(Server s) {
		server = s;
	}

	public void actionPerformed(ActionEvent e) {
		gui = server.getGui();
		client = server.getClient();
		
		Object source = e.getSource();
		Server server = Server_GUI.server;
		Thread thread = new Thread(server);
		
		JMenuItem exit = gui.getMn_Menu_Exit();
		JMenuItem run = gui.getMn_Menu_Status_Run();
		JMenuItem close = gui.getMn_Menu_Status_Close();
		
		// EventHandler
		if(source == exit) {
			try {
				Server_GUI.server.closeAll();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			System.exit(0);
		}
		
		if(source == run) {
			gui.getMn_Menu_Status_Run().setEnabled(false);
			gui.getMn_Menu_Status_Close().setEnabled(true);
			server.setStatus(true);
			try {
				server.setClient(new GUI_Client(server));
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			thread.start();
		}
		
		if(source == close) {
			try {
				gui.getMn_Menu_Status_Run().setEnabled(true);
				gui.getMn_Menu_Status_Close().setEnabled(false);
				server.setStatus(false);
				thread.stop();
				Server_GUI.server.closeAll();
				Server_GUI.server.setClientList(new ArrayList<Socket>());
				gui.tableData();
				server.getSs().close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		boolean isRunning = server.getStatus();
		
		JLabel status = gui.getLblServer_Status();
		if(isRunning) {
			status.setText("Server : ON");
			status.setForeground(new Color(16, 169, 35));
		}
		else {
			status.setText("Server : OFF");
			status.setForeground(new Color(128, 0, 0));
		}
	}

}
