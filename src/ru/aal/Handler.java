package ru.aal;

import java.util.Objects;

public class Handler implements Runnable {
    private FrontEndSystem frontEndSystem;
    private BackEndSystem backEndSystem;

    public Handler(FrontEndSystem frontEndSystem, BackEndSystem backEndSystem) {
        this.frontEndSystem = frontEndSystem;
        this.backEndSystem = backEndSystem;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            Request request = frontEndSystem.getRequest();
            if (Objects.nonNull(request)) {
                backEndSystem.executeRequest(request);
            }
        }
    }
}
