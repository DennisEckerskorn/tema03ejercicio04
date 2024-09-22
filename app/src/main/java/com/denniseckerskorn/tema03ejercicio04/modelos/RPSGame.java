package com.denniseckerskorn.tema03ejercicio04.modelos;

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

    public void setPlayerChoice(GameSymbol choice) {
        this.playerChoice = choice;
    }

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

    public GameSymbol getComputerChoice() {
        return computerChoice;
    }

    private void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void incrementLosses() {
        losses++;
    }

    public int getLosses() {
        return losses;
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

    public void reset() {
        score = 0;
        losses = 0;
    }
}
