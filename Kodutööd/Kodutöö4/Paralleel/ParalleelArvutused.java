package Kodutööd.Kodutöö4.Paralleel;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParalleelArvutused {
    private static BlockingQueue<String> in;
    private static BlockingQueue<DataHolder> out;
    private static String numbersDirectory = "Numbers";

    public static void main(String[] args) throws InterruptedException {
        int processors = Runtime.getRuntime().availableProcessors();

        File[] folder = new File(numbersDirectory).listFiles();
        assert folder != null;
        List<String> fileNames = Arrays.stream(folder).map(File::getName).toList();

        in = new ArrayBlockingQueue<>(fileNames.size());
        out = new ArrayBlockingQueue<>(fileNames.size());
        in.addAll(fileNames);

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < processors; i++) {
            Thread t = new Thread(new Worker());
            t.start();
            threadList.add(t);
        }

        // Got some feedback about it, but as I am working on fixing stuff during lessons like I always am, I'll leave it for later
        /**
        for (Thread tt : threadList) {
            tt.join();
        }

        DataHolder[] outArray = out.toArray(new DataHolder[out.size()]);

        BigInteger[] sums = Arrays.stream(outArray).map(i -> i.sum).toArray(BigInteger[]::new);
        BigInteger[] maxs = Arrays.stream(outArray).map(i -> i.max).toArray(BigInteger[]::new);
        String[] fileName = Arrays.stream(outArray).map(i -> i.file).toArray(String[]::new);


        BigInteger fullSum = Arrays.stream(sums).reduce(BigInteger::add).get(); // Get the total sum

        BigInteger biggestMax = Arrays.stream(maxs).max(BigInteger::compareTo).get(); // Largest number

        // Sort so binarysearch can be performed
        List<BigInteger> maxsList = new ArrayList<>(Arrays.stream(maxs).toList());
        maxsList.sort(BigInteger::compareTo);
        maxs = maxsList.toArray(BigInteger[]::new);

        int biggestMaxIndex = Arrays.binarySearch(maxs, biggestMax); // Index of that number
        String maxFileName = fileName[biggestMaxIndex]; // Name of that number's file

        BigInteger minSum = Arrays.stream(sums).min(BigInteger::compareTo).get();

        // Sort so binarySearch can be performed
        List<BigInteger> sumList = new ArrayList<>(Arrays.stream(sums).toList());
        sumList.sort(BigInteger::compareTo);
        sums = sumList.toArray(BigInteger[]::new);

        int smallestSumIndex = Arrays.binarySearch(sums, minSum);
        String minSumFileName = fileName[smallestSumIndex];
         */

        BigInteger fullSum = BigInteger.ZERO;
        BigInteger biggestMax = null;
        String maxFileName = "";
        String minSumFileName = "";

        while (threadList.size() > 0) {
            DataHolder holder = out.take();

        }

        System.out.println("Total sum of the numbers: " + fullSum);
        System.out.printf("Biggest number was %s and it was found in file %s%n", biggestMax, maxFileName);
        System.out.println("Smallest sum was in the file " + minSumFileName);

    }

    private static record DataHolder (String file, BigInteger max, BigInteger sum) {}

    private static class Worker implements Runnable {

        @Override
        public void run() throws RuntimeException {
            while (true) {
                // Initialize or reset max and sum values for next file
                BigInteger max = null;
                BigInteger sum = null;

                String fileName = in.poll();

                // If there is no next file stop the cycle
                if (fileName == null) break;

                // Start scanning the file
                try (Scanner reader = new Scanner(Path.of(numbersDirectory, fileName), StandardCharsets.UTF_8)) {
                    while (reader.hasNextBigInteger()) { // Read until next whitespace
                        BigInteger number = reader.nextBigInteger();
                        if (number.compareTo(max) > 0) {
                            max = number;
                        }
                        sum = sum.add(number);
                    }

                } catch (IOException e) {
                    System.out.println(e);
                }
                DataHolder holder = new DataHolder(fileName, max, sum);
                out.add(holder);
            }
        }
    }
}
