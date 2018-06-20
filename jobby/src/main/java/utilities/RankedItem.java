/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author AmineD
 */
public class RankedItem implements Comparable{
    
    private int iAlternative;
    private float rang;

    public RankedItem(int iAlternative, float rang) {
        this.iAlternative = iAlternative;
        this.rang = rang;
    }

    public int getiAlternative() {
        return iAlternative;
    }

    public void setiAlternative(int iAlternative) {
        this.iAlternative = iAlternative;
    }

    public float getRang() {
        return rang;
    }

    public void setRang(float rang) {
        this.rang = rang;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RankedItem other = (RankedItem) obj;
        if (Float.floatToIntBits(this.rang) != Float.floatToIntBits(other.rang)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "iAlternative=" + iAlternative + ", rang=" + rang + '}';
    }

    @Override
    public int compareTo(Object o) {
        RankedItem rankedItem;
        try{
            rankedItem=(RankedItem)o;
            return Float.compare(rang,rankedItem.rang);
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
    
    
    
}
