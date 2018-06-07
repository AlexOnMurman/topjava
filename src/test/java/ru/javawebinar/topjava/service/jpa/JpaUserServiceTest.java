package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JPA;

/**
 * Created by av.sitov on 07.06.2018.
 */
@ActiveProfiles(JPA)
public class JpaUserServiceTest extends AbstractUserServiceTest {
}
