package com.zsgs.filesystem.visitor;

import com.zsgs.filesystem.model.File;
import com.zsgs.filesystem.model.FolderNode;

public interface Visitor {

     void visit( FolderNode folder);
    void visit( File file);
}
