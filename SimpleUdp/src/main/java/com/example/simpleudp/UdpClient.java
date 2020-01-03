package com.example.simpleudp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * author: xpf
 * date: 2020/1/3 13:34
 * description: upd协议通信客户端
 */
public class UdpClient {
    public static void main(String[] args) throws IOException {
        String msg = "Hello service";
        DatagramSocket datagramSocket = new DatagramSocket();

        //创建datagramPacket发送信息
        DatagramPacket datagramPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length, InetAddress.getLocalHost(),12307);

        datagramSocket.send(datagramPacket);

    }
}
