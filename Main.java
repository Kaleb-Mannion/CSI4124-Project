import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    static Queue serviceQueue = new LinkedList<>();
    static ArrayList<Cook> cookList = new ArrayList<>();

    public static void main(String[] args) {

        cookList.add(new Cook(true, true, false));
        cookList.add(new Cook(false, true, true));
        cookList.add(new Cook(true, false, true));


        for (int i = 0; i < 10; i++){
            //serviceQueue.add(new Customer(i + 1));
        }

        // Runs until list is depleted
        while (!serviceQueue.isEmpty()){

            // removes head of queue (represents customer being "serviced")
            //Customer current = serviceQueue.poll();

            // checks to see if the recent customer removal produces an empty queue
            if (!serviceQueue.isEmpty()) {

                //informs the head of the queue of the customer currently being serviced to calculate wait time.
                //serviceQueue.peek().setFrontCustomer(current);
                //serviceQueue.peek().calculateWaitTime();

            }
        }
    }

}
