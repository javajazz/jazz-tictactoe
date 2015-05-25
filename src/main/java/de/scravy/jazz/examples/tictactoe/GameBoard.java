package de.scravy.jazz.examples.tictactoe;

import de.scravy.jazz.Color;
import de.scravy.jazz.Event;
import de.scravy.jazz.Picture;
import de.scravy.jazz.World;
import de.scravy.jazz.grids.RectangularGrid;
import de.scravy.jazz.grids.TileEventHandler;
import de.scravy.jazz.grids.TileFactory;
import de.scravy.jazz.grids.TileRenderer;
import de.scravy.jazz.pictures.mutable.Circle;

public class GameBoard extends World {

  private boolean gameOver;
  private boolean turn;
  private Tile currentTile;

  private enum TileType {
    X, O
  }

  private static class Tile {

    private TileType value;
    private boolean highlight;

    Tile() {
      reset();
    }

    void reset() {
      value = null;
      highlight = false;
    }
  }

  private final TileFactory<Tile> tileFactory = new TileFactory<Tile>() {
    @Override
    public Tile createTile(final int x, final int y) {
      return new Tile();
    }
  };

  private final TileEventHandler<Tile> tileEventHandler = new TileEventHandler<Tile>() {

    private void check(final int x0, final int y0, final int x1,
        final int y1, final int x2, final int y2) {
      if (grid.getTileAt(x0, y0).value != null
          && grid.getTileAt(x0, y0).value == grid.getTileAt(x1, y1).value
          && grid.getTileAt(x1, y1).value == grid.getTileAt(x2, y2).value) {

        grid.getTileAt(x0, y0).highlight = true;
        grid.getTileAt(x1, y1).highlight = true;
        grid.getTileAt(x2, y2).highlight = true;

        gameOver = true;
      }
    }

    @Override
    public void on(final Event ev, final Tile tile) {
      if (gameOver) {
        if (ev.getType() == Event.Type.CLICK) {
          for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
              grid.getTileAt(i, j).reset();
            }
          }
          gameOver = false;
        }
      } else if (ev.getType() == Event.Type.CLICK && tile.value == null) {
        tile.value = turn ? TileType.O : TileType.X;
        turn = !turn;

        check(0, 0, 1, 1, 2, 2);
        check(0, 2, 1, 1, 2, 0);

        check(0, 0, 0, 1, 0, 2);
        check(1, 0, 1, 1, 1, 2);
        check(2, 0, 2, 1, 2, 2);

        check(0, 0, 1, 0, 2, 0);
        check(0, 1, 1, 1, 2, 1);
        check(0, 2, 1, 2, 2, 2);

        if (grid.getTileAt(0, 0).value != null
            && grid.getTileAt(0, 1).value != null
            && grid.getTileAt(0, 2).value != null
            && grid.getTileAt(1, 0).value != null
            && grid.getTileAt(1, 1).value != null
            && grid.getTileAt(1, 2).value != null
            && grid.getTileAt(2, 0).value != null
            && grid.getTileAt(2, 1).value != null
            && grid.getTileAt(2, 2).value != null) {
          gameOver = true;
        }
      } else if (ev.getType() == Event.Type.MOUSE_MOVE) {
        currentTile = tile;
      }
    }
  };

  private final TileRenderer<Tile> tileRenderer = new TileRenderer<Tile>() {

    @Override
    public Picture render(final Tile tile,
        final double x, final double y,
        final double width, final double height) {

      final Picture picture;

      if (tile.value != null) {
        picture = tile.value == TileType.X
            ? new Cross(width * 0.8)
            : new Circle(width * 0.4);
        picture.filled(true);
      } else if (tile == currentTile) {
        picture = turn
            ? new Circle(width * 0.4)
            : new Cross(width * 0.8);
      } else {
        return null;
      }

      if (tile.highlight) {
        picture.color(Color.RED);
      } else if (gameOver) {
        picture.color(Color.AZURE);
      } else {
        picture.color(Color.BLACK);
      }

      return picture.translate(x, y);
    }
  };

  private final RectangularGrid<Tile> grid = new RectangularGrid<>(
      3, 3, 200, 200, tileFactory, tileEventHandler, tileRenderer);

  @Override
  public void on(final Event ev) {
    grid.on(ev);
  }

  @Override
  public Picture getPicture() {
    return grid.getPicture();
  }
}
