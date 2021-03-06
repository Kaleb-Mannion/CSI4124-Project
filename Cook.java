import java.util.LinkedList;
import java.util.*;
//import java.io.*;

public class Cook{

	int noCookTops;//number of available cook tops
	int noOvens;//number of ovens available
	int noFryers;//number of fryers available
	boolean isBusy;//if the cook is busy themselves
	boolean advancedTime;
	ArrayList<Integer> seenVariables = new ArrayList<>();
	ArrayList<Double> orderStart = new ArrayList<>(); ArrayList<Double> orderEnd = new ArrayList<>();
	
	class resource{//used to keep track of when resources become available again
		public String name;
		public double arrivalTime;
		
		resource(String t_name, double t_arrivalTime){
			name = t_name;
			arrivalTime = t_arrivalTime;
		}
	}
	LinkedList<resource> futureResourceList;//resource and time it will be available again
	

	Cook(int t_noCookTops, int t_noOvens, int t_noFryers){
		noCookTops = t_noCookTops;
		noOvens = t_noOvens;
		noFryers = t_noFryers;
		isBusy = false;
		advancedTime = false;
		futureResourceList = new LinkedList<>();
	}

	double nextEvent(LinkedList<task> taskList, LinkedList<task> futureTaskList, double currentTime){//cook checks to see if they are able to start any new tasks with the resources available
		advancedTime = false;
		ListIterator<task> i = taskList.listIterator(0);//iterator for the available tasks
		while(i.hasNext()){//as interrupts have priority we search for any new ones and deal with them
			task currentTask = i.next();
			if(currentTask.isInterrupt){//if the current task is an interrupt
				currentTime = doTask(currentTask,futureTaskList, currentTime);//deal with the interrupt
				taskList.remove(currentTask);//remove it from the task list
				return currentTime;//update the time
			}
		}
		ListIterator<resource> j = futureResourceList.listIterator(0);//iterator for the tools in use
		LinkedList<resource> freedResources = new LinkedList<>();
		if(futureTaskList.isEmpty()){
			resource soonestResource = null;
			while(j.hasNext()){
				resource resourceInUse = j.next();
				if(soonestResource == null || resourceInUse.arrivalTime < soonestResource.arrivalTime){
					soonestResource = resourceInUse;
				}
			}
			if(soonestResource != null){
    			currentTime = soonestResource.arrivalTime;
    		}
		}
		j = futureResourceList.listIterator(0);//iterator for the tools in use
		while(j.hasNext()){//after all the interrupts have been cleared, we can guarantee the resources they were using are no free, so we add the freed resources
			resource resourceInUse = j.next();
			if(resourceInUse.arrivalTime <= currentTime){//if the resource was freed in the past or now
				freedResources.add(resourceInUse);
				if(resourceInUse.name == "Oven"){//add the resource back to its pool
					noOvens++;
				}else if(resourceInUse.name == "Cook Top"){
					noCookTops++;
				}else if(resourceInUse.name == "Fryer"){
					noFryers++;
				}else if(resourceInUse.name == "Cook"){
					isBusy = false;
				}					
			}
		}
		ListIterator<resource> k = freedResources.listIterator();
		while(k.hasNext()){
			futureResourceList.remove(k.next());
		}
		i = taskList.listIterator(0);//iterator for the available tasks
		while(i.hasNext()){//for each item in the queue that isnt an interrupt, serve the first one the tools are available for 
			task currentTask = i.next();
			if(currentTask.resource == "Oven" && noOvens > 0 || currentTask.resource == "Fryer" && noFryers > 0 || currentTask.resource == "Cook Top" && noCookTops > 0){// || currentTask.resource == "Cook" && !isBusy){
				//check if the task uses a resource and if the resource is available
				currentTime = doTask(currentTask,futureTaskList, currentTime);//do the task if the resource is available
				taskList.remove(currentTask);//remove the task from the task list
				return currentTime;//return to the main routine with the new time
			}	
		}
		i = taskList.listIterator(0);
		while(i.hasNext()){//for each item in the queue that isnt an interrupt, serve the first one the tools are available for 
			task currentTask = i.next();
			if(currentTask.resource == "Cook" && isBusy == false){
				//check if the task uses a resource and if the resource is available
				currentTime = doTask(currentTask,futureTaskList, currentTime);//do the task if the resource is available
				taskList.remove(currentTask);//remove the task from the task list
				return currentTime;//return to the main routine with the new time
			}	
		}
		return currentTime;
	}

