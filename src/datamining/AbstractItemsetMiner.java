package datamining;

import java.util.*;
import representation.*;

public abstract class AbstractItemsetMiner implements ItemsetMiner {

    protected BooleanDatabase base;
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    public AbstractItemsetMiner(BooleanDatabase base) {
        this.base = base;
    }

    public BooleanDatabase getDatabase() {
        return this.base;
    }

    public float frequency(Set<BooleanVariable> motif) {
        List<Set<BooleanVariable>> transactions = this.base.getTransactions();

        int counter = 0;

        for(Set<BooleanVariable> transaction: transactions) {
            if(transaction.containsAll(motif)) {
                counter++;
            }
        }
        return (float) counter / (float) transactions.size();
    }
    
}

// 3 termes:

// item: un elem atomique (representé par var éventuellement), servant a composer 
// tout ce qu'on va traiter par la suite (ex ens des produit)

// motif: ens d'item construit qu'on a chercher; sous ensemble ditems 
// (singleton, pas forcement dans la transaction)

// transaction: ens d'item 