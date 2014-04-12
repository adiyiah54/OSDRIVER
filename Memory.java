

package osdriver;


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
    
    public void printRam()
    {
        for(int i = 0; i < ram.length; i++)
        {
            System.out.println(i +"[ " + ram[i] + " ]\t");
        }
    }
}
