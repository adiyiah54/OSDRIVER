

package osdriver;

import java.util.Stack;

public class PCB_Container {
    
    private Stack<PCB> jobQueue;    //The PCB Container
    private int countJob;   //Count Number of PCB in addJobToQueue()
    private PCB pcb;
    
    //Constructor
    public PCB_Container()
    {
        jobQueue = new Stack<PCB>();
        countJob = 0;
    }
    
    //Function
    public void addJobToQueue(int jobNum, int jobSize, int jobPriority, int inputBuffer, int outputBuffer, int tempBuffer, int address)
    {
        pcb = new PCB(jobNum, jobSize, jobPriority, inputBuffer, outputBuffer, tempBuffer, address);
        jobQueue.add(pcb);
        countJob++;
    }
    
    //get and set
    public void setBufferSize(int n)
    {
        pcb.setBufferSize(n);
    }
    public int getJobSize(PCB pcb)
    {
        return pcb.getJobSize();
    }
    
    
    
    public int getCurrentNumOfJob()
    {
        return countJob;
    }
    //Select PCB from PCB_Container
    public PCB getJob(int n)
    {
        return jobQueue.elementAt(n);
    }  
    
    public void printQueue()
    {
        for(PCB pcb : jobQueue)
        {
            System.out.println("[" + "JobID: " + pcb.getJobNum() + " JobStatus: " + pcb.getStatus() + " JobSize: " + pcb.getJobSize()+ "]");
        }
    }
}
