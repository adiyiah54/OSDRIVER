

package osdriver;

import java.lang.*;

public class Memory 
{
    public String[] ram;
    
    
    
    
    public Memory()
    {
        ram = new String[1024];
    }
    
    
    
    
    public void write2Ram(int index, String data)
    {
        ram[index] = data;
    }
    
    public String read(int index)
    {
        return ram[index];
    }
    
    public int readint(int index)
    {
        return Integer.parseInt(ram[index], 2);
    }
    
    public void writeint(int index, int data)
    {
        ram[index] = Integer.toBinaryString(data);
    }
    
    public void printRam()
    {
        for(int i = 0; i < ram.length; i++)
        {
            System.out.println(i +"[ " + ram[i] + " ]\t");
        }
    }
}
