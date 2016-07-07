package com.lite.blackdream.business.repository;

import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.component.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author LaineyC
 */
@Repository
public class UserRepositoryImpl extends BaseRepository<User, Long> implements UserRepository {

}
