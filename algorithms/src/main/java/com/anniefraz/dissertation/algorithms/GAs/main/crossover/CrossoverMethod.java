package com.anniefraz.dissertation.algorithms.GAs.main.crossover;

import com.anniefraz.dissertation.gin.patch.Offspring;
import com.anniefraz.dissertation.gin.patch.Patch;
import com.anniefraz.dissertation.gin.source.Source;

import java.util.List;

public interface CrossoverMethod {

    List<Offspring> crossover(List<Patch> parents, Source source);
}
