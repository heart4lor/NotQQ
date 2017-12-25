# NotQQ

截图：

![](http://img-1253392534.cossh.myqcloud.com/NotQQ.png)

---

服务端：

* Server.java

  监听14300端口，如果有连接就接受并调用ServerThread类为这个连接创建一个socket

* ServerThread.java

  接收和处理客户端发来的数据，调用Server类的login和logout方法上线和下线

客户端：

* Client.java

  调用Test类的getServerIP方法搜索服务器ip，如果搜索到就实例化一个MainUI对象

* MainUI.java

  实例化一个LoginUI登陆窗口

* LoginUI.java

  创建登陆窗口，点击登陆按钮时将昵称通过socket发送到Server，Server为之创建一个新的ServerThread线程，并调用ListUI

* ListUI.java

  在线好友窗口，调用Server.getUsers或许在线用户列表，并通过一个JList展示

  自动刷新会导致当前窗口失去焦点，所以设置了一个刷新按钮，点击更新JList

  选中一个好友后点击私聊，调用ChatUI，创建一个聊天窗口

  选中“all”好友或点击群聊按钮即可和所有在线用户群聊

* ChatUI.java

  功能一：展示聊天窗口

  功能二：继承Runnable方法，作为线程处理消息的发送和读取

  * 消息的发送：本类包含一个Msg类型的ArrayList对象msgs，用来存储待发送的消息。点击发送按钮后将文本框中的内容及相关信息封装成一个Msg对象，并添加到msgs列表中。run方法中持续运行着的代码检测到msgs不为空，就将其中的对象取出，调用MessageQueue中的add方法，将消息存入其中。
  * 消息的收取：本类的run方法中持续运行着的代码调用MessageQueue类的isEmpty方法检测到有未读消息就进行处理。调用MessageQueue的get方法获取到一条消息后，如果当前窗口不是和发信人的聊天窗口，就创建一个新窗口，相当于QQ中的来消息强制弹出。之后调用本类中的receive方法显示这条信息。


消息处理：

* Msg.java

  描述了一条消息的基本数据结构，包括发信人、收信人、内容、时间

* MessageQueue.java

  存储了所有的未读消息