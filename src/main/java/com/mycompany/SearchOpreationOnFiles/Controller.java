package com.mycompany.SearchOpreationOnFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Stream;

public class Controller {

    private static final int NUMBER_OF_CONSUMERS = 3;
    private static final int NUMBER_OF_PRODUCERS = 3;
    private static final int QUEUE_SIZE = 2;
    private static BlockingQueue<Path> queue;
    private static Collection<Thread> producerThreadCollection, allThreadCollection;

    public static void main(String[] args) throws IOException {
        producerThreadCollection = new ArrayList<Thread>();
        allThreadCollection = new ArrayList<Thread>();
        queue = new LinkedBlockingDeque<Path>(QUEUE_SIZE);
        
        Path dirName = Paths.get("/Users/swathiangirekula/downloads");

        
        try (Stream<Path> paths = Files.find(
        		dirName, Integer.MAX_VALUE,
                (path,attrs) -> attrs.isRegularFile()
                        && path.toString().endsWith(".txt"))) {
            // Consume only the first 5 from the stream:
            paths.forEach(filePath-> createAndStartProducers(filePath));
        }

        
//        try {
//			Files.list(new File(dirName).toPath())
//			   
//			        .forEach(path -> {
//			            System.out.println("path varaiable" +path);
//			            createAndStartProducers(path);
//			            
//			        });
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        createAndStartConsumers();
       

        for(Thread t: allThreadCollection){
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

       System.out.println("Controller finished");
    }

    private static void createAndStartProducers(Path filePath){
    	
     for(int i = 1; i <= NUMBER_OF_PRODUCERS; i++){
            Producer producer = new Producer(filePath, queue);
            Thread producerThread = new Thread(producer,"producer-"+i);
            producerThreadCollection.add(producerThread);
            producerThread.start();
        }
        allThreadCollection.addAll(producerThreadCollection);
    }

    private static void createAndStartConsumers(){
        for(int i = 0; i < NUMBER_OF_CONSUMERS; i++){
            Thread consumerThread = new Thread(new Consumer(queue), "consumer-"+i);
            allThreadCollection.add(consumerThread);
            consumerThread.start();
        }
    }

    public static boolean isProducerAlive(){
        for(Thread t: producerThreadCollection){
            if(t.isAlive())
                return true;
        }
        return false;
    }
    
   
}
