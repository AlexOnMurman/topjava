package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(int userID, Meal meal) {
        return repository.save(userID, meal);
    }

    @Override
    public void delete(int userId, int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(userId, id), id);
    }

    @Override
    public Meal update(int userId, Meal meal) throws NotFoundException {
        return checkNotFoundWithId(repository.save(userId, meal), meal.getId());
    }

    @Override
    public Meal get(int userId, int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(userId, id), id);
    }

    @Override
    public List<Meal> getBetweenDateTimes(int userID, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.getBetween(userID, startDateTime, endDateTime);
    }

    @Override
    public List<Meal> getAll(int userID) {
        return repository.getAll(userID);
    }
}