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

	public void run() {
		try {
			BufferedReader buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
			// 获取Socket的输入流，用来接收从客户端发送过来的数据
			this.clientname = buf.readLine(); // 得到发送来的用户名
			Server.login(clientname); // 调用登陆方法
			System.out.println("用户" + clientname + "已上线");
			this.ip = buf.readLine();
			ui = new ListUI(Server.getUsers(), clientname, ip); // 从线程登陆

		} catch (SocketException e) {
//			e.printStackTrace();
			Server.logout(clientname);
			System.out.println("用户" + clientname + "已断开");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
