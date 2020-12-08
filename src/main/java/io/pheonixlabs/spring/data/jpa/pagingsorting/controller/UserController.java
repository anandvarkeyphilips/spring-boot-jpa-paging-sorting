package io.pheonixlabs.spring.data.jpa.pagingsorting.controller;

import io.pheonixlabs.spring.data.jpa.pagingsorting.model.ResponseModel;
import io.pheonixlabs.spring.data.jpa.pagingsorting.model.User;
import io.pheonixlabs.spring.data.jpa.pagingsorting.repository.UserRepository;
import io.pheonixlabs.spring.data.jpa.pagingsorting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public ResponseModel search(@RequestParam(value = "search") String search,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "3") int size) {

        Page<User> myUsers = userService.getUsers(search,  PageRequest.of(page, size));
        ResponseModel<User> responseModel = new ResponseModel<>();
        responseModel.setElements(myUsers.getContent());
        responseModel.setPageNumber(myUsers.getNumber());
        responseModel.setTotalPages(myUsers.getTotalPages());
        responseModel.setPageSize(myUsers.getSize());
        responseModel.setTotalElements(myUsers.getTotalElements());
        return responseModel;

    }
}