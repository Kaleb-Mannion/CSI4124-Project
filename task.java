//import java.util.LinkedList;
import java.util.Random;

public class task{
	public String resource;//server/tool required to complete task
	public boolean isInterrupt;//if the task must be done immediately
	public boolean isInterruptable;
	public boolean affectedByInterrupts;//would this task be pushed back if its predecessor was interrupted?
	public String description;
	public int orderID;//the order the task is apart of
	private double mean;//mean time to complete task
	private double deviation;//standard deviation of time to complete task
	public task nextTask;
	public double arrivalTime = -1;
	public double completionTime = -1;
	
	public task(String t_resource, boolean t_isInterrupt, boolean t_isInterruptable, boolean t_affectedByInterrupts, int t_orderID, double t_mean, double t_deviation, double t_arrivalTime, task t_nextTask){
		resource = t_resource;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		affectedByInterrupts = t_affectedByInterrupts;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = "";
		nextTask = t_nextTask;
		arrivalTime = t_arrivalTime;
	}
	
	public task(String t_resource, boolean t_isInterrupt, boolean t_isInterruptable, boolean t_affectedByInterrupts, int t_orderID, double t_mean, double t_deviation, double t_arrivalTime, task t_nextTask, String t_description){
		resource = t_resource;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		affectedByInterrupts = t_affectedByInterrupts;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = t_description;
		nextTask = t_nextTask;
		arrivalTime = t_arrivalTime;
	}
	
	public task(String t_resource, boolean t_isInterrupt, boolean t_isInterruptable, boolean t_affectedByInterrupts, int t_orderID, double t_mean, double t_deviation, task t_nextTask){
		resource = t_resource;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		affectedByInterrupts = t_affectedByInterrupts;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = "";
		nextTask = t_nextTask;
	}
	
	public task(String t_resource, boolean t_isInterrupt, boolean t_isInterruptable, boolean t_affectedByInterrupts, int t_orderID, double t_mean, double t_deviation, task t_nextTask, String t_description){
		resource = t_resource;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		affectedByInterrupts = t_affectedByInterrupts;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = t_description;
		nextTask = t_nextTask;
	}
	
	public double generateCompletionTime(double currentTime){
		Random generator = new Random();//may want to use generator that only gives positive values, folded normal?
		double timeLength = generator.nextGaussian()*deviation+mean;//gaussian returns a normally distributed random number with deviation of 1 and mean of 0, so we can add the desired mean and multiply to get the deviation
		completionTime = currentTime + timeLength;
		return timeLength;
	}
}
