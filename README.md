## java-8-matchers

[![Build Status](https://travis-ci.org/unruly/java-8-matchers.svg?branch=master)](https://travis-ci.org/unruly/java-8-matchers)

Hamcrest Matchers for Java 8 features

Contains matchers for:

* java.util.Optional (and primitive variants)
* java.util.Stream (and primitive variants)
* java.time.Temporal (Instant, LocalDateTime, various other calendar systems)
* java.time.TemporalAmount (Duration and Period)

# Installation

Available from the Central Repository. In Maven style:

```xml
<dependency>
  <groupId>co.unruly</groupId>
  <artifactId>java-8-matchers</artifactId>
  <version>1.2</version>
</dependency>
```

A test-jar is also available to gain access to a helper for testing the error messages of failing
Matchers.

```xml
<type>test-jar</type>
```

# Examples

Below are some examples of the java-8-matchers API

### Optionals
```java
// Contents of Optional
assertThat(Optional.of("Hi!"), OptionalMatchers.contains("Hi!"));

// Contents of Optional with Matchers
assertThat(Optional.of(4), OptionalMatchers.contains(Matchers.greaterThan(3)));

// Assert empty
assertThat(Optional.empty(), OptionalMatchers.empty());
```
### Streams
```java
// Stream is empty
assertThat(Stream.empty(), StreamMatchers.empty());

// Stream contains elements
assertThat(Stream.of("a", "b", "c"), StreamMatchers.contains("a", "b", "c"));

// Stream has only elements matching specified Matcher
assertThat(Stream.of("bar","baz"), StreamMatchers.allMatch(containsString("a")));

// Stream contains at least one element matching specific Matcher
assertThat(Stream.of("foo", "bar", "baz", "waldo"), StreamMatchers.anyMatch(containsString("ald")));

// Stream has declared first elements (iterates over whole stream)
assertThat(Stream.of("a","b","c","d","e"), StreamMatchers.startsWith("a", "b", "c"));

// Stream has declared first elements (only pull first 10 values)
assertThat(Stream.iterate(0,i -> i + 1), StreamMatchers.startsWith(Stream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 10));

// Stream matches all objects within limit
assertThat(Stream.generate(() -> 10), StreamMatchers.startsWithAll(Matchers.equalTo(10), 100));

// Stream matches at least one object within limit
assertThat(Stream.iterate(0, i -> i + 1), StreamMatchers.startsWithAny(Matchers.equalTo(10), 100));
```
## Time
```java
// Time is before another
assertThat(Instant.parse("2014-01-01T00:00:00.00Z"), TimeMatchers.before(Instant.parse("2015-01-01T00:00:00.00Z")));

// Time is after another
assertThat(LocalDate.parse("2015-01-01"), TimeMatchers.after(LocalDate.parse("2014-01-01")));

// Time is between two limits
assertThat(Instant.parse("2015-01-01T00:00:00.00Z"), TimeMatchers.between(
  Instant.parse("2014-01-01T00:00:00.00Z"),
  Instant.parse("2016-01-01T00:00:00.00Z")
));

// Duration is longer than another
assertThat(Duration.ofMinutes(4), TimeMatchers.longerThan(Duration.ofSeconds(4)));

// Duration is shorter than another
assertThat(Duration.ofSeconds(4), TimeMatchers.shorterThan(Duration.ofMinutes(4)));

// Period matches element-wise
assertThat(Period.of(1, 2, 3), TimeMatchers.matches(equalTo(1), equalTo(2), equalTo(3)));
```
