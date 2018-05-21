package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Collection;

import static org.slf4j.LoggerFactory.getLogger;
/**
 * Created by av.sitov on 17.05.2018.
 */
public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "all";
        }



        switch (action) {
            case "delete":
                int id = Integer.valueOf(request.getParameter("id"));
                log.info("delete {}", id);
                repository.delete(id);
                response.sendRedirect("meals");
                break;
            case "update":
            case "create":

            case "all":
            default:
                log.info("getAll");
                Collection<MealWithExceed> allMeals = MealsUtil.getFilteredWithExceeded(repository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY);
                log.info("Throw collection of " + allMeals.size() + " elements");
                request.setAttribute("meals", allMeals);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }
}
