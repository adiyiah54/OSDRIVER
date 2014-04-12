/**
 * The long-term scheduler is responsible for picking the jobs from job spool
 * and into RAM. In doing so it creates PCB entries for each job and added to 
 * the ready queue.
 */


package osdriver;

import java.util.ArrayList;

public class LongTermScheduler 
{
    //public ArrayList<PCB> readyQueue;
    PCB pcb;
    
    private final int READY = 4;
    
    private int jobSize,  jobIBSize, jobOBSize,  jobTBSize;
    private int index = 0;
    private int jobStart, currentJob, dataSize;
    
    
    
    /**
     * Default constructor
     */
    public LongTermScheduler() 
    {
        //readyQueue= new ArrayList<PCB>();
        currentJob = 0;
    }

    

    /**
     * This loads all jobs to RAM through checking the amount of memory left
     * 
     * @param disk
     * @param ram
     * @param pcbQueue
     * @param status
     * @return 
     */
    public boolean load2Ram(Disk disk, Memory ram, PCB_Container pcbQueue, ArrayList<PCB> readyQueue) 
    {
        //Current Job, starting from 0, is not highter than the total job in the queue.
        while (currentJob < pcbQueue.getCurrentNumOfJob()) 
        {

            pcb = pcbQueue.getJob(currentJob); //grab the current job from the queue starting from job 0;
            jobStart = pcb.getDiskIndex(); //where the job start in the disk index
            jobSize = pcb.getJobSize(); //current job size
            jobIBSize = pcb.getInputBufferSize(); //current input buffer size 
            jobOBSize = pcb.getOutputBufferSize(); // current output buffer size
            jobTBSize = pcb.getTempBufferSize(); // current temp buffer size
            dataSize = pcb.getDataSize();// current buffer size;

            System.out.println("Starting write to ram...");
            
            int startingBufferIndex = jobStart + jobSize;  //starting buffer location
            pcb.setMemoryStartIndex(index); //starting from 0 of ram index

            for ( int i = jobStart; i < startingBufferIndex; i++ ) //p starting current job starting index to end of job index
            {
                String instructionBits = getBinaryData(i, disk); //use getBinaryData which return a string of binary converted from hex string
                
                short binaryBits1 = Short.valueOf(instructionBits.substring(0,8), 2); // from 0 to 7 substring, converted into short decimal 
                
                ram.write2Ram(index++, binaryBits1); //write into the ram array

                short binaryBits2 = Short.valueOf(instructionBits.substring(8,16), 2);
                
                ram.write2Ram(index++, binaryBits2);

                short binaryBits3 = Short.valueOf(instructionBits.substring(16,24), 2);
                
                ram.write2Ram(index++, binaryBits3);

                short binaryBits4 = Short.valueOf(instructionBits.substring(24,32), 2);
                
                ram.write2Ram(index++, binaryBits4);

            }
                
            short[] temp = new short[dataSize*4]; //temporary store data buffer and expand 4 time cause short can't store 32 range all at once

            String dataBits; // String for buffer data
            int dataStart = pcb.getDiskIndex() + pcb.getJobSize(); // add disk index and job size to obtain the buffer starting index

            
            int z = 0; //index
            while ( z < dataSize * 4 ) 
            {
                dataBits = getBinaryData(dataStart++, disk); 
                
                temp[z++] = Short.valueOf(dataBits.substring( 0,8 ), 2);
                temp[z++] = Short.valueOf(dataBits.substring( 8,16 ), 2);
                temp[z++] = Short.valueOf(dataBits.substring( 16,24 ), 2);
                temp[z++] = Short.valueOf(dataBits.substring( 24,32 ), 2);
            }

            pcb.setInputBuffer( temp ); //fill the inputBuffer array in the pcb with short binary for inputBuffer
            pcb.setMemoryEndIndex( index ); //once all the instruction are added index the end of the instruction memory
            
            pcb.setStatus(READY); //set ready status to the pcb

            //Add job to the ready queue and record the time.
            readyQueue.add(pcb);

            currentJob++; //next pcb or job in the pcbQueue
            System.out.println( "Added job: " + currentJob + " from disk index: " + pcb.getMemoryStartIndex() + "-" + pcb.getMemoryEndIndex() + "\n" );
            
            

        }
        
        System.out.println("Finish Loading job into ram");
        
        return true;//signal OSDriver
    }

    
    
    public String getBinaryData(int index, Disk disk) 
    {
        //System.out.println("Index: " + index);
        String hexString = disk.readDiskData(index);
       
        // Strip of the 0x prefix
        hexString = hexString.substring(2,10);

        // Print the hexString after the stripping
        //System.out.println("Adding hexString: " + hexString);

        long t = Long.parseLong(hexString, 16);
        
        System.out.println("Binary Long: " + t);
        
        String bits = Long.toBinaryString(t);
        
        // convenrt binaryBits to to a string of bits
        

        int length = bits.length();

        if (length < 32) 
        {
            int diff = 32 - length;
            for (int i = 0; i < diff; i++) 
            {
                bits = "0" + bits;
            }
        }
        System.out.println("Binary String: " + bits);
        return bits;
    }
}
