package NotQQ;

public class MessageQueue {
	static int[] num = new int[10240]; // id编号
	static Msg[][] content = new Msg[10240][1024]; // 消息内容

	static boolean isEmpty(int id) {
		return num[id] == 0;
	}

	static void add(int from, int to, String time, String msg) {
		System.out.println("added!"); // 添加消息方法
		Msg amsg = new Msg(from, to, time, msg);
		content[to][num[to]++] = amsg;
	}

	static Msg get(int id) { // 获取未读消息
		if(num[id] == 0)
			return null;
		return content[id][--num[id]];
	}
}
