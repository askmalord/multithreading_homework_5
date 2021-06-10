package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final int PORT = 9999;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT);
             Socket clientSocket = serverSocket.accept();
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {
            System.out.println("Новый запрос на сервер");
            String clientMessage = in.readLine();
            int number = Integer.parseInt(clientMessage);
            int serverMessage = getNumberFromFibonacciSeries(number);
            out.println(serverMessage);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
    public static int getNumberFromFibonacciSeries(int serialNumber) {
        int firstElement = 0;
        int secondElement = 1;
        int result = 0;
        if (serialNumber == 0) {
            return 0;
        }
        if (serialNumber == 1 ) {
            return 1;
        }
        for (int i = 0; i < serialNumber - 1; i++) {
            result = firstElement + secondElement;
            firstElement = secondElement;
            secondElement = result;
        }
        return result;
    }
}
