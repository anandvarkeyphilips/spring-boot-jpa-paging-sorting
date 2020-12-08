package io.pheonixlabs.spring.data.jpa.pagingsorting.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import io.pheonixlabs.spring.data.jpa.pagingsorting.model.User;
import io.pheonixlabs.spring.data.jpa.pagingsorting.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Page<User> getUsers(String search, Pageable pageable){
        UserPredicatesBuilder builder = new UserPredicatesBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return userRepository.findAll(exp, pageable);
    }
}
