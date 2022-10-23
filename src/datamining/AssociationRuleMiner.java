package datamining;

import java.util.*;
import representation.*;

public interface AssociationRuleMiner {
    
    public BooleanDatabase getDatabase();

    public Set<AssociationRule> extract(float minFreq, float minConfidence);
}
