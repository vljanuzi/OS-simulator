package sossim.core;

import java.util.ArrayList;

public class myProcessScheduller implements IProcessScheduler{
  protected ArrayList<SOSProcess> processQueue;

    public myProcessScheduller(){
      processQueue = new ArrayList();
    }
    public void addProcess(SOSProcess p){
      processQueue.add(p);
    }
    public boolean preempt(){
      return true;
    }
    public void removeProcess(SOSProcess p){
      processQueue.remove(p);
    }
    //agorithm for shortest time remaining next
   public SOSProcess selectProcessToRun(){
    if(processQueue.size() > 0){
      //set the min to the first process
      int min = processQueue.get(0).getCodeSize() - processQueue.get(0).getCounter() ;
      for(int i = 0; i < processQueue.size(); i++){
        //check each processes left time
     int timeLeft = processQueue.get(i).getCodeSize() - processQueue.get(i).getCounter();
     if( min <= timeLeft){
       return processQueue.get(0);
     }
     //if any processes's left time is smaller than the first one's, make that the min
     else{
       min = timeLeft;
     }
   }
   //return the process with that min
     for(SOSProcess p : processQueue){
       if(p.getCodeSize() - p.getCounter() == min)
         return p; 
     }
    }
    return null;
  }

   public int getNumberOfProcessesInQueue() {
       return processQueue.size();
    }
    
    public double getProgresssOfProcessInQueue(int i){
        return processQueue.get(i).getProgress();
      
    }
    public SOSProcess getProcessAtQueue(int i) {
     return processQueue.get(i);
  }
    public String toString(){
      return "Shortest Remaining Time First";
    }
  
}
