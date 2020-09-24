package co.edu.unal.tictactoe;

import android.app.Activity;
//import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AndroidTicTacToeActivity extends AppCompatActivity {
    static final String TAG = "game.AndroidTicTacToeActivity";
    // Represents the internal state of the game
    private TicTacToeGame mGame;

    // Buttons making up the board
    private Button mBoardButtons[];
    private Button mNewGame;
    private Button mExitGame;
    private Button mDifficultyGame;
    private Button mAboutMe;

    // Various text displayed
    private TextView mInfoTextView;
    private TextView mPlayerOneCount;
    private TextView mTieCount;
    private TextView mComputerCount;
    private TextView mPlayerOneText;
    private TextView mComputerText;
    private char mGoFirst = TicTacToeGame.HUMAN_PLAYER;

    // Counters for the wins and ties
    private int mPlayerOneCounter = 0;
    private int mTieCounter = 0;
    private int mComputerCounter = 0;

    static final int DIALOG_DIFFICULTY_ID = 0;
    static final int DIALOG_QUIT_ID = 1;
    //static final int ABOUT_ME = 2;



    // Bools needed to see if player one goes first
    // if the gameType is to be single or local multiplayer
    // if it is player one's turn
    // and if the game is over

    private boolean mGameOver = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BottomNavigationView navView = findViewById(R.id.nav_view);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupWithNavController(navView, navController);

        // Initialize the buttons
        mBoardButtons = new Button[mGame.BOARD_SIZE];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);
        addListenerOnButton();

        // setup the textviews
        mInfoTextView = (TextView) findViewById(R.id.information);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mComputerCount = (TextView) findViewById(R.id.androidCount);
        mPlayerOneText = (TextView) findViewById(R.id.human);
        mComputerText = (TextView) findViewById(R.id.android);

        // set the initial counter display values
        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mComputerCount.setText(Integer.toString(mComputerCounter));

        // create a new game object
        mGame = new TicTacToeGame();

        // start a new game
        startNewGame();

    }

    public void addListenerOnButton(){

        mNewGame = (Button) findViewById(R.id.newGame);
        mExitGame = (Button) findViewById(R.id.exitGame);
        mDifficultyGame = (Button) findViewById(R.id.difficulty);
        //mAboutMe = (Button) findViewById(R.id.aboutMe);

        mNewGame.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startNewGame();
            }
        });
        mDifficultyGame.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DIALOG_DIFFICULTY_ID);
            }
        });
        mExitGame.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DIALOG_QUIT_ID);
            }
        });
        /*mAboutMe.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(ABOUT_ME);

            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.newGame:
                startNewGame();
                return true;
            case R.id.difficulty:
                showDialog(DIALOG_DIFFICULTY_ID);
                return true;
            case R.id.exitGame:
                showDialog(DIALOG_QUIT_ID);
                return true;
        }

        return true;
    }

    @Override
    protected AlertDialog onCreateDialog(int id) {
        AlertDialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        switch(id) {
            case DIALOG_DIFFICULTY_ID:

                builder.setTitle(R.string.difficulty_choose);
                final CharSequence[] levels = {
                        getResources().getString(R.string.difficulty_easy),
                        getResources().getString(R.string.difficulty_harder),
                        getResources().getString(R.string.difficulty_expert) };

                int selected = 0;

                switch (mGame.getDifficultyLevel()) { case Easy: selected = 0;
                    break; case Harder: selected = 1; break; case Expert: selected =
                        2; break; }

                builder.setSingleChoiceItems(levels, selected,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                dialog.dismiss(); //
                                //Close dialog
                                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[item]);

                                // Display the selected difficulty level
                                Toast.makeText(getApplicationContext(), levels[item],
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                dialog = builder.create();

                break;

            case DIALOG_QUIT_ID:
                // Create the quit confirmation dialog

                builder.setMessage(R.string.quit_question)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                AndroidTicTacToeActivity.this.finish();
                            }
                        })
                        .setNegativeButton(R.string.no, null);
                dialog = builder.create();

                break;

           /* case ABOUT_ME:
                builder.setTitle("Name");
                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.about_dialog, null);
                builder.setView(customLayout);
                // add a button
                builder.setPositiveButton("OK", null);
                dialog= builder.create();*/
        }

        return dialog;


    }


    // start a new game
    // clears the board and resets all buttons / text
    // sets game over to be false
    private void startNewGame()
    {
        mGameOver = false;
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++)
        {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        mInfoTextView.setText(R.string.first_human);
    }

    private class ButtonClickListener implements OnClickListener {
        int location;

        public ButtonClickListener(int location) {
            this.location = location;
        }

        public void onClick(View view) {
            if (!mGameOver) {
                if (mBoardButtons[location].isEnabled()) {
                    setMove(mGame.HUMAN_PLAYER, location);

                    int winner = mGame.checkForWinner();

                    if (winner == 0) {
                        mInfoTextView.setText(R.string.turn_computer);
                        int move = mGame.getComputerMove();
                        setMove(mGame.COMPUTER_PLAYER, move);
                        winner = mGame.checkForWinner();
                    }

                    if (winner == 0) {
                        mInfoTextView.setText(R.string.turn_human);
                    } else if (winner == 1) {
                        mInfoTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                        mGameOver = true;
                    } else if (winner == 2) {
                        mInfoTextView.setText(R.string.result_human_wins);
                        mPlayerOneCounter++;
                        mPlayerOneCount.setText(Integer.toString(mPlayerOneCounter));
                        mGameOver = true;
                    } else {
                        mInfoTextView.setText(R.string.result_android_wins);
                        mComputerCounter++;
                        mComputerCount.setText(Integer.toString(mComputerCounter));
                        mGameOver = true;
                    }

                }
            }
        }
    }

    // set move for the current player
    public void setMove(char player, int location) {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if(player == TicTacToeGame.HUMAN_PLAYER) {
            mBoardButtons[location].setTextColor(Color.rgb(0, 200, 0));
        }
        else {
            mBoardButtons[location].setTextColor(Color.rgb(200, 0, 0));
        }
    }

}