import jstreams.toolkit.ShuffleCollector;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        System.out.println("Hello!");

        List<Integer> l1 = ThreadLocalRandom.current()
                .ints(10,100)
                .boxed()
                .distinct()
                .limit(10)
                .collect(Collectors.toList());
        l1.forEach(i -> System.out.println(i + ':'));
        System.out.println();
////                newStream().limit(10).collect(Collectors.toList());
        Collections.addAll(l1, 1,2,3,4,5);

        l1.forEach(i -> System.out.print(i + ":"));
        System.out.println();
        l1.stream().forEach(i -> System.out.print(i + ":"));
        System.out.println();
        l1.stream()
                .collect(ShuffleCollector.shuffle())
                .forEach(i -> System.out.print(i + ":"));

        System.exit(0);
    }
}
