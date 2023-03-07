package Praktikumid.Praktikum7;

import java.util.ArrayList;

public class yl2 {
    private static ArrayList<Integer> empty = new ArrayList();

    static class Worker implements Runnable {
        @Override
        public void run() {
            synchronized (empty) {
                for (int i = 0; i < 1000000; i++) {
                    empty.add(i);
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Worker());
        Thread t2 = new Thread(new Worker());
        t1.start();
        t2.start();

        // Wait until done
        t1.join();
        t2.join();
        System.out.println(empty.size());
    }
}

