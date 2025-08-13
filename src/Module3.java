public class Module3 {

    static enum DayOfWeek {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}

    record ClassStart(DayOfWeek dayOfWeek, int hour, int minute) {
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof ClassStart (DayOfWeek d, int h, int m)) {
                return d == dayOfWeek && h == hour;
            }
            return false;
        }
    }

    static int traditional(DayOfWeek dayOfWeek) {
        int result;
        switch (dayOfWeek) {
            case SATURDAY:
            case SUNDAY:
                result = 1;
                break;
            default:
                result = dayOfWeek.toString().length();
        }
        return result;

    }

    static int switchExpressions(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case SATURDAY, SUNDAY -> 1;
            default -> dayOfWeek.toString().length();
        };
    }

    static int patternMatching(Object obj) {
        if (obj instanceof DayOfWeek d) {
            return switchExpressions(d);
        }
        return -1;
    }

    static int patternMatchingInverted(Object obj) {
        if (! (obj instanceof DayOfWeek d)) {
            return -1;
        }
        return switchExpressions(d);
    }

    static boolean switchExpressionWithRecord(Object object) {
        return switch(object) {
            case ClassStart(DayOfWeek day, int hour, int minute)
                when hour > 10 && (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                    -> true;
            case ClassStart(DayOfWeek day, int hour, int minute)
                    when hour > 17
                    -> true;
            default -> false;
        };

    }

    public static void main(String[] args) {
        System.out.println(traditional(DayOfWeek.FRIDAY));
        System.out.println(traditional(DayOfWeek.SATURDAY));
        System.out.println();
        System.out.println(switchExpressions(DayOfWeek.FRIDAY));
        System.out.println(switchExpressions(DayOfWeek.SATURDAY));
        System.out.println();
        System.out.println(patternMatching(DayOfWeek.FRIDAY));
        System.out.println(patternMatching("friday"));
        System.out.println();
        System.out.println(patternMatchingInverted(DayOfWeek.FRIDAY));
        System.out.println(patternMatchingInverted("friday"));
        System.out.println();
        System.out.println(switchExpressionWithRecord(new ClassStart(DayOfWeek.MONDAY, 11, 0)));
        System.out.println(switchExpressionWithRecord(new ClassStart(DayOfWeek.SUNDAY, 11, 0)));

    }
}
