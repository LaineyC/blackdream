package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.user.*;
import com.lite.blackdream.framework.layer.Service;
import com.lite.blackdream.framework.model.PagerResult;

/**
 * @author LaineyC
 */
public interface UserService extends Service {

    User create(UserCreateRequest request);

    User login(UserLoginRequest request);

    User update(UserUpdateRequest request);

    void passwordUpdate(UserPasswordUpdateRequest request);

    User get(UserGetRequest request);

    PagerResult<User> search(UserSearchRequest request);

    void create();

}