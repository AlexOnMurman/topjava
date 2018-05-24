package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll for user {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        log.info("get meal {} and user {}", id, userId);
        return service.get(userId, id);
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("create new meal {} for user {}", meal, userId);
        checkNew(meal);
        return service.create(userId, meal);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(userId, id);
    }

    public void update(Meal meal, int id) {
        int userId = AuthorizedUser.id();
        log.info("update meal {} with id {} for user {}", meal, id, userId);
        assureIdConsistent(meal, id);
        service.update(userId, meal);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        log.info("get meal between {} {} and {} {} for user {}", startDate, startTime, endDate, endTime, userId);

        List<Meal> userDateFiltered = service.getBetweenDates(userId,
                startDate == null ? DateTimeUtil.MIN_DATE : startDate,
                endDate == null ? DateTimeUtil.MAX_DATE : endDate);

        return MealsUtil.getFilteredWithExceeded(userDateFiltered, AuthorizedUser.getCaloriesPerDay(),
                startTime == null ? LocalTime.MIN : startTime,
                endTime == null ? LocalTime.MAX : endTime);
    }
}