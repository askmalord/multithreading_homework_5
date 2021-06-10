package ru.netology;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static final String HOST = "localhost";

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(HOST, Server.PORT);
             BufferedReader readerOfClientMessage = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            System.out.println("Введите целое число - порядковый номер числа из ряда Фибоначчи " +
                "(нумерация начинается с нуля):");
            String message = readerOfClientMessage.readLine();
            out.println(message);
            String serverAnswer = in.readLine();
            System.out.println("Ответ сервера: " + serverAnswer);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
}
