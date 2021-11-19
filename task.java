import java.util.LinkedList;
import java.util.Random;

public class task{
	public String serverType;//server/tool required to complete task
	public boolean isInterrupt;//if the task must be done immediately
	public boolean isInterruptable;
	public String description;
	public int orderID;//the order the task is apart of
	private double mean;//mean time to complete task
	private double deviation;//standard deviation of time to complete task
	
	public task(String t_serverType, boolean t_isInterrupt, boolean t_isInterruptable, int t_orderID, double t_mean, double t_deviation){
		serverType = t_serverType;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = "";
	}
	
	public task(String t_serverType, boolean t_isInterrupt, boolean t_isInterruptable, int t_orderID, double t_mean, double t_deviation, String t_description){
		serverType = t_serverType;
		isInterrupt = t_isInterrupt;
		isInterruptable = t_isInterruptable;
		orderID = t_orderID;
		mean = t_mean;
		deviation = t_deviation;
		description = t_description;
	}
	
	public double getActivityTime(){
		Random generator = new Random()
		return generator.nextGaussian()*deviation+mean;//gaussian returns a normally distributed random number with deviation of 1 and mean of 0, so we can add the desired mean and multiply to get the deviation
	}
}

LinkedList<task> createFries(int orderID){
	LinkedList<task> fries = new LinkedList<task>;
	fries.push(new task("Cook", false, true, orderID, 10, 1,"Plating fries"));
	fries.push(new task("Cook", true, false, orderID, 3, 1,"Removing fries from deep fryer"));
	fries.push(new task("Deep Fryer", false, false, orderID,  120, 1,"Fries deep frying"));
	fries.push(new task("Cook", false, false, orderID, 10, 1,"Adding fries to deep fryer"));
	return fries;
}

LinkedList<task> createNatcho(int orderID){
	LinkedList<task> natchos = new LinkedList<task>;
	natchos.push(new task("Cook", false, true, orderID, 10, 1,"Plating natchos"));
	natchos.push(new task("Cook", true, false, orderID, 5, 1,"Removing natchos from oven"));
	natchos.push(new task("Deep Fryer", false, false, orderID,  50, 1,"Natchos cooking"));
	natchos.push(new task("Cook", false, false, orderID, 5, 1,"Adding natchos to oven"));
	natchos.push(new task("Cook", false, true, orderID, 120, 1,"Preparing natchos"));
	return natchos;
}