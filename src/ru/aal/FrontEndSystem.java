package ru.aal;

import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Queue;

public class FrontEndSystem {
    private Queue<Request> requests = new ArrayDeque<>();
    private int requestLeft;

    public FrontEndSystem(int requestLeft) {
        this.requestLeft = requestLeft;
    }

    public synchronized void addRequest(Request request) {
        try {
            while (requests.size() == 2) {
                wait();
            }
            requests.add(request);
            System.out.printf("%s: %s отправлена в банк\n", request.getClientThreadName(), request);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Request getRequest() {
        try {
            Request request = requests.remove();
            System.out.printf("%s: получена заявка на обработку по клиенту - %s \n", Thread.currentThread().getName(), request.getClientThreadName());
            return request;
        } catch (NoSuchElementException e) {
            return null;
        } finally {
            notifyAll();
        }
    }
}
