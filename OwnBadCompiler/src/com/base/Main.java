package com.base;


/*
    Entry point for Java.
    Also used for measuring the overall performance.
 */

public class Main
{

    public static void main(String[] args)
    {
        String absolutePath = "";
        if(args.length > 0)
            absolutePath = args[0];

//        int iterations = 0;
//
//        //warm-up for better benchmarking, set iteration to 0 to prevent console spam
//        for (int i=0; i<iterations; i++)
//            exec();

        double start = System.nanoTime();
        exec(absolutePath);

        System.out.println("This operation took " + ((System.nanoTime() - start) / (double)1000000) + " ms.");
    }

    private static void exec(String path)
    {
        System.out.println("### Starting reader ###" + (char)10);

        if(!path.equals(""))
            Reader.read(path);
        else
            Reader.read("testFile2.txt");

        System.out.println((char)10 + "### Stopping reader ###");
    }

}
