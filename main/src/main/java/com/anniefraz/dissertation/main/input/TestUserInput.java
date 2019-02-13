package com.anniefraz.dissertation.main.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUserInput {

    private static Logger LOG = LoggerFactory.getLogger(TestUserInput.class);

    public static void main(String[] args) {
        UserInput userInput = new UserInput();

        if (args.length < 4){
            LOG.error("There are not enough Parameters");
        }else {
            userInput.setClassFileName(args[0]);
            userInput.setTestFileName(args[1]);
            userInput.setPackageName(args[2]);
            userInput.setIterations(Integer.parseInt(args[3]));

            System.out.println(args[0]);
            System.out.println(args[1]);
            System.out.println(args[2]);
            System.out.println(args[3]);
        }
    }
}
