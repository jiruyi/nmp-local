package com.matrictime.network.netty.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author jiruyi
 * @copyright www.matrictime.com
 * @project nmp-local
 * @date 2023/8/24 0024 10:10
 * @desc
 */
public class TCPEchoClient {
    // 定义一个用于客户端的Socket对象
    private Socket socket;

    /**
     * 初始化客户端的Socket
     *
     * @param serverIp 服务器IP地址
     * @param serverPort 服务器的端口号
     * @throws IOException
     */
    public TCPEchoClient (String serverIp, int serverPort) throws IOException {
        this.socket = new Socket(serverIp, serverPort);
    }

    public void start () throws IOException {
        System.out.println("客户端已启动...");
        // 获取Socket中的输入输出流
        try (InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            // 循环处理用户的输入
                System.out.println("->");
                try {
                    OutputStream ou = socket.getOutputStream();//发送io流
                    byte[] bytes = {1, 1, -25, 3, 52, 0, 0, 0, 49, 50, 51, 52, 53, 54, 55, 56, 57, 49, 50, 51, 52, 53, 54, 55, 49, 50, 51, 52, 53, 54, 55, 56, 57, 49, 50, 51, 52, 53, 54, 55, 0, 0, 0, 0, 1, 1, 1, 104, 101, 108, 108, 111};
                    ou.write(bytes);//getBytes()是将String字符串转换为Byte数组
                } catch (Exception se) {
                    se.printStackTrace();
                }

                // 接收服务器的响应
                Scanner responseScanner = new Scanner(inputStream);
                // 获取响应数据
              //  String response = responseScanner.nextLine();
                // 打印响应内容
                //System.out.println("接收到服务器的响应：" + response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }
    public Boolean send(String message) {
        try {
            OutputStream ou = socket.getOutputStream();//发送io流
            ou.write(message.getBytes("gbk"));//getBytes()是将String字符串转换为Byte数组
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            out.println(message);
            return true;
        } catch (Exception se) {
            se.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) throws IOException {
        TCPEchoClient client = new TCPEchoClient("192.168.72.221", 60000);
        client.start();
    }

}
