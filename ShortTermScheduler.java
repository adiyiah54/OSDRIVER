
package osdriver;

import java.util.*;

public class ShortTermScheduler{
        
        //Instance variables
        private PCB pcb;
        //public static ArrayList<Short> regQueue;
        int jobID;
        private final int LOADED = 1;
        
        public ShortTermScheduler(){
            
        }

        /**This sorts jobs using priority for easy allocation of the CPU and puts
         * the highest priority at the top of the ready queue.
         *
         */
        public void FirstComeFirstServe(ArrayList<PCB> readyQueue) 
        {
            //Sort readyQueue in priority order
            int n = readyQueue.size();
            
            boolean doMore = true;
            
            while (doMore) {
                n--;
                doMore = false;
                for (int i = 0; i < n; i++) 
                {
                    if (readyQueue.get(i).getJobPriority() < readyQueue.get(i+1).getJobPriority()) 
                    {

                        pcb = readyQueue.get(i);
                        readyQueue.set(i, readyQueue.get(i+1));
                        readyQueue.set(i+1, pcb);
                        doMore = true;
                    }
                }
            }
        }

        /**This removes the job from the ready queue and also registers the wait time between
         * the job leaving the ready queue and start of execution on the CPU.
         * @param jobID
         * @return
         */
        public PCB toCPU(int jobID, ArrayList<PCB> readyQueue){

            PCB temp = readyQueue.get(jobID);
            temp.setStatus(LOADED);
            readyQueue.remove(temp);
            //temp.setoutQueueTime(System.nanoTime());        //returns the current running time of JVM in nanoseconds
            //temp.setCpuStartTime(System.nanoTime());

            System.out.println("REMOVED FROM READY QUEUE... CURRENT SIZE: " +
                    readyQueue.size());
            
            return temp;
        }

}

