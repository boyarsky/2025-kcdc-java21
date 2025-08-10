# 2025-kcdc-java21

This repository includes the lab of my KCDC Java 21 along with the lab solutions. The lab assignment is as follows.

Note that you are allowed to use Google or the Java 21 JavaDoc for any of these. You are not expected to have memorized the methods from the presentation.

You can use https://dev.java/playground/ for modules 1-3. See tips at: https://www.selikoff.net/2025/08/10/using-the-java-playground/

# Module 1

1. Use a text block to print a tic tac toe grid that looks something like this:
```
-------------------------------
|         |         |         |
-------------------------------
|         |         |         |
-------------------------------
|         |         |         |
-------------------------------
```
2. Add a title to the beginning of your text block that includes quotes. For example "My board"
3. Add a description to the end of your text block that includes \ and \s and essential whitespace
4. Add incidental whitespace to your text block if it isn't already there
5. Create a SequencedSet containing your three text blocks. Print out the set in the default and reversed order
6. Explore using strip(), stripIndent(), stripLeading() and stripTrailing() on your code
7. Look at the solution of a neighbor to see what your code has in common/different. (Yes, I'm asking you to actually talk to another human being.)

Optional: Ask AI to generate some multiple choice questions on the topic in the module. How did it do?

# Module 2

1. Create a public record named Weekday that takes in a String for the day of the week. Call it from a main method that prints the day using the getter and also using toString().
2. Customize the getter so it formats the result as "Day: Monday"
3. Add a compact constructor to the method that throws an exception if a weekend is passed in
4. Create a public sealed interface called AnyDay and have your existing record be the sole implementer.
5. Create a public non-sealed implementation called Weekend to implement AnyDay
6. Try making your sealed interface, a sealed class. What goes wrong?

Optional: Ask AI to generate some multiple choice questions on the topic in the module. How did it do?

# Module 3

1. Create an enum for the days of the week.
2. Write a method using a traditional switch statement and exactly one "return" that takes a day of the week and returns and int. If a weekend is passed in, the method should return 1. If a weekday is passed in, the method should return the number of letters in the name of that day of the week.
3. Write a method that returns the same data however uses a switch expression and is as short as possible.
5. Write a method that takes an object and returns the same as the prior methods if the parameter is your enum. Otherwise, have it return -1.
6. Write a method that behaves the same as the previous method. However, negate your if statement to see different behavior.
7. Write a record that takes the day of the week enum and two ints representing the hour/minute the class starts (uses a 24 hour clock, not a 12 hour one
8. Write a method that takes an object and uses a switch expression with pattern matching. It should return false if not the same type as your record. It should return true for weekday classes after 10:00 and weekend classes after 17:00
9. Write an equals method on your record that only checks day of week and hour, but not minute. Be sure to use instanceof and record pattern matching.

Optional: Ask AI to generate some multiple choice questions on the topic in the module. How did it do?

# Module 4 - need to add virtual threads

Note that this module's lab cannot be done in the playground.

1. Try running this code. How long does it take?
```
 static class Logic {
        static void waitUp() {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

public static void main(String[] args) throws InterruptedException {

        var start = System.nanoTime();

        var platformThreads = Stream.generate(() -> Thread.ofPlatform().unstarted(Logic::waitUp)).limit(1_000).toList();
        platformThreads.forEach(Thread::start);
        for (var t : platformThreads) {
            t.join();
        }
        
        var end = System.nanoTime();
        System.out.printf("Done. %d seconds ", (end - start) / (1_000 * 1_000));
}
```

2. Try increasing the number of threads in the limit to a million. Does it work?
3. Try changing the above example to use a virtual thread. Now can you change the number of threads to a million? How long does it take to run?
4. Now see how many actual platform threads were used to service all these virtual threads:

```
 static class Logic {
        private static Set<String> workers = new TreeSet<>();

        static void waitUp() {
            String name = Thread.currentThread().toString();
            workers.add(name.replaceFirst("^.*/", ""));
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        var start = System.nanoTime();

        var virtualThreads = Stream.generate(() -> Thread.ofVirtual().unstarted(Logic::waitUp)).limit(1_000_000).toList();
        virtualThreads.forEach(Thread::start);
        for (var t : virtualThreads) {
            t.join();
        }

        var end = System.nanoTime();
        System.out.printf("Done. %d seconds ", (end - start) / (1_000 * 1_000));
        Logic.workers.forEach(System.out::println);
    }
```

Optional: Ask AI to generate some multiple choice questions on the topic in the module. How did it do?
