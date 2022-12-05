package test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import representation.BooleanVariable;
import datamining.Apriori;
import datamining.FPGrowth;
import datamining.ItemsetMiner;
import datamining.BooleanDatabase;

/**
 * Executable class to run benchmarks for ItemsetMiner Algorithms implementated, Apriori and FPGrowth.
 * 
 * Could have been better organized, but not enough time and motivations to do it.
 * Also due to the lack of performant enough computers and time, we can't push those test futher.
 */
public class BenchmarkItemsetMiner {
    
    /**
     * Generator for random boolean databases.
     * @param nbVariables number of variables in the database
     * @param nbTransactions number of transactions in the database
     * @param nbItemsPerTransaction average number of items per transaction
     * @return a random boolean database
     */
    public static BooleanDatabase generateRandomDatabase(int nbVariables, int nbTransactions, int nbItemsPerTransaction) {
        ArrayList<BooleanVariable> variables = new ArrayList<>(nbVariables);

        for (int i = 0; i < nbVariables; i++) {
            variables.add(new BooleanVariable("var" + i));
        }
        BooleanDatabase database = new BooleanDatabase(new HashSet<>(variables));
        // create the transactions
        Random random = new Random();
        for (int i = 0; i < nbTransactions; i++) {
            // create the transaction
            Set<BooleanVariable> transaction = new HashSet<BooleanVariable>();
            // add random items
            for (int j = 0; j < nbItemsPerTransaction; j++) {
                transaction.add(variables.get(random.nextInt(nbVariables)));
            }
            // add the transaction to the database
            database.add(transaction);
        }
        return database;
    }

    /**
     * Running benchmarks for Apriori and FPGrowth based on growing number of variables in the databases.
     * @param nbIterations number of iterations for each number of variables
     * @param frequency minimum frequency for the itemsets
     * @return a Map containing the evolution of the running time for each algorithm based on the number of variables. True for Apriori, False for FPGrowth.
     */
    public static Map<Boolean, List<Long>> benchmarkNbVariable(Integer nbIterations, Float frequency) {
        if(nbIterations == null) {
            nbIterations = 10;
        }
        if(frequency == null) {
            frequency = 0.5f;
        }
        List<Long> aprioriTimes = new LinkedList<>();
        List<Long> fpgrowthTimes = new LinkedList<>();
        

        for(int i=0; i < nbIterations; ++i) {
            BooleanDatabase database = generateRandomDatabase(2*i, 10, 2*i);
            ItemsetMiner apriori = new Apriori(database);
            ItemsetMiner fpgrowth = new FPGrowth(database);
            long t1;
            long t2;
            
            System.gc();
            t1 = System.currentTimeMillis();
            apriori.extract(frequency);
            t2 = System.currentTimeMillis();
            aprioriTimes.add(t2 - t1);
            
            System.gc();
            t1 = System.currentTimeMillis();
            fpgrowth.extract(frequency);
            t2 = System.currentTimeMillis();
            fpgrowthTimes.add(t2 - t1);
        }

        Map<Boolean, List<Long>> res = new HashMap<>();
        res.put(true, aprioriTimes);
        res.put(false, fpgrowthTimes);
        
        return res;
    }

    /**
     * Running benchmarks for Apriori and FPGrowth based on growing number of transactions.
     * @param nbIterations number of iterations for each number of variables
     * @param frequency minimum frequency for the itemsets
     * @return a Map containing the evolution of the running time for each algorithm based on the number of variables. True for Apriori, False for FPGrowth.
     */
    public static Map<Boolean, List<Long>> benchmarkNbTransaction(Integer nbIterations, Float frequency) {
        if(nbIterations == null) {
            nbIterations = 10;
        }
        if(frequency == null) {
            frequency = 0.5f;
        }
        List<Long> aprioriTimes = new LinkedList<>();
        List<Long> fpgrowthTimes = new LinkedList<>();

        for(int i=0; i < nbIterations; ++i) {
            BooleanDatabase database = generateRandomDatabase(10, 2*i, 10);
            ItemsetMiner apriori = new Apriori(database);
            ItemsetMiner fpgrowth = new FPGrowth(database);
            long t1;
            long t2;
            
            System.gc();
            t1 = System.currentTimeMillis();
            apriori.extract(frequency);
            t2 = System.currentTimeMillis();
            aprioriTimes.add(t2 - t1);
            
            System.gc();
            t1 = System.currentTimeMillis();
            fpgrowth.extract(frequency);
            t2 = System.currentTimeMillis();
            fpgrowthTimes.add(t2 - t1);
        }

        Map<Boolean, List<Long>> res = new HashMap<>();
        res.put(true, aprioriTimes);
        res.put(false, fpgrowthTimes);
        
        return res;
    }

