package com.denniseckerskorn.tema03ejercicio04;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

    /**
     * Método que simula las rondas jugadas.
     * EL jugador selecciona las imagenes hasta llegar a 3 puntos para ganar o 3 perdidas para perder.
     * Cuando se gana o pierde, se ejecuta un CountDown antes de resetear el juego.
     *
     * @param playerChoice     Imagen seleccionada por el jugador
     * @param playerImageResId ID de la imagen seleccionada.
     */
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

        //Evaluar si ha ganado
        if (rpsGame.getScore() >= 3) {
            Toast.makeText(this, getString(R.string.finalWin), Toast.LENGTH_SHORT).show();
            //Reset Game
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    resetGame();
                }
            }.start();
        }

        //Evaluar si ha perdido
        if (rpsGame.getLosses() >= 3) {
            Toast.makeText(this, getString(R.string.finalLoss), Toast.LENGTH_SHORT).show();
            new CountDownTimer(2000, 1000) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    resetGame();
                }
            }.start();
        }

        //Actualizar imagen al perder
        if (result == 2) {
            updateImageHeart(rpsGame.getLosses());
        }

    }

    /**
     * Método que actualiza las imagen de los corazones en funcion de los puntos perdidos.
     *
     * @param losses Número de perdidas del jugador
     */
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

    /**
     * Muestra un mensaje de resultado en función del resultado de la ronda.
     *
     * @param result Resultado de la ronda (0: empate, 1: jugador gana, 2: jugador pierde).
     */
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

    /**
     * Actualiza la imagen de la selección de la CPU según su elección.
     *
     * @param computerChoice Elección aleatoria de la CPU.
     */
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

    /**
     * Reinicia el juego, reseteando el puntaje y las imágenes de estado.
     */
    private void resetGame() {
        rpsGame.reset();
        tvPoints.setText("0");
        ivWLMessage.setText("");
        resetHeartImages();
    }

    /**
     * Resetea las imágenes de los corazones a su estado original.
     */
    private void resetHeartImages() {
        ivRedHeart1.setImageResource(R.drawable.life_heart);
        ivRedHeart2.setImageResource(R.drawable.life_heart);
        ivRedHeart3.setImageResource(R.drawable.life_heart);
    }
}