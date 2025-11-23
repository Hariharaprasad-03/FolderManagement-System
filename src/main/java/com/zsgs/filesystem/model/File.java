package com.zsgs.filesystem.model;

import lombok.Getter;
import lombok.Setter;

public class File  implements  Folder{

    @Getter
    @Setter private String name ;

    @Setter private int size =1;
    @Setter private FolderNode parentNode ;

    public File() {

    }

    public String getPath(){
        if ( parentNode == null){
            return "root";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(parentNode.getPath());
        sb.append("/");
        sb.append(name);
        return sb.toString();
    }


    @Override
    public int getSize() {
        return this.size;
    }

    public String toJson(){

        return String.format(
                "{ \"type\" : \"file\", \"name\" : \"%s\", \"size\": %d}",
                this.name ,
                this.getSize()
        );

    }

    @Override
    public Folder clone() {
        File file = new File();
        file.setName(this.name);
        file.setSize(this.size);
        file.setParentNode(null);
        return file;
    }
}
