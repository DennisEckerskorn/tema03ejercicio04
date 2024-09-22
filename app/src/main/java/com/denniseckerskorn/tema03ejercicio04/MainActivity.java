package com.denniseckerskorn.tema03ejercicio04;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denniseckerskorn.tema03ejercicio04.modelos.RPSGame;

public class MainActivity extends AppCompatActivity {

    private RPSGame rpsGame;
    private TextView tvPoints;
    private TextView ivWLMessage;
    private ImageView ivPlayer;
    private ImageView ivCPU;
    private ImageView ivRedHeart1;
    private ImageView ivRedHeart2;
    private ImageView ivRedHeart3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Inicializar el juego
         */
        rpsGame = new RPSGame();

        /*
         * Enlazar elementos de la UI
         */
        tvPoints = findViewById(R.id.tvPoints);
        ivWLMessage = findViewById(R.id.tvWLMessage);
        ivPlayer = findViewById(R.id.ivPlayer);
        ivCPU = findViewById(R.id.ivCPU);

        ImageView ivRock = findViewById(R.id.ivRock);
        ImageView ivPaper = findViewById(R.id.ivPaper);
        ImageView ivScissor = findViewById(R.id.ivScissor);

        ivRedHeart1 = findViewById(R.id.ivRedHeart1);
        ivRedHeart2 = findViewById(R.id.ivRedHeart2);
        ivRedHeart3 = findViewById(R.id.ivRedHeart3);

        /*
         * Listener Piedra
         */
        ivRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playRound(RPSGame.GameSymbol.ROCK, R.drawable.pedra);
            }
        });

        /*
         * Listener Papel
         */
        ivPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playRound(RPSGame.GameSymbol.PAPER, R.drawable.paper);
            }
        });

        /*
         * Listener Tijera
         */
        ivScissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playRound(RPSGame.GameSymbol.SCISSOR, R.drawable.tisores);
            }
        });
    }

    private void playRound(RPSGame.GameSymbol playerChoice, int playerImageResId) {
        //Asignar imagen del jugador:
        ivPlayer.setImageResource(playerImageResId);

        //Configurar la seleccion del jugador:
        rpsGame.setPlayerChoice(playerChoice);

        //Eleccion aleatorio de la CPU:
        rpsGame.generateComputerChoice();
        RPSGame.GameSymbol computerChoice = rpsGame.getComputerChoice();
        updateImageComputerChoice(computerChoice);

        //Determinar el resultado:
        int result = rpsGame.determineResult();
        showResultMessage(result);

        //Actualizar el score:
        tvPoints.setText(String.valueOf(rpsGame.getScore()));

        if (rpsGame.getScore() == 3) {
            Toast.makeText(this, getString(R.string.finalWin), Toast.LENGTH_SHORT).show();
            //Reset Game
            resetGame();
        }

        if(rpsGame.getLosses() == 3) {
            Toast.makeText(this, getString(R.string.finalLoss), Toast.LENGTH_SHORT).show();
            //Reset game
            resetGame();
        }

        if(result == 2) {
            updateImageHeart(rpsGame.getLosses());
        }

    }

    private void updateImageHeart(int losses) {
        switch (losses) {
            case 1:
                ivRedHeart1.setImageResource(R.drawable.black_life_heart);
                break;
            case 2:
                ivRedHeart2.setImageResource(R.drawable.black_life_heart);
                break;
            case 3:
                ivRedHeart3.setImageResource(R.drawable.black_life_heart);
                break;
        }
    }

    private void showResultMessage(int result) {
        switch (result) {
            case 0: //Empate
                ivWLMessage.setText(getString(R.string.tvDrawMessage));
                break;
            case 1: //Jugador gana
                ivWLMessage.setText(getString(R.string.tvWinnerMessage));
                break;
            case 2: //Jugador pierde
                ivWLMessage.setText(getString(R.string.tvLooserMessage));
                break;
        }

    }

    private void updateImageComputerChoice(RPSGame.GameSymbol computerChoice) {
        switch (computerChoice) {
            case ROCK:
                ivCPU.setImageResource(R.drawable.pedra);
                break;
            case PAPER:
                ivCPU.setImageResource(R.drawable.paper);
                break;
            case SCISSOR:
                ivCPU.setImageResource(R.drawable.tisores);
        }
    }

    private void resetGame() {
        rpsGame.reset();
        tvPoints.setText("0");
        ivWLMessage.setText("");
        resetHeartImages();
    }

    private void resetHeartImages() {
        ivRedHeart1.setImageResource(R.drawable.life_heart);
        ivRedHeart2.setImageResource(R.drawable.life_heart);
        ivRedHeart3.setImageResource(R.drawable.life_heart);
    }
}