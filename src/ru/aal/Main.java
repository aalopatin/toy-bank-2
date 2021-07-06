package ru.aal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static FrontEndSystem frontEndSystem;
    private static BackEndSystem backEndSystem;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Введите количество клиентов (не меньше 4): ");
        Scanner scanner = new Scanner(System.in);
        int countClients = scanner.nextInt();
        countClients = countClients < 4 ? 4 : countClients;

        frontEndSystem = new FrontEndSystem(countClients);
        backEndSystem = new BackEndSystem(1_000_000, countClients);

        System.out.println("Введите количество обработчиков (не меньше 2): ");
        int countHandlers = scanner.nextInt();
        countHandlers = countHandlers < 2 ? 2 : countHandlers;

        for (int i = 1; i < countClients + 1; i++) {
            Client client = new Client(frontEndSystem);
            Thread thread = new Thread(client, "Клиент №" + i);
            thread.start();
        }

        List<Thread> handlers = new ArrayList<>();
        for (int i = 1; i < countHandlers + 1; i++) {
            Handler handler = new Handler(frontEndSystem, backEndSystem);
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
