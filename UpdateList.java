package NotQQ;

import javax.swing.*;
import java.util.ArrayList;

public class UpdateList extends Thread {
	JList list;

	UpdateList(JList list) {
		this.list = list;
	}

	public void run() {
		while(true) {
			list.setListData(Server.getUsers().toArray());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
