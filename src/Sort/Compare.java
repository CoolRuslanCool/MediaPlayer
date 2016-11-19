package Sort;

import java.util.Comparator;

/**
 * Created by ruslan on 19.11.16.
 */
public class Compare implements Comparable<Compare>{
    String name1;
    String name2;
    public Compare(String n1,String n2) {
        name1 = n1;
        name2 = n2;
    }
    public String getName1() {
        return name1;
    }
    public String getName2() {
        return name2;
    }
    public String toString() {
        return name1;
    }
    public boolean equals(Object c) {
        Compare co = (Compare) c;
        return getName1().equals(co.getName1());
    }
    public int hashCode() {
        return name1.hashCode();
    }
    public int compareTo(Compare o) {
        return name1.compareTo(o.getName1());

}
}
