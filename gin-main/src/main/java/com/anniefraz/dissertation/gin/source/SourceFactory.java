package com.anniefraz.dissertation.gin.source;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SourceFactory {


    private Path path;

    public SourceFactory(Path path) {
        this.path = path;
    }

    public Source getSourceFromAnnaPath(AnnaPath annaPath) {
        Path annaClassPath = path.resolve(annaPath.toPath());
        List<String> lines = null;
        try {
            lines = Files.readAllLines(annaClassPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new AnnaClass(annaPath, lines);
    }

    public Source getSourceFromAnnaPaths(List<AnnaPath> paths) {

        Map<AnnaPath, Source> sources = paths.stream()
                .map(this::getSourceFromAnnaPath)
                .collect(Collectors.toMap(source -> source.getPaths().get(0), Function.identity()));

        return new AnnaPackage(sources);
    }
}
