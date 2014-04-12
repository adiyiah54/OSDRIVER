
package osdriver;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OSDriver 
{
    
    public static void main(String[] args)
    {
        ArrayList<PCB> readyQueue = new ArrayList<PCB>();
        
        PCB_Container pcbContainer = new PCB_Container();
        Disk disk = new Disk();
        Loader loader;
        Memory ram = new Memory();
        Boolean status_Load2Ram = false;
        
        //Start Load
        FileReader file = null;
        try {        
            file = new FileReader("DataFile2-Jobs1+2.txt");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OSDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        loader = new Loader(file);
        try {        
            loader.load(disk, pcbContainer);
        } catch (IOException ex) {
            Logger.getLogger(OSDriver.class.getName()).log(Level.SEVERE, null, ex);
        }
        //End Load
        
        
         
        /*
         *TEST Disk and PCB_Container
         */
        //System.out.println(disk);
        
        /*
         *TEST PCBs status
         */
        //pcbContainer.printQueue();
        
        
        LongTermScheduler LTS = new LongTermScheduler();
        status_Load2Ram = LTS.load2Ram(disk, ram, pcbContainer, readyQueue);
        
        /*
         *TEST PCBs status
         */
        //pcbContainer.printQueue();
        
        /*
         *TEST Memory
         */
        //ram.printRam();
        
        ShortTermScheduler STS = new ShortTermScheduler();
        STS.FirstComeFirstServe(readyQueue);
        
        /*
        for(int i = 0; i < pcbContainer.getCurrentNumOfJob(); i++)
        {
            STS.toCPU(i, readyQueue);
        }
        */
        
        
        
        //TEST Show current PCBs
        for(PCB pcb : readyQueue)
        {
            System.out.print(pcb);
        }
    }
}
    
    

