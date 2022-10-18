package datamining;

import java.util.*;
import representation.*;

public interface ItemsetMiner {
    public BooleanDatabase getDatabase();
    
    // retourne l'ensemble des itemsets (!= null) ayant au moins cette fréq dans la base.
    public Set<Itemset> extract(float frequency);
}
