package NotQQ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatUI extends JFrame implements Runnable {
	TextArea history = new TextArea(20, 55);
	String buddy;
	String me;
	int myid;
	int buddyid;
	ArrayList<Msg> msgs = new ArrayList<Msg>();

	ChatUI(String buddy, String me, int myid, int buddyid) {
		this.buddyid = buddyid;
		this.myid = myid;
		this.me = me;
		this.buddy = buddy;
		setLayout(new FlowLayout());
		setBounds(600, 300, 500, 600);
		String lookAndFeel =UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setTitle("和" + buddy + "聊天中");

		TextArea msg = new TextArea(5, 55);
		JButton file = new JButton("选择文件");
		JButton send = new JButton("发送");
		JButton close = new JButton("关闭");
		history.setEditable(false);

		add(history);
		add(new JLabel("在下方输入聊天内容，或者你也可以：", SwingConstants.LEFT));
		add(file);
		add(msg);
		add(send);
		add(close);

		send.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
				String time = df.format(new Date());
				msgs.add(new Msg(myid, buddyid, time, msg.getText())); // 创建消息对象,并添加到待发送的msgs数组
//				System.out.println(buddyid + " " + myid);
				receive("我", time, msg.getText()); // 展示已发消息
				System.out.println(msg.getText());
			}
		});

		close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setVisible(true);
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void receive(String buddy, String time, String content) {
		history.append(buddy + " " + time + "\n"); // 更新到消息框
		history.append(" " + content + "\n\n");
	}

	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(msgs.size() != 0) { // 如果有待发送消息
				for (int i = 0; i < msgs.size(); i++) { // 获取所有的消息
					Msg amsg = msgs.get(i);
					MessageQueue.add(amsg.from, amsg.to, amsg.time, amsg.content); // 添加到消息队列
				}
				msgs.clear(); // 清空待发送消息
			}
			if(!MessageQueue.isEmpty(myid)) { // 如果有未读消息,就处理
				for (int i = 0; i < MessageQueue.num[myid]; i++) {
					String newbuddy = "[" + Server.users.get(MessageQueue.content[myid][i].from) + "]";
					if (!this.buddy.equals(newbuddy)) { // 如果没有打开和对方的聊天窗口
						ChatUI t = new ChatUI(newbuddy, me, myid, buddyid); // 创建窗口并开启收发消息进程
						new Thread(t).start();
					}
					Msg amsg = MessageQueue.get(myid);
					while (amsg != null) { // 获取所有未读消息
						receive(Server.users.get(amsg.from).toString(), amsg.time, amsg.content); // 接收消息
						amsg = MessageQueue.get(myid);
					}
				}
			}
		}
	}

//	public static void main(String[] args) {
//		new ChatUI(new ArrayList(), "cat");
//	}
}
