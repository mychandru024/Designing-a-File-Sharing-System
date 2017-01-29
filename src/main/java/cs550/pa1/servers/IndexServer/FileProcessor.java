package cs550.pa1.servers.IndexServer;

import cs550.pa1.helpers.Util;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by Ajay on 1/28/17.
 */
public class FileProcessor{
     int fileUnlocked = 0;
     

    public FileProcessor() {

    }
    public  synchronized  void registry(String peerID, String filename) throws InterruptedException {
        while(fileUnlocked == 1) wait(2000);
        lockIndexFile();
        registry(peerID,filename,"file");
        unlockIndexFile();

    }
    public synchronized void lookup(String text, PrintWriter out) throws InterruptedException{
        while(fileUnlocked == 1) wait(2000);
        lockIndexFile();
        List<String> results = search(text);
        for (String i:results)
            out.print(i);

        unlockIndexFile();
    }


    public void registry(String loc,String portRequested,String type) {
        Util.appendDataToFile(loc+", "+portRequested+"\n");
    }


    public List<String> search(String text) {
        return Util.searchInFile(text);
    }

    private void lockIndexFile(){

        fileUnlocked = 1;
    }


    private void unlockIndexFile(){
        fileUnlocked = 0;
        notify();
    }
}

