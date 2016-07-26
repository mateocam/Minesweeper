package hu.ait.android.minesweeper;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import hu.ait.android.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout layoutContent;
    private MinesweeperView minesweeperView;

    // 2 = try on field mode
    // 1 = place a flag mode
    public static int toggleTag = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        minesweeperView =
                (MinesweeperView) findViewById(R.id.gameView);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.btnToggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    toggleTag = 2;
                } else {
                    toggleTag = 1;
                }
            }
        });

        Button btnRestart = (Button) findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSnackBarMessage(getString(R.string.rstrt_msg));
                minesweeperView.restartGame();
            }
        });

        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, getString(R.string.try_mode_txt) +
                        getString(R.string.try_help1) +
                        getString(R.string.try_help2) +
                        getString(R.string.flag_mode_txt) +
                        getString(R.string.flag_help1) +
                        getString(R.string.win_help1), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showSnackBarMessage(String msg) {
        Snackbar.make(layoutContent, msg,
                Snackbar.LENGTH_LONG).show();
    }
}
