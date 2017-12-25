package NotQQ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

class ListUI extends JFrame {
	String server;

	ListUI(List users, String me, String server) {
		this.server = server;

		setLayout(new FlowLayout());
		setBounds(600, 300, 250, 500);
		String lookAndFeel = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JList list = new JList();
		list.setListData(users.toArray());
		list.setFixedCellHeight(30);
		list.setFixedCellWidth(170);
		JButton flash = new JButton("刷新");
		JButton buddy = new JButton("私聊");
		JButton group = new JButton("群聊");

		add(new JLabel("我的昵称是：" + me));
		add(new JLabel("当前在线的好友:"));
		add(flash);
		add(new JScrollPane(list));
		add(buddy);
		add(new JLabel("——————————————"));
		add(new JLabel("或者，你也可以选择："));
		add(group);

		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		flash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				list.setListData(Server.getUsers().toArray());
			}
		});

		buddy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buddy = list.getSelectedValuesList().toString();
				ChatUI t = new ChatUI(buddy, me, users.indexOf(me), users.indexOf(buddy.substring(1, buddy.length()-1)));
				new Thread(t).start();
			}
		});

		group.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String buddy = "[all]";
				ChatUI t = new ChatUI(buddy, me, users.indexOf(me), users.indexOf(buddy.substring(1, buddy.length()-1)));
				new Thread(t).start();
			}
		});
	}

//	public static void main(String[] args) {
//		new ListUI(null);
//	}
}
