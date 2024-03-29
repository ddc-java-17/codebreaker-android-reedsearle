package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.GameResultsAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentScoresBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.CodebreakerViewModel;
import edu.cnm.deepdive.codebreaker.viewmodel.GameResultViewModel;
import edu.cnm.deepdive.codebreaker.viewmodel.PreferencesViewModel;

@AndroidEntryPoint
public class ScoresFragment extends Fragment implements OnSeekBarChangeListener {

  private FragmentScoresBinding binding;
  private GameResultViewModel viewModel;
  private int initialCodeLength;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
// TODO: 3/11/2024 Read initial Code Length from settings
  }

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentScoresBinding.inflate(inflater, container, false);
    binding.codeLength.setOnSeekBarChangeListener(this);
    binding.clearScores.setOnClickListener((v)->viewModel.clearResults());
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ViewModelProvider provider = new ViewModelProvider(this);
    viewModel = provider.get(GameResultViewModel.class);
    getLifecycle().addObserver(viewModel);
    LifecycleOwner owner = getViewLifecycleOwner();
    viewModel
        .getGameResults()
        .observe(owner, (gameResults) -> {
          GameResultsAdapter adapter = new GameResultsAdapter(requireContext(), gameResults);
          binding.gameResults.setAdapter(adapter);
        });
    binding.codeLength.setProgress(binding.codeLength.getMax());
    binding.codeLength.setProgress(binding.codeLength.getMin());
//    binding.codeLength.setProgress(initialCodeLength);// for use with original navigation
    CodebreakerViewModel codebreakerViewModel = provider.get(CodebreakerViewModel.class);
    codebreakerViewModel
        .getGame()
        .observe(owner, (game)-> binding.codeLength.setProgress(game.getLength()));
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }



  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    viewModel.setCodeLength(progress);
    binding.codeLengthValue.setText(String.valueOf(progress));
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
    // Do nothing. No need to handle this
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    // Do nothing. No need to handle this
  }

}