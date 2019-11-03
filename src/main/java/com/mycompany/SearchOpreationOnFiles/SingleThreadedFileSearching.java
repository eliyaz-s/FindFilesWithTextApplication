package com.mycompany.SearchOpreationOnFiles;

/**
 * Hello world!
 *
 */
import java.io.*;
import java.util.concurrent.*;

public class SingleThreadedFileSearching {
	ConcurrentLinkedQueue<String> searchResult = new ConcurrentLinkedQueue<String>();	
	
	public boolean isRoot(File f){
		File roots[] = File.listRoots(); 
		for(File i : roots){
			if(f.getAbsolutePath().equals(i.getAbsolutePath()))
				return true; 
		}
		return false; 
	}
	
	public void search(String homeDirectory, String pattern) {

		ConcurrentLinkedQueue<File> queue = new ConcurrentLinkedQueue<File>();
		File home = new File(homeDirectory);
		if (!home.isDirectory()) {
			System.out.println("directoty " + home);
			return;
		}
		queue.add(home);
		while (queue.isEmpty() == false) {
		
			File current = queue.remove();
			if (current.getAbsolutePath().toLowerCase().contains(pattern.toLowerCase()))
				searchResult.add(current.getAbsolutePath());
			if (current.isDirectory() || isRoot(current)) {
					File childern[] = current.listFiles();
					if(childern != null)
						for (File c : childern) 
								queue.add(c);
			}
		}
	}
	
	public static void main(String[] args) {
		SingleThreadedFileSearching search = new SingleThreadedFileSearching(); 
		System.out.println("found result "); 
	
		search.search("/Users/swathiangirekula/downloads", "eliyaz"); 
		for(String s : search.searchResult)
			System.out.println("found result "+ s); 
	}
}