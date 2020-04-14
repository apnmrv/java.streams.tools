package jstreams.toolkit;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamToolsFunc {
    public static<T> Function<Stream<Stream<T>>, Stream<T>> concatenate(Stream<Stream<T>> streams){
        return (Stream<Stream<T>> str) -> streams.flatMap(s -> s);
    }

    @SafeVarargs
    public static<T> Function<Stream<Stream<T>>, Stream<T>> concatenate(Stream<T>... streams){
        return concatenate(Stream.of(streams));
    }

    public static<T> Function<Stream<Stream<T>>, Stream<T>> concatenate(Collection<Stream<T>> streams){
        return concatenate(streams.stream());
    }

//    public static<T> Function<Stream<T>, Stream<T>> shuffle(Stream<T> stream){
//        return (Stream<T> s) -> {
//            Collectors.collectingAndThen(
//                    toList(),
//                    list -> {
//                        Collections.shuffle(list);
//                        return list.stream();
//                    });
//            return list;
//        };
//    }

//    public static <T> Combiner combiner(){
//        return new Combiner();
//    }
//
//    public static <T> Combiner combiner(Stream<T>... streams){
//        return new Combiner(streams);
//    }
//
//    public static <T> Combiner combiner(Collection<Stream<T>> streams){
//        return new Combiner<T>(streams);
//    }
//
//    public static class Combiner<T> {
//        private Stream<T> _stream = Stream.empty();
//
//        private Combiner(){
//        }
//
//        @SafeVarargs
//        private Combiner(Stream<T>... streams) {
//            _stream = StreamToolsFunc.concatenate(streams);
//        }
//
//        private Combiner(Collection<Stream<T>> streams) {
//            _stream = StreamToolsFunc.concatenate(streams);
//        }
//
//        @SafeVarargs
//        public final Combiner<T> append(Stream<T>... streams){
//            _stream = StreamToolsFunc.concatenate(_stream, StreamToolsFunc.concatenate(streams));
//            return this;
//        }
//
//        public final Combiner<T> append(Collection<Stream<T>> streams){
//            _stream = StreamToolsFunc.concatenate(_stream, StreamToolsFunc.concatenate(streams));
//            return this;
//        }
//
//        public final Combiner<T> append(Stream<T> stream, int times) {
//            assert times > 0;
//            Collection<T> fStream = fixedStream(stream);
//            return append(Stream.generate(() -> fStream.stream())
//                    .limit(times).flatMap(s -> s));
//        }
//
//        public Combiner<T> shuffle(){
//            _stream = StreamToolsFunc.shuffle(_stream);
//            return this;
//        }
//
//        public Stream<T> build(){
//            return _stream;
//        }
//
//        private Collection<T> fixedStream(Stream<T> stream) {
//            return stream.collect(toList());
//        }
//    }
}
