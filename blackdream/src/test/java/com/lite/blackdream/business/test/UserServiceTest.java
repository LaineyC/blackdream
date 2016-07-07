package com.lite.blackdream.business.test;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.user.UserCreateRequest;
import com.lite.blackdream.business.service.UserService;
import com.lite.blackdream.framework.component.ServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author LaineyC
 */
public class UserServiceTest extends ServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create(){
        UserCreateRequest userCreateRequest = new UserCreateRequest();
        userCreateRequest.setUserName("cboss");
        User currentUser = new User();
        //userCreateRequest.setCurrentUser(currentUser);
        userService.create(userCreateRequest);
    }

}
