package com.github.ngodat0103.yamp.authsvc.service.impl;

import com.github.ngodat0103.yamp.authsvc.dto.kafka.SiteUserTopicDto;
import com.github.ngodat0103.yamp.authsvc.dto.mapper.UserMapper;
import com.github.ngodat0103.yamp.authsvc.persistence.repository.UserRepository;
import com.github.ngodat0103.yamp.authsvc.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  private static final String DEFAULT_ROLE = "CUSTOMER";

  @Override
  public void createUser(SiteUserTopicDto siteUserTopicDto) {}

  @Override
  public void updateUser(SiteUserTopicDto siteUserTopicDto) {}

  @Override
  public void deleteUser(SiteUserTopicDto siteUserTopicDto) {}
}
