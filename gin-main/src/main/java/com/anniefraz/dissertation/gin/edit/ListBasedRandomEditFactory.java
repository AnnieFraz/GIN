package com.anniefraz.dissertation.gin.edit;

import com.anniefraz.dissertation.gin.source.AnnaClass;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ListBasedRandomEditFactory implements RandomEditFactory {
    private final List<Function<AnnaClass, Edit>> editFunctions;
    private final Supplier<Double> randomNumberGenerator;

    public ListBasedRandomEditFactory(List<Function<AnnaClass, Edit>> editFunctions, Supplier<Double> randomNumberGenerator) {
        this.editFunctions = editFunctions;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public Edit getRandomEdit(AnnaClass annaClass) {
        int size = editFunctions.size();
        int whichEdit = Double.valueOf(Math.floor(randomNumberGenerator.get() * size)).intValue();
        return editFunctions.get(whichEdit).apply(annaClass);
    }
}
