package phonebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;

public class Main {
    static int index; //kind of a pointer for quick sort

    public static int linear_search(String[][] detail) {
        int c = 0;
        for (int i = 0; i < detail[2].length; i++) {
            for (int j = 0; j < detail[0].length; j++) {
                if (detail[0][j].equals(detail[2][i])) {
                    c++;
                    break;
                }
            }
        }
        return c;
    }

    static String[][] bubbleSort(String[][] detail) {
        for (int i = 0; i < detail[0].length - 1; i++) {
            for (int j = i + 1; j < detail[0].length; j++) {
                if (detail[0][i].compareTo(detail[0][j]) > 0) {

                    String temp1 = detail[0][i];
                    detail[0][i] = detail[0][j];
                    detail[0][j] = temp1;

                    String temp2 = detail[1][i];
                    detail[1][i] = detail[1][j];
                    detail[1][j] = temp2;
                }
            }
        }
        return detail;
    }

    static int jumpSearch(String[][] detail) {
        int c = 0;
        int jump = (int) Math.floor(Math.sqrt(detail[0].length));
        int last = detail[0].length - 1;
        for (int i = 0; i < detail[2].length; i++) {
            int prev = 0;
            int curr = jump - 1;
            while (curr < last) {

                if (detail[0][curr].compareTo(detail[2][i]) < 0) {
                    prev = curr;
                    curr = Math.min(curr + jump, last);
                } else {
                    break;
                }
            }
            for (int j = curr; j > prev; j--) {
                if (detail[0][j].compareTo(detail[2][i]) == 0) {
                    c++;
                    break;
                }
            }
        }
        return c;
    }

    static String[][] partition(String[][] detail, int start, int end) {
        int mid = start + (end - start) / 2;
        String pivot = detail[0][mid];
        index = start - 1;
        for (int j = start; j <= end; j++) {
            if (detail[0][j].compareTo(pivot) < 0) {
                index++;
                String temp1 = detail[0][index];
                detail[0][index] = detail[0][j];
                detail[0][j] = temp1;
                String temp2 = detail[1][index];
                detail[1][index] = detail[1][j];
                detail[1][j] = temp2;
                //beause in case of swapping with mid the index of original pivot changes so we need to change value of mid to get the correct index
                if (index == mid) {
                    mid = j;
                }
            }
        }
        String temp1 = detail[0][index + 1];
        detail[0][index + 1] = detail[0][mid];
        detail[0][mid] = temp1;
        String temp2 = detail[1][index + 1];
        detail[1][index + 1] = detail[1][mid];
        detail[1][mid] = temp2;
        return detail;
    }

    static String[][] quickSort(String[][] detail, int start, int end) {

        while (start < end) {

            detail = partition(detail, start, end);
            int part = index + 1;


            if ((part - 1 - start) <= (end - (part + 1))) {
                quickSort(detail, start, part - 1);
                start = part + 1;
            } else {
                quickSort(detail, part + 1, end);
                end = part - 1;
            }
        }
        return detail;
    }

    static int binarySearch(String[][] detail) {
        int c = 0;
        for (int i = 0; i < detail[2].length; i++) {
            int start = 0;
            int end = detail[0].length - 1;
            int mid = start + (end - start) / 2;
            while (mid >= start && mid <= end) {
                if (detail[0][mid].compareTo(detail[2][i]) < 0) {
                    start = mid + 1;
                } else if (detail[0][mid].compareTo(detail[2][i]) > 0) {
                    end = mid - 1;
                } else {
                    c++; //found
                    break;
                }
                mid = start + (end - start) / 2;
            }
        }
        return c;
    }

    static int hashsearch(String[][] detail, Hashtable<String, String> tab) {
        int c = 0;
        for (int i = 0; i < detail[2].length; i++) {
            if (tab.containsKey(detail[2][i])) {
                c++;
            }
        }
        return c;
    }

    static String msToTime(int ms) {
        String t;
        int min = ms / 60000;
        ms %= 60000;
        int sec = ms / 1000;
        ms %= 1000;
        t = min + " min. " + sec + " sec. " + ms + " ms.";
        return t;
    }

