package de.scravy.jazz.examples.tictactoe;

import de.scravy.jazz.Vector;
import de.scravy.jazz.pictures.DelegatingPicture;
import de.scravy.jazz.pictures.mutable.Polygon;

public class Cross extends DelegatingPicture<Cross> {

  private static final long serialVersionUID = 1L;

  public Cross(final double size) {
    super(new Polygon(
        new Vector(-0.5 * size, -0.25 * size),
        new Vector(-0.25 * size, -0.5 * size),
        new Vector(0, -0.25 * size),
        new Vector(0.25 * size, -0.5 * size),
        new Vector(0.5 * size, -0.25 * size),

        new Vector(0.25 * size, 0),

        new Vector(0.5 * size, 0.25 * size),
        new Vector(0.25 * size, 0.5 * size),
        new Vector(0, 0.25 * size),
        new Vector(-0.25 * size, 0.5 * size),
        new Vector(-0.5 * size, 0.25 * size),

        new Vector(-0.25 * size, 0)));
  }
}