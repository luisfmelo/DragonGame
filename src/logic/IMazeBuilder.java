package logic;

/**
 * Interface to build a random {@link logic.Maze}
 */
public interface IMazeBuilder {
	public char[][] buildMaze(int size) throws IllegalArgumentException;
}
