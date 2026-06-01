package com.mycompany.app;

import org.junit.jupiter.api.Test;

import java.awt.GridLayout;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    void constructorCreatesEmptyBoard() {
        Game game = new Game();

        assertEquals(9, game.board.length);

        for (char c : game.board) {
            assertEquals(' ', c);
        }

        assertEquals(State.PLAYING, game.state);
        assertEquals('X', game.player1.symbol);
        assertEquals('O', game.player2.symbol);
    }

    @Test
    void checkStateXWin() {
        Game game = new Game();

        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'X';

        assertEquals(State.XWIN, game.checkState(board));
    }

    @Test
    void checkStateOWin() {
        Game game = new Game();

        char[] board = {
                'O', ' ', ' ',
                'O', ' ', ' ',
                'O', ' ', ' '
        };

        game.symbol = 'O';

        assertEquals(State.OWIN, game.checkState(board));
    }

    @Test
    void checkStateDraw() {
        Game game = new Game();

        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };

        game.symbol = 'X';

        assertEquals(State.DRAW, game.checkState(board));
    }

    @Test
    void checkStatePlaying() {
        Game game = new Game();

        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                ' ', 'X', ' '
        };

        game.symbol = 'X';

        assertEquals(State.PLAYING, game.checkState(board));
    }

    @Test
    void generateMovesReturnsCorrectMoves() {
        Game game = new Game();

        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                ' ', 'X', ' '
        };

        ArrayList<Integer> moves = new ArrayList<>();

        game.generateMoves(board, moves);

        assertEquals(4, moves.size());
        assertTrue(moves.contains(3));
        assertTrue(moves.contains(5));
        assertTrue(moves.contains(6));
        assertTrue(moves.contains(8));
    }

    @Test
    void evaluateWinningPositionForX() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'X', 'X', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'X';

        assertEquals(Game.INF,
                game.evaluatePosition(board, player));
    }

    @Test
    void evaluateLosingPositionForX() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'O', 'O', 'O',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };

        game.symbol = 'O';

        assertEquals(-Game.INF,
                game.evaluatePosition(board, player));
    }

    @Test
    void evaluateDrawPosition() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'X', 'O', 'X',
                'X', 'O', 'O',
                'O', 'X', 'X'
        };

        game.symbol = 'X';

        assertEquals(0,
                game.evaluatePosition(board, player));
    }

    @Test
    void minimaxFindsWinningMoveForO() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'O';

        char[] board = {
                'O', 'O', ' ',
                'X', 'X', ' ',
                ' ', ' ', ' '
        };

        int move = game.MiniMax(board, player);

        assertEquals(3, move);
    }

    @Test
    void maxMoveReturnsValidValue() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {


'X', 'O', 'X',
                ' ', 'O', ' ',
                ' ', 'X', ' '
        };

        int score = game.MaxMove(board, player);

        assertTrue(score >= -Game.INF);
        assertTrue(score <= Game.INF);
    }

    @Test
    void minMoveReturnsValidValue() {
        Game game = new Game();

        Player player = new Player();
        player.symbol = 'X';

        char[] board = {
                'X', 'O', 'X',
                ' ', 'O', ' ',
                ' ', 'X', ' '
        };

        int score = game.MinMove(board, player);

        assertTrue(score >= -Game.INF);
        assertTrue(score <= Game.INF);
    }

    @Test
    void ticTacToeCellTest() {
        TicTacToeCell cell = new TicTacToeCell(5, 1, 2);

        assertEquals(5, cell.getNum());
        assertEquals(1, cell.getCol());
        assertEquals(2, cell.getRow());
        assertEquals(' ', cell.getMarker());

        cell.setMarker("X");

        assertEquals('X', cell.getMarker());
    }

    @Test
    void panelCreationTest() {
        TicTacToePanel panel =
                new TicTacToePanel(new GridLayout(3, 3));

        assertEquals(9, panel.getComponentCount());
    }

    @Test
    void utilityPrintCharArray() {
        char[] board = {
                'X', 'O', 'X',
                ' ', ' ', ' ',
                ' ', ' ', ' '
        };

        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void utilityPrintIntArray() {
        int[] board = {
                1,2,3,4,5,6,7,8,9
        };

        assertDoesNotThrow(() -> Utility.print(board));
    }

    @Test
    void utilityPrintArrayList() {
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(1);
        moves.add(2);
        moves.add(3);

        assertDoesNotThrow(() -> Utility.print(moves));
    }
}
