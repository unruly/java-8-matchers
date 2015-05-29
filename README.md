## java-8-matchers
Hamcrest Matchers for Java 8 features

Contains matchers for:

* java.util.Stream (and primitive variants)
* java.util.Optional (and primitive variants)
* java.time.Temporal (Instant, LocalDateTime, various other calendar systems)
* java.time.TemporalAmount (Duration and Period)

# Installation

Available from the Central Repository. In Maven style:

        <dependency>
            <groupId>co.unruly</groupId>
            <artifactId>java-8-matchers</artifactId>
            <version>1.1</version>
        </dependency>

A test-jar is also available to gain access to a helper for testing the error messages of failing
Matchers.

            <type>test-jar</type>

# Examples

    assertThat(Optional.of("Hi!"),
        OptionalMatchers.contains("Hi!"));
    assertThat(Optional.of(4),
        OptionalMatchers.contains(Matchers.greaterThan(3)));
    assertThat(Optional.empty(),
        OptionalMatchers.empty());

    assertThat(Stream.empty(),
        StreamMatchers.empty());
    assertThat(Stream.of("a", "b", "c"),
        StreamMatchers.contains("a", "b", "c"));
    assertThat(Stream.of("bar","baz"),
        StreamMatchers.allMatch(containsString("a")));
    assertThat(Stream.of("bar", "bar", "foo", "grault", "garply", "waldo"),
        StreamMatchers.anyMatch(containsString("ald")));
    assertThat(Stream.of("a","b","c","d","e", "f", "g", "h"),
        StreamMatchers.startsWith("a", "b", "c", "d", "e"));
    assertThat(Stream.iterate(0,i -> i + 1),
        StreamMatchers.startsWith(Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 10));
    assertThat(Stream.generate(() -> 10),
        StreamMatchers.startsWithAll(Matchers.equalTo(10), 100));
    assertThat(Stream.iterate(0, i -> i + 1),
        StreamMatchers.startsWithAny(Matchers.equalTo(10), 100));

    assertThat(Instant.parse("2014-01-01T00:00:00.00Z"),
        TimeMatchers.before(Instant.parse("2015-01-01T00:00:00.00Z")));
    assertThat(LocalDate.parse("2015-01-01"),
        TimeMatchers.after(LocalDate.parse("2014-01-01")));
    assertThat(Instant.parse("2015-01-01T00:00:00.00Z"),
        TimeMatchers.between(Instant.parse("2014-01-01T00:00:00.00Z"),Instant.parse("2016-01-01T00:00:00.00Z")));
    assertThat(Duration.ofMinutes(4),
        TimeMatchers.longerThan(Duration.ofSeconds(4)));
    assertThat(Duration.ofSeconds(4),
        TimeMatchers.shorterThan(Duration.ofMinutes(4)));
    assertThat(Period.of(1, 2, 3),
        TimeMatchers.matches(equalTo(1), equalTo(2), equalTo(3)));