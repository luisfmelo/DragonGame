package logic;

/**
 * Interface to build a random Maze
 */
public interface IMazeBuilder {
	public char[][] buildMaze(int size) throws IllegalArgumentException;
}
