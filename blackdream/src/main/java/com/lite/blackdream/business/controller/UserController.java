package com.lite.blackdream.business.controller;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.user.*;
import com.lite.blackdream.business.service.UserService;
import com.lite.blackdream.framework.aop.Security;
import com.lite.blackdream.framework.component.BaseController;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.model.Role;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author LaineyC
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @Security(open = false, role = Role.ROOT)
    @RequestMapping(params = "method=user.create")
    public UserCreateResponse create(UserCreateRequest request) {
        User user = userService.create(request);
        return new UserCreateResponse(user);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=user.get")
    public UserGetResponse get(UserGetRequest request) {
        User user = userService.get(request);
        return new UserGetResponse(user);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=user.authGet")
    public UserGetResponse authGet(UserGetRequest request) {
        Long userId = request.getAuthentication().getUserId();
        request.setId(userId);
        User user = userService.get(request);
        return new UserGetResponse(user);
    }

    @ResponseBody
    @Security(open = true, role = Role.USER)
    @RequestMapping(params = "method=user.login")
    public UserLoginResponse login(UserLoginRequest request, HttpServletRequest servletRequest) {
        User user = userService.login(request);
        Authentication authentication = new Authentication();
        authentication.setUserId(user.getId());
        authentication.setUserName(user.getUserName());
        authentication.setRole(user.getCreator() == null ? Role.ROOT : (user.getIsDeveloper() ? Role.DEVELOPER : Role.USER));
        servletRequest.getSession().setAttribute(ConfigProperties.SESSION_KEY_AUTHENTICATION, authentication);
        return new UserLoginResponse(user);
    }

    @ResponseBody
    @Security(open = true, role = Role.USER)
    @RequestMapping(params = "method=user.logout")
    public UserLogoutResponse logout(UserLogoutRequest request, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        Authentication authentication = (Authentication)session.getAttribute(ConfigProperties.SESSION_KEY_AUTHENTICATION);
        User user = null;
        if(authentication != null){
            user = new User();
            user.setId(authentication.getUserId());
            user.setUserName(authentication.getUserName());
            session.removeAttribute(ConfigProperties.SESSION_KEY_AUTHENTICATION);
        }
        return new UserLogoutResponse(user);
    }

    @ResponseBody
    @Security(open = false, role = Role.USER)
    @RequestMapping(params = "method=user.password.update")
    public UserPasswordUpdateResponse passwordUpdate(UserPasswordUpdateRequest request) {
        User user = userService.passwordUpdate(request);
        return new UserPasswordUpdateResponse(user);
    }

    @ResponseBody
    @Security(open = false, role = Role.ROOT)
    @RequestMapping(params = "method=user.search")
    public UserSearchResponse search(UserSearchRequest request) {
        PagerResult<User> pagerResult = userService.search(request);
        return new UserSearchResponse(pagerResult);
    }

    @ResponseBody
    @Security(open = false, role = Role.ROOT)
    @RequestMapping(params = "method=user.update")
    public UserUpdateResponse update(UserUpdateRequest request) {
        User user = userService.update(request);
        return new UserUpdateResponse(user);
    }

    @ResponseBody
    @Security(open = false, role = Role.ROOT)
    @RequestMapping(params = "method=user.password.reset")
    public UserPasswordResetResponse passwordReset(UserPasswordResetRequest request) {
        User user = userService.passwordReset(request);
        return new UserPasswordResetResponse(user);
    }

    @ResponseBody
    @Security(open = false, role = Role.ROOT)
    @RequestMapping(params = "method=user.enableOrDisable")
    public UserEnableOrDisableResponse enableOrDisable(UserEnableOrDisableRequest request) {
        User user = userService.enableOrDisable(request);
        return new UserEnableOrDisableResponse(user);
    }

}
