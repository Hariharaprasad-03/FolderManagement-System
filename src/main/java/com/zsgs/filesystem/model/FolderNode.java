package com.zsgs.filesystem.model;

import java.util.ArrayList;
import java.util.List;
import com.zsgs.filesystem.exception.ItemNotFoundException;
import lombok.Getter;
import lombok.Setter;

public class FolderNode implements  Folder{

    @Setter private String name ;
    @Getter
    @Setter private Folder parentNode ;
    private  final List<Folder> children ;


    public FolderNode(){
        this.children = new ArrayList<>() ;
    }

    public int getSize(){
         int size = children.stream().mapToInt(Folder :: getSize).sum();
        return size+1;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {

        if ( parentNode == null){
            return "root";

        }

        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(parentNode.getPath());
        pathBuilder.append("/");
        pathBuilder.append(name);
        return pathBuilder.toString();
    }


    public String toJson() {
        StringBuilder json = new StringBuilder();


        json.append("{");
        json.append("\"type\": \"folder\",");
        json.append("\"name\": \"").append(this.name).append("\",");

        json.append("\"children\": [");

        for (int i = 0; i < children.size(); i++) {
            Folder child = children.get(i);
            json.append(child.toJson());
            if (i < children.size() - 1) {
                json.append(",");
            }
        }
        
        json.append("]");
        json.append("}");
        return json.toString();
    }
    public List<Folder> getChildren(){
        return new ArrayList<>(children);
    }

    public void addItem( Folder child ) {
        children.add(child);
        System.out.println("SuccessFully Added to Folder"+ this.name);
    }

    public void remove(String name) {

        boolean removed = children.removeIf(c-> c.getName().equals(name));
        if( removed ){
            System.out.println(name + "Successfully removed" );
        } else {
            throw  new ItemNotFoundException("Item '" + name + "' not found in current directory.");
        }

    }
    public FolderNode clone(){
        
        FolderNode newFolder = new FolderNode();
        newFolder.setName(this.name);
        newFolder.setParentNode(null);


        for (Folder child : this.children) {
            Folder clonedChild = child.clone();
            newFolder.addItem(clonedChild);
        }
        return newFolder;
    }
        


}
