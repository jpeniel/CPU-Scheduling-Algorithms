package controller;
import java.util.*;
import model.Job;

public class CPUSched{

    private double currentTime;
    private double totalTime;
    private ArrayList<GC> gc;
    private ArrayList<Job> aux;
    private int y;
    private int counter;
    private double wht;
    private ArrayList<Job> listCopy;
    public CPUSched() {
        gc = new ArrayList<>();
        aux = new ArrayList<>();
        listCopy = new ArrayList<>();
    }
    
    public void countCurrTime(ArrayList<Job> list){
        totalTime = 0.0;
        for(int i = 0; i < list.size(); i++){
            if(totalTime < list.get(i).getArrivalTime()){
               totalTime+=(list.get(i).getArrivalTime() - currentTime);
            }
            totalTime+=list.get(i).getBurstTime();
        }
    }

    public void countCurrTime(ArrayList<Job> list, double overhead){
        totalTime = 0.0;
        for(int i = 0; i < list.size(); i++){
            if(totalTime < list.get(i).getArrivalTime()){
                totalTime+=(list.get(i).getArrivalTime() - currentTime);
            }
            totalTime+=list.get(i).getBurstTime();
        }
        totalTime+=(overhead * list.size()-1);
    }
    
    public  int search(ArrayList<Job> list, ArrayList<Job> al){
        for(int i=y; i<list.size(); i++){
            if(list.get(i).getArrivalTime()<=currentTime){
                al.add(list.get(i));
            }else if(list.get(i).getArrivalTime() > currentTime){
                return i;
            }
        }
        return counter;
    }

    // FIRST COME FIRST SERVE CODE BLOCK
    public ArrayList<GC> methodFCFS(ArrayList<Job> list){
        for(int i=0; i < list.size(); i++){
            if(currentTime < list.get(i).getArrivalTime()){
                currentTime+=(list.get(i).getArrivalTime() - currentTime);
                gc.add(new GC("idle", currentTime));
            }
        currentTime+=list.get(i).getBurstTime();
        gc.add(new GC(list.get(i).getProcessName(), currentTime));
        }
        //gc.get(0).compute(list, gc);
        return gc;
    }




    //NON PREEMPTIVE
    public ArrayList<GC> methodNPP(ArrayList<Job> list){
        for(int i=0; i<list.size(); i++){
            if(y<list.size() && currentTime < list.get(y).getArrivalTime()){
                currentTime+=(list.get(y).getArrivalTime() - currentTime);
                gc.add(new GC("idle", currentTime));
            }
            if(y<list.size()){
                y = search(list, aux);
            }
            Collections.sort(aux, Job.SortByPriority);
            currentTime+=aux.get(0).getBurstTime();
            gc.add(new GC(aux.get(0).getProcessName(), currentTime));
            aux.remove(0);
       }
       //gc.get(0).compute(list, gc);
       return gc;
    }

    // SHORTEST JOB FIRST CODE BLOCK
    public ArrayList<GC> methodSJF(ArrayList<Job> list){
        for(int i=0; i<list.size(); i++){
            if(y<list.size() && currentTime < list.get(y).getArrivalTime()){
                currentTime+=(list.get(y).getArrivalTime() - currentTime);
                gc.add(new GC("idle", currentTime));
            }
            if(y<list.size()){
                y = search(list, aux);
            }
            Collections.sort(aux, Job.SortByBurst);
            currentTime+=aux.get(0).getBurstTime();
            gc.add(new GC(aux.get(0).getProcessName(), currentTime));
            aux.remove(0);
        }
        //gc.get(0).compute(list, gc);
        return gc;
    }

    //ROUND ROBIN CODE BLOCK
  public ArrayList<GC> methodRR(ArrayList<Job> list, double quantum, double overhead) {
    Job currentJob = null;
    double currentBurst;
    int i = 0, j = 0;
    boolean addAgain = false;

    countCurrTime(list, overhead);
    System.out.println("Total time " + totalTime);
    while(j < list.size() || !aux.isEmpty()) {
        if(j < list.size()) {
            while(list.get(j).getArrivalTime() <= currentTime) {
                aux.add(list.get(j++));
                if(j == list.size()) break;
            }
        } //queue the job if its less than or equal to the current time
        if(addAgain) aux.add(currentJob);
        //add the current job with remaining burst
        if(aux.isEmpty() && j != list.size()) {
            currentTime = list.get(j).getArrivalTime();
            gc.add(new GC("idle", currentTime));
            continue;
        }
        currentJob = aux.remove(0);
        currentBurst = currentJob.getBurstTime();
        if(currentBurst > quantum) {
            currentJob.setBurstTime(currentBurst - quantum);
            currentBurst = quantum;
            addAgain = true;
        } else {
            addAgain = false;
        }
        currentTime += currentBurst;
        gc.add(new GC(currentJob.getProcessName(), currentTime));
        if(overhead > 0) {
            currentTime += overhead;
            gc.add(new GC("overhead", currentTime));
        }
		
        //if(addAgain) aux.add(currentJob);
    }
	if(overhead > 0) {
		gc.remove(gc.size()-1);
	}
    return gc;
  }


