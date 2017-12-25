package NotQQ;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket client = null;
	private PrintStream out = null;
	private String clientname;
	private ListUI ui = null;
	private String ip = null;

	ServerThread(Socket client) {
		this.client = client;
		try {
			out = new PrintStream(client.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMsg(String from, String content) {
//		System.out.println(clientname + " reveive from " + from + ": " + content);
		out.println("用户" + from + "向您发送了一条消息: " + content);
	}

	public void run() {
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			// 获取Socket的输入流，用来接收从客户端发送过来的数据
			this.clientname = buf.readLine();
			Server.login(clientname);
			System.out.println("用户" + clientname + "已上线");
			this.ip = buf.readLine();
			ui = new ListUI(Server.getUsers(), clientname, ip); // login from Thread

			while(true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String str = buf.readLine();
				if(str != null)
					System.out.println("Client " + clientname + " said:" + str);
			}

		} catch (SocketException e) {
//			e.printStackTrace();
			Server.logout(clientname);
			System.out.println("用户" + clientname + "已断开");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
