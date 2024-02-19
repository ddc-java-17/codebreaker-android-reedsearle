package edu.cnm.deepdive.codebreaker.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.time.Duration;
import java.time.Instant;

@Entity(tableName = "game_result")
public class GameResult {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "game_result_id")
  private long id;

  @ColumnInfo(name = "timestamp")
  private Instant timestamp;

  @ColumnInfo(name = "code_length")
  private int codeLength;

  @ColumnInfo(name = "guess_count")
  private int guessCount;

  @ColumnInfo(name = "duration")
  private Duration duration;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public int getCodeLength() {
    return codeLength;
  }

  public void setCodeLength(int codeLength) {
    this.codeLength = codeLength;
  }

  public int getGuessCount() {
    return guessCount;
  }

  public void setGuessCount(int guessCount) {
    this.guessCount = guessCount;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }
}
