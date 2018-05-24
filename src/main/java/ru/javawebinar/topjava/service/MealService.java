package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public interface MealService {

    Meal create(int userId, Meal meal);

    void delete(int userId, int id) throws NotFoundException;

    Meal update(int userId, Meal meal) throws NotFoundException;

    Meal get(int userId, int id) throws NotFoundException;

    List<Meal> getBetweenDateTimes(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    default List<Meal> getBetweenDates(int userId, LocalDate startDate, LocalDate endDate) {
        return getBetweenDateTimes(userId, LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX));
    }

    List<Meal> getAll(int userId);

}