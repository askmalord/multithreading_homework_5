package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        final ByteBuffer inputBuffer;
        String message;
        InetSocketAddress socketAddress = new InetSocketAddress(Server.HOST, Server.PORT);
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader readerOfClientMessage = new BufferedReader(new InputStreamReader(System.in));) {
            socketChannel.connect(socketAddress);
            inputBuffer = ByteBuffer.allocate(2 << 10);
            while (true) {
                System.out.println("Введите сообщение для отправки на сервер " +
                    "(для прекращения отправки введите end):");
                message = readerOfClientMessage.readLine();
                if (message.equals("end")) {
                    break;
                }
                socketChannel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8));
                inputBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
