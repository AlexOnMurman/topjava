package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int START_MEAL_SEQ = START_SEQ + 2;

    public static final List<Meal> USER_MEALS = Collections.unmodifiableList(Arrays.asList(
            new Meal(START_MEAL_SEQ, LocalDateTime.of(2015, Month.MAY, 29, 9, 00), "Завтрак", 900),
            new Meal(START_MEAL_SEQ + 1, LocalDateTime.of(2015, Month.MAY, 29, 14, 00), "Обед", 1000),
            new Meal(START_MEAL_SEQ + 2, LocalDateTime.of(2015, Month.MAY, 29, 18, 30), "Ужин", 100),
            new Meal(START_MEAL_SEQ + 3, LocalDateTime.of(2015, Month.MAY, 30, 7, 00), "Ранний завтрак", 800),
            new Meal(START_MEAL_SEQ + 4, LocalDateTime.of(2015, Month.MAY, 30, 13, 00), "Ланч", 1000),
            new Meal(START_MEAL_SEQ + 5, LocalDateTime.of(2015, Month.MAY, 30, 17, 00), "Обед", 1000)
    ));
    public static final List<Meal> ADMIN_MEALS = Collections.unmodifiableList(Arrays.asList(
            new Meal(START_MEAL_SEQ + 6, LocalDateTime.of(2015, Month.MAY, 31, 9, 00), "Breakfast", 500),
            new Meal(START_MEAL_SEQ + 7, LocalDateTime.of(2015, Month.MAY, 31, 15, 30), "Lunch", 1000)
    ));

    public static void assertMatch(Meal actual, Meal expexted) {
        assertThat(actual).isEqualToComparingFieldByField(expexted);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }

}
