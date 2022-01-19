package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import utils.Properties_Reader;
import utils.Utilities;

public class Client implements Runnable {
	
	private Socket socket;
	
	public Client(Socket socket) {
		this.socket = socket;
	}
	
	static String getExt(Socket soc)throws Exception{
		BufferedReader plec = new BufferedReader(
			new InputStreamReader(soc.getInputStream())
			);
		return plec.readLine();
	}
	
	private String getFilename(InputStream inputStream)throws Exception{
		BufferedReader plec = new BufferedReader(
			new InputStreamReader(inputStream)
		);
		return plec.readLine();
	}
	
	private String getFilesize1(InputStream inputStream)throws Exception{
		BufferedReader plec = new BufferedReader(
			new InputStreamReader(inputStream)
		);
		return plec.readLine();
	}
	
	private long getFilesize(InputStream inputStream)throws Exception{
		DataInputStream dIn = new DataInputStream(inputStream);
		return dIn.readLong();
	}
	
	public void run() {
		File file = new File("f");
		while(true) {
			try {
				InputStream inputStream = socket.getInputStream();
				byte[] bytes = new byte[1024];
				file = new File(getFilename(inputStream));
				long fileSize = getFilesize(inputStream);
				FileOutputStream fileOutputStream = new FileOutputStream("downloads/" + file);
				int nByte;
				long total = 0;
				while(total <= fileSize) {
					nByte = inputStream.read(bytes);
					if(nByte == -1) break;
					total += bytes.length;
					fileOutputStream.write(bytes, 0, nByte);
				}
				JOptionPane.showMessageDialog(null, "Fichier Recu par :\n" + socket , "Message de Transfert", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Server unavailable", "ERROR", JOptionPane.ERROR_MESSAGE);
//				e.printStackTrace();
				break;
			} 
		}
	}
	public static void main(String[] args) throws Exception {
		String ip = Properties_Reader.getLine("ip", "client_config.properties");
		String sPort = Properties_Reader.getLine("port", "client_config.properties");
		int port = new Integer(sPort);
		Client client;
		try {
			client = new Client(new Socket(ip, port));
			Thread thread = new Thread(client);
			thread.start();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Invalid address", "ERROR", JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Server unavailable", "ERROR", JOptionPane.ERROR_MESSAGE);
//			e.printStackTrace();
		}
	}
}
