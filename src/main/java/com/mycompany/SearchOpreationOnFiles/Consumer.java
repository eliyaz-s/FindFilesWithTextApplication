package com.mycompany.SearchOpreationOnFiles;


import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable{
    private BlockingQueue<Path> queue;

    public Consumer(BlockingQueue<Path> q){
        queue = q;
    }

    public void run(){
        while(true){
            Path line = queue.poll();

            if(line == null && !Controller.isProducerAlive())
                return;

            if(line != null){
            	 
                System.out.println("string availbale in path "+line);
                //Do something with the line here like see if it contains a string
            
            }

        }
    }
}