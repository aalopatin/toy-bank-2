package ru.aal;

public class BackEndSystem {

    private int balance;
    private final String SUCCESS = "Бэк система: %s УСПЕШНО ВЫПОЛНЕНА. Получена от %s. Баланс банка: %d\n";
    private final String FAILURE = "Бэк система: %s НЕ ВЫПОЛНЕНА. Получена от %s. " +
                                    "Сумма больше баланса банка. Баланс банка: %d\n";

    private int remainPerform;

    public BackEndSystem(int balance, int remainPerform) {
        this.balance = balance;
        this.remainPerform = remainPerform;
    }

    public synchronized void executeRequest(Request request) {

        switch (request.getType()) {
            case CREDIT:
                if(balance >= request.getAmount()) {
                    balance -= request.getAmount();
                    System.out.printf(SUCCESS,
                            request,
                            Thread.currentThread().getName(),
                            balance);
                } else {
                    System.out.printf(FAILURE,
                                    request,
                                    Thread.currentThread().getName(),
                                    balance);
                }

                break;
            case REPAYMENT:
                balance += request.getAmount();
                System.out.printf(SUCCESS,
                        request,
                        Thread.currentThread().getName(),
                        balance);
                break;

        }
        remainPerform--;
    }

    public synchronized int getRemainPerform() {
        return remainPerform;
    }
}
