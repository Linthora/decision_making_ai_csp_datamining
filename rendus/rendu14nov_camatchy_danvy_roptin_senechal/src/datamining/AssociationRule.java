package datamining;

import representation.BooleanVariable;

import java.util.*;

/**
 * A class which represents an association rule.
 * An association rule is like an implication but with a confidence value which is the probability that the antecedent implies the consequent.
 */
public class AssociationRule {

    /**
     * The premise and conclusion of the rule.
     */
    protected Set<BooleanVariable> premise, conclusion;

    /**
     * The frequency and confidence of the rule.
     */
    protected float frequency, confiance;

    /**
     * Creates a new association rule given its antecedent, its consequent, its frequency and its confidence.
     * @param premise the antecedent of the rule.
     * @param conclusion the consequent of the rule.
     * @param freq the frequency of the rule.
     * @param conf the confidence of the rule.
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion, float freq, float conf) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = freq;
        this.confiance = conf;
    }

    /**
     * Returns the premise of the rule.
     * @return the premise of the rule.
     */
    public Set<BooleanVariable> getPremise() {
        return this.premise;
    }

    /**
     * Returns the conclusion of the rule.
     * @return the conclusion of the rule.
     */
    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }

    /**
     * Returns the frequency of the rule.
     * @return the frequency of the rule.
     */
    public float getFrequency() {
        return this.frequency;
    }

    /**
     * Returns the confidence of the rule.
     * @return the confidence of the rule.
     */
    public float getConfidence() {
        return this.confiance;
    }

    @Override
    public String toString() {
        return "AssociationRule: [premise=" + premise + ", conclusion=" + conclusion + ", frequency=" + frequency + ", confidence=" + confiance + "]";
    }

}
