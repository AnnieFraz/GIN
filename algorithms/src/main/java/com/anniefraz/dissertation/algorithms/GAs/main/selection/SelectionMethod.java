package com.anniefraz.dissertation.algorithms.GAs.main.selection;

import com.anniefraz.dissertation.gin.patch.Patch;

import java.util.List;

public interface SelectionMethod {

    List<Patch> select (List<Patch> patches);
}
