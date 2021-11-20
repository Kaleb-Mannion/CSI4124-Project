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
		Random generator = new Random();
		double timeLength = generator.nextGaussian()*deviation+mean;//gaussian returns a normally distributed random number with deviation of 1 and mean of 0, so we can add the desired mean and multiply to get the deviation
		completionTime = currentTime + timeLength;
		return timeLength;
	}
}

/*task createNatcho(int orderID){
	//LinkedList<task> natchos = new LinkedList<task>;
	natchos.push(new task("Cook", false, true, orderID, 10, 1,"Plating natchos"));
	natchos.push(new task("Cook", true, false, orderID, 5, 1,"Removing natchos from oven"));
	natchos.push(new task("Deep Fryer", false, false, orderID,  50, 1,"Natchos cooking"));
	natchos.push(new task("Cook", false, false, orderID, 5, 1,"Adding natchos to oven"));
	natchos.push(new task("Cook", false, true, orderID, 120, 1,"Preparing natchos"));
	return natchos;
}*/import java.util.LinkedList;
import java.util.Random;

public class Task {
	public String serverType;//server/tool required to complete Task
	public boolean isInterrupt;//if the Task must be done immediately
	public boolean isInterruptable;
	public String description;
	public int orderID;//the order the Task is apart of
	private double mean;//mean time to complete Task
	private double deviation;//standard deviation of time to complete Task

	public Task(String t_serverType, boolean t_isInterrupt, boolean t_isInterruptable, int t_orderID, double t_mean, double t_deviation) {
		serverType = t_serverType;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = "";
	}

	public Task(String t_serverType, boolean t_isInterrupt, boolean t_isInterruptable, int t_orderID, double t_mean, double t_deviation, String t_description) {
		serverType = t_serverType;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = t_description;
	}

	public double getActivityTime() {
		Random generator = new Random();
		return generator.nextGaussian() * deviation + mean;//gaussian returns a normally distributed random number with deviation of 1 and mean of 0, so we can add the desired mean and multiply to get the deviation
	}


}
