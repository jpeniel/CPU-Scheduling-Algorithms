package model;
import java.util.*;

public class Job{

	private String processName;
	private double arrivalTime;
	private double burstTime;
	private int priority;

		
		
	public Job(String processName, double arrivalTime, double burstTime, int priority){
		this.processName = processName;
		this.arrivalTime = arrivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		
	 }
	 
	public double getArrivalTime(){
		return arrivalTime;
	}
	
	public double getBurstTime(){
		return burstTime;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public String getProcessName(){
		return processName;
	}
	
	public void setBurstTime(double burstTime){
		this.burstTime = burstTime;
		
	}
	public void setArrivalTime(double arrivalTime){
		this.arrivalTime = arrivalTime;
		
	}
	public static Comparator<Job> SortByArrival = (Job j1, Job j2) -> {
		return Double.compare(j1.getArrivalTime(), j2.getArrivalTime());
	};
   
	public static Comparator<Job> SortByBurst = (Job j1, Job j2) -> {
		return Double.compare(j1.getBurstTime(), j2.getBurstTime());
	};

	public static Comparator<Job> SortByPriority = (Job j1, Job j2) -> {
		return Integer.compare(j1.getPriority(), j2.getPriority());
	};

	public static Comparator<Job> SortByProcessName = (Job j1, Job j2) -> {
		return j1.getProcessName().compareTo(j2.getProcessName());
	};
	
}