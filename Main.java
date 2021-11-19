import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static Queue<Task> taskQueue = new LinkedList<>();
    static ArrayList<Cook> cookList = new ArrayList<>();

    public static void main(String[] args) {

        cookList.add(new Cook(true, true, false));
        cookList.add(new Cook(false, true, true));
        cookList.add(new Cook(true, false, true));

        taskQueue.addAll(createFries(0));
        taskQueue.addAll(createNacho(1));
        taskQueue.addAll(createFries(2));
        taskQueue.addAll(createNacho(3));
        taskQueue.addAll(createFries(4));
        taskQueue.addAll(createNacho(5));

        System.out.println(taskQueue.size());

        while(!taskQueue.isEmpty()){
            break;
        }

        // Runs until list is depleted
        while (!taskQueue.isEmpty()){

            // removes head of queue (represents customer being "serviced")
            //Customer current = serviceQueue.poll();

            // checks to see if the recent customer removal produces an empty queue
            if (!taskQueue.isEmpty()) {

                //informs the head of the queue of the customer currently being serviced to calculate wait time.
                //serviceQueue.peek().setFrontCustomer(current);
                //serviceQueue.peek().calculateWaitTime();

            }

            break;
        }
    }

    static LinkedList<Task> createFries(int orderID) {
        LinkedList<Task> fries = new LinkedList<>();
        fries.push(new Task("Cooktop", false, true, orderID, 10, 1, "Plating fries"));
        fries.push(new Task("Cooktop", true, false, orderID, 3, 1, "Removing fries from deep fryer"));
        fries.push(new Task("Deep Fryer", false, false, orderID, 120, 1, "Fries deep frying"));
        fries.push(new Task("Cooktop", false, false, orderID, 10, 1, "Adding fries to deep fryer"));
        return fries;
    }

    static LinkedList<Task> createNacho(int orderID) {
        LinkedList<Task> nachos = new LinkedList<>();
        nachos.push(new Task("Cooktop", false, true, orderID, 10, 1, "Plating nachos"));
        nachos.push(new Task("Cooktop", true, false, orderID, 5, 1, "Removing nachos from oven"));
        nachos.push(new Task("Oven", false, false, orderID, 50, 1, "Natchos cooking"));
        nachos.push(new Task("Cooktop", false, false, orderID, 5, 1, "Adding nachos to oven"));
        nachos.push(new Task("Cooktop", false, true, orderID, 120, 1, "Preparing nachos"));
        return nachos;
    }

}
