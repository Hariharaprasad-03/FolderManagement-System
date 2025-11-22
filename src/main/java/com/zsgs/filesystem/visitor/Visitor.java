package com.zsgs.filesystem.visitor;

import com.zsgs.filesystem.model.File;
import com.zsgs.filesystem.model.FolderNode;

public interface Visitor {

    public void visit( FolderNode folder);
    public void visit( File file);
}
