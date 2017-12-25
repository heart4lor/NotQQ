package NotQQ;

import java.net.*;
import java.util.ArrayList;

public class Server {
	static int cnt = 0;
	static ArrayList users = new ArrayList(); // 存储所有用户
	static ServerThread[] threads = new ServerThread[10240]; // 线程数组


	public static ArrayList getUsers() {
		return users;
	}

	public static synchronized void login(String name) { // 登陆函数
		users.add(name);
		System.out.println(users);
	}

	public static synchronized void logout(String name) { // 登出函数
		users.remove(name);
		System.out.println(users);
	}

	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(14300); // 监听14300端口
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
			client.close(); // 第一次连接是探测服务器时的试练,需要断开

			client = server.accept();
			threads[cnt] = new ServerThread(client);
			threads[cnt].start();
			cnt++;
			// 为每个客户端连接开启一个ServerThread线程,一个客户端对应一个服务端处理请求
		}
	}
}
