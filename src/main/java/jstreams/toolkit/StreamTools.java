package jstreams.toolkit;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamTools {
    public static<T> Stream<T> concatenate(Stream<Stream<T>> streams){
        return streams.flatMap(s -> s);
    }

    @SafeVarargs
    public static<T> Stream<T> concatenate(Stream<T>... streams){
        return concatenate(Stream.of(streams));
    }

    public static<T> Stream<T> concatenate(Collection<Stream<T>> streams){
        return concatenate(streams.stream());
    }

    public static<T> Stream<T> shuffle(Stream<T> stream){
        List<T> l = stream.collect(Collectors.toList());
        Collections.shuffle(l);
        return l.stream();
    }

    public static <T>Builder builder(){
        return new Builder();
    }

    public static <T> Builder builder(Stream<T>... streams){
        return new Builder(streams);
    }

    public static <T> Builder builder(Collection<Stream<T>> streams){
        return new Builder<T>(streams);
    }

    public static class Builder<T> {
        private Stream<T> _stream = Stream.empty();

        private Builder(){
        }

        @SafeVarargs
        private Builder(Stream<T>... streams) {
            _stream = StreamTools.concatenate(streams);
        }

        private Builder(Collection<Stream<T>> streams) {
            _stream = StreamTools.concatenate(streams);
        }

        @SafeVarargs
        public final Builder<T> append(Stream<T>... streams){
            _stream = StreamTools.concatenate(_stream, StreamTools.concatenate(streams));
            return this;
        }

        public final Builder<T> append(Collection<Stream<T>> streams){
            _stream = StreamTools.concatenate(_stream, StreamTools.concatenate(streams));
            return this;
        }

        public final Builder<T> append(Stream<T> stream, int times) {
            assert times > 0;
            Collection<T> fStream = fixedStream(stream);
            return append(Stream.generate(() -> fStream.stream())
                    .limit(times).flatMap(s -> s));
        }

        public Builder<T> shuffle(){
            _stream = StreamTools.shuffle(_stream);
            return this;
        }

        public Stream<T> build(){
            return _stream;
        }

        private Collection<T> fixedStream(Stream<T> stream) {
            return stream.collect(Collectors.toList());
        }
    }
}
