package com.zsgs.filesystem.client;

import com.zsgs.filesystem.exception.InvalidCmtException;
import com.zsgs.filesystem.service.FileSystemManager;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class ClientApplication {

    private final FileSystemManager manager = FileSystemManager.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    public void init()  {
        while (true) {

            System.out.print("\n" + manager.getCurrentPath() + " > ");

            String input = scanner.nextLine().trim();
            String[] parts = input.split(" ", 2);
            String command = parts[0].toLowerCase();
            String arg = (parts.length > 1) ? parts[1] : "";

            try {
                switch (command) {
                    case "ls":
                        manager.listContents();
                        break;
                    case "mkdir":
                        manager.createFolder(arg);
                        break;
                    case "touch": // create file
                        manager.createFile(arg);
                        break;
                    case "cd":
                        manager.changeDirectory(arg);
                        break;
                    case "back":
                        manager.goBack();
                        break;
                    case "root":
                        manager.goToRoot();
                        break;
                    case "save":
                        manager.saveSystem();
                        break;
                    case "exit":
                        scanner.close();
                        System.out.println("Shutting down...");
                        System.exit(0);
                    case "dlt":
                        manager.remove(arg);
                        break;
                    case "size":
                        manager.getFolderSize();
                        break;
                    case "copy":
                        manager.copy(arg);
                        break;
                    case "paste":
                        manager.paste();
                        break;
                    case "search":
                        manager.searchItem(arg);
                    default:
                        throw new InvalidCmtException("Unknown Comment");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
