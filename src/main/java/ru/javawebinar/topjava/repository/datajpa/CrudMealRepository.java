package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Meal m where m.id = :id and m.user.id = :userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Transactional
    @Override
    Meal save(Meal m);

    @Query("select m from Meal m where m.id = :id and m.user.id = :userId")
    Optional<Meal> get(@Param("id") int id, @Param("userId") int userId);

    @Query("select m from Meal m where m.user.id = :userId order by m.dateTime desc")
    List<Meal> getAll(@Param("userId") int userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("select m from Meal m where m.user.id = :userId and "
            + "m.dateTime between :startDate and :endDate order by m.dateTime desc")
    List<Meal> getBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("select m from Meal m join fetch m.user where m.id = :id and m.user.id = : userId")
    Meal getWithUser(int id, int user);
}
