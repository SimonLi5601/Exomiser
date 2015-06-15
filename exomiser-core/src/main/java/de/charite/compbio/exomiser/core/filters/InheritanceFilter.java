/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.charite.compbio.exomiser.core.filters;

import de.charite.compbio.exomiser.core.model.Gene;
import de.charite.compbio.jannovar.pedigree.ModeOfInheritance;
import java.util.Objects;

/**
 * A Gene runFilter for filtering against a particular inheritance mode.
 * 
 * @author Jules Jacobsen <jules.jacobsen@sanger.ac.uk>
 */
public class InheritanceFilter implements GeneFilter {

    private static final FilterType filterType = FilterType.INHERITANCE_FILTER;
    
    //add a token pass/failed score - this is essentially a boolean pass/fail, where 1 = pass and 0 = fail
    private final FilterResult passResult = new InheritanceFilterResult(1f, FilterResultStatus.PASS);
    private final FilterResult failResult = new InheritanceFilterResult(0f, FilterResultStatus.FAIL);

    private final ModeOfInheritance modeOfInheritance;
    
    public InheritanceFilter(ModeOfInheritance modeOfInheritance) {
        this.modeOfInheritance = modeOfInheritance;
    }

    public ModeOfInheritance getModeOfInheritance() {
        return modeOfInheritance;
    }
    
    @Override
    public FilterResult runFilter(Gene gene) {
        if (modeOfInheritance == ModeOfInheritance.UNINITIALIZED) {
            //if ModeOfInheritance.UNINITIALIZED pass the runFilter - ideally it shouldn't be applied in the first place.
            return new InheritanceFilterResult(1f, FilterResultStatus.NOT_RUN);
        }
        if (gene.isConsistentWith(modeOfInheritance)) {
            return passResult;
        } else {
            return failResult;
        }
    }

    @Override
    public FilterType getFilterType() {
        return filterType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.modeOfInheritance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InheritanceFilter other = (InheritanceFilter) obj;
        return this.modeOfInheritance == other.modeOfInheritance;
    }

    @Override
    public String toString() {
        return filterType + " filter: ModeOfInheritance=" + modeOfInheritance;
    }
    
}
