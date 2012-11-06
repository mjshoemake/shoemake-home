package mjs.mybatis;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("rawtypes")
public class UserService {

  private UserMapper userMapper;

  public List<HashMap> getFamilyMemberList() {
     return userMapper.getFamilyMemberList();
  }

}
