package server;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class Server_Return implements Runnable {
	private Server server;
	private Socket socket;
	
	public Server getServer() {
		return server;
	}
	public Socket getSocket() {
		return socket;
	}
	
	public Server_Return(Server server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}	
	
	private void sendExtension(Socket socket,File file)throws Exception{
		PrintWriter pred = new PrintWriter(
			new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true
			);
		pred.println(file.getName());      
	}
	
	private void sendFilename(File file, OutputStream outputStream)throws Exception{
		PrintWriter pred = new PrintWriter(
			new BufferedWriter(new OutputStreamWriter(outputStream)), true
			);
		pred.println(file.getName());      
	}
	
	private void sendFilesize1(File file, OutputStream outputStream)throws Exception{
		PrintWriter pred = new PrintWriter(
			new BufferedWriter(new OutputStreamWriter(outputStream)), true
			);
		pred.println(file.length());      
	}
	
	private void sendFilesize(File file, OutputStream outputStream)throws Exception{
		DataOutputStream dOut = new DataOutputStream(outputStream);
		dOut.writeLong(file.length());
	}
	
	public boolean sendFile(String f) throws Exception {
		long pc = 0, c = 0;
		File file = new File(f);
		
		FileInputStream fileInputStream = new FileInputStream(file);
		OutputStream outputStream = socket.getOutputStream();
		sendFilename(file, outputStream);
		sendFilesize(file, outputStream);
		byte[] bytes = new byte[1024];
		int nByte;
		while((nByte = fileInputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, nByte);
			c ++;
			pc = (int) ((c * 102400) / file.length());
			server.getClient().progressBar.setValue((int) pc);
			if(pc > 100) {
				pc = 100;
				server.getClient().progressBar.setString("Fichier Envoye");
			}
		}
		JOptionPane.showMessageDialog(server.getClient(), "Fichier Envoye a :\n" + socket , "Message de Transfert", JOptionPane.INFORMATION_MESSAGE);
		fileInputStream.close();
		
		return false;
	}
	public void run() {
		try {
			sendFile(server.getClient().getLblNoFileSelected().getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(server.getClient(), " "+socket+" is closed", "ERROR", JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
		}
	}

	
	
}
