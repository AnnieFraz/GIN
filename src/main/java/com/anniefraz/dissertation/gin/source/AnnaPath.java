package com.anniefraz.dissertation.gin.source;

import sun.tools.java.Imports;

import javax.naming.NameClassPair;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnnaPath {
    private List<String> packages;
    private String className;

    public AnnaPath(List<String> packages, String className) {
        this.packages = packages;
        this.className = className;
    }

    public AnnaPath(String className) {
        this(new ArrayList<>(), className);
    }


    public Path toPath() {

        Path path = null;
        if (!packages.isEmpty()) {
            path = Paths.get(packages.get(0));
            for (int i = 1; i < packages.size(); i++) {
                path = path.resolve(packages.get(i));
            }
        }
        return path == null ? Paths.get(className + ".java") : path.resolve(className + ".java");
    }

    @Override
    public String toString() {
        return "AnnaPath{" +
                "packages=" + packages +
                ", className='" + className + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnaPath annaPath = (AnnaPath) o;
        return Objects.equals(packages, annaPath.packages) &&
                Objects.equals(className, annaPath.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(packages, className);
    }

    public static AnnaPathBuilder getBuilder() {

        return new AnnaPathBuilder(new ArrayList<>());
    }

    public static class AnnaPathBuilder {
        private List<String> packages;
        private String className;


        private AnnaPathBuilder(List<String> packages) {

            this.packages = packages;
        }

        public AnnaPathBuilder addPackage(String packageName) {
            packages.add(packageName);
            return this;
        }

        public AnnaPathBuilder setClassName(String className) {
            this.className = className;
            return this;
        }

        public AnnaPath build() {
            return new AnnaPath(packages, className);
        }
    }
}
