package de.scravy.jazz.examples.tictactoe;

import de.scravy.jazz.Jazz;

public class TicTacToe {

  public static void main(String... args) {
    Jazz.play("Tic Tac Toe", 600, 600, new GameBoard());
  }
}
