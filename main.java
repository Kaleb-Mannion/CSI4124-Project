import java.util.*;
import java.io.*;
import java.util.Random;
import java.lang.Math;

public class main {
    public static void main(String[] args) {


    	//Initializations
		LinkedList<task> taskList = new LinkedList<task>();//keep a list of all the current tasks
		LinkedList<task> futureTaskList = new LinkedList<task>();//keep a list off all the future tasks
		Cook cook = new Cook(4, 2, 4);//create a cook with 4 cook tops, 2 oven, 4 fryers
		double currentTime = 0;//start time at 0
		int orderCounter = 0;
		//generate group inter arrival times and group sizes
		double noGroups = 50;
		Random generator = new Random();


		//loops through groups
		double groupArrivalTime = 0;
		for(int iteratorGroups = 0; iteratorGroups < noGroups; iteratorGroups++){
			if(iteratorGroups == 0){
				groupArrivalTime = 0;
			}else{
				groupArrivalTime += generator.nextGaussian()*10+600;
			}
			int groupSize = (int)Math.log(1-generator.nextDouble())/(-1)+1;//use inverse method to generate a exponentially distributed group size, smallest group size is 1

			//loops through members of group, adds items to order
			double memberArrivalTime = groupArrivalTime;
			for(int iteratorGroupMembers = 0; iteratorGroupMembers < groupSize; iteratorGroupMembers++){
				if(iteratorGroupMembers == 0){
					memberArrivalTime = groupArrivalTime;
				}else{
					memberArrivalTime += Math.log(1-generator.nextDouble())/(-1)*10+1;
				}
				task[] order = createOrder(orderCounter,memberArrivalTime);
				for(int iteratorItems = 0; iteratorItems < order.length; iteratorItems++){
					futureTaskList.push(order[iteratorItems]);
				}
				orderCounter++;
			}
		}
		System.out.println(futureTaskList.size());
		
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
			//System.out.println("Ovens" + cook.noOvens + " Cook Tops" + cook.noCookTops + "Fryers" + cook.noFryers + " Cook?" + cook.isBusy);
		}
    }
	
	//chances should sum to 1
	static double appetizerChance = 0.50;
	static double mainChance = 0.1;
	static double pizzaChance = 0.125;
	static double natchoChance = 0.125;
	static double saladChance = 0.05;
	static double kidsMealChance = 0.1;
	
	static task[] createOrder(int orderID, double arrivalTime){
		Random generator = new Random();
		int noItems = (int)(Math.log(1-generator.nextDouble())/(-0.5)+1);//use inverse method to generate number of items to order, fewest items is 1  //(int)(Math.abs(generator.nextGaussian()*itemDeviation + meanItems)+1);//guarantee no orders of 0 items by rounding up
		task[] orders = new task[noItems];
		for(int item = 0; item < noItems; item++){
			double randomValue = generator.nextDouble();
			if(randomValue < appetizerChance){
				randomValue = generator.nextDouble();
				if(randomValue < friesChance){
					orders[item] = createFries(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance){
					orders[item] = createSweetPotatoFries(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance + waffleFriesChance){
					orders[item] = createWaffleFries(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance + waffleFriesChance + kettleChipsChance){
					orders[item] = createKettleChips(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance + waffleFriesChance + kettleChipsChance + jalapenoTatersChance){
					orders[item] = createJalapenoTaters(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance + waffleFriesChance + kettleChipsChance + jalapenoTatersChance + deepFriedCurdsChance){
					orders[item] = createDeepFriedCurds(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance + waffleFriesChance + kettleChipsChance + jalapenoTatersChance + deepFriedCurdsChance + pizzaFingersChance){
					orders[item] = createPizzaFingers(orderID, arrivalTime);
				}else if(randomValue < friesChance + sweetPotatoFriesChance + waffleFriesChance + kettleChipsChance + jalapenoTatersChance + deepFriedCurdsChance + pizzaFingersChance + createWingsChance){
					orders[item] = createWings(orderID, arrivalTime);
				}
			}else if(randomValue < appetizerChance + mainChance){//don't need to check if its greater than the previous chance as it would have been handled by that if statement
				orders[item] = createWings(orderID, arrivalTime);//TEMP
			}else if(randomValue < appetizerChance + mainChance + pizzaChance){//don't need to check if its greater than the previous chance as it would have been handled by that if statement
				randomValue = generator.nextDouble();
				if(randomValue < cheesePizzaChance){
					orders[item] = createCheesePizza(orderID,arrivalTime);
				}else if(randomValue < cheesePizzaChance + peperoniPizzaChance){
					orders[item] = createPeperoniPizza(orderID,arrivalTime);
				}else if(randomValue < cheesePizzaChance + peperoniPizzaChance + housePizzaChance){
					orders[item] = createHousePizza(orderID,arrivalTime);
				}else if(randomValue < cheesePizzaChance + peperoniPizzaChance + housePizzaChance + BBQPizzaChance){
					orders[item] = createBBQPizza(orderID,arrivalTime);
				}else if(randomValue < cheesePizzaChance + peperoniPizzaChance + housePizzaChance + BBQPizzaChance + canadianPizzaChance){
					orders[item] = createCanadianPizza(orderID,arrivalTime);
				}
			}else if(randomValue < appetizerChance + mainChance + pizzaChance + natchoChance){//don't need to check if its greater than the previous chance as it would have been handled by that if statement
				orders[item] = createNatcho(orderID,arrivalTime);
			}else if(randomValue < appetizerChance + mainChance + pizzaChance + natchoChance + saladChance){//don't need to check if its greater than the previous chance as it would have been handled by that if statement
				orders[item] = createSalad(orderID,arrivalTime);
			}else if(randomValue < appetizerChance + mainChance + pizzaChance + natchoChance + saladChance + kidsMealChance){//don't need to check if its greater than the previous chance as it would have been handled by that if statement
				orders[item] = createWings(orderID, arrivalTime);//TEMP
			}
		}
		return orders;
	}
	// Appetizers
				   
	static double friesChance = 0.5;
	static double sweetPotatoFriesChance = 0.5/7;
	static double waffleFriesChance = 0.5/7;
	static double kettleChipsChance = 0.5/7;
	static double jalapenoTatersChance = 0.5/7;
	static double deepFriedCurdsChance = 0.5/7;
	static double pizzaFingersChance = 0.5/7;
	static double createWingsChance = 0.5/7;

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
	
	static task createPizzaFingers(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating pizza fingers");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing pizza fingers");
		task frying = new task("Fryer", false, true, true, orderID,  9*60, 1, removing,"Pizza fingers deep frying");
		task fingers = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding pizza fingers to deep fryer");
		return fingers;
	}
	
	static task createWings(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating wings");
		task flavoring = new task("Cook", false, true, true, orderID, 10, 1, plating,"Seasoning wings");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, flavoring,"Removing wings from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  9*60, 1, removing,"Wings deep frying");
		task wings = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding wings to deep fryer");
		return wings;
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
	
	static double cheesePizzaChance = 0.4;
	static double peperoniPizzaChance = 0.3;
	static double housePizzaChance = 0.1;
	static double BBQPizzaChance = 0.1;
	static double canadianPizzaChance = 0.1;
	
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
	
	//Mains
	
	static task createChickenTenders(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating chicken tenders");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing chicken tenders");
		task frying = new task("Fryer", false, true, true, orderID,  7*60, 1, removing,"Chicken tenders deep frying");
		task tenders = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime, frying,"Adding chicken tenders to deep fryer");
		return tenders;
	}
	
	static task createBeefBurger(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 5, 1, null,"Plating burger");
		task buildingBurger = new task("Cook", false, true, true, orderID, 15, 1, plating,"Building burger");
		task removingBun = new task("Cook", true, true, false, orderID, 10, 1, buildingBurger,"Removing bun from oven");
		task toastingBun = new task("Oven", false, true, true, orderID, 50, 1, removingBun,"Toasting burger bun");
		task addingBunToOven = new task("Cook", false, true, true, orderID, 15, 1, toastingBun,"Adding burger bun to oven");
		task removingPatty = new task("Cook", true, true, false, orderID, 10, 1, addingBunToOven,"Removing patty from grill");
		task cookingPatty = new task("Cook Top", false, true, true, orderID, 10*60, 1, removingPatty,"Cooking patty");
		task addingPattyToGrill = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime,cookingPatty,"Adding patty to grill");
		return addingPattyToGrill;
	}
	
	static task createCrispyChickenBurger(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 5, 1, null,"Plating burger");
		task buildingBurger = new task("Cook", false, true, true, orderID, 15, 1, plating,"Building burger");
		task removingBun = new task("Cook", true, true, false, orderID, 10, 1, buildingBurger,"Removing bun from oven");
		task toastingBun = new task("Oven", false, true, true, orderID, 50, 1, removingBun,"Toasting burger bun");
		task addingBunToOven = new task("Cook", false, true, true, orderID, 15, 1, toastingBun,"Adding burger bun to oven");
		task removingChicken = new task("Cook", true, true, false, orderID, 10, 1, addingBunToOven,"Removing chicken tenders from fryers");
		task cookingChicken = new task("Deep Fryer", false, true, true, orderID, 7*60, 1, removingChicken,"Cooking chicken tenders");
		task addingChickenToDeepFryer = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime,cookingChicken,"Adding chicken tenders to fryer");
		return addingChickenToDeepFryer;
	}
	
	static task createGrilledChickenBurger(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 5, 1, null,"Plating burger");
		task buildingBurger = new task("Cook", false, true, true, orderID, 15, 1, plating,"Building burger");
		task removingBun = new task("Cook", true, true, false, orderID, 10, 1, buildingBurger,"Removing bun from oven");
		task toastingBun = new task("Oven", false, true, true, orderID, 50, 1, removingBun,"Toasting burger bun");
		task addingBunToOven = new task("Cook", false, true, true, orderID, 15, 1, toastingBun,"Adding burger bun to oven");
		task removingChicken = new task("Cook", true, true, false, orderID, 10, 1, addingBunToOven,"Removing chicken from grill");
		task cookingChicken = new task("Cook Top", false, true, true, orderID, 8*60, 1, removingChicken,"Cooking chicken");
		task addingChickenToGrill = new task("Cook", false, true, false, orderID, 10, 1, arrivalTime,cookingChicken,"Adding chicken to grill");
		return addingChickenToGrill;
	}
	
	/*static task createCrispyWrap(int orderID, double arrivalTime){
		task plating = new task
	}
	
	static task createGrilledWrap(int orderID, double arrivalTime){
		task plating = new task
	}*/
	
	//Salads (don't distinguish, all take roughly same amount of time and have similar procedure)
	
	static task createSalad(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 7, 1, null, "Plating salad");
		task tossing = new task("Cook", false, true, true, orderID, 10, 1, plating, "Tossing salad");
		task combiningIngredients = new task("Cook", false, true, true, orderID, 10, 1, tossing, "Combining salad ingredients");
		task preparingIngredients = new task("Cook", false, true, false, orderID, 30, 1, arrivalTime, combiningIngredients, "Preparing salad ingredients");
		return preparingIngredients;
	}
	
	//Kids meals
	
	static double pogoChance = 1/3;
	static double hotdogChance = 1/3;
	static double kidschickenTenderChance = 1/3;
	
	static task createPogo(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, true, orderID, 10, 1, null,"Plating pogo");
		task removing = new task("Cook", true, false, false, orderID, 3, 1, plating,"Removing pogo from deep fryer");
		task frying = new task("Fryer", false, true, true, orderID,  180, 1, removing,"Pogo deep frying");
		task pogo = new task("Cook", false, true, false, orderID, 5, 1, arrivalTime, frying,"Adding pogo to deep fryer");
		return pogo;
	}
	
	static task createHotdog(int orderID, double arrivalTime){
		task plating = new task("Cook", false, true, false, orderID, 10, 1, null,"Plating hotdog");
		task bunRemoving = new task("Cook", true, false, false, orderID, 10, 1, plating,"Removing hotdog bun from oven");
		task toasting = new task("Oven", false, true, true, orderID, 10, 1, bunRemoving,"Toasting hotdog bun");
		task addingBun = new task("Cook", false, true, true, orderID, 50, 1, toasting,"Toasting hotdog bun");
		task removing = new task("Cook", true, false, false, orderID, 10, 1, addingBun,"Removing hotdog from oven");
		task cooking = new task("Oven", false, true, true, orderID,  210, 1, removing,"Hotdog cooking");
		task hotdog = new task("Cook", false, true, false, orderID, 5, 1, arrivalTime, cooking,"Adding hotdog to oven");
		return hotdog;
	}
	
	//just use normal chicken tender, takes same amount of time and tools
}
