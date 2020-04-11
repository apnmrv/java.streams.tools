package java.streams.toolkit;

import java.util.Collection;
import java.util.stream.Stream;

public class StreamTools {
    public static<T> Stream<T> concatenate(Stream<Stream<T>> streams){
        return streams.reduce(Stream.of(), Stream::concat);
    }

    @SafeVarargs
    public static<T> Stream<T> concatenate(Stream<T>... streams){
        return concatenate(Stream.of(streams));
    }

    public static<T> Stream<T> concatenate(Collection<Stream<T>> streams){
        return concatenate(streams.stream());
    }

    public static<T> Stream<T> shuffle(Stream<T> stream){
        return stream.parallel();
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

        private Builder(Stream<T> stream) {
            _stream = stream;
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

        public Builder<T> shuffle(){
            _stream = StreamTools.shuffle(_stream);
            return this;
        }

        public Stream<T> build(){
            return _stream;
        }
    }
}
