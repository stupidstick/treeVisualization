package com.bebra.treevisualization.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static void main(String[] args) {
        test(100);
        for (int i = 500; i <= 10000; i += 500) {
            test(i);
        }
    }

    public static void test(int n) {
        TestTree<Integer, Integer> tree = new TestTree<>();
        List<Integer> keys = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            Integer key = random.nextInt();
            keys.add(key);
            tree.put(key, 0, () -> {
            });
        }
        int nn = 0;
        AtomicInteger insert = new AtomicInteger();
        AtomicInteger delete = new AtomicInteger();
        AtomicInteger search = new AtomicInteger();
        for (int i = 0; i < n / 2; i++) {
            if (i % 10 == 0) {
                tree.delete(random.nextInt(), delete::incrementAndGet);
                tree.put(keys.get(random.nextInt(keys.size())), 0, insert::incrementAndGet);
                tree.get(random.nextInt(), search::incrementAndGet);
            } else {
                int randI = random.nextInt(keys.size());
                tree.delete(keys.get(randI), delete::incrementAndGet);
                int key = random.nextInt();
                tree.put(key, 0, insert::incrementAndGet);
                keys.set(randI, key);
                tree.get(random.nextInt(keys.size()), search::incrementAndGet);
            }
        }
        System.out.println(tree.size() + ";" +
                (1.39 * (Math.log(n) / Math.log(2))) + ";" +
                insert.doubleValue() / ((double) n / 2) + ";" +
                delete.doubleValue() / ((double) n / 2) + ";" +
                search.doubleValue() / ((double) n / 2)
        );
    }
}
