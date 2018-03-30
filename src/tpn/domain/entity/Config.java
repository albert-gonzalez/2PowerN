package tpn.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Config {
    private int size = 4;
    private int target;
    private List<Integer> firstValues;

    public Config() {
       this.size = 4;
       this.target = 1024;
       this.firstValues = new ArrayList(); 
       this.firstValues.add(2);
       this.firstValues.add(2);
       this.firstValues.add(4);
    } 
    
    public Config(int size, int target, List<Integer> firstValues) {
        this.size = size;
        this.target = target;
        this.firstValues = firstValues;
    }
    
    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the target
     */
    public int getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(int target) {
        this.target = target;
    }

    /**
     * @return the firstValues
     */
    public List<Integer> getFirstValues() {
        return firstValues;
    }
    
    /**
     * @param firstValues the firstValues to set
     */
    public void setFirstValues(List<Integer> firstValues) {
        this.firstValues = firstValues;
    }
    
    
}
