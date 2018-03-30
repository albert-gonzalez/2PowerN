package tpn.domain.entity;

import java.util.Objects;

public class Move {

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

    /**
     * @return the mergedAtX
     */
    public int getMergedAtX() {
        return mergedAtX;
    }

    /**
     * @param mergedAtX the mergedAtX to set
     */
    public void setMergedAtX(int mergedAtX) {
        this.mergedAtX = mergedAtX;
    }

    /**
     * @return the mergedAtY
     */
    public int getMergedAtY() {
        return mergedAtY;
    }

    /**
     * @param mergedAtY the mergedAtY to set
     */
    public void setMergedAtY(int mergedAtY) {
        this.mergedAtY = mergedAtY;
    }

    public enum Type{TRANSLATION, FUSION};
    
    private Type type;
    private int initX;
    private int initY;
    private int destX;
    private int destY;
    private boolean merged = false;
    private int mergedAtX;
    private int mergedAtY;
    private int value;
    
    public Move(Type type, int value, int initX, int initY, int destX, int destY) {
        this.type = type;
        this.value  = value;
        this.initX = initX;
        this.initY = initY;
        this.destX = destX;
        this.destY = destY;
    }
    
    public Move(Type type, int value, int initX, int initY, 
            int destX, int destY, int mergedAtX, int mergedAtY) {
        this.type = type;
        this.value  = value;
        this.initX = initX;
        this.initY = initY;
        this.destX = destX;
        this.destY = destY;
        this.mergedAtX = mergedAtX;
        this.mergedAtY = mergedAtY;
        this.merged = mergedAtX >= 0 && mergedAtY >= 0;
    }
    
    
    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return the initX
     */
    public int getInitX() {
        return initX;
    }

    /**
     * @param initX the initX to set
     */
    public void setInitX(int initX) {
        this.initX = initX;
    }

    /**
     * @return the initY
     */
    public int getInitY() {
        return initY;
    }

    /**
     * @param initY the initY to set
     */
    public void setInitY(int initY) {
        this.initY = initY;
    }

    /**
     * @return the destX
     */
    public int getDestX() {
        return destX;
    }

    /**
     * @param destX the destX to set
     */
    public void setDestX(int destX) {
        this.destX = destX;
    }

    /**
     * @return the destY
     */
    public int getDestY() {
        return destY;
    }

    /**
     * @param destY the destY to set
     */
    public void setDestY(int destY) {
        this.destY = destY;
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
        if (other == null || !(other instanceof Move)) {
            return false;
        }
        
        Move otherMove = (Move) other;
        
        return this.destX == otherMove.destX && this.destY == otherMove.destY
                && this.initX == otherMove.initX && this.initY == otherMove.initY
                && this.value == otherMove.value && this.type == otherMove.type;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.type);
        hash = 73 * hash + this.initX;
        hash = 73 * hash + this.initY;
        hash = 73 * hash + this.destX;
        hash = 73 * hash + this.destY;
        hash = 73 * hash + this.value;
        return hash;
    }
    
    public String toString() {
        return this.type + ": " + this.value + " - "
                + " init(" + this.initX + ", " + this.initY + "), "
                + "dest(" + this.destX + ", " + this.destY + ") ";
    }
    
    
}
