import java.io.RandomAccessFile;


import java.util.Random;
import java.io.*;

public class gate {

    RandomAccessFile admin;


    public gate() //throws FileNotFoundException{
    {
        try {
            admin = new RandomAccessFile("admin.txt", "rw");
        }
        catch(Exception e){
            System.out.println(e);
        }


    }

    public void counting(int gatePosition) //throws IOException{
    {
        try {
            int counter;
            // admin.seek(0);

            for (int i =0; i < 50; i++) {


                // System.out.println(i);
                //admin.seek(gatePosition);
                admin.seek(gatePosition);

                counter = admin.read();
                Thread.sleep((int)(Math.random() *500 +0)); //<-Task 2


                counter = counter + 1;

                System.out.println(counter);

                admin.seek(gatePosition);
                admin.write(counter);
                Thread.sleep((int)(Math.random() *500 +0));
            }
            admin.close();

        }
        catch (Exception e){
            System.out.println(e);
        }
    }

}