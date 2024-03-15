package lab1;

import java.util.*;

public class ThreadedPrime {
    public static void main(String[] args) {
        ThreadedPrime threadedPrime = new ThreadedPrime();
        List<Integer> primes = threadedPrime.getListOfPrimes(4,100);
        threadedPrime.printListOfPrimes(primes);
    }

    private List<Integer> getListOfPrimes(int numberOfThreads, Integer maxNumber) {
        int elementsPerThread = maxNumber/numberOfThreads;
        //long now = System.currentTimeMillis(); // I used this to measure speed, ignore. -Nathan
        List<Integer> primeList = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < numberOfThreads; i++) {
            int listEnd = (elementsPerThread)*(i+1);
            int counter = i;
            Thread newThread = new Thread(() -> {
                for (int x = 2+(elementsPerThread*counter); x < listEnd; x++){

                    int flag = 0;
                    int upperBound = x/2;

                    for (int y = 2; y <= upperBound; y++){
                        if(x% y == 0) {
                            flag = 1;
                            break;
                        }
                    }

                    if(flag == 0) {
                        primeList.add(x);
                    }
                }
            });
            threads.add(newThread);
            newThread.start();

        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //System.out.println((System.currentTimeMillis() - now)); // measuring runtime lol
        return new ArrayList<>(primeList);
    }

    private void printListOfPrimes(List<Integer> primes) {
        Collections.sort(primes);
        int counter = 1;
        for (Integer element : primes) {
            String suffix = ", ";
            if (counter==10){
                suffix = ", \n";
                counter = 0;
            }
            System.out.print(element+suffix);
            counter++;
        }
    }}