package co.edu.unal.tictactoe;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class AndroidTicTacToeActivity extends Activity {
        // Represents the internal state of the game
        private TicTacToeGame mGame;

        // Buttons making up the board
        private Button mBoardButtons[];
        private Button mNewGame;

        // Various text displayed
        private TextView mInfoTextView;
        private TextView mPlayerOneCount;
        private TextView mTieCount;
        private TextView mComputerCount;
        private TextView mPlayerOneText;
        private TextView mComputerText;

        // Counters for the wins and ties
        private int mPlayerOneCounter = 0;
        private int mTieCounter = 0;
        private int mComputerCounter = 0;

        // Bools needed to see if player one goes first
        // if the gameType is to be single or local multiplayer
        // if it is player one's turn
        // and if the game is over

        private boolean mGameOver = false;

        /** Called when the activity is first created. */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.activity_main);

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

            mNewGame = (Button) findViewById(R.id.NewGame);

            mNewGame.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    startNewGame();
                }
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main, menu);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            switch(item.getItemId())
            {
                case R.id.newGame:
                    startNewGame();
                    break;
                case R.id.exitGame:
                    AndroidTicTacToeActivity.this.finish();
                    break;
            }

            return true;
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

        private class ButtonClickListener implements View.OnClickListener {
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