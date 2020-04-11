import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Tests {
    public static void main(String[] args) {

        Stream<Integer> stream = Stream.of(1,2,3);

        List<Integer> list = stream.collect(Collectors.toList());

        Stream.generate(() -> list.stream())
                .limit(3)
                .flatMap(s -> s)
                .forEach(System.out::println);
    }
}
