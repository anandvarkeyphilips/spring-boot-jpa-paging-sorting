package com.bezkoder.spring.data.jpa.pagingsorting.controller;

import com.bezkoder.spring.data.jpa.pagingsorting.model.MyUser;
import com.bezkoder.spring.data.jpa.pagingsorting.repository.MyUserRepository;
import com.bezkoder.spring.data.jpa.pagingsorting.service.MyUserPredicatesBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    @Autowired
    private MyUserRepository myUserRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/myusers")
    @ResponseBody
    public Iterable<MyUser> search(@RequestParam(value = "search") String search) {
        MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
        BooleanExpression exp = builder.build();
        return myUserRepository.findAll(exp);
    }
}