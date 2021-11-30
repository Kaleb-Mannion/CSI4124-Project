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
	
	// Appetizers

	static task createFries(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating fries");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing fries from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  120, 1, removing,"Fries deep frying");
		task fries = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding fries to deep fryer");
		return fries;
	}
	
	static task createSweetPotatoFries(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating sweet potato fries");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing sweet potato fries from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  180, 1, removing,"Sweet potato fries deep frying");
		task fries = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding sweet potato fries to deep fryer");
		return fries;
	}
	
	static task createWaffleFries(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating waffle fries");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing waffle fries from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  180, 1, removing,"Sweet waffle fries deep frying");
		task fries = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding waffle fries to deep fryer");
		return fries;
	}
	
	static task createKettleChips(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating kettle chips");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing kettle chips from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  180, 1, removing,"Kettle chips deep frying");
		task chips = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding kettle chips to deep fryer");
		return chips;
	}
	
	static task createJalapenoTaters(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating jalapeno taters");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing jalapeno taters from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  180, 1, removing,"Jalapeno taters deep frying");
		task taters = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding jalapeno taters to deep fryer");
		return taters;
	}
	
	static task createDeepFriedCurds(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating deep fried cheese curds");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing deep fried cheese curds from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  60, 1, removing,"Deep fried cheese curds deep frying");
		task curds = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding deep fried cheese curds to deep fryer");
		return curds;
	}
	
	static task createWings(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating wings");
		task flavoring = new task("Cook", false, true, true, orderID, 10, 1, plating,"Seasoning wings");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, flavoring,"Removing wings from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  9*60, 1, removing,"Wings deep frying");
		task wings = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding wings to deep fryer");
		return wings;
	}
	
	static task createPizzaFingers(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating pizza fingers");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing pizza fingers");
		task frying = new task("Fryer", false, true, true, orderID,  9*60, 1, removing,"Pizza fingers deep frying");
		task fingers = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding pizza fingers to deep fryer");
		return fingers;
	}
	
	static task createChickenTenders(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating chicken tenders");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing chicken tenders");
		task frying = new task("Fryer", false, true, true, orderID,  7*60, 1, removing,"Chicken tenders deep frying");
		task tenders = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding chicken tenders to deep fryer");
		return tenders;
	}
	
	//Natchos (don't distinguish between small and large and different types, all take ~ same amount of time)
	
	static task createNatcho(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating natchos");
		task removing = new task("Cook", true, false, false, orderID, 5, 1, plating,"Removing natchos from oven");
		task cooking = new task("Oven", false, true, true, orderID,  50, 1, removing,"Natchos cooking");
		task adding = new task("Cook", false, true, true, orderID, 5, 1, cooking,"Adding natchos to oven");
		task preparing = new task("Cook", false, true, false, orderID, 120, 1,arrivalTime, adding,"Preparing natchos");
		return preparing;
	}
	
	//Pizza
	
	static task createCheesePizza(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 30, 1, null,"Plating cheese pizza");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing cheese pizza");
		task cooking = new task("Oven", false, true, true, orderID,  3*60, 1, removing,"Cooking cheese pizza");
		task adding = new task("Cook", false, true, true, orderID,  5, 1, cooking,"Putting cheese pizza in oven");
		task pizza = new task("Cook", false, true, false, orderID, 3*60, 3, arrivalTime, adding,"Preparing cheese pizza");
		return pizza;
	}
	
	static task createPeperoniPizza(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 30, 1, null,"Plating peperoni pizza");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing peperoni pizza");
		task cooking = new task("Oven", false, true, true, orderID,  3*60, 1, removing,"Cooking peperoni pizza");
		task adding = new task("Cook", false, true, true, orderID,  5, 1, cooking,"Putting peperoni pizza in oven");
		task pizza = new task("Cook", false, true, false, orderID, 4*60, 3, arrivalTime, adding,"Preparing peperoni pizza");
		return pizza;
	}
	
	static task createHousePizza(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 30, 1, null,"Plating house pizza");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing house pizza");
		task cooking = new task("Oven", false, true, true, orderID,  3*60, 1, removing,"Cooking house pizza");
		task adding = new task("Cook", false, true, true, orderID,  5, 1, cooking,"Putting house pizza in oven");
		task pizza = new task("Cook", false, true, false, orderID, 4.5*60, 3, arrivalTime, adding,"Preparing house pizza");
		return pizza;
	}
	
	static task createBBQPizza(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 30, 1, null,"Plating BBQ pizza");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing BBQ pizza");
		task cooking = new task("Oven", false, true, true, orderID,  3*60, 1, removing,"Cooking BBQ pizza");
		task adding = new task("Cook", false, true, true, orderID,  5, 1, cooking,"Putting BBQ pizza in oven");
		task pizza = new task("Cook", false, true, false, orderID, 4.5*60, 3, arrivalTime, adding,"Preparing BBQ pizza");
		return pizza;
	}
	
	static task createCanadianPizza(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 30, 1, null,"Plating canadian pizza");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing canadian pizza");
		task cooking = new task("Oven", false, true, true, orderID,  3*60, 1, removing,"Cooking canadian pizza");
		task adding = new task("Cook", false, true, true, orderID,  5, 1, cooking,"Putting canadian pizza in oven");
		task pizza = new task("Cook", false, true, false, orderID, 4.5*60, 3, arrivalTime, adding,"Preparing canadian pizza");
		return pizza;
	}
	
	//Burgers
	
	
}
