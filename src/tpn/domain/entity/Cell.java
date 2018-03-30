package tpn.domain.entity;

public class Cell {
   
    private int value;
    private boolean merged;
    private boolean modified;
    private int x;
    private int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Cell(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }
    
    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    @Override 
    public boolean equals(Object other) {
        return (other instanceof Cell)
                && ((Cell)other).getValue() == this.getValue();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + this.value;
        return hash;
    }

    /**
     * @return the merged
     */
    public boolean isMerged() {
        return merged;
    }

    /**
     * @param merged the merged to set
     */
    public void setMerged(boolean merged) {
        this.merged = merged;
    }
    
    public void clear() {
        this.merged = false;
        this.value = 0;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the modified
     */
    public boolean isModified() {
        return modified;
    }

    /**
     * @param modified the modified to set
     */
    public void setModified(boolean modified) {
        this.modified = modified;
    }
    
}
