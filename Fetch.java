/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package osdriver;

/**
 *
 * @author Wut
 */
public class Fetch {
    //Constructor
    public Fetch()
    {
    }
    
    public String fetch(int programCounter)
    {
        MemoryManager ram = new MemoryMagaer();
        String instruction = "";
        for(int i = 0; i < 4; i++)//8x4= 32 bits long
        {
            instruction = Integer.toBinaryString(ram.read(programCounter));
            
            if(instruction.length() < 8)
            {
                instruction = "0" + instruction;
            }
            programCounter++;
        }
        return instruction;
    }
}


/*With support from the Memory module/method, this method fetches instructions or data from RAM
depending on the content of the CPU’s program counter (PC). On instruction fetch, the PC value should
point to the next instruction to be fetched. The Fetch method therefore calls the Effective-Address
method to translate the logical address to the corresponding absolute address, using the base-register
value and a ‘calculated’ offset/address displacement. The Fetch, therefore, also supports the Decode
method of the CPU.*/