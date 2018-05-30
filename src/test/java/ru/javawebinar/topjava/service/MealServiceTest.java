package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal testMeal = service.get(START_MEAL_SEQ, USER_ID);
        assertMatch(testMeal, USER_MEALS.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUsersMeal() {
        Meal testMeasl = service.get(START_MEAL_SEQ, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(START_MEAL_SEQ, USER_ID);
        List<Meal> mealList = service.getAll(USER_ID);
        Collections.reverse(mealList);
        assertMatch(mealList, USER_MEALS.subList(1, 6));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUsersMeal() {
        service.delete(START_MEAL_SEQ + 7, USER_ID);
    }

    @Test
    public void getBetweenDateTime() throws Exception {
        List<Meal> mealList = service.getBetweenDateTimes(LocalDateTime.of(2015, Month.MAY, 29, 15, 0),
                LocalDateTime.of(2015, Month.MAY, 30, 14, 0), USER_ID);
        Collections.reverse(mealList);
        assertMatch(mealList, USER_MEALS.subList(2, 5));
    }

    @Test
    public void getBetweenDatesEmptyList() throws Exception {
        List<Meal> mealList = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 29), LocalDate.of(2015, Month.MAY, 30), ADMIN_ID);
        Assert.assertEquals(mealList.size(), 0);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> mealList = service.getAll(ADMIN_ID);
        Collections.reverse(mealList);
        assertMatch(mealList, ADMIN_MEALS);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEALS.get(0));
        updated.setCalories(100);
        updated.setDescription("Легкий завтрак");
        updated.setDateTime(updated.getDateTime().plusMinutes(30));
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUsersMeal() throws Exception {
        Meal updated = new Meal(USER_MEALS.get(0));
        updated.setCalories(100);
        updated.setDescription("Легкий завтрак");
        updated.setDateTime(updated.getDateTime().plusMinutes(30));
        service.update(updated, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Lunch", 1000);
        Meal meal = service.create(newMeal, ADMIN_ID);
        newMeal.setId(meal.getId());
        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEALS.get(1), ADMIN_MEALS.get(0));
    }

}