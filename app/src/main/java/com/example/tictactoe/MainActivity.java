package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean isPlayerXTurn = true;
    private int roundCount = 0;
    private int playerXScore = 0;
    private int playerOScore = 0;

    private TextView playerXScoreText;
    private TextView playerOScoreText;
    private TextView statusText;
    private Button resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerXScoreText = findViewById(R.id.playerXScore);
        playerOScoreText = findViewById(R.id.playerOScore);
        statusText = findViewById(R.id.statusText);
        resetButton = findViewById(R.id.resetButton);

        // Initialize buttons in GridLayout
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                int finalI = i;
                int finalJ = j;

                buttons[i][j].setOnClickListener(v -> onButtonClick(finalI, finalJ));
            }
        }

        resetButton.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int row, int col) {
        Button button = buttons[row][col];
        if (!button.getText().toString().equals("")) {
            return; // Already clicked
        }

        if (isPlayerXTurn) {
            button.setText("X");
            statusText.setText("Turn: Player Y");
        } else {
            button.setText("O");
            statusText.setText("Turn: Player X");
        }

        roundCount++;

        if (checkForWin()) {
            if (isPlayerXTurn) {
                playerXWins();
            } else {
                playerOWins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            isPlayerXTurn = !isPlayerXTurn;
        }
    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (!field[i][0].equals("") &&
                    field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2])) {
                return true;
            }

            if (!field[0][i].equals("") &&
                    field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i])) {
                return true;
            }
        }

        // Check diagonals
        if (!field[0][0].equals("") &&
                field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2])) {
            return true;
        }

        if (!field[0][2].equals("") &&
                field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0])) {
            return true;
        }

        return false;
    }

    private void playerXWins() {
        playerXScore++;
        Toast.makeText(this, "Player X Jit gya!", Toast.LENGTH_SHORT).show();
        updateScores();
        resetBoard();
    }

    private void playerOWins() {
        playerOScore++;
        Toast.makeText(this, "Player Y Jit gya!!", Toast.LENGTH_SHORT).show();
        updateScores();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Shabash aesi hi takkar do!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updateScores() {
        playerXScoreText.setText("Player X: " + playerXScore);
        playerOScoreText.setText("Player Y: " + playerOScore);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        isPlayerXTurn = true;
        statusText.setText("Turn: Player X");
    }

    private void resetGame() {
        playerXScore = 0;
        playerOScore = 0;
        updateScores();
        resetBoard();
    }
}
