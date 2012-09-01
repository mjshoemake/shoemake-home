package mjs.mybatis;

import java.util.List;
import mjs.users.User;

public class UserService {

  private UserMapper userMapper;

  public List<User> getPersonList() {
    return userMapper.getPersonList();
  }

}
