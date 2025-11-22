package com.zsgs.filesystem.visitor;

import com.zsgs.filesystem.model.File;
import com.zsgs.filesystem.model.Folder;
import com.zsgs.filesystem.model.FolderNode;

import java.util.ArrayList;
import java.util.List;

public class SearchVisitor implements  Visitor{

    private final String searchTerm ;
    private final List<Folder> results  = new ArrayList<>();

    public SearchVisitor( String searchTerm){
        this.searchTerm = searchTerm;
    }

    public void visit(FolderNode folder){
        if ( folder.getName().contains(searchTerm)){
            results.add(folder);
        }
    }

    public void visit(File file){
        if ( file.getName().contains(searchTerm)) {
            results.add(file);
        }
    }
}
