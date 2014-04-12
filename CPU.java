package osdriver;

import java.util.ArrayList;
import java.util.Stack;

public class CPU 
{
    
    private String[] regs;
    private int programcounter;
    private int inputbuffer;
    private int outputbuffer;
    private int tempbuffer;
    private int size;
    
    public CPU()
    {
      //RAM = new String[1024];
      regs = new String[16];
      programcounter = -1;
      inputbuffer = 0;
      outputbuffer = 0;
      tempbuffer = 0;

    }
    
    public void Execute(String instructions, Memory RAM)
    {
        int[] ins;
        ins = decode(instructions);
        
                switch(ins[1])
        {
            case 0:
            {
                regs[0] = inputbuffer;
                break;
            }
            case 1:
            {
                outputbuffer = regs[0];
                break;
            }
            case 2:
            {
                if (ins[4] != 0) 
                { 
                    regs[ins[2]] = RAM.read(ins[4]); 
                }
                else 
                {
                    regs[ins[2]] = RAM.read(ins[3]);
                }
                break;
            }
            case 3:
            {
                if (ins[4] != 0) { RAM.Write2RAM(ins[4], regs[ins[2]]); }
                else {  RAM.Write2RAM(ins[3], regs[ins[2]]); }
                break;
            }
            case 4:
            {
                if (ins[2] != 0) { regs[ins[4]] = regs[ins[2]]; regs[ins[2]] = 0; }
                else { regs[ins[4]] = regs[ins[3]]; regs[ins[3]] = 0; }
                break;
            }
            case 5:{ regs[ins[4]] = (regs[ins[2]] + regs[ins[3]]); break; }
            case 6:{ regs[ins[4]] = (regs[ins[2]] - regs[ins[3]]); break; }
            case 7:{ regs[ins[4]] = (regs[ins[2]] * regs[ins[3]]); break; }
            case 8:{ regs[ins[4]] = (regs[ins[2]] / regs[ins[3]]); break; }
            case 9:{ regs[ins[4]] = (regs[ins[2]] & regs[ins[3]]); break; }
            case 10:{ regs[ins[4]] = (regs[ins[2]] | regs[ins[3]]); break; }
            case 11:{ regs[ins[3]] = RAM.Read(ins[4]); RAM.Write2RAM(ins[4], 0); break; }
            case 12:{ regs[ins[3]] += RAM.Read(ins[4]); break; }
            case 13:{ regs[ins[3]] *= RAM.Read(ins[4]); break; }
            case 14:{ regs[ins[3]] /= RAM.Read(ins[4]); break; }
            case 15:{ regs[ins[3]] = RAM.Read(ins[4]); break; }
            case 16:
            {
                if(regs[ins[2]] < regs[ins[3]]) { regs[ins[4]] = 1;}
                else{ regs[ins[4]] = 0; }
                break;
            }
            case 17:
            {
                if(regs[ins[2]] < RAM.Read(ins[4])) { regs[ins[3]] = 1;}
                else{ regs[ins[3]] = 0; }
                break;
            }
            case 18:{ End(); break; }
            case 19:{ break; }
            case 20:{ programcounter = ins[2]; break; }
            case 21:{ if (regs[ins[2]] == regs[ins[3]]){ programcounter = ins[4]; } break; }
            case 22:{ if (regs[ins[2]] != regs[ins[3]]){ programcounter = ins[4]; } break; }
            case 23:{ if (regs[ins[2]] == 0){ programcounter = ins[4]; } break; }
            case 24:{ if (regs[ins[2]] != 0){ programcounter = ins[4]; } break; }
            case 25:{ if (regs[ins[2]] > 0){ programcounter = ins[4]; } break; }
            case 26:{ if (regs[ins[2]] < 0){ programcounter = ins[4]; } break; }
            
        }
    }
    public void setProCount(int x)//program counter is set to a new value
    {
      programcounter = x;
    }
    public void setSize(int x)//size of code of process in the cpu for exec
    {
      size = x;
    }
    public void setRegs(int x, int y)//set registers value
    {
      regs[y]= x;
    }
    public int getProCount()//returns program counter value
    {
      return programcounter;
    }
    public int[] getRegs()//returns cpu registers
    {
      return regs;
    }
    public int getSize()//return size of code
    {
      return size;
    }
    public String convert(String str)//converts hex to binary
    {
      int x = 0;
      String bin = "";
      String binary = "";

      for(int i=0; i<str.length();i++)
      {
        String s = str.substring(i,i+1);
        if(s.equals("0"))
          x = 0;
        else{
          x= Integer.parseInt(s,16);
          bin = Integer.toBinaryString(x);
          
          if(bin.length()== 1)
            bin = "000"+bin;
          else if (bin.length() == 2)
            bin = "00" + bin; 
          else if (s.length() == 3)
            bin = "0" + bin;
          binary = binary + bin;  
        }
      }
      return binary;
      
    }
    //dummy
    private int hexToDecimal(String hex)//converts hex to decimal
    {
      String hexDigit ="0123456789ABCDEF";
      int hexVal= 0;
      int x=0;
      for(int i =0; 1<hex.length();i++)
      {
        char c = hex.charAt(i);
        x = hexDigit.indexOf(c);
        hexVal = 16*hexVal + x;
      }
        return hexVal;
    }
    //dummy
    private String decimalToHex(int dec)//converts decimal to hex
    {
      int x =16;
      String hex ="";
      char[] hexChar ={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
      int hexDigit = dec % x;
      hex = hexChar[hexDigit] + hex;
      dec = dec / x;
      return hex;
    }
    //The array of ints from Chuku's example seems like the
    //path of least resistance here, so the decode method
    //should return an array of ints with the right data
    
   /* public int[] Decode(String raw)
    {
    int[] dummy = new int[0];
    return dummy;
    }
    //dummy*/
    public int[] decode(String instr){
    int[] regArray={};//
    int regSlot =0;
    int insType = Integer.parseInt(instr.substring(0,2),2);//type
   
    switch(insType){
      case 0:
        regSlot = 6;//Arithemetic Instruction Format 
        regArray[0]= Integer.parseInt(instr.substring(0,2),2);//type
        regArray[1]= Integer.parseInt(instr.substring(2,8),2);//opcode
        regArray[2]= Integer.parseInt(instr.substring(8,12),2);//s-reg
        regArray[3]= Integer.parseInt(instr.substring(12,16),2);//s-reg
        regArray[4]= Integer.parseInt(instr.substring(16,20),2);//d-reg
        regArray[5]= Integer.parseInt(instr.substring(20),2);//**32 Long**
        break;
      case 1:
        regSlot = 5;//conditional branch and immediate format
        regArray[0]= Integer.parseInt(instr.substring(0,2),2);//type
        regArray[1]= Integer.parseInt(instr.substring(2,8),2);//opcode
        regArray[2]= Integer.parseInt(instr.substring(8,12),2);//b-reg
        regArray[3]= Integer.parseInt(instr.substring(12,16),2);//d-reg
        regArray[4]= Integer.parseInt(instr.substring(16),2);//address
        break;
        
      case 2:
        regSlot = 3;//Unconditional Jump format
        regArray[0]= Integer.parseInt(instr.substring(0,2),2);//type
        regArray[1]= Integer.parseInt(instr.substring(2,8),2);//opcode
        regArray[2]= Integer.parseInt(instr.substring(8),2);//address
        break;
        
      case 3:
        regSlot = 5;//IO format
        regArray[0]= Integer.parseInt(instr.substring(0,2),2);//type
        regArray[1]= Integer.parseInt(instr.substring(2,8),2);//opcode
        regArray[2]= Integer.parseInt(instr.substring(8,12),2);//reg-1
        regArray[3]= Integer.parseInt(instr.substring(12,16),2);//reg-2
        regArray[4]= Integer.parseInt(instr.substring(16),2);// address
        break;  
    }
    int[] regArray2 = new int [regSlot];
    //array copy
    for(int i = 0;i < regArray2.length; i++){
      regArray2[i] = regArray[i];}
    
    return regArray2;
  }
  
    
    
    
    
    //NOTE: Needs scheduler,indirect and direct addressing and dispatcher

    /*private void End()
    {
    
    }*///what were u thinking William??


/* This method assign process with a state of ready to the CPU.This ready queue is based off of the first come, first served scheduling algorithm.*/
boolean dispatchJobs (ArrayList<PCB> readyQueue, Memory RAM)
{

    PCB currentJob;
    int numberOfJobs = readyQueue.size();
    int completedJobs = 0;

    if(numberOfJobs == completedJobs || readyQueue.isEmpty() == true) //if empty return false
    {
        return false; 
    }

    while( numberOfJobs!=completedJobs ) 
    {

        currentJob= readyQueue.get(0);

        /*Grab the process at the top of the queue and change its status to running and assign the process  to the CPU  */
        final int STATUS_RUNNING = 1;
        
        readyQueue.get(0).setStatus(STATUS_RUNNING);
        
        for(int i = 0; i <= currentJob.getMemoryEndIndex(); i++)
        {
            String instructions = RAM.read(currentJob.getMemoryStartIndex());

            /*Execute of process method goes here*/

            Execute(instructions, RAM);
        }
        
        /*After it is complete, change the status to complete and update the completedJobs count */
        final int STATUS_COMPLETE = 3;
        readyQueue.get(0).setStatus(STATUS_COMPLETE);
        completedJobs++;

        /* Remove the completed process from the queue and reorder the queue.  */

        readyQueue.remove(0);
 
    }
    return true;
 
}
 


    
    
}
