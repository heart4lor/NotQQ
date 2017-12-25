package NotQQ;

import java.net.*;
import java.io.*;

public class Test {

	public static String getServerIP() {
		String host = "192.168.43."; // 设置要搜索的ip段
		int port = 14300;
		for (int i = 0; i < 256; i++) {
			String a = String.valueOf(i);
			Socket s = new Socket();
			try {
				s.connect(new InetSocketAddress(host+a, port), 10);
				while(true) {
					if (s.isConnected()) // 测试14300端口是否开启
						return host+a;
					s.close();
				}
			} catch (IOException ignored) {}
		}
		return null;
	}
}
