package com.anniefraz.dissertation.gin.patch;

import com.anniefraz.dissertation.gin.source.Source;

public interface PatchFactory {
    Patch getPatchForSourceWithEdits(Source source, int i);

}
