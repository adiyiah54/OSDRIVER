

package osdriver;


import java.io.*;


public class Loader {
    
        // TODO code application logic here
    
    private FileReader file;
    
    private int jNum;
    private int Size;
    private int pri;
    private int iBuffer;
    private int oBuffer;
    private int tBuffer;
    private String data;
    private int address = 0;
    //Constructor
    public Loader(FileReader file)
    {
        this.file = file;
    }
    
    
    public void load(Disk disk, PCB_Container pcbContainer) throws IOException
    {
        try (BufferedReader br = new BufferedReader(file))
        {
           String sCurrentLine;
           while ((sCurrentLine = br.readLine()) != null) 
           {
              
              //Extract program attributes from // JOB and // Data
              if(sCurrentLine.contains("JOB"))
              { 
                 String[] jSplit = sCurrentLine.split(" ");
                 for(int i = 2; i < jSplit.length; i++)
                 {
                    jNum = Integer.parseInt(jSplit[2]);
                    Size = Integer.parseInt(jSplit[3],16);
                    pri = Integer.parseInt(jSplit[4]);
                 }
                 //System.out.println("jobNUM = " + jNum + " Size = " + Size + " Priority = " + pri);
              }
              if(sCurrentLine.contains("Data"))
              {
                 String[] dSplit = sCurrentLine.split(" ");
                 for(int i = 2; i < dSplit.length; i++)
                 {
                    iBuffer = Integer.parseInt(dSplit[2],16);
                    oBuffer = Integer.parseInt(dSplit[3],16);
                    tBuffer = Integer.parseInt(dSplit[4],16);
                 }
                 pcbContainer.addJobToQueue(jNum, Size, pri, iBuffer, oBuffer, tBuffer, address - Size); //to be able to store correct address, place here.
                 //System.out.println("iBuffer = " + iBuffer + " oBuffer = " + oBuffer + " tBuffer = " + tBuffer);
              }
              if(sCurrentLine.startsWith("//") == false)//if current line doesn't start with "//", add to the disk array
              {
                 data = sCurrentLine;
                 disk.write2Disk(address, data); //Communicate with Jared MemoryManager Code here
                 address++;
                 //System.out.println(data);
              }
              if(sCurrentLine.contains("END")) //store attribute inside PCB here
              {
                 pcbContainer.setBufferSize(iBuffer + oBuffer + tBuffer);
              }
           }
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println("Don't forget to place text data :D");
        }
        
    }
    
}
