package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.user.*;
import com.lite.blackdream.framework.component.Service;
import com.lite.blackdream.framework.model.PagerResult;

/**
 * @author LaineyC
 */
public interface UserService extends Service {

    User create(UserCreateRequest request);

    User login(UserLoginRequest request);

    User update(UserUpdateRequest request);

    User passwordUpdate(UserPasswordUpdateRequest request);

    User get(UserGetRequest request);

    PagerResult<User> search(UserSearchRequest request);

    User passwordReset(UserPasswordResetRequest request);

    User enableOrDisable(UserEnableOrDisableRequest request);

    void createRoot();

}