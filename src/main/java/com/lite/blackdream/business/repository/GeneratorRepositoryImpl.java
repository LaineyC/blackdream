package com.lite.blackdream.business.repository;

import com.lite.blackdream.business.domain.Generator;
import com.lite.blackdream.business.domain.User;
import com.lite.blackdream.framework.layer.BaseRepository;
import com.lite.blackdream.framework.model.PropertyDefinition;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LaineyC
 */
@Repository
public class GeneratorRepositoryImpl extends BaseRepository<Generator, Long> implements GeneratorRepository {

}
