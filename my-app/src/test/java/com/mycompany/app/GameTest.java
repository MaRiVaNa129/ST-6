package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  void gameStartsWithEmptyBoardAndPlayers() {
    Game game = new Game();

    assertEquals(State.PLAYING, game.state);
    assertEquals('X', game.player1.symbol);
    assertEquals('O', game.player2.symbol);
    for (char cell : game.board) {
      assertEquals(' ', cell);
    }
  }

  @Test
  void checkStateDetectsXWin() {
    Game game = new Game();
    game.symbol = 'X';

    assertEquals(State.XWIN, game.checkState(new char[] {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}));
  }

  @Test
  void checkStateDetectsOWin() {
    Game game = new Game();
    game.symbol = 'O';

    assertEquals(State.OWIN, game.checkState(new char[] {'O', ' ', ' ', 'O', ' ', ' ', 'O', ' ', ' '}));
  }

  @Test
  void checkStateDetectsDraw() {
    Game game = new Game();
    game.symbol = 'X';

    assertEquals(State.DRAW, game.checkState(new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'}));
  }

  @Test
  void checkStateDetectsGameStillPlaying() {
    Game game = new Game();
    game.symbol = 'X';

    assertEquals(State.PLAYING, game.checkState(new char[] {'X', 'O', ' ', ' ', 'O', ' ', ' ', 'X', ' '}));
  }

  @Test
  void generateMovesCollectsEmptyCells() {
    Game game = new Game();
    ArrayList<Integer> moves = new ArrayList<>();

    game.generateMoves(new char[] {'X', ' ', 'O', ' ', ' ', 'X', 'O', ' ', 'X'}, moves);

    assertEquals(new ArrayList<>(Arrays.asList(1, 3, 4, 7)), moves);
  }

  @Test
  void evaluatePositionScoresWinLossAndDraw() {
    Game game = new Game();
    Player x = game.player1;
    Player o = game.player2;

    game.symbol = 'X';
    assertEquals(Game.INF, game.evaluatePosition(new char[] {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}, x));
    assertEquals(-Game.INF, game.evaluatePosition(new char[] {'X', 'X', 'X', ' ', ' ', ' ', ' ', ' ', ' '}, o));

    game.symbol = 'O';
    assertEquals(Game.INF, game.evaluatePosition(new char[] {'O', 'O', 'O', ' ', ' ', ' ', ' ', ' ', ' '}, o));

    game.symbol = 'X';
    assertEquals(0, game.evaluatePosition(new char[] {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'}, x));
    assertEquals(-1, game.evaluatePosition(new char[] {'X', 'O', ' ', ' ', 'O', ' ', ' ', 'X', ' '}, x));
  }

  @Test
  void ticTacToeCellStoresCoordinatesAndMarker() {
    TicTacToeCell cell = new TicTacToeCell(4, 1, 2);

    assertEquals(4, cell.getNum());
    assertEquals(2, cell.getRow());
    assertEquals(1, cell.getCol());
    assertEquals(' ', cell.getMarker());
    assertTrue(cell.isEnabled());

    cell.setMarker("X");

    assertEquals('X', cell.getMarker());
    assertEquals("X", cell.getText());
    assertFalse(cell.isEnabled());
  }

  @Test
  void utilityPrintMethodsWriteToStdout() throws Exception {
    PrintStream original = System.out;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8.name()));

    try {
      Utility.print(new char[] {'X', 'O', 'X', 'O', 'X', 'O', 'X', 'O', 'X'});
      Utility.print(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
      Utility.print(new ArrayList<>(Arrays.asList(0, 4, 8)));
    } finally {
      System.setOut(original);
    }

    String printed = output.toString(StandardCharsets.UTF_8.name());
    assertTrue(printed.contains("X-O-X-O-X-O-X-O-X-"));
    assertTrue(printed.contains("1-2-3-4-5-6-7-8-9-"));
    assertTrue(printed.contains("0-4-8-"));
  }

  @Test
  void panelInitializesCellsAndProcessesOneTurn() throws Exception {
    TicTacToePanel panel = new TicTacToePanel(new GridLayout(3, 3));
    TicTacToeCell[] cells = extractCells(panel);
    Game game = extractGame(panel);

    assertEquals(9, cells.length);
    assertNotNull(cells[0]);
    assertEquals(game.player1, game.cplayer);

    cells[0].doClick();

    assertEquals('X', cells[0].getMarker());
    assertFalse(cells[0].isEnabled());
    assertEquals(State.PLAYING, game.state);

    int markedCells = 0;
    for (TicTacToeCell cell : cells) {
      if (cell.getMarker() != ' ') {
        markedCells++;
      }
    }
    assertEquals(2, markedCells);
    assertEquals(game.player1, game.cplayer);
  }

  private TicTacToeCell[] extractCells(TicTacToePanel panel) throws Exception {
    Field field = TicTacToePanel.class.getDeclaredField("cells");
    field.setAccessible(true);
    return (TicTacToeCell[]) field.get(panel);
  }

  private Game extractGame(TicTacToePanel panel) throws Exception {
    Field field = TicTacToePanel.class.getDeclaredField("game");
    field.setAccessible(true);
    return (Game) field.get(panel);
  }
}