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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.adapter.RanksAdapter;
import edu.cnm.deepdive.codebreaker.databinding.FragmentRanksBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.CodebreakerViewModel;
import edu.cnm.deepdive.codebreaker.viewmodel.PreferencesViewModel;
import edu.cnm.deepdive.codebreaker.viewmodel.RankingsViewModel;

@AndroidEntryPoint
public class RanksFragment extends Fragment {

  private FragmentRanksBinding binding;
  private RankingsViewModel rankingsViewModel;

  @Override
  public View onCreateView(
      LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    binding = FragmentRanksBinding.inflate(inflater, container, false);
    binding.codeLength.setOnSeekBarChangeListener(
        (SimpleOnSeekBarChangeListener) (seekBar, progress, fromUser) -> {
          binding.codeLengthValue.setText(String.valueOf(progress));
          rankingsViewModel.fetch(progress, binding.gamesThreshold.getProgress());
        });
    binding.gamesThreshold.setOnSeekBarChangeListener(
        (SimpleOnSeekBarChangeListener) (seekBar, progress, fromUser) -> {
          binding.gamesThresholdValue.setText(String.valueOf(progress));
          rankingsViewModel.fetch(binding.codeLength.getProgress(), progress);
        });
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ViewModelProvider provider = new ViewModelProvider(requireActivity());
    LifecycleOwner owner = getViewLifecycleOwner();
    rankingsViewModel = provider.get(RankingsViewModel.class);
    rankingsViewModel
        .getRankings()
        .observe(owner, (rankings) -> {
          RanksAdapter adapter = new RanksAdapter(requireContext(), rankings);
          binding.rankings.setAdapter(adapter);
        });
    CodebreakerViewModel codebreakerViewModel = provider.get(CodebreakerViewModel.class);
    codebreakerViewModel
        .getGame()
        .observe(owner, (game) -> {
          // TODO: 3/18/2024 If we have both code length and games threshold, invoke rankingsViewModel.fetch
        });
    PreferencesViewModel preferencesViewModel = provider.get(PreferencesViewModel.class);
    preferencesViewModel
        .getPreferedCodeLength()
        .observe(owner, (threshold) -> {
          // TODO: 3/18/2024 If we have both code length and games threshold, invoke rankingsViewModel.fetch
        });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  @FunctionalInterface
  private interface SimpleOnSeekBarChangeListener extends OnSeekBarChangeListener {

    @Override
    default void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    default void onStopTrackingTouch(SeekBar seekBar) {
    }
  }

}