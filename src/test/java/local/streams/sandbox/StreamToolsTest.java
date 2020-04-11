package local.streams.sandbox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class StreamToolsTest {
    private static final IntStream randomIntsStream1 = ThreadLocalRandom.current()
            .ints(1_000_000,0,1000);
    private static final IntStream randomIntsStream2 = ThreadLocalRandom.current()
            .ints(1_000_000,0,1000);

    private static final Stream<Integer> str1 = randomIntsStream1.limit(100).boxed();
    private static final Stream<Integer> str2 = randomIntsStream2.limit(100).boxed();
    private static final List<Integer> num1 = str1.collect(Collectors.toList());
    private static final List<Integer> num2 = str2.collect(Collectors.toList());
    private static final ArrayList<Integer> listOfNumbers = new ArrayList<>();
    private static final ArrayList<Stream<Integer>> listOfStreams = new ArrayList<>();

    @BeforeEach
    void setUp() {
        listOfNumbers.clear();
        listOfStreams.clear();
        listOfNumbers.addAll(num1);
        listOfNumbers.addAll(num2);
        listOfStreams.add(num1.stream());
        listOfStreams.add(num2.stream());
    }

    @Test
    void buildEmpty() {
        assertEquals(0, StreamTools.builder().build().toArray().length);
    }

    @Test
    void buildFromStream() {
        assertArrayEquals(num1.toArray(),
                StreamTools.builder(num1.stream()).build().toArray());
    }

    @Test
    void buildFromStreamsArray() {
        assertArrayEquals(listOfNumbers.toArray(),
                StreamTools.builder(num1.stream(),num2.stream()).build().toArray());
    }

    @Test
    void buildFromStreamsCollection() {
        assertArrayEquals(listOfNumbers.toArray(),
                StreamTools.builder(listOfStreams).build().toArray());
    }

    @Test
    void appendStream() {
        assertArrayEquals(num1.toArray(),
                StreamTools.builder()
                        .append(num1.stream())
                        .build().toArray()
        );
    }

    @Test
    void appendArray() {
        assertArrayEquals(listOfNumbers.toArray(),
                StreamTools.builder()
                        .append(num1.stream(), num2.stream())
                        .build().toArray()
        );
    }

    @Test
    void appendCollection() {
        assertArrayEquals(listOfNumbers.toArray(),
                StreamTools.builder()
                        .append(listOfStreams)
                        .build().toArray()
        );
    }

    @Test
    void shuffle() {
        Stream<Integer> shuffledStream = StreamTools.builder()
                .append(num1.stream())
                .append(num2.stream())
                .shuffle()
                .build();

        List<Integer> listFromStream = shuffledStream.collect(Collectors.toList());

        assertTrue(
                listOfNumbers.size() == listFromStream.size()
                && listOfNumbers.containsAll(listFromStream)
                && listFromStream.containsAll(listOfNumbers)
                && !listOfNumbers.toArray().equals(listFromStream.toArray())
        );
    }
}