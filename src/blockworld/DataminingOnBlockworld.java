package blockworld;

import java.util.*;

import representation.*;

import bwgeneratordemo.Demo;

import datamining.*;

public class DataminingOnBlockworld {
    
    public static void main(String[] args) {

        System.out.println("Blockworld datamining demo: BEGIN");
        WorldWithBooleanVariable world = new WorldWithBooleanVariable(Demo.NB_BLOCKS, Demo.NB_STACKS);
        System.out.println("Demo nb blocks: " + Demo.NB_BLOCKS + ", nb stacks: " + Demo.NB_STACKS);

        BooleanDatabase database = new BooleanDatabase(world.getBooleanVariables());

        int nb_transactions = 10_000;

        Random rand = new Random();

        for(int i=0; i < nb_transactions; ++i) {
            List<List<Integer>> state = Demo.getState(rand);

            Set<BooleanVariable> transaction = WorldWithBooleanVariable.getCorrespondingState(state, Demo.NB_BLOCKS, Demo.NB_STACKS);

            database.add(transaction);
        }

        String itemSetMinerUsed = "FP-Growth";
        AssociationRuleMiner miner = new BruteForceAssociationRuleMiner(database, true);
        if(args.length > 0) {
            if(args[0].equals("apriori")) {
                miner = new BruteForceAssociationRuleMiner(database, false);
                itemSetMinerUsed = "Apriori";
                nb_transactions = 10_000;
            }
        }

        float minFreq = (float) (2f/3f);
        float minConf = .95f;

        Set<AssociationRule> rules = miner.extract(minFreq, minConf);

        System.out.println("Itemset miner used: " + itemSetMinerUsed);
        System.out.println("Mining with minFreq = " + minFreq + " and minConf = " + minConf);
        System.out.println("On a number of transactions of " + database.getTransactions().size() + " and a number of variables of " + database.getItems().size());
        System.out.println("NB rules found: " + rules.size());

        System.out.println("The rules found:\n");
        int counter = 1;
        for(AssociationRule rule: rules) {
            System.out.println((counter++) + ": "+ rule.getPremise() + "\n\t\t => " + rule.getConclusion());

            System.out.println("\t\t\tConfidence: " + rule.getConfidence());
            System.out.println("\t\t\tfrequency: " + rule.getFrequency());

            System.out.println("");
        }

        System.out.println("Datamining demo: END");

        System.out.println("\nConclusion of the results:");
        System.out.println("The rules found express 2 things: \n\tthe first one being, that if a block is under another block, it is fixed. It is funny to note that this is one of the constraint we added to the blockworld.\n");
        System.out.println("\tthe second is on the way the BWgenerator build the states: It seem that the majority of the time, 2 is on 1 and 1 is on 0. It seems that the generator randomness isn't uniform. An hypothesis would be that the generator add each block with an ascending for loop and put the block on a stack with a random number based on a normal distribution and then shuffles the stacks. This would explain this observed regularity, however I am not sure at all about this and it would require more investigation (like to try and recreate this from our hypothesis, or mine with other settings (which I looked into a little but wasn't conclusive about why the other value didn't appeared on this stack) or try and generate with the same generator worlds with more block and stack but that doesn't seem possible). (Assuming of course we don't want to use a decompiler and directly in the code of the generator.)");
    }
}
