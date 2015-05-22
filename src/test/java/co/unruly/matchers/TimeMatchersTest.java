package co.unruly.matchers;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class TimeMatchersTest {

    private Instant instant2016 = Instant.parse("2016-01-01T00:00:00.00Z");
    private Instant instant2015 = Instant.parse("2015-01-01T00:00:00.00Z");
    private Instant instant2014 = Instant.parse("2014-01-01T00:00:00.00Z");
    private LocalDate localDate2015 = LocalDate.parse("2015-01-01");
    private LocalDate localDate2014 = LocalDate.parse("2014-01-01");

    @Test
    public void after_successInstant() throws Exception {
        assertThat(instant2015, TimeMatchers.after(instant2014));
    }

    @Test
    public void after_successLocalDate() throws Exception {
        assertThat(localDate2015, TimeMatchers.after(localDate2014));
    }

    @Test
    public void after_failureMessage() throws Exception {
        Helper.testFailingMatcher(instant2014, TimeMatchers.after(instant2015), "after <2015-01-01T00:00:00Z>","2014-01-01T00:00:00Z");
    }

    @Test
    public void before_successInstant() throws Exception {
        assertThat(instant2014, TimeMatchers.before(instant2015));
    }

    @Test
    public void before_successLocalDate() throws Exception {
        assertThat(localDate2014, TimeMatchers.before(localDate2015));
    }

    @Test
    public void beforeTemporal_failureMessage() throws Exception {
        Helper.testFailingMatcher(instant2015, TimeMatchers.before(instant2014), "before <2014-01-01T00:00:00Z>","2015-01-01T00:00:00Z");
    }

    @Test
    public void betweenTemporal_successSameTimes() throws Exception {
        assertThat(instant2015, TimeMatchers.between(instant2015, instant2015));
    }

    @Test
    public void betweenTemporal_successDifferentTimes() throws Exception {
        assertThat(instant2015, TimeMatchers.between(instant2014, instant2016));
    }

    @Test
    public void betweenTemporal_failureBefore() throws Exception {
        Helper.testFailingMatcher(instant2014, TimeMatchers.between(instant2015, instant2016), "between <2015-01-01T00:00:00Z> and <2016-01-01T00:00:00Z> inclusive", "<2014-01-01T00:00:00Z>");
    }

    @Test
    public void betweenTemporal_failureAfter() throws Exception {
        Helper.testFailingMatcher(instant2016,TimeMatchers.between(instant2014,instant2015),"between <2014-01-01T00:00:00Z> and <2015-01-01T00:00:00Z> inclusive","<2016-01-01T00:00:00Z>");
    }

    @Test
    public void longer_success() throws Exception {
        assertThat(Duration.ofMinutes(4), TimeMatchers.longerThan(Duration.ofSeconds(4)));
    }

    @Test
    public void longer_failureMessage() throws Exception {
        Helper.testFailingMatcher(Duration.ofMillis(1), TimeMatchers.longerThan(Duration.ofSeconds(4)), "longer than <PT4S>", "<PT0.001S>");
    }

    @Test
    public void shorter_success() throws Exception {
        assertThat(Duration.ofSeconds(4), TimeMatchers.shorterThan(Duration.ofMinutes(4)));
    }

    @Test
    public void shorter_failureMessage() throws Exception {
        Helper.testFailingMatcher(Duration.ofDays(1), TimeMatchers.shorterThan(Duration.ofSeconds(4)), "shorter than <PT4S>", "<PT24H>");
    }

    @Test
    public void betweenTemporalAmount_successSame() throws Exception {
        assertThat(Duration.ofSeconds(3), TimeMatchers.between(Duration.ofSeconds(3), Duration.ofSeconds(3)));
    }

    @Test
    public void periodMatcher_success() throws Exception {
        assertThat(Period.of(1, 2, 3), TimeMatchers.matches(equalTo(1), equalTo(2), equalTo(3)));
    }

    @Test
    public void periodMatcher_failureMessage() throws Exception {
        Helper.testFailingMatcher(Period.of(4, 5, 6), TimeMatchers.matches(equalTo(1), equalTo(2), equalTo(3)), "a Period with years matching <<1>> months matching <<1>> and days matching <<3>>", "P4Y5M6D");
    }

/*    @Test
    public void shouldNotCompileWithUnlikeTimes() throws Exception {
        assertThat(instant2014, TimeMatchers.after(localDate2015));
    }

    @Test
    public void shouldNotCompileWithNonTimes() throws Exception {
        assertThat(Integer.valueOf(3), TimeMatchers.after(Integer.valueOf(4)));
    }*/
}