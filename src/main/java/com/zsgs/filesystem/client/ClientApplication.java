package com.zsgs.filesystem.client;

import com.zsgs.filesystem.service.FileSystemManager;
import java.util.Scanner;

public class ClientApplication {

    private FileSystemManager manager = FileSystemManager.getInstance();
    private Scanner scanner = new Scanner(System.in);

    public void init(){
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
                    case "create": // create file
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
                        System.out.println("Shutting down...");
                        System.exit(0);
                    default:
                        System.out.println("Unknown command.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
