package ru.netology;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server {
    public static final String HOST = "localhost";
    public static final int PORT = 9999;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(HOST, PORT));
        while (true) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCount = socketChannel.read(inputBuffer);
                    if (bytesCount == -1) {
                        break;
                    }
                    final String message = new String(inputBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8);
                    String resultMessage = removeSpaces(message);
                    inputBuffer.clear();
                    socketChannel.write(ByteBuffer.wrap(("Строка без пробелов: " + resultMessage)
                        .getBytes(StandardCharsets.UTF_8)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                serverSocketChannel.close();
            }
        }
    }

    public static String removeSpaces(String someString) {
        StringBuilder builder = new StringBuilder();
        String[] massOfWords = someString.split(" ");
        for (int i = 0; i < massOfWords.length; i++) {
            builder.append(massOfWords[i].trim());
        }
        return builder.toString();
    }
}
