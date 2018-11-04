import java.util.Random;
import java.io.*;

public class GardenGateProblem {


    static public void main(String[] args) {

        RandomAccessFile admin;
        byte init[] = {0};

        int gatePosition = 0;

        int counter2 = 0;
        int counter = 0 ;

        if (args.length != 1) {
            System.err.println("usage: java {gate_bottom,gate_top}");
        } else {


            if (args[0].compareToIgnoreCase("gate_bottom") == 0) {
                System.out.println("running the bottom gate");
                try {
                    admin = new RandomAccessFile("admin.txt", "rw");
                    admin.seek(0);
                    admin.write(init);
                    admin.close();
                } catch (IOException e) {
                    System.out.println("something wrong with file access" + e);
                }
            } else {
                System.out.println("running the top gate");
                gatePosition = 1;

            }

            try {
                RandomAccessFile adminTop = new RandomAccessFile("admin.txt", "rw");
                adminTop.seek(1);
                adminTop.write(init);

                gate gate = new gate();

                gate.counting(gatePosition);

                adminTop.seek(0);
                counter = adminTop.read();

                adminTop.seek(1);
                counter2 = adminTop.read();
                int result = (counter + counter2);
                System.out.println(result);
                adminTop.close();


            }catch(Exception e){

            }


            //TODO: work out a way so when a gate says that it is done it can then


        }

    }
}