    //SHORTEST REMAINING TIME FIRST

    public  ArrayList<GC> methodSRTF(ArrayList<Job> list){
        System.out.println("SRTF");
          	ArrayList<Job> listCopy = new ArrayList<Job>();
		for(Job temp: list){
			listCopy.add(new Job(temp.getProcessName(), temp.getArrivalTime(), temp.getBurstTime(), temp.getPriority()));
		} 
        countCurrTime(list);
        if(y<list.size() && currentTime < list.get(y).getArrivalTime()){
            currentTime+=(list.get(y).getArrivalTime() - currentTime);
            gc.add(new GC("idle", currentTime));
        }
        y = search(listCopy, aux);
        int i = 0;
        while(currentTime!=totalTime){
            System.out.println(i++);
            if(y<list.size()){
                y = search(listCopy, aux);
            }
            Collections.sort(aux, Job.SortByBurst);
            if(y<counter && currentTime < aux.get(0).getArrivalTime()){
                    currentTime+=(aux.get(0).getArrivalTime() - currentTime);
                    gc.add(new GC("idle", currentTime));
            }

            if(y<counter && currentTime+aux.get(0).getBurstTime() > listCopy.get(y).getArrivalTime()){
                wht = aux.get(0).getBurstTime() - (listCopy.get(y).getArrivalTime() - currentTime);
                currentTime += (listCopy.get(y).getArrivalTime() - currentTime);
                aux.get(0).setBurstTime(wht);
                gc.add(new GC(aux.get(0).getProcessName(), currentTime));
            }else{
                currentTime+=aux.get(0).getBurstTime();
                gc.add(new GC(aux.get(0).getProcessName(), currentTime));
                aux.remove(0);
            }
        }

        //gc.get(0).compute(list, gc);
        return gc;
    }


    //PREEMPTIVE getPriority()

    public ArrayList<GC> methodPP(ArrayList<Job> list){
        
        countCurrTime(list);
       	ArrayList<Job> listCopy = new ArrayList<Job>();
		for(Job temp: list){
			listCopy.add(new Job(temp.getProcessName(), temp.getArrivalTime(), temp.getBurstTime(), temp.getPriority()));
		}
        if(currentTime < list.get(0).getArrivalTime()){
            currentTime+=(list.get(0).getArrivalTime() - currentTime);
            gc.add(new GC("idle", currentTime));
        }
        y = search(listCopy, aux);
        while(currentTime!=totalTime){
            if(y<counter){
                y = search(listCopy, aux);
            }
            Collections.sort(aux, Job.SortByPriority);
            if(y<counter && currentTime < aux.get(0).getArrivalTime()){
                currentTime+=(aux.get(0).getArrivalTime() - currentTime);
                gc.add(new GC("idle", currentTime));
            }

            if(y<counter && currentTime+aux.get(0).getBurstTime() > list.get(y).getArrivalTime()){
                wht = aux.get(0).getBurstTime() - (list.get(y).getArrivalTime() - currentTime);
                currentTime += (list.get(y).getArrivalTime() - currentTime);
				       gc.add(new GC(aux.get(0).getProcessName(), currentTime));
                aux.get(0).setBurstTime(wht);
         
            }else{
                currentTime+=aux.get(0).getBurstTime();
                gc.add(new GC(aux.get(0).getProcessName(), currentTime));
                aux.remove(0);
            }
        }
        //gc.get(0).compute(list, gc);
        return gc;
    }
    public void setCPU(int numOfJobs) {
        listCopy.clear();
        counter = numOfJobs;
        aux.clear();
        gc.clear();
        y = 0;
        currentTime = 0;
        totalTime = 0;
        wht = 0;
    }
}
