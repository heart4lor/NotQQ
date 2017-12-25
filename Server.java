package NotQQ;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server {
	static int cnt = 0;
	static ArrayList users = new ArrayList();
	static ServerThread[] threads = new ServerThread[10240];


	public static ArrayList getUsers() {
		return users;
	}

	public static synchronized void login(String name) {
		users.add(name);
		System.out.println(users);
	}

	public static synchronized void logout(String name) {
		users.remove(name);
		System.out.println(users);
	}

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(14300);
		System.out.println("服务端已开启");
		Socket client = null;
		login("all");
//		Server notqq = new Server();
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			client = server.accept();
			client.close(); // touch

			client = server.accept();
			threads[cnt] = new ServerThread(client);
			threads[cnt].start();
			cnt++;
//			System.out.println("Connect success, #" + cnt);
//			System.out.printf("当前%d个用户在线\n", cnt);
			// 为每个客户端连接开启一个线程
		}
	}
}
