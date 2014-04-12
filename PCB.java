

package osdriver;


public class PCB {
    private int jobNum;
    private int jobSize;
    private int jobPriority;
    private int inputBuffer;
    private int outputBuffer;
    private int tempBuffer;
    private int diskIndex;
    
    private short[] inputBufferArray;
    private short[] outputBufferArray;
    private short[] tempBufferArray;
    
    private int bufferSize;
    private int memoryStartIndex;
    private int memoryEndIndex;
    private int status;
    
    public final int STATUS_CPU = 1;
    public final int STATUS_IO = 2;
    public final int STATUS_COMPLETE = 3;
    public final int STATUS_READY = 4;
    public final int STATUS_ARRIVAL = 0;
    
    
    
    //Constructor
    public PCB(int jNum, int jSize, int jPriority, int iBuffer, int oBuffer, int tBuffer, int dIndex)
    {
        jobNum = jNum;
        jobSize = jSize;
        jobPriority = jPriority;
        inputBuffer = iBuffer;
        outputBuffer = oBuffer;
        tempBuffer = tBuffer;
        diskIndex = dIndex;
        
        inputBufferArray = new short[iBuffer];
        outputBufferArray = new short[oBuffer];
        tempBufferArray = new short[tBuffer];
    }
    
    //get and set
    public void setBufferSize(int n)
    {
        this.bufferSize = n;
    }
    public int getJobSize()
    {
        return jobSize;
    }
    public int getDiskIndex()
    {
        return diskIndex;
    }
    public int getInputBufferSize()
    {
        return inputBuffer;
    }
    public int getOutputBufferSize()
    {
        return outputBuffer;
    }
    public int getTempBufferSize()
    {
        return tempBuffer;
    }
    public int getDataSize()
    {
        return inputBuffer + outputBuffer + tempBuffer;
    }
    public int getJobNum()
    {
        return jobNum;
    }
    public int getJobPriority()
    {
        return jobPriority;
    }
    public void setInputBuffer(short[] input)
    {
        inputBufferArray = input;
    }
    public void setMemoryStartIndex(int index)
    {
        memoryStartIndex = index;
    }
    public void setMemoryEndIndex(int index)
    {
        memoryEndIndex = index;
    }
    public int getMemoryStartIndex()
    {
        return memoryStartIndex;
    }
    public int getMemoryEndIndex()
    {
        return memoryEndIndex;
    }
    public int setStatus(int currentStatus)
    {
        return status = currentStatus;
    }
    public int getStatus()
    {
        return status;
    }
    
    
    public String toString()
    {
        return "JobID: "+ jobNum + " Priority: " + jobPriority + "\n";
    }
    
}
 