package com.anniefraz.dissertation.main.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUserInput {

    private static Logger LOG = LoggerFactory.getLogger(TestUserInput.class);

    public static void main(String[] args) {

        if (args.length < 4){
            LOG.error("There are not enough Parameters");
        }else {
            UserInput userInput = UserInput.getBuilder()
                    .setClassFileName(args[0])
                    .setTestFileName(args[1])
                    .setPackageName(args[2])
                    .setIterations(Integer.parseInt(args[3]))
                    .build();

            System.out.println(userInput);
        }
    }
}
