package edu.cnm.deepdive.codebreaker.model.dao;

import androidx.room.Insert;
import edu.cnm.deepdive.codebreaker.model.entity.User;
import io.reactivex.rxjava3.core.Single;

public interface UserDao {

  @Insert
  Single<Long> insert(User user);



}