	boolean seenOrder(int orderID){
		for (int seenID : seenVariables){
			if (orderID == seenID){
				return true;
			}
		}
		return false;
	}

	void updateEnd(int orderID, double endTime){
		for (int i = 0; i < seenVariables.size(); i++){
			if (i == orderID){
				orderEnd.set(i, endTime);
			}
		}
	}

	void displayMetrics(){
		double accumulator = 0;
		for (int i = 0; i < seenVariables.size(); i++){
			double serviceTime = orderEnd.get(i) - orderStart.get(i);
			System.out.println(String.format("Order # %s started at %s and ended at %s. (Service time %s)", i, orderStart.get(i), orderEnd.get(i), serviceTime));
			accumulator += serviceTime;
		}

		double average = accumulator / seenVariables.size();
		System.out.println(String.format("Average service time: %s", average));

	}

	double doTask(task currentTask, LinkedList<task> futureTaskList, double currentTime){//updates the available resource pools and advances time for uninterruptable tasks
		double timeLength = currentTask.generateCompletionTime(currentTime);//determine when the task will end
		System.out.println(Double.toString(currentTime) + "s : Starting Task for order #" + Integer.toString(currentTask.orderID) + " : " + currentTask.description);

		if (!seenOrder(currentTask.orderID)){
			System.out.println(String.format("Starting order %s at time %s", currentTask.orderID, currentTime));
			seenVariables.add(currentTask.orderID);
			orderStart.add(currentTime);
			orderEnd.add(0.0);
		}

		task futureEvent = currentTask.nextTask;//get the task that occurs after this one completes
		if(futureEvent != null){//if there is a task after this one
			futureEvent.arrivalTime = currentTask.completionTime;//set the task that occurs after this one completes to arrive when this one completes
			futureTaskList.add(futureEvent);//add the future task to the future task list
		}else{//otherwise
			updateEnd(currentTask.orderID, currentTime);
			System.out.println(Double.toString(currentTime) + "s : Finished Order #" + Integer.toString(currentTask.orderID));//display order completion
		}
		if(currentTask.resource == null){
			//raise an error about missing resource?
		}else{
			if(currentTask.resource == "Oven"){//whenever a resource is in use, keep track of it
				noOvens--;
			}else if(currentTask.resource == "Fryer"){
				noFryers--;
			}else if(currentTask.resource == "Cook Top"){
				noCookTops--;
			}else if(currentTask.resource == "Cook"){
				isBusy = true;
			}			
			futureResourceList.add(new resource(currentTask.resource, currentTask.completionTime));//when a resource is done being used, schedule it to return to the pool of available resources
		} 
		if (currentTask.isInterruptable == false && currentTask.resource == "Cook"){//if the current task cannot be interrupted and its the cook
			currentTime = currentTask.completionTime;//jump to the time where it is completed
			ListIterator<task> i = futureTaskList.listIterator(0);
			while(i.hasNext()){//for each task in the future task list, push back the ones with a predecessor that relies on the cook(i.e if the cook works on the predecessor step)
				task futureTask = i.next();
				if(futureTask.affectedByInterrupts){
					futureTask.arrivalTime += timeLength;//add the length of the interruption to the arrival time of tasks affected by interruptions
				}
			}
		}else if(currentTask.resource == "Cook"){
		    ListIterator<task> i = futureTaskList.listIterator(0);
	        task soonestInterrupt = null;
		    while(i.hasNext()){
		        task futureTask = i.next();
		        if(soonestInterrupt == null || futureTask.isInterrupt && futureTask.arrivalTime < soonestInterrupt.arrivalTime){
		            soonestInterrupt = futureTask;
		        }
		    }
		    if(soonestInterrupt != null && soonestInterrupt.arrivalTime < currentTask.completionTime){
		        currentTime = soonestInterrupt.arrivalTime;   
		    }else{
		        currentTime = currentTask.completionTime;
		        advancedTime = true;
		    }
		}
		return currentTime;
	}	
}//end Class Cook
