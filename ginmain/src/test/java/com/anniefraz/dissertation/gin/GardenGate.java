package com.anniefraz.dissertation.gin;

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class GardenGate {


    @Test
    public void test() throws IOException {
        int startingPeople = 0;

        AtomicFileNumberStore howManyPeople = getHowManyPeople(startingPeople); //file based
        //AtomicInteger howManyPeople = new AtomicInteger(startingPeople); //normal sane person way that runs WAY faster


        AtomicInteger threadNo = new AtomicInteger(1);

        int range = 100;


        Runnable gate = () -> {
            int thread = threadNo.getAndIncrement();
            for (int i = 0; i < 50; i++) {
                AtomicInteger people = new AtomicInteger(Double.valueOf(Math.floor(Math.random() * (2 * range) - range)).intValue());
                int currentPeople = howManyPeople.updateAndGet(val -> {
                    if (people.get() <= 0) {
                        people.updateAndGet(peep -> val + peep >= 0 ? peep : -val);
                    }
                    return val + people.get();
                });
                System.out.println("Thread " + thread + ": " + Math.abs(people.get()) + " people have " + ((people.get() >= 0) ? "arrived" : "left") + ", resulting in " + currentPeople + " people in the garden");
            }
        };

        Runnable simpleGate = () -> {
            int thread = threadNo.getAndIncrement();
            for (int i = 0; i < 50; i++) {
                System.out.println("Thread " + thread + ": " + howManyPeople.incrementAndGet() + " people in the garden");
            }
        };

        int concurrentThreads = 2;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(concurrentThreads, concurrentThreads, 10L, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));

        List<Future> gates = new ArrayList<>();
        gates.add(executor.submit(simpleGate));
        gates.add(executor.submit(simpleGate));
        gates.add(executor.submit(simpleGate));
        gates.add(executor.submit(gate));
        gates.add(executor.submit(gate));
        gates.add(executor.submit(gate));


        while (true) {
            if (gates.stream().allMatch(Future::isDone)) break;
        }

        System.out.println();
        System.out.println("Total people: " + howManyPeople.get());

    }

    private AtomicFileNumberStore getHowManyPeople(int i) throws IOException {
        Path path = Files.createTempFile("admin-", ".txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
            bufferedWriter.write(Integer.valueOf(i).toString());
        }
        return new AtomicFileNumberStore(path);
    }

    private class AtomicFileNumberStore {

        private Path path;

        private AtomicFileNumberStore(Path path) {
            this.path = path;
        }


        public synchronized int updateAndGet(UnaryOperator<Integer> updater) {
            Integer integer;
            try (Stream<String> lines = Files.lines(path)) {
                Optional<String> first = lines.findFirst();
                integer = first.map(Integer::new).map(updater).orElseThrow(IllegalAccessError::new);
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
                    bufferedWriter.write(integer.toString());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return integer;
        }

        public synchronized int get() {
            try (Stream<String> lines = Files.lines(path)){
                Optional<String> first = lines.findFirst();
                Optional<Integer> integer = first.map(Integer::new);
                return integer.orElseThrow(IllegalAccessError::new);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public synchronized int incrementAndGet() {
            return updateAndGet(i -> i+1);
        }
    }
}
