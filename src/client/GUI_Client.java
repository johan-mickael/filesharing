package client;

import java.awt.Component;
import java.awt.Container;
import java.awt.Insets;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;

import server.Server;


public class GUI_Client extends JFrame {

	private JPanel contentPane;
	private Font font = new Font("Calibri", Font.PLAIN, 11);
	private Server server;
	private final JLabel lblNoFileSelected = new JLabel("No File Selected");
	public JProgressBar progressBar;

	public JLabel getLblNoFileSelected() {
		return lblNoFileSelected;
	}

	public GUI_Client(Server serv) throws UnknownHostException, IOException {
		server = serv;
		setResizable(false);
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 455, 451, 147);
		setAlwaysOnTop(true);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		menu();
		panel();
		setVisible(true);
		
	}
	
	private void panel() {
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(new Color(230, 230, 250));
		panel.setBounds(0, 0, 445, 97);
		contentPane.add(panel);
		panel.setLayout(null);
		
		components(panel);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(new Color(0, 255, 127));
		progressBar.setFont(new Font("Calibri", Font.BOLD, 10));
		progressBar.setStringPainted(true);
		progressBar.setBounds(7, 39, 432, 16);
		panel.add(progressBar);
	}
	private void components(JPanel panel) {
		JLabel lblSelectFile = new JLabel("Select File :");
		lblSelectFile.setFont(new Font("Calibri", Font.BOLD, 12));
		lblSelectFile.setBounds(10, 11, 61, 14);
		panel.add(lblSelectFile);
		
		lblNoFileSelected.setFont(new Font("Calibri", Font.ITALIC, 11));
		lblNoFileSelected.setBounds(77, 11, 257, 14);
		panel.add(lblNoFileSelected);
		
		final JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browse(btnBrowse, lblNoFileSelected);
			}
		});
		btnBrowse.setForeground(new Color(255, 255, 255));
		btnBrowse.setBackground(new Color(119, 136, 153));
		btnBrowse.setToolTipText("Browse a File");
		btnBrowse.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 10));
		btnBrowse.setBounds(344, 9, 45, 19);
		btnBrowse.setMargin(new Insets(0, 0, 0, 0));
		panel.add(btnBrowse);
		
		JButton btnSend = new JButton("Send");
		btnSend.setToolTipText("Send  File");
		btnSend.setForeground(new Color(255, 255, 255));
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!server.isSending()) {
						server.send();
						server.setSending(true);
					}
						
					else {
						JOptionPane.showMessageDialog(server.getClient(), "The server is still sending", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					progressBar.setIndeterminate(true);
					e.printStackTrace();
				}
			}
		});
		btnSend.setBackground(new Color(119, 136, 153));
		btnSend.setFont(new Font("Calibri", Font.BOLD | Font.ITALIC, 10));
		btnSend.setBounds(394, 9, 45, 19);
		btnSend.setMargin(new Insets(0, 0, 0, 0));
		panel.add(btnSend);
	}

	private void menu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(230, 230, 250));
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		mnMenu.setFont(font );
		menuBar.add(mnMenu);
		
		JMenu mnStatus = new JMenu("Run");
		mnStatus.setFont(font);
		mnMenu.add(mnStatus);
		
		JMenuItem mntmConnect = new JMenuItem("Connect");
		mntmConnect.setFont(font);
		mnStatus.add(mntmConnect);
		
		JMenuItem mntmDisconnect = new JMenuItem("Disconnect");
		mntmDisconnect.setFont(font);
		mnStatus.add(mntmDisconnect);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setFont(font);
		mnMenu.add(mntmExit);
	}
	
	
	private void browse(JButton btnBrowse, JLabel path) {
		JFileChooser fileChooser = new JFileChooser("d:/");
		setFileChooserFont(fileChooser.getComponents());
		fileChooser.showOpenDialog(btnBrowse);
		String str = fileChooser.getSelectedFile().getAbsolutePath();
		path.setText(str);
	}
	
	public void setFileChooserFont(Component[] comp) {
		for(int x = 0; x < comp.length; x++) {
			if(comp[x] instanceof Container) 
				setFileChooserFont(((Container)comp[x]).getComponents());
			try {
				comp[x].setFont(new Font("Calibri", Font.PLAIN, 11));
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
