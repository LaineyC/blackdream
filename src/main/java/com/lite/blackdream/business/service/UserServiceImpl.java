package com.lite.blackdream.business.service;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.business.parameter.user.*;
import com.lite.blackdream.business.repository.UserRepository;
import com.lite.blackdream.framework.exception.AppException;
import com.lite.blackdream.framework.component.BaseService;
import com.lite.blackdream.framework.model.Authentication;
import com.lite.blackdream.framework.model.PagerResult;
import com.lite.blackdream.framework.util.ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LaineyC
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User create(UserCreateRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        User currentUser = userRepository.selectById(userId);
        if(currentUser.getCreator() != null){
            throw new AppException("权限不足");
        }

        User user = new User();
        user.setUserName(request.getUserName());
        user.setIsDelete(false);
        if(userRepository.exists(user, null)){
            throw new AppException("用户名已注册");
        }

        user.setId(idWorker.nextId());
        user.setPassword(passwordEncoder.encode(ConfigProperties.PASSWORD));
        user.setIsDisable(request.getIsDisable());
        user.setIsDeveloper(request.getIsDeveloper());
        user.setLoginCount(0);
        User creator = new User();
        creator.setId(currentUser.getId());
        user.setCreator(creator);
        userRepository.insert(user);
        return user;
    }

    //创建启动数据--管理员
    public void createRoot(){
        long count = userRepository.count(new User());
        if(count == 0){
            User user = new User();
            user.setUserName(ConfigProperties.USERNAME);
            user.setIsDelete(false);
            if(userRepository.exists(user, null)){
                throw new AppException("用户名已注册");
            }
            user.setId(idWorker.nextId());
            user.setPassword(passwordEncoder.encode(ConfigProperties.PASSWORD));
            user.setIsDisable(false);
            user.setIsDeveloper(true);
            user.setLoginCount(0);
            user.setCreator(null);
            userRepository.insert(user);
        }
    }

    @Override
    public User login(UserLoginRequest request) {
        String userName = request.getUserName();
        String password = request.getPassword();
        User user = new User();
        user.setUserName(userName);
        user.setIsDelete(false);
        User userPersistence = userRepository.selectOne(user);
        if(userPersistence == null){
            throw new AppException("用户名或密码错误");
        }
        if(userPersistence.getIsDisable()){
            throw new AppException("用户名或密码错误");
        }
        if(!passwordEncoder.matches(password, userPersistence.getPassword())){
            throw new AppException("用户名或密码错误");
        }
        userPersistence.setLoginCount(userPersistence.getLoginCount() + 1);
        userRepository.update(userPersistence);
        return userPersistence;
    }

    @Override
    public User update(UserUpdateRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        User currentUser = userRepository.selectById(userId);
        if(currentUser.getCreator() != null){
            throw new AppException("权限不足");
        }

        User user = new User();
        user.setId(request.getId());
        user.setIsDelete(false);
        User userPersistence = userRepository.selectOne(user);
        if(userPersistence == null){
            throw new AppException("用户不存在");
        }

        if(userPersistence.getCreator() == null){
            throw new AppException("权限不足");
        }

        userPersistence.setIsDisable(request.getIsDisable());
        userPersistence.setIsDeveloper(request.getIsDeveloper());
        userRepository.update(userPersistence);

        return userPersistence;
    }

    @Override
    public User passwordUpdate(UserPasswordUpdateRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        User userPersistence = userRepository.selectById(userId);
        if(userPersistence == null){
            throw new AppException("用户不存在");
        }
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        //String repeatPassword = request.getRepeatPassword();
        if(!passwordEncoder.matches(oldPassword, userPersistence.getPassword())){
            throw new AppException("旧密码不正确");
        }
        userPersistence.setPassword(passwordEncoder.encode(newPassword));
        userRepository.update(userPersistence);

        return userPersistence;
    }

    @Override
    public User get(UserGetRequest request) {
        User userPersistence = userRepository.selectById(request.getId());
        User user = new User();
        user.setId(userPersistence.getId());
        user.setLoginCount(userPersistence.getLoginCount());
        user.setUserName(userPersistence.getUserName());
        user.setIsDisable(userPersistence.getIsDisable());
        user.setIsDeveloper(userPersistence.getIsDeveloper());
        User creator = userPersistence.getCreator();
        if(creator != null){
            User creatorPersistence = userRepository.selectById(creator.getId());
            user.setCreator(creatorPersistence);
        }
        return user;
    }

    @Override
    public PagerResult<User> search(UserSearchRequest request) {
        User userTemplate = new User();
        userTemplate.setIsDelete(false);
        userTemplate.setIsDeveloper(request.getIsDeveloper());
        String userName = request.getUserName();
        userName = StringUtils.hasText(userName) ? userName : null;
        userTemplate.setUserName(userName);
        userTemplate.setIsDisable(request.getIsDisable());
        List<User> records = userRepository.selectList(userTemplate);
        Integer page = request.getPage();
        Integer pageSize = request.getPageSize();
        Integer fromIndex = (page - 1) * pageSize;
        Integer toIndex = fromIndex + pageSize > records.size() ? records.size() : fromIndex + pageSize;
        List<User> limitRecords = records.subList(fromIndex, toIndex);
        List<User> result = new ArrayList<>();
        for(User u : limitRecords){
            User user = new User();
            user.setId(u.getId());
            user.setLoginCount(u.getLoginCount());
            user.setUserName(u.getUserName());
            user.setIsDisable(u.getIsDisable());
            user.setIsDeveloper(u.getIsDeveloper());
            User creator = u.getCreator();
            if(creator != null){
                User creatorPersistence = userRepository.selectById(creator.getId());
                user.setCreator(creatorPersistence);
            }
            result.add(user);
        }
        return new PagerResult<>(result, (long)records.size());
    }

    @Override
    public User passwordReset(UserPasswordResetRequest request) {
        Authentication authentication = request.getAuthentication();
        Long userId = authentication.getUserId();
        User currentUser = userRepository.selectById(userId);
        if(currentUser.getCreator() != null){
            throw new AppException("权限不足");
        }

        Long id = request.getId();
        User userPersistence = userRepository.selectById(id);
        if(userPersistence == null){
            throw new AppException("用户不存在");
        }

        if(userPersistence.getCreator() == null){
            throw new AppException("权限不足");
        }

        userPersistence.setPassword(passwordEncoder.encode(ConfigProperties.PASSWORD));
        userRepository.update(userPersistence);

        return userPersistence;
    }
}
