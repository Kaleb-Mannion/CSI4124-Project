import java.util.*;
import java.io.*;

public class main {
    public static void main(String[] args) {
		LinkedList<task> taskList = new LinkedList<task>();//keep a list of all the current tasks
		LinkedList<task> futureTaskList = new LinkedList<task>();//keep a list off all the future tasks
		Cook cook = new Cook(4, 2, 4);//create a cook with 4 cook tops, 2 oven, 4 fryers
		double currentTime = 0;//start time at 0
        for (int i = 0; i < 10; i++){//create ten fry orders 60s apart to test the queuing system
			futureTaskList.push(createFries(i, i*60));
        }
		while(!taskList.isEmpty() || !futureTaskList.isEmpty()){//as long as there are future tasks or current tasks, keep working
			boolean eventsSkipped = false;//keep track if any Tasks were skipped by a non interruptable task advancing time (see cook)
			ListIterator<task> iteratorFutureTasks = futureTaskList.listIterator(0);//create an iterator for the future tasks
			LinkedList<task> missedTasks = new LinkedList<>();//create a list of missed tasks
			while(iteratorFutureTasks.hasNext()){//check if any tasks have been skipped over by an uninterruptable activity
				task futureEvent = iteratorFutureTasks.next();
				if(futureEvent.arrivalTime < currentTime){//if the current task occured in the past
					eventsSkipped = true;//we know we skipped a task arriving
					missedTasks.add(futureEvent);//add it to the missed task list
					taskList.add(futureEvent);//add it to the active task list
				}
			}
			ListIterator<task> missedIterator = missedTasks.listIterator();//iterator for the missed tasks
			while(missedIterator.hasNext()){//iterate through the tasks that were missed
				task missed = missedIterator.next();
				futureTaskList.remove(missed);//remove the missed tasks from the future task list
			}
			if(!eventsSkipped){//if no events were skipped, we can advance time to the next task
				iteratorFutureTasks = futureTaskList.listIterator(0);//create an iterator for the future tasks
				task soonestEvent = null;//keep track of which task in the future arrives the soonest
				while(iteratorFutureTasks.hasNext()){//iterate through the future tasks
					task futureEvent = iteratorFutureTasks.next();
					if(soonestEvent == null || soonestEvent.arrivalTime > futureEvent.arrivalTime){//if the soonest event hasnt been chosen yet, or the current event is sooner than the current soonest
						soonestEvent = futureEvent;//replace the soonest task with the new task
					}
				}
				if(soonestEvent != null){//if there was a next event
					futureTaskList.remove(soonestEvent);//remove it from the future task list
					taskList.add(soonestEvent);//add it to the current task list
					currentTime = soonestEvent.arrivalTime;//jump time forward to the arrival of the task
//					System.out.println("Order #" + Integer.toString(soonestEvent.orderID) + " received");
				}
			}
			currentTime = cook.nextEvent(taskList, futureTaskList, currentTime);//have the cook check if the advancement of time has allowed them to work on a new task
			//if(futureTaskList.isEmpty()){
			//	return;
			//}
		}
    }

	static task createFries(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating fries");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing fries from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  120, 1, removing,"Fries deep frying");
		task fries = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding fries to deep fryer");
		return fries;
	}
	
	task createNatcho(int orderID){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating natchos");
		task removing = new task("Cook", true, false, false, orderID, 5, 1, plating,"Removing natchos from oven");
		task cooking = new task("Oven", false, true, true, orderID,  50, 1, removing,"Natchos cooking");
		task adding = new task("Cook", false, true, true, orderID, 5, 1, cooking,"Adding natchos to oven");
		task preparing = new task("Cook", false, true, false, orderID, 120, 1, adding,"Preparing natchos");
		return preparing;
	}
}
