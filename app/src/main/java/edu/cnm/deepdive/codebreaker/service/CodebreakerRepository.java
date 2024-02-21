package edu.cnm.deepdive.codebreaker.service;


import edu.cnm.deepdive.codebreaker.model.Game;
import edu.cnm.deepdive.codebreaker.model.Guess;
import edu.cnm.deepdive.codebreaker.model.entity.GameResult;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;

public class CodebreakerRepository {

  private final CodebreakerServiceProxy proxy;
  private final Scheduler scheduler;
  private Game game;

  @Inject
  CodebreakerRepository(CodebreakerServiceProxy proxy) {
    this.proxy = proxy;
    scheduler = Schedulers.single();
  }
  public Single<Game> startGame(Game game) {
    return proxy
        .startGame(game)
        .doOnSuccess(this::setGame)
        .subscribeOn(scheduler);
  }

  public Single<Guess> submitGuess(String text) {
    return Single.fromSupplier(() -> game.validate(text))
        .flatMap((guess) -> proxy.submitGuess(game.getId(), guess))
        .doOnSuccess((guess) -> game.getGuesses().add(guess))
        .subscribeOn(scheduler);
  }

  public Single<Game> getGame(String id) {
    return proxy
        .getGame(id)
        .doOnSuccess(this::setGame)
        .subscribeOn(scheduler);
  }

  public Game getGame() {
    return game;
  }

  private void setGame(Game game) {
    this.game = game;
  }

  private GameResult toResult(Game game){
    GameResult result = new GameResult();

  }
}
