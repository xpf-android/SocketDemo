package com.example.simplechatroom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: xpf
 * date: 2020/1/2 14:52
 * description: 模拟聊天室的服务端，直到客户端发出bye的信息,停止回复客户端，结束通信
 */
public class ChatServer {
    //服务器的ServerSocket
    private ServerSocket server = null;
    private static final int PORT = 10065;
    private List<Socket> mClients = new ArrayList<>();
    private ExecutorService mExec = null;

    public static void main(String[] args) {
        new ChatServer();
    }

    public ChatServer() {
        //开启服务
        System.out.println("服务器运行中...");
        try {
            server = new ServerSocket(PORT);
            //创建一个线程池
            mExec = Executors.newCachedThreadPool();
            Socket client = null;
            while (true) {
                client = server.accept();
                mClients.add(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Service implements Runnable {
        private Socket socket;
        private BufferedReader br = null;
        private String msg = "";

        public Service(Socket socket) {
            this.socket = socket;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.sendMsg();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 回复客户端
         */
        public void sendMsg() {
            int num = mClients.size();
            //向服务器写信息
            OutputStream os = null;
            try {
                os = socket.getOutputStream();
                //将输出流包装成打印流
                PrintWriter pw = new PrintWriter(os);
                pw.write("你好 你是第" + num + "个客户");
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if ((msg = br.readLine()) != null) {
                        System.out.print("客户端说：" + msg);
                        if ("bye".equals(msg)) {//应用自己定义的协议
                            socket.close();
                            break;
                        } else {
                            sendMsg();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
