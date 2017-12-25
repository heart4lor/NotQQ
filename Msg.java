package NotQQ;

public class Msg { // 存储消息的对象
	int from; // 发件人
	int to; // 目标
	String time; // 时间
	String content; // 内容

	public Msg(int from, int to, String time, String content) {
		this.from = from;
		this.to = to;
		this.time = time;
		this.content = content;
	}
}
