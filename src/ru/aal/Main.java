package ru.aal;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("Введите количество клиентов (не меньше 4): ");
        Scanner scanner = new Scanner(System.in);

        int countClients = scanner.nextInt();
        countClients = Math.max(countClients, 4);

        FrontEndSystem frontEndSystem = new FrontEndSystem(countClients);
        BackEndSystem backEndSystem = new BackEndSystem(1_000_000, countClients);

        System.out.println("Введите количество обработчиков (не меньше 2): ");

        int countHandlers = scanner.nextInt();
        countHandlers = Math.max(countHandlers, 2);

        Client client = new Client(frontEndSystem);
        for (int i = 1; i < countClients + 1; i++) {
            Thread thread = new Thread(client, "Клиент №" + i);
            thread.start();
        }

        Handler handler = new Handler(frontEndSystem, backEndSystem);
        List<Thread> handlers = new ArrayList<>();
        for (int i = 1; i < countHandlers + 1; i++) {
            Thread thread = new Thread(handler, "Обработчик заявок №" + i);
            thread.start();
            handlers.add(thread);
        }

        while(true) {
            if (backEndSystem.getRemainPerform() == 0) {
                for (Thread thread : handlers) {
                    thread.interrupt();
                }
                break;
            }
        }

    }
}
