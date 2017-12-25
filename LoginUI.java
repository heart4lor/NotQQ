package NotQQ;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

class LoginUI extends JFrame {

	LoginUI(String ip) {
		setLayout(new FlowLayout());
		setBounds(600, 300, 250, 150);
		setTitle("Login");
		String lookAndFeel =UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JTextField inputname = new JTextField(10);
		JButton submit = new JButton("登陆");
		JButton cancel = new JButton("取消");

		add(new JLabel("Server ip: " + ip));
		add(new JLabel("Nickname:"));
		add(inputname);
		add(submit);
		add(cancel);

		setVisible(true);
//		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Socket client = new Socket(ip, 14300); // 本机和服务端建立socket连接
					PrintStream out = new PrintStream(client.getOutputStream());
					out.println(inputname.getText()); // 向服务端发送用户名
					out.println(ip);
					client.close();
//					dispose();
					setVisible(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
