package edu.cnm.deepdive.codebreaker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.ItemRanksBinding;
import edu.cnm.deepdive.codebreaker.model.Ranking;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class RanksAdapter extends Adapter<ViewHolder> {

  private final List<Ranking> ranks;
  private final String durationFormat;
  private final String guessCountFormat;
  private LayoutInflater inflater;

  public RanksAdapter(Context context, List<Ranking> ranks) {
    this.ranks = new ArrayList<>(ranks);
    inflater = LayoutInflater.from(context);
    durationFormat = context.getString(R.string.duration_format);
    guessCountFormat = context.getString(R.string.guess_count_format);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    ItemRanksBinding binding = ItemRanksBinding.inflate(inflater, viewGroup, false);
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    ((Holder) viewHolder).bind(position);
  }

  @Override
  public int getItemCount() {
    return ranks.size();
  }

  private class Holder extends ViewHolder {

    private final ItemRanksBinding binding;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final double MS_PER_SECOND = 1000.0;

    public Holder(@NonNull ItemRanksBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(int position) {
      Ranking ranking = ranks.get(position);
      binding.rank.setText(String.valueOf(position + 1));
      binding.displayName.setText(ranking.getUser().getDisplayName());
      binding.avgGuessCount.setText(String.format(guessCountFormat, ranking.getAvgGuessCount()));
      double milliseconds = ranking.getAvgDuration();
      double seconds = milliseconds / MS_PER_SECOND;
      int minutes = (int) seconds / SECONDS_PER_MINUTE;
      int hours = minutes / MINUTES_PER_HOUR;
      minutes %= MINUTES_PER_HOUR;
      binding.avgDuration.setText(
          String.format(durationFormat, hours, minutes, seconds));
    }

  }
}
