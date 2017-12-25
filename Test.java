package NotQQ;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Test {
	public static ArrayList users = new ArrayList();

	public static synchronized void login(String name) {
		users.add(name);
		System.out.println(users);
	}

	public static synchronized void logout(String name) {
		users.remove(name);
		System.out.println(users);
	}

	public static void testprint() {
		System.out.println("hello");
	}

	public static String getServerIP() {
		String host = "192.168.43.";
		int port = 14300;
		for (int i = 0; i < 256; i++) {
			String a = String.valueOf(i);
			Socket s = new Socket();
			try {
				s.connect(new InetSocketAddress(host+a, port), 10);
				while(true) {
					if (s.isConnected())
						return host+a;
					s.close();
				}
			} catch (IOException ignored) {}
		}
		return null;
	}

	public static void testsplit() {
		String str = "[cat]";
//		String[] s = str.split("\\s+");
//		System.out.println(s[0]);
//		System.out.println(str.split("\\S+")[0]);
		System.out.println(str.substring(1, str.length()-1));
	}

	public static void main(String[] args) {
//		System.out.println(getServerIP());
//		while(true){}
		testsplit();
	}
}
