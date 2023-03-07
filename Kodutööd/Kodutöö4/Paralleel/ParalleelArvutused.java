package Kodutööd.Kodutöö4.Paralleel;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ParalleelArvutused {
    private static BlockingQueue<String> in;
    private static BlockingQueue<DataHolder> out;
    private static String numbersDirectory = "Numbers/";

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
        for (Thread tt : threadList) {
            tt.join();
        }

        DataHolder[] outArray = out.toArray(new DataHolder[out.size()]);

        BigInteger[] sums = Arrays.stream(outArray).map(DataHolder::getSum).toArray(BigInteger[]::new);
        BigInteger[] maxs = Arrays.stream(outArray).map(DataHolder::getMax).toArray(BigInteger[]::new);
        String[] fileName = Arrays.stream(outArray).map(DataHolder::getFile).toArray(String[]::new);


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

        System.out.println("Total sum of the numbers: " + fullSum.toString());
        System.out.printf("Biggest number was %s and it was found in file %s%n", biggestMax.toString(), maxFileName);
        System.out.println("Smallest sum was in the file " + minSumFileName);

    }

    private static class DataHolder {
        private String file;
        private BigInteger max;
        private BigInteger sum;

        public DataHolder(String file, BigInteger max, BigInteger sum) {
            this.file = file;
            this.max = max;
            this.sum = sum;
        }

        public String getFile() {
            return file;
        }

        public BigInteger getMax() {
            return max;
        }

        public BigInteger getSum() {
            return sum;
        }
    }

    private static class Worker implements Runnable {

        @Override
        public void run() throws RuntimeException {
            while (true) {
                // Initialize or reset max and sum values for next file
                BigInteger max = BigInteger.ZERO;
                BigInteger sum = BigInteger.ZERO;

                String fileName = in.poll();

                // If there is no next file stop the cycle
                if (fileName == null) break;

                File file = new File(numbersDirectory + fileName);

                // Start scanning the file
                try (Scanner reader = new Scanner(file.toPath(), StandardCharsets.UTF_8)) {
                    while (reader.hasNext()) { // Read until next whitespace
                        BigInteger number = new BigInteger(reader.next());
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
