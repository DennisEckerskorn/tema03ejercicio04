package com.denniseckerskorn.tema03ejercicio04.modelos;

import java.util.Objects;
import java.util.Random;

public class RPSGame {
    public enum GameSymbol {
        ROCK,
        PAPER,
        SCISSOR
    }

    private int score;
    private int losses;
    private GameSymbol playerChoice;
    private GameSymbol computerChoice;
    private final Random rnd;

    public RPSGame() {
        this.score = 0;
        this.losses = 0;
        this.rnd = new Random();
    }

    /**
     * Metodo para determinar el resultado.
     * Si la eleccion del jugador es igual al de la CPU = Empate
     * Piedra vence a Tijera
     * Papel vence a Piedra
     * Tijera vence a Papel
     * 0 = Empate
     * 1 = Ganar
     * 2 = Perder
     * -1 = Error
     *
     * @return
     */
    public int determineResult() {
        int draw = 0;
        int win = 1;
        int loss = 2;
        int error = -1;

        if (playerChoice == computerChoice) {
            return draw; //Empate
        }

        switch (playerChoice) {
            case ROCK:
                if (computerChoice == GameSymbol.SCISSOR) {
                    incrementScore();
                    return win; //Ganas
                }
                incrementLosses();
                return loss; //Pierdes

            case PAPER:
                if (computerChoice == GameSymbol.ROCK) {
                    incrementScore();
                    return win; //Ganas
                }
                incrementLosses();
                return loss; //Pierdes
            case SCISSOR:
                if (computerChoice == GameSymbol.PAPER) {
                    incrementScore();
                    return win; //Ganas
                }
                incrementLosses();
                return loss; //Pierdes
        }
        return error; // Error
    }
    
    /**
     * Establece la elección del jugador.
     *
     * @param choice La elección del jugador, de tipo GameSymbol.
     */
    public void setPlayerChoice(GameSymbol choice) {
        this.playerChoice = choice;
    }

    /**
     * Genera una elección aleatoria para la CPU.
     * Selecciona un símbolo al azar entre ROCK, PAPER y SCISSOR.
     */
    public void generateComputerChoice() {
        int randomChoice = rnd.nextInt(3);
        switch (randomChoice) {
            case 0:
                this.computerChoice = GameSymbol.ROCK;
                break;
            case 1:
                this.computerChoice = GameSymbol.PAPER;
                break;
            case 2:
                this.computerChoice = GameSymbol.SCISSOR;
                break;
        }
    }

    /**
     * Incrementa el puntaje del jugador en uno.
     */
    private void incrementScore() {
        score++;
    }

    /**
     * Incrementa el contador de pérdidas del jugador en uno.
     */
    public void incrementLosses() {
        losses++;
    }

    /**
     * Devuelve la elección actual de la CPU.
     *
     * @return La elección de la CPU de tipo GameSymbol.
     */
    public GameSymbol getComputerChoice() {
        return computerChoice;
    }

    /**
     * Devuelve el puntaje actual del jugador.
     *
     * @return Puntaje actual.
     */
    public int getScore() {
        return score;
    }

    /**
     * Devuelve el número de pérdidas del jugador.
     *
     * @return Número de pérdidas.
     */
    public int getLosses() {
        return losses;
    }


    /**
     * Resetea el puntaje y las pérdidas a cero.
     */
    public void reset() {
        score = 0;
        losses = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RPSGame rpsGame = (RPSGame) o;
        return score == rpsGame.score && losses == rpsGame.losses && playerChoice == rpsGame.playerChoice && computerChoice == rpsGame.computerChoice && Objects.equals(rnd, rpsGame.rnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(score, losses, playerChoice, computerChoice, rnd);
    }
}
