package Sort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

/**
 * Created by ruslan on 19.11.16.
 */
public class CompareSort {
    ArrayList<Compare> compares = new ArrayList<>();
    HashSet<Compare> hashSet = new HashSet<>();

    public static void main(String[] args) {
        new CompareSort().go();
    }
    class ComprateName1 implements Comparator<Compare> {
        public int compare(Compare one, Compare two) {
            return one.getName2().compareTo(two.getName2());
        }
    }
    public void go() {
        addArray();
        hashSet.addAll(compares);
        System.out.println(hashSet);
        System.out.println(compares.toString());
        Collections.sort(compares, new ComprateName1());
        for (Compare c: compares)
            System.out.print(c.getName2()+" ");
        System.out.println();
        System.out.println(compares);
        Collections.sort(compares);

        System.out.println(compares);
//        for (Compare c: compares)
//            System.out.println(c.getName1());
    }
    void addArray() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("/home/ruslan/Рабочий стол/MediaPlayer/src/Sort/sss.txt")));
            String ms;
            while ((ms = reader.readLine())!=null) {
                splitString(ms);
            }
        } catch (Exception e) {}
    }
    void splitString(String ms) {
        String[] mas = ms.split("/");
        Compare ne = new Compare(mas[0],mas[1]);
        compares.add(ne);
    }
}
