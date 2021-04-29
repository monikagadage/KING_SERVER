package com.KingIsDdServer;

import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Utility {

	
	public static boolean readFile(Boolean exitGame) throws InterruptedException, IOException {
		boolean canBreakP1 = false;
		boolean canBreakP2 = false;
		String lineP1,lineP2;
		System.out.println("in 2");
		try {
			
			FileInputStream fInP1 = new FileInputStream("/tmp/FromP1");
			BufferedReader bReadP1 = new BufferedReader(new InputStreamReader(fInP1));
			FileInputStream fInP2 = new FileInputStream("/tmp/FromP2");
			BufferedReader bReadP2 = new BufferedReader(new InputStreamReader(fInP2));

			System.out.println("in 3");
			while (!exitGame) {

				lineP1 = bReadP1.readLine();
				lineP2 = bReadP2.readLine();
				if ( (lineP1 == null || lineP1.isEmpty() ) && (lineP2 == null || lineP2.isEmpty())) {				
					Thread.sleep(3000);
					continue;
				}
				if(lineP1 != null) {
					canBreakP1 = parseMessage(lineP1, "P1");
					clearTheFile("/tmp/FromP1");
					fInP1.getChannel().position(0);
					bReadP1 = new BufferedReader(new InputStreamReader(fInP1));
					if(canBreakP1)
						Utility.writeFile("/tmp/ToP1", "Game End", "P1");
				}
				if(lineP2 != null) {
					canBreakP2 = parseMessage(lineP2, "P2");
					clearTheFile("/tmp/FromP2");
					fInP1.getChannel().position(0);
					bReadP2 = new BufferedReader(new InputStreamReader(fInP1));
					if(canBreakP2)
						Utility.writeFile("/tmp/ToP2", "Game End", "P2");
				}
				
				if(canBreakP1 && canBreakP2)
						break;
				
				
			}
			bReadP1.close();
			bReadP2.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return exitGame;
	}

	public static void clearTheFile(String fileName) throws IOException {
        FileWriter fwOb = new FileWriter(fileName, false); 
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

	public static Boolean parseMessage(String line, String playerName) {
		
		if(line.equals("END")) {
			return true;
		}
		else{
			System.out.println("Message from " +  playerName + "--> " + line);
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	public static void writeFile(String filePath, String message, String playerName) {
		try {
		
			Path path = Paths.get(filePath);
			OutputStream outputStream = Files.newOutputStream(path, WRITE);
			outputStream.write(message.getBytes());
			outputStream.close();
			System.out.println("Player "+ playerName + " has ended");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
