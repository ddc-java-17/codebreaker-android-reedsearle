package edu.cnm.deepdive.codebreaker.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout.SimpleDrawerListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.codebreaker.R;
import edu.cnm.deepdive.codebreaker.databinding.ActivityMainBinding;
import edu.cnm.deepdive.codebreaker.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private ActivityMainBinding binding;
  private LoginViewModel loginViewModel;
  private NavController navController;
  private AppBarConfiguration appBarConfig;
  private DrawerLayout drawer;
  private OnBackCallback onBackPressedCallback;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    loginViewModel = new ViewModelProvider(this)
        .get(LoginViewModel.class);
    getLifecycle()
        .addObserver(loginViewModel);
    loginViewModel
        .getAccount()
        .observe(this, this::handleAccount);
    loginViewModel
        .getThrowable()
        .observe(this, this::handleThrowable);
    drawer = binding.getRoot();
    setContentView(drawer);
    setupNavigation();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, drawer)
        || super.onSupportNavigateUp();
  }

  private void handleAccount(GoogleSignInAccount account) {
    if (account == null) {
      Intent intent = new Intent(this, LoginActivity.class)
          .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
  }

  private void handleThrowable(Throwable throwable) {
  }

  private void setupNavigation() {
    //noinspection DataFlowIssue
    navController = ((NavHostFragment) getSupportFragmentManager()
        .findFragmentById(R.id.nav_host_fragment))
        .getNavController();
    appBarConfig = new AppBarConfiguration.Builder(
        R.id.game_fragment, R.id.scores_fragment, R.id.ranks_fragment, R.id.settings_fragment)
        .setOpenableLayout(drawer)
        .build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
    NavigationUI.setupWithNavController(binding.navigator, navController);
    binding.navigator
        .getMenu()
        .findItem(R.id.sign_out)
        .setOnMenuItemClickListener((item) -> {
          loginViewModel.signOut();
          return true;
        });
    onBackPressedCallback = new OnBackCallback(drawer.isDrawerOpen(GravityCompat.START));
    getOnBackPressedDispatcher().addCallback(onBackPressedCallback);
    drawer.addDrawerListener(new DrawerListener());
  }
  private class OnBackCallback extends OnBackPressedCallback {

    public OnBackCallback(boolean enabled) {
      super(enabled);
    }

    @Override
    public void handleOnBackPressed() {
      drawer.closeDrawer(GravityCompat.START);
    }
  }

  private class DrawerListener extends SimpleDrawerListener {

    @Override
    public void onDrawerOpened(View drawerView) {
      onBackPressedCallback.setEnabled(true);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
      onBackPressedCallback.setEnabled(false);
    }
  }

}