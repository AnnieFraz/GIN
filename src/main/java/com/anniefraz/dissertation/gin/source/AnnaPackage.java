package com.anniefraz.dissertation.gin.source;

import com.anniefraz.dissertation.gin.edit.Edit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnnaPackage implements Source {
    private Map<AnnaPath, Source> sources;

    public AnnaPackage(Map<AnnaPath, Source> sources) {
        this.sources = sources;
    }

    @Override
    public List<AnnaPath> getPaths() {
        return new ArrayList<>(sources.keySet());
    }

    @Override
    public void apply(Edit edit) {
        sources.values().forEach(source -> source.apply(edit));
    }

    @Override
    public List<AnnaClass> getAnnaClasses() {
        return sources
                .values()
                .stream()
                .map(Source::getAnnaClasses)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Source clone() {

        return new AnnaPackage(sources.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().clone())));
    }
}
