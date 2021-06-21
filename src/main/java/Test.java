import dao.DBService;
import entity.PivotProductFX;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) throws Exception{
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, -1, 2, 3, 4, 5, 5, 6, 7);
        Stream<Integer> stream = list.stream();

        System.out.println(stream.reduce((x, y) -> {
            System.out.print("x - " + x);
            System.out.println(" : y - " + y);
            return x + y;
        }).get());

        



    }
}
