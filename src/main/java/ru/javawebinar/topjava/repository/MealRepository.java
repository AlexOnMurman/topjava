package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(int userID, Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(int userID, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
