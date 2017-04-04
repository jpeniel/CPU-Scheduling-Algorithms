package controller;
import java.util.*;
import model.Job;

public class GC {

    private double endTime;
    private String processName;
    private static double turnAve;
    private static double waitAve;

    public GC(String processName, double burstTime){
            this.processName = processName;
            this.endTime = burstTime;
    }

    public static void compute(ArrayList<Job> list, ArrayList<GC> gc){

        double turnSum = 0.0;
        double waitSum = 0.0;


        Collections.sort(list, Job.SortByArrival);

        int j=list.size()-1;

        while(j>=0){
            for(int i=gc.size()-1; i>=0; i--){
                if(list.get(j).getProcessName().equals(gc.get(i).getProcessName())){
					System.out.println("Turn around time");
					System.out.println(gc.get(i).getEndTime()-list.get(j).getArrivalTime());
					System.out.println("Waiting time");
					System.out.println((gc.get(i).getEndTime()-list.get(j).getArrivalTime())-list.get(j).getBurstTime());
                    turnSum+=gc.get(i).getEndTime()-list.get(j).getArrivalTime();
                    waitSum+=(gc.get(i).getEndTime()-list.get(j).getArrivalTime())-list.get(j).getBurstTime();		
                    break;
                }
            }
            j--;
        }

        turnAve = turnSum/list.size();
        waitAve = waitSum/list.size();


    }

    public String getProcessName() {
            return processName;
    }
    public double getEndTime() {
            return endTime;
    }

    public static double getTurnAve() {
        return turnAve;
    }

    public static double getWaitAve() {
        return waitAve;
    }
}