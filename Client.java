package NotQQ;

public class Client {
	public static String name;

	public static void main(String[] args) {
		String host = Test.getServerIP(); // 搜索局域网内开启14300端口的ip

		if(host == null) {
			System.out.println("未搜索到服务器");
			System.exit(0);
		}
		else
			new MainUI(host);

		System.out.println("搜索到服务器" + host);
	}
}
