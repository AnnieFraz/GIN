package com.anniefraz.dissertation.main.input;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserInput {

    private final String classFileName;
    private final String testFileName;
    private final String packageName;
    private final int iterations;
    private final int populationSize;


    private UserInput(UserInputBuilder builder) {
        classFileName = builder.classFileName;
        testFileName = builder.testFileName;
        packageName = builder.packageName;
        iterations = builder.iterations;
        populationSize = builder.populationSize;
    }

    public String getClassFileName() {
        return classFileName;
    }

    public String getTestFileName() {
        return testFileName;
    }

    public String getPackageName() {
        return packageName;
    }

    public int getIterations() {
        return iterations;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("classFileName", classFileName)
                .append("testFileName", testFileName)
                .append("packageName", packageName)
                .append("iterations", iterations)
                .toString();
    }

    public static UserInputBuilder getBuilder(){
        return new UserInputBuilder();
    }

    public static class UserInputBuilder{

        private String classFileName;
        private String testFileName;
        private String packageName;
        private int iterations;
        private int populationSize;

        private UserInputBuilder() {
        }

        public UserInputBuilder setClassFileName(String classFileName) {
            this.classFileName = classFileName;
            return this;
        }

        public UserInputBuilder setTestFileName(String testFileName) {
            this.testFileName = testFileName;
            return this;
        }

        public UserInputBuilder setPackageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public UserInputBuilder setIterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public UserInputBuilder setPopulationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public UserInput build(){
            return new UserInput(this);
        }


    }
}
