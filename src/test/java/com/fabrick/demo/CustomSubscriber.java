package com.fabrick.demo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class CustomSubscriber extends BaseSubscriber<Integer> {

    @Override
    public void hookOnNext(Integer i) {
        System.out.println("Hook On Next " + i);
    }

    @Override
    public void hookOnSubscribe(Subscription s){
        s.request(100);
    }

}