    /**
     * Running benchmarks for Apriori and FPGrowth based on growing number of variables and transactions.
     * @param nbIterations number of iterations for each number of variables
     * @param frequency minimum frequency for the itemsets
     * @return a Map containing the evolution of the running time for each algorithm based on the number of variables. True for Apriori, False for FPGrowth.
     */
    public static Map<Boolean, List<Long>> benchmarkAll(Integer nbIterations, Float frequency) {
        if(nbIterations == null) {
            nbIterations = 10;
        }
        if(frequency == null) {
            frequency = 0.5f;
        }
        List<Long> aprioriTimes = new LinkedList<>();
        List<Long> fpgrowthTimes = new LinkedList<>();

        for(int i=0; i < nbIterations; ++i) {
            BooleanDatabase database = generateRandomDatabase(2*i, 2*i, 2*i);
            ItemsetMiner apriori = new Apriori(database);
            ItemsetMiner fpgrowth = new FPGrowth(database);
            long t1;
            long t2;
            
            System.gc();
            t1 = System.currentTimeMillis();
            apriori.extract(frequency);
            t2 = System.currentTimeMillis();
            aprioriTimes.add(t2 - t1);
            
            System.gc();
            t1 = System.currentTimeMillis();
            fpgrowth.extract(frequency);
            t2 = System.currentTimeMillis();
            fpgrowthTimes.add(t2 - t1);
        }

        Map<Boolean, List<Long>> res = new HashMap<>();
        res.put(true, aprioriTimes);
        res.put(false, fpgrowthTimes);
        
        return res;
    }

    /**
     * CSV builder for given results.
     * @param list1 list of results for Apriori
     * @param list2 list of results for FPGrowth
     * @param filename name of the file to write
     */
    public static void toCSV(List<Long> list1, List<Long> list2, String filename) {
        try {
            FileWriter file = new FileWriter("benchmark_results/"+filename);
            file.append("Apriori,FPGrowth\n");

            for(int i=0; i < list1.size(); ++i) {
                file.append(list1.get(i) + "," + list2.get(i) + "\n");
            }

            file.flush();
            file.close();
            System.out.println("CSV file created successfully: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Main method for running benchmarks.
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Starting benchmarks...\n(0/9)");
        Map<Boolean, List<Long>> res;
        res = benchmarkNbVariable(8, 0.1f);
        toCSV(res.get(true), res.get(false), "benchmarkNbVariable_0-1freq.csv");

        System.out.println("(1/9)");
        res = benchmarkNbVariable(20, 0.5f);
        toCSV(res.get(true), res.get(false), "benchmarkNbVariable_0-5freq.csv");

        System.out.println("(2/9)");
        res = benchmarkNbVariable(20, 0.9f);
        toCSV(res.get(true), res.get(false), "benchmarkNbVariable_0-9freq.csv");

        System.out.println("(3/9)");
        res = benchmarkNbTransaction(150, 0.1f);
        toCSV(res.get(true), res.get(false), "benchmarkNbTransaction_0-1freq.csv");

        System.out.println("(4/9)");
        res = benchmarkNbTransaction(1000, 0.5f);
        toCSV(res.get(true), res.get(false), "benchmarkNbTransaction_0-5freq.csv");

        System.out.println("(5/9)");
        res = benchmarkNbTransaction(1000, 0.9f);
        toCSV(res.get(true), res.get(false), "benchmarkNbTransaction_0-9freq.csv");


        System.out.println("(6/9)");
        res = benchmarkAll(10, 0.1f);
        toCSV(res.get(true), res.get(false), "benchmarkAll_0-1freq.csv");

        System.out.println("(7/9)");
        res = benchmarkAll(150, 0.5f);
        toCSV(res.get(true), res.get(false), "benchmarkAll_0-5freq.csv");

        System.out.println("(8/9)");
        res = benchmarkAll(500, 0.9f);
        toCSV(res.get(true), res.get(false), "benchmarkAll_0-9freq.csv");
        System.out.println("(9/9)\nBenchmarks finished.");

    }
}
