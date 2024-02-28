package edu.cnm.deepdive.codebreaker.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import edu.cnm.deepdive.codebreaker.model.entity.Game;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface GameDao {

  String TRUNCATION_QUERY = "DELETE FROM game";

  @Insert
  Single<Long> insert(Game game);

  @Query(TRUNCATION_QUERY)
  Completable truncate();

  default Single<Game> insertAndUpdate(Game game){
    return insert(game)
        .map((id)->{
          game.setId(id);
          return game;
        });
  }
}
