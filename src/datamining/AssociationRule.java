package datamining;

import representation.BooleanVariable;

import java.util.*;
public class AssociationRule {
    protected Set<BooleanVariable> premisse, conclusion;
    protected float frequency, confiance;

    public AssociationRule(Set<BooleanVariable> premisse, Set<BooleanVariable> conclusion, float freq, float conf) {
        this.premisse = premisse;
        this.conclusion = conclusion;
        this.frequency = freq;
        this.confiance = conf;
    }

    public Set<BooleanVariable> getPremise() {
        return this.premisse;
    }

    public Set<BooleanVariable> getConclusion() {
        return this.conclusion;
    }

    public float getFrequency() {
        return this.frequency;
    }

    public float getConfidence() {
        return this.confiance;
    }

    @Override
    public String toString() {
        return "ASSOCIATION RULE: premisse : " + this.premisse + "----- conclusion: " + this.conclusion+" --- freq: " + this.frequency + " ---- confidence : "+ this.confiance +"   ]";
    }

}
