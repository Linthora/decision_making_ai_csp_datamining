package datamining;

import java.util.*;

public interface AssociationRuleMiner {
    
    public BooleanDatabase getDatabase();

    public Set<AssociationRule> extract(float minFreq, float minConfidence);
}
