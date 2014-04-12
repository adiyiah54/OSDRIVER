

package osdriver;


public class Memory 
{
    public int[] ram;
    
    
    
    
    public Memory()
    {
        ram = new int[1024];
    }
    
    
    
    
    public void write2Ram(int index, int data)
    {
        ram[index] = data;
    }
    
    
    public void printRam()
    {
        for(int i = 0; i < ram.length; i++)
        {
            System.out.println(i +"[ " + ram[i] + " ]\t");
        }
    }
}
