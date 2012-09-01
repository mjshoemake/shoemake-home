package mjs.mybatis;

import java.util.List;
import mjs.users.User;

public interface UserMapper {

	List<User> getPersonList();

/*	
  Account getAccountByUsername(String username);

  Account getAccountByUsernameAndPassword(Account account);

  void insertAccount(Account account);
  
  void insertProfile(Account account);
  
  void insertSignon(Account account);

  void updateAccount(Account account);

  void updateProfile(Account account);

  void updateSignon(Account account);
*/
}
