package com.fabrick.demo;

import liquibase.pro.packaged.A;
import liquibase.pro.packaged.C;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ReactiveTest {

    @Test
    public void thenMany(){

        AtomicInteger letters = new AtomicInteger();
        AtomicInteger numbers = new AtomicInteger();
        Flux<String> lettersPublisher = Flux.just("a", "b", "c")
                .log()
                .doOnNext(value -> letters.incrementAndGet());
        Flux<Integer> numberPublisher = Flux.just(1, 2, 3)
                .log()
                .doOnNext(value -> numbers.incrementAndGet());

        Flux<Integer> thisBeforeThat = lettersPublisher.thenMany(numberPublisher);

        lettersPublisher.subscribe(s -> s.length());
        StepVerifier.create(thisBeforeThat).expectNext(1, 2, 3).verifyComplete();

        Assert.assertEquals(letters.get(), 3);
        Assert.assertEquals(numbers.get(), 3);

    }

    @Test
    public void transform(){
        AtomicBoolean finished = new AtomicBoolean();
        Flux<String> transform = Flux.just("A", "B", "C", "D")
                .transform(stringFlux -> stringFlux.doFinally(signalType -> finished.set(true)));

        StepVerifier.create(transform).expectNextCount(3).verifyComplete();
        Assert.assertTrue(finished.get());

    }

    @Test
    public void fun(){

        Flux<Integer> integerFlux = Flux.just(1, 2, 3)
                .log()
                .doOnNext(integer -> System.out.println("on next called integer"));

        integerFlux
                .subscribe(new CustomSubscriber());
    }

}
