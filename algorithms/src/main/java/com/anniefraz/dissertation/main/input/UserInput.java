package com.anniefraz.dissertation.main.input;

public class UserInput {

    private static String classFileName;

    private static String testFileName;

    private static String packageName;

    public UserInput() {

    }

    public static String getClassFileName() {
        return classFileName;
    }

    public static void setClassFileName(String classFileName) {
        UserInput.classFileName = classFileName;
    }

    public static String getTestFileName() {
        return testFileName;
    }

    public static void setTestFileName(String testFileName) {
        UserInput.testFileName = testFileName;
    }

    public static String getPackageName() {
        return packageName;
    }

    public static void setPackageName(String packageName) {
        UserInput.packageName = packageName;
    }

    public static int getIterations() {
        return iterations;
    }

    public static void setIterations(int iterations) {
        UserInput.iterations = iterations;
    }

    private static int iterations;

}
