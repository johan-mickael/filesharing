package server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;

import java.net.Socket;

import listener.GUI_Action;

public class Server_GUI extends JFrame {

	private JPanel contentPane;
	public static Server server;
	private JMenuItem mn_Menu_Exit;
	private JMenuItem mn_Menu_Status_Run;
	private JMenuItem mn_Menu_Status_Close;
	private JLabel lblServer_Status;
	private JScrollPane scrollPane;
	private JTable table;
	private JScrollPane jscrollPane;
	protected Font font = new Font("Tahoma", Font.PLAIN, 11);

	public JScrollPane getJscrollPane() {
		return jscrollPane;
	}
	
	public void setJscrollPane(JScrollPane jscrollPane) {
		this.jscrollPane = jscrollPane;
	}
	
	public JLabel getLblServer_Status() {
		return lblServer_Status;
	}

	public void setLblServer_Status(JLabel lblServer_Status) {
		this.lblServer_Status = lblServer_Status;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JMenuItem getMn_Menu_Exit() {
		return mn_Menu_Exit;
	}

	public void setMn_Menu_Exit(JMenuItem mn_Menu_Exit) {
		this.mn_Menu_Exit = mn_Menu_Exit;
	}

	public JMenuItem getMn_Menu_Status_Run() {
		return mn_Menu_Status_Run;
	}

	public void setMn_Menu_Status_Run(JMenuItem mn_Menu_Status_Run) {
		this.mn_Menu_Status_Run = mn_Menu_Status_Run;
	}

	public JMenuItem getMn_Menu_Status_Close() {
		return mn_Menu_Status_Close;
	}

	public void setMn_Menu_Status_Close(JMenuItem mn_Menu_Status_Close) {
		this.mn_Menu_Status_Close = mn_Menu_Status_Close;
	}
	
	public void setJTable(JTable tableData) {
		this.table = tableData;
	}

	public JTable getJTable() {
		return table;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server = new Server();
					Server_GUI frame = new Server_GUI(server);
					server.gui = frame;
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Server_GUI(Server server) {
		setFont(font);
		frameSettings();		
		Server_GUI.server = server;
	}

	private void menuSettings() {
		GUI_Action listener = new GUI_Action(server);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(font);
		setJMenuBar(menuBar);
		
		JMenu mn_Menu = new JMenu("Menu");
		mn_Menu.setFont(font);
		menuBar.add(mn_Menu);
		
		JMenu mn_Menu_Status = new JMenu("Server");
		mn_Menu_Status.setFont(font);
		mn_Menu.add(mn_Menu_Status);
		
		mn_Menu_Status_Run = new JMenuItem("Run");
		mn_Menu_Status_Run.setFont(font);
		mn_Menu_Status_Run.addActionListener(listener);
		mn_Menu_Status.add(mn_Menu_Status_Run);
		
		mn_Menu_Status_Close = new JMenuItem("Close");
		mn_Menu_Status_Close.setFont(font);
		mn_Menu_Status_Close.setEnabled(false);
		mn_Menu_Status_Close.addActionListener(listener);
		mn_Menu_Status.add(mn_Menu_Status_Close);
		
		mn_Menu_Exit = new JMenuItem("Exit");
		mn_Menu_Exit.setFont(font);
		mn_Menu_Exit.addActionListener(listener);
		mn_Menu.add(mn_Menu_Exit);
	}

	private void contentPaneSettings() {
		contentPane();
		lblServer_Status();
		scrollPane();
		gl_contentPane();
	}
	
	private void gl_contentPane() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(151)
							.addComponent(lblServer_Status))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 383, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addComponent(lblServer_Status)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
					.addGap(183))
		);
		contentPane.setLayout(gl_contentPane);
	}

	private void scrollPane() {
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setToolTipText("Clients List");
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	}

	private void lblServer_Status() {
		lblServer_Status = new JLabel("Server : OFF");
		lblServer_Status.setToolTipText("Server Status");
		lblServer_Status.setBackground(new Color(0, 0, 0));
		lblServer_Status.setForeground(new Color(128, 0, 0));
		lblServer_Status.setFont(new Font("Tahoma", Font.BOLD, 10));
	}

	private void contentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	}

	public void tableData() {
		int countClient = server.getClientList().size();
		Object[][] rows = new Object[countClient][2];
		Object[] cols = {"Canonical Host Name", "Remote Socket Address"};
		
		for(int  i = 0 ; i < countClient ; i += 1) {
			Socket tempSock = (Socket) server.getClientList().get(i);
			System.out.println(tempSock);
			rows[i][0] = tempSock.getInetAddress().getCanonicalHostName();
			rows[i][1] = tempSock.getRemoteSocketAddress();
		}
		
		JTable table = new JTable(rows, cols);
		table.setEnabled(false);
		scrollPane.setViewportView(table);
	}

	private void frameSettings() {
		setResizable(false);
		setBackground(new Color(255, 255, 255));
		setForeground(new Color(0, 0, 0));
		setTitle("Chat Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 276);		
		menuSettings();
		contentPaneSettings();
	}
}
