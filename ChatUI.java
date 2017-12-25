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
				msgs.add(new Msg(myid, buddyid, time, msg.getText()));
//				System.out.println(buddyid + " " + myid);
				receive("我", time, msg.getText());
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
		history.append(buddy + " " + time + "\n");
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
			if(msgs.size() != 0) {
				for (int i = 0; i < msgs.size(); i++) {
					Msg amsg = msgs.get(i);
					//				System.out.println(amsg.id);
					MessageQueue.add(amsg.from, amsg.to, amsg.time, amsg.content);
				}
				msgs.clear();
			}
			if(!MessageQueue.isEmpty(myid)) {
				for (int i = 0; i < MessageQueue.num[myid]; i++) {
					String newbuddy = "[" + (String) Server.users.get(MessageQueue.content[myid][i].from) + "]";
					if (!this.buddy.equals(newbuddy)) {
						ChatUI t = new ChatUI(newbuddy, me, myid, buddyid);
						new Thread(t).start();
					}
					Msg amsg = MessageQueue.get(myid);
					while (amsg != null) {
						receive(buddy, amsg.time, amsg.content);
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
