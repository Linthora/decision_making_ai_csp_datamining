package test;

import utility.*;

/**
 * Test class to test PriorityQueueHeap.
 * Not all the method in the class need to be tested as they're based on the fully tested {@link java.util.ArrayList} class.
 */
public class TestPriorityQueueHeap {

    /**
     * Doc constructor to stop useless warning.
     */
    public TestPriorityQueueHeap() {}

    /**
     * Main running test of the queue.
     * @param args not used.
     */
    public static void main(String[] args) {
        System.out.println("Starting tests:");
        System.out.println(testPoll() ? "Pool : OK" : "KO");
        System.out.println(testPeek() ? "Peek : OK" : "KO");
        System.out.println(testAdd() ? "Add : OK" : "KO");
    }

    /**
     * Method to laund all the test for the class.
     * @return true if all the test passed. false otherwise.
     */
    public static boolean testAll() {
        boolean ok = true;

        System.out.println("[Tests] [PriorityQueueHeap::poll] launched");
        ok = ok && testPoll();
        System.out.println("[Tests] [PriorityQueueHeap::poll] " + (ok ? "passed" : "failed"));

        System.out.println("[Tests] [PriorityQueueHeap::peek] launched");
        ok = ok && testPeek();
        System.out.println("[Tests] [PriorityQueueHeap::peek] " + (ok ? "passed" : "failed"));

        System.out.println("[Tests] [PriorityQueueHeap::add] launched");
        ok = ok && testAdd();
        System.out.println("[Tests] [PriorityQueueHeap::add] " + (ok ? "passed" : "failed"));
        return ok;
    }

    /**
     * Method to test poll method of the class.
     * @return true if all the test passed. false otherwise.
     */
    public static boolean testPoll() {
        PriorityQueueHeap<Integer> q = new PriorityQueueHeap<>(5);
        q.add(2);
        q.add(3);
        Integer r = q.poll();
        if(r!=2 || q.size()!=1) {
            System.out.println("Poll method doesn't function as intended");
            return false;
        }

        q.add(-1);
        q.add(0);

        r = q.poll();
        if(r!=-1 || q.size()!=2) {
            System.out.println("Poll method doesn't function as intended");
            return false;
        }
        return true;
    }

    /**
     * Method to test peek method of the class.
     * @return true if all the test passed. false otherwise.
     */
    public static boolean testPeek() {
        PriorityQueueHeap<Integer> q = new PriorityQueueHeap<>(5);
        q.add(2);
        q.add(3);
        Integer r = q.peek();
        if(r!=2 || q.size()!=2) {
            System.out.println("Peek method doesn't function as intended");
            return false;
        }

        q.add(-1);
        q.add(0);

        r = q.peek();
        if(r!=-1 || q.size()!=4) {
            System.out.println("Peek method doesn't function as intended");
            return false;
        }
        return true;
    }

    /**
     * Method to test add method of the class.
     * @return true if all the test passed. false otherwise.
     */
    public static boolean testAdd() {

        PriorityQueueHeap<Integer> cq = new PriorityQueueHeap<>(1);
        
        cq.add(1);
        cq.add(2);
        
        if(cq.peek() != 1 || cq.size() != 1) {
            System.out.println("PriorityQueueHeap of max size 1 can't handle more than 1 things and/or first value isn't correct");
            return false;
        }

        cq = new PriorityQueueHeap<>(1);
        
        cq.add(1);
        cq.add(0);
        cq.add((-1));
        
        if(cq.peek() != -1 || cq.size() != 1) {
            System.out.println("PriorityQueueHeap of max size 1 can't handle more than 1 things and/or first value isn't correct");
            return false;
        }

        PriorityQueueHeap<Integer> cq2 = new PriorityQueueHeap<>(5);

        for(int i=0; i < 10; ++i) {
            cq2.add(i);
        }

        int counter = 0;
        for(Integer i: cq2) {
            if(i != counter++) {
                System.out.println("Queue didn't inserted correctly increasing values");
                return false;
            }
        }


        Integer[] arr = new Integer[10];
        for(int i = 0; i < 5; ++i) {
            arr[i] = i;
        }


        cq2 = new PriorityQueueHeap<>(5);

        for(int i=10; i > 0; --i) {
            cq2.add(i);
        }

        counter = 1;
        for(Integer i: cq2) {
            if(i != counter++) {
                System.out.println(" Queue didn't inserted correctly decreasing values");
                return false;
            }
        }

        cq2 = new PriorityQueueHeap<>(5);

        cq2.add(0);
        cq2.add(1);
        cq2.add(2);
        cq2.add(5);
        cq2.add(5);

        cq2.add(3);
        cq2.add(4);

        counter = 0;
        for(Integer i: cq2) {
            if(i != counter++) {
                System.out.println("Queue didn't inserted correctly increasing values");
                return false;
            }
        }

        return true;

    }

}
