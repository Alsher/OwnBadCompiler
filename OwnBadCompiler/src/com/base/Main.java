package com.base;


/*
    Entry point for Java.
    Also used for measuring the overall performance.
 */

public class Main
{

    public static void main(String[] args)
    {
        double starttime, endtime;

        int iterations = 10;

        //warm-up for better benchmarking, set iteration to 0 to prevent console spam
        for (int i=0; i<iterations; i++)
            doStuff();

        starttime = System.nanoTime();
        doStuff();
        endtime = System.nanoTime();

        System.out.println("This operation took " + ((endtime - starttime) / (double)1000000) + " ms.");

    }

    private static void doStuff()
    {
        System.out.println("### Starting reader ###"); System.out.println();
        Reader reader = new Reader("testFile.txt");

        reader.destroy();
        System.out.println();
        System.out.println("### Stopping reader ###");
    }

}
