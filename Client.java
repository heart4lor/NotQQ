package NotQQ;

import java.io.*;
import java.net.*;

public class Client {
	public static String name;

	public static void main(String[] args) throws IOException {
		String host = Test.getServerIP();

		if(host == null) {
			System.out.println("未搜索到服务器");
			System.exit(0);
		}
		else
			new MainUI(host);

		System.out.println("搜索到服务器" + host);
	}
}