    public static void main(String[] args) throws FileNotFoundException {
        // Extracting file data to ArrayLists
        long data_extract_time1 = System.currentTimeMillis();
        Scanner s = new Scanner(new File("directory.txt"));
        Scanner s_list = new Scanner(new File("find.txt"));
        ArrayList<String> list = new ArrayList<>(); //To store the full name directory
        ArrayList<String> pno = new ArrayList<>(); // To store the phonenumbers of the corresponding names
        ArrayList<String> sname = new ArrayList<>(); // To store names of people to be searched
        String str = "";
        int c = 0;
        while (s.hasNext()) {
            String word = s.next();
            if ((word.charAt(0) >= 48) && (word.charAt(0)) <= 57) {
                if (c != 0) {
                    list.add(str.trim());
                }
                pno.add(word);
                str = "";
            } else {
                str = str + word + " ";
                c++;
            }
        }
        list.add(str.trim());
        s.close();
        while (s_list.hasNextLine()) {
            sname.add(s_list.nextLine());
        }
        s_list.close();
        long data_extract_time2 = System.currentTimeMillis();

        //Analysis Start

        int cases, time;// Cases to store how many entries found, time for total time in each case
        int data_extract_time = (int) (data_extract_time2 - data_extract_time1);

        // Linear Search Analysis

        System.out.println("Start searching (linear search)...");

        long array_making_time1 = System.currentTimeMillis();// To determine time taken in converting arraylists to array.
        String[][] detail_linear = new String[3][list.size()]; // 2D array to store names number and search names
        detail_linear[0] = list.toArray(new String[0]);
        detail_linear[1] = pno.toArray(new String[0]);
        detail_linear[2] = sname.toArray(new String[0]);
        long array_making_time2 = System.currentTimeMillis();
        int array_making_time = (int) (array_making_time2 - array_making_time1);

        long lineartime1 = System.currentTimeMillis();

        cases = linear_search(detail_linear);

        long lineartime2 = System.currentTimeMillis();

        int lineartime = (int) (lineartime2 - lineartime1);
        time = lineartime + array_making_time + data_extract_time;
        System.out.println("Found " + cases + " / " + detail_linear[2].length + " entries. Time taken: " + msToTime(time));

        System.out.println();


        // Quick Sort + Jump Search Analysis

        System.out.println("Start searching (quick sort + jump search)...");

        array_making_time1 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again

        String[][] detail_quickjump = new String[3][list.size()];
        detail_quickjump[0] = list.toArray(new String[0]);
        detail_quickjump[1] = pno.toArray(new String[0]);
        detail_quickjump[2] = sname.toArray(new String[0]);

        array_making_time2 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again
        array_making_time = (int) (array_making_time2 - array_making_time1); //Variable already defined in linear search, we can use it again

        long quicktime_withjump1 = System.currentTimeMillis();

        detail_quickjump = quickSort(detail_quickjump, 0, detail_quickjump[0].length - 1);

        long quicktime_withjump2 = System.currentTimeMillis();
        int quicktime_withjump = (int) (quicktime_withjump2 - quicktime_withjump1);

        long jumptime_withquick1 = System.currentTimeMillis();

        cases = jumpSearch(detail_quickjump);

        long jumptime_withquick2 = System.currentTimeMillis();
        int jumptime_withquick = (int) (jumptime_withquick2 - jumptime_withquick1);

        time = jumptime_withquick + quicktime_withjump + array_making_time + data_extract_time;

        System.out.println("Found " + cases + " / " + detail_quickjump[2].length + " entries. Time taken: " + msToTime(time));
        System.out.println("Sorting time: " + msToTime(quicktime_withjump));
        System.out.println("Searching time: " + msToTime(jumptime_withquick));

        System.out.println();


        // Quick Sort + Binary Search Analysis

        System.out.println("Start searching (quick sort + binary search)...");

        array_making_time1 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again

        String[][] detail_quickbinary = new String[3][list.size()];
        detail_quickbinary[0] = list.toArray(new String[0]);
        detail_quickbinary[1] = pno.toArray(new String[0]);
        detail_quickbinary[2] = sname.toArray(new String[0]);

        array_making_time2 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again
        array_making_time = (int) (array_making_time2 - array_making_time1); //Variable already defined in linear search, we can use it again

        long quicktime1 = System.currentTimeMillis();

        detail_quickbinary = quickSort(detail_quickbinary, 0, detail_quickbinary[0].length - 1);

        long quicktime2 = System.currentTimeMillis();
        int quicktime = (int) (quicktime2 - quicktime1);

        long binarytime1 = System.currentTimeMillis();

        cases = binarySearch(detail_quickbinary);

        long binarytime2 = System.currentTimeMillis();
        int binarytime = (int) (binarytime2 - binarytime1);

        time = binarytime + quicktime + array_making_time + data_extract_time;

        System.out.println("Found " + cases + " / " + detail_quickbinary[2].length + " entries. Time taken: " + msToTime(time));
        System.out.println("Sorting time: " + msToTime(quicktime));
        System.out.println("Searching time: " + msToTime(binarytime));

        System.out.println();

        // Hash Table Analysis
        System.out.println("Start searching (hash table)...");

        array_making_time1 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again

        String[][] detail_hash = new String[3][list.size()];
        detail_hash[0] = list.toArray(new String[0]);
        detail_hash[1] = pno.toArray(new String[0]);
        detail_hash[2] = sname.toArray(new String[0]);

        array_making_time2 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again
        array_making_time = (int) (array_making_time2 - array_making_time1); //Variable already defined in linear search, we can use it again

        long hashcreatetime1 = System.currentTimeMillis();

        Hashtable<String, String> data_hash = new Hashtable<>(detail_hash[0].length, 0.5f);
        for (int k = 0; k < detail_hash[0].length; k++) {
            data_hash.put(detail_hash[0][k], detail_hash[1][k]);
        }

        long hashcreatetime2 = System.currentTimeMillis();
        int hashcreatetime = (int) (hashcreatetime2 - hashcreatetime1);


        long hashsearchtime1 = System.currentTimeMillis();

        cases = hashsearch(detail_hash, data_hash);

        long hashsearchtime2 = System.currentTimeMillis();
        int hashsearchtime = (int) (hashsearchtime2 - hashsearchtime1);

        time = hashsearchtime + hashcreatetime + array_making_time + data_extract_time;

        System.out.println("Found " + cases + " / " + detail_quickbinary[2].length + " entries. Time taken: " + msToTime(time));
        System.out.println("Creating time: " + msToTime(hashcreatetime));
        System.out.println("Searching time: " + msToTime(hashsearchtime));

        System.out.println();


        //Bubble Sort + Jump Search Analysis

        System.out.println("Start searching (bubble sort + jump search)...");

        array_making_time1 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again

        String[][] detail_bubblejump = new String[3][list.size()];
        detail_bubblejump[0] = list.toArray(new String[0]);
        detail_bubblejump[1] = pno.toArray(new String[0]);
        detail_bubblejump[2] = sname.toArray(new String[0]);

        array_making_time2 = System.currentTimeMillis(); //Variable already defined in linear search, we can use it again
        array_making_time = (int) (array_making_time2 - array_making_time1); //Variable already defined in linear search, we can use it again

        long bubbletime1 = System.currentTimeMillis();

        detail_bubblejump = bubbleSort(detail_bubblejump);

        long bubbletime2 = System.currentTimeMillis();
        int bubbletime = (int) (bubbletime2 - bubbletime1);

        long jumptime1 = System.currentTimeMillis();

        cases = jumpSearch(detail_bubblejump);

        long jumptime2 = System.currentTimeMillis();
        int jumptime = (int) (jumptime2 - jumptime1);

        time = jumptime + bubbletime + array_making_time + data_extract_time;

        System.out.println("Found " + cases + " / " + detail_bubblejump[2].length + " entries. Time taken: " + msToTime(time));
        System.out.println("Sorting time: " + msToTime(bubbletime));
        System.out.println("Searching time: " + msToTime(jumptime));

        System.out.println();


    }
}
