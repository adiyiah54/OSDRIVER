
package osdriver;


public class Disk 
{
    
    private String[] disk;
    
    
    //Constructor
    public Disk()
    {
        disk = new String[500];
    }
    
    
    //Function
    public void write2Disk(int address, String data)
    {
        disk[address] = data;
    }
    
    public String readDiskData(int index)
    {
        return disk[index];
    }
    
    @Override
    public String toString()
    {
        String s = new String();
        for (String disk1 : disk) 
        {
            System.out.println("[ " + disk1 + " ]");
            s = disk1;
        }
        return s;
    }
}
