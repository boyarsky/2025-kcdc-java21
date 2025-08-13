import java.util.SequencedSet;
import java.util.TreeSet;

public class Module2 {

    // must be an interface rather than a class because Weekday is a record and cannot subclass
    public sealed interface AnyDay permits Weekday, Weekend {}

    public non-sealed class Weekend implements AnyDay {

    }

    public record Weekday(String dayOfWeek) implements AnyDay {

        public Weekday {
            if ("Saturday".equals(dayOfWeek) || "Sunday".equals(dayOfWeek)) {
                throw new IllegalArgumentException("weekends not allowed");
            }
        }

        @Override
        public String dayOfWeek() {
            return "Day: " + dayOfWeek;
        }
    }

    public static void main(String[] args) {
        Weekday weekday = new Weekday("Monday");
        System.out.println(weekday.dayOfWeek());
        System.out.println(weekday.toString());

        new Weekday("Sunday");
    }
}
