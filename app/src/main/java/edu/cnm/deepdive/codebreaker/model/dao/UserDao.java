package edu.cnm.deepdive.codebreaker.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;

public interface UserDao {

  String USER_QUERY = "SELECT * FROM user WHERE user_id = :userId";
  String USER_OATH_QUERY = "SELECT * FROM user WHERE oath_key = :oathKey";
  @Insert
  Single<Long> insert(User user);

  @Query(USER_QUERY)
  LiveData<User> select(long userId);

  @Query(USER_OATH_QUERY)
  Maybe<User> select(String oathKey);

}
