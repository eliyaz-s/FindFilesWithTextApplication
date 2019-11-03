package com.mycompany.SearchOpreationOnFiles;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    private Path fileToRead;
    private BlockingQueue<Path> queue;

    public Producer(Path filePath, BlockingQueue<Path> q){
        fileToRead = filePath;
        queue = q;
    }

    public void run() {
        try {
            BufferedReader reader = Files.newBufferedReader(fileToRead);
            String line;
            while((line = reader.readLine()) != null){
                try {
                	if(line.contains("Eliyaz")) {
                    queue.put(fileToRead);
                    System.out.println(Thread.currentThread().getName() + " added \"" + line + "\" to queue, queue size: " + queue.size());             
                	break;
                	}
                	} catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

           System.out.println(Thread.currentThread().getName()+" finished");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
