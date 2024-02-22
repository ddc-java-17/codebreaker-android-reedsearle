package edu.cnm.deepdive.codebreaker.controller;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.GameResultsAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentScoresBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.GameResultViewModel;

@AndroidEntryPoint
public class ScoresFragment extends Fragment implements OnSeekBarChangeListener {

  private FragmentScoresBinding binding;
  private GameResultViewModel viewModel;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentScoresBinding.inflate(inflater, container, false);
    binding.codeLength.setOnSeekBarChangeListener(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(this).get(GameResultViewModel.class);
    getLifecycle().addObserver(viewModel);
    viewModel
        .getGameResults()
        .observe(getViewLifecycleOwner(), (gameResults) -> {
          GameResultsAdapter adapter = new GameResultsAdapter(requireContext(), gameResults);
          binding.gameResults.setAdapter(adapter);
        });

  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    if (fromUser) {
      viewModel.setCodeLength(progress);
    }
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