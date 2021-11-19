import java.util.LinkedList;
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