package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.edit.Edit;
import com.anniefraz.dissertation.gin.edit.RandomEditFactory;
import com.anniefraz.dissertation.gin.source.AnnaClass;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SimplePatchFactory implements PatchFactory {
    private final RandomEditFactory editFactory;
    private final Supplier<Double> randomNumberGenerator;

    public SimplePatchFactory(RandomEditFactory editFactory, Supplier<Double> randomNumberGenerator) {
        this.editFactory = editFactory;
        this.randomNumberGenerator = randomNumberGenerator;
    }

    @Override
    public Patch getPatchForSourceWithEdits(Source source, int maxNoOfEdits) {
        List<AnnaClass> annaClassList = source.getAnnaClasses();
        List<Edit> edits = new ArrayList<>();
        for (AnnaClass annaClass : annaClassList) {
            for (int i = 0; i <= Double.valueOf(Math.floor(randomNumberGenerator.get() * maxNoOfEdits)).intValue(); i++){
                edits.add(editFactory.getRandomEdit(annaClass));
             }
        }
        return new Patch(source, edits);
    }
}
