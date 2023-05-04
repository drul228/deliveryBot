package org.example;

import java.util.*;

public class Main {
    public static final int ROADS = 1000;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static int countOfR = 0;
    public static final int howMuchRis = 0;


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int repeatR(String text) {
        int maxSize = 0;
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < text.length(); j++) {
                if (i >= j) {
                    continue;
                }
                boolean bFound = false;
                for (int k = i; k < j; k++) {
                    if (text.charAt(k) != 'R') {
                        bFound = true;
                        break;
                    }
                }
                if (!bFound && maxSize < j - i) {
                    maxSize = j - i;
                }
            }
        }
        return maxSize;
    }

    public static List<Thread> intialisationThreads(int count) {
        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            threadList.add(new Thread(() -> {
                synchronized (sizeToFreq) {
                    var text = generateRoute("RLRFR", 100);
                    var repeatR = repeatR(text);
                    if (sizeToFreq.containsKey(repeatR)) {
                        var countOfR = sizeToFreq.get(repeatR);
                        sizeToFreq.put(repeatR,countOfR + 1);
                    }
                    sizeToFreq.putIfAbsent(repeatR,1);
                }
            }));
        }
        return threadList;
    }


    public static void main(String[] args) {
        intialisationThreads(ROADS).forEach(thread -> thread.start());
        sizeToFreq.entrySet().stream().sorted(((o1, o2) -> o2.getValue().compareTo(o1.getValue()))).forEach(System.out::println);
    }
}