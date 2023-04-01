import java.util.Stack;
import java.util.LinkedList;
import java.util.Queue;
/**
 * Solves the given maze using DFS or BFS
 * @author Lucas Ying
 * @version 03/31/2023
 */

import java.util.ArrayList;

public class MazeSolver {
    private Maze maze;

    public MazeSolver() {
        this.maze = null;
    }

    public MazeSolver(Maze maze) {
        this.maze = maze;
    }

    public void setMaze(Maze maze) {
        this.maze = maze;
    }

    /**
     * Starting from the end cell, backtracks through
     * the parents to determine the solution
     * @return An arraylist of MazeCells to visit in order
     */
    public ArrayList<MazeCell> getSolution() {
        // TODO: Get the solution from the maze
        //Make my ArrayList that I will return.
        ArrayList<MazeCell> solution = new ArrayList<MazeCell>();
        //Make my Stack
        Stack<MazeCell> s = new Stack<>();
        //Get the end cell
        MazeCell first = maze.getEndCell();
        //Add the end cell found to the Stack
        s.push(first);
        //Call the recursive method that loops through and adds the parent cells to the Stack
        s = stackSolution(first, s);
        int size = s.size();

        for(int i = 0; i<size; i++){
            solution.add(s.pop());
        }

        // Should be from start to end cells
        return solution;
    }

    public Stack<MazeCell> stackSolution(MazeCell current, Stack<MazeCell> s){
        if(s.peek() == maze.getStartCell()){
            return s;
        }
        MazeCell next = current.getParent();
        s.push(next);
        return stackSolution(next, s);
    }

    /**
     * Performs a Depth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeDFS() {
        // TODO: Use DFS to solve the maze
        Stack<MazeCell> cellsToVisit = new Stack<>();
        ArrayList<MazeCell> visitedCells = new ArrayList<>();

        //Add my starting cell to my stack of cells to visit.
        MazeCell currentCell = maze.getStartCell();
        currentCell.setExplored(true);
        cellsToVisit.push(currentCell);

        //While loop
        //loops through and visits cells until there are none left in the stack to visit.
        //Once there are none left we should have reached the end and have found our solution.
            while (!cellsToVisit.empty()) {
                currentCell = cellsToVisit.pop();
                visitedCells.add(currentCell);

                //Gets the solution when we reach the end
                if (currentCell == maze.getEndCell()) {
                    return getSolution();
                }

                //Creates a neighbors array list that all the valid naighbors found in the getNeighbors method.
                ArrayList<MazeCell> neighbors = getNeighbors(currentCell);
                //Loops through the neighbors
                for (int i = 0; i < neighbors.size(); i++) {
                    //Gets the first neighbor in the list
                    MazeCell neighborCell = neighbors.get(i);
                    //Defines rows and columns
                    int row = neighborCell.getRow();
                    int col = neighborCell.getCol();
                    //If it has been explored and is valid then, go to that cell.
                    if (!neighborCell.isExplored() && maze.isValidCell(row, col)) {
                        neighborCell.setParent(currentCell);
                        neighborCell.setExplored(true);
                        cellsToVisit.push(neighborCell);
                    }
                }
            }

            return visitedCells;

        }

        //If east is valid go there
        //if west is valid go there
        //if south is valid go there
        //

    /**
     * Performs a Breadth-First Search to solve the Maze
     * @return An ArrayList of MazeCells in order from the start to end cell
     */
    public ArrayList<MazeCell> solveMazeBFS() {
        // TODO: Use BFS to solve the maze
        // Explore the cells in the order: NORTH, EAST, SOUTH, WEST

        Queue<MazeCell> cellsToVisit = new LinkedList<MazeCell>();
        ArrayList<MazeCell> visitedCells = new ArrayList<>();

        //Add my starting cell to my stack of cells to visit.
        MazeCell currentCell = maze.getStartCell();
        currentCell.setExplored(true);
        cellsToVisit.add(currentCell);

        //While loop
        //loops through and visits cells until there are none left in the queue to visit.
        //Once there are none left we should have reached the end and have found our solution.
        while (!cellsToVisit.isEmpty()) {
            currentCell = cellsToVisit.remove();
            visitedCells.add(currentCell);

            //Gets the solution
            if (currentCell == maze.getEndCell()) {
                return getSolution();

            }

            //Creates a neighbors array list that all the valid naighbors found in the getNeighbors method.
            ArrayList<MazeCell> neighbors = getNeighbors(currentCell);
            //Loops through the neighbors
            for (int i = 0; i < neighbors.size(); i++) {
                //Gets the first neighbor in the list
                MazeCell neighborCell = neighbors.get(i);
                //Defines rows and columns
                int row = neighborCell.getRow();
                int col = neighborCell.getCol();
                //If it has been explored and is valid then, go to that cell.
                if (!neighborCell.isExplored() && maze.isValidCell(row, col)) {
                    neighborCell.setParent(currentCell);
                    neighborCell.setExplored(true);
                    cellsToVisit.add(neighborCell);
                }
            }
        }

        return visitedCells;
    }

    private ArrayList<MazeCell> getNeighbors(MazeCell cell) {
        ArrayList<MazeCell> neighbors = new ArrayList<>();

        int row = cell.getRow();
        int col = cell.getCol();
        //All four of these if statements simply ensure we do not go out of bounds.
        //The rest of these next couple lines are just defining the neighbor in every direction.
        MazeCell north = maze.getCell(row, col);
        if(row != 0){
            north = maze.getCell(row - 1, col);
        }
        MazeCell west = maze.getCell(row, col);
        if(col != 0){
            west = maze.getCell(row, col - 1);
        }
        MazeCell east = maze.getCell(row, col);
        if(col != maze.getNumCols()-1){
            east = maze.getCell(row, col + 1);
        }
        MazeCell south = maze.getCell(row, col);
        if(row != maze.getNumRows()-1) {
            south = maze.getCell(row + 1, col);
        }

        //Checks if the neighbor in the direction is not null.
        //Then it adds the neighbors to the array list in order of priority for searching.
        if (north != null) {
            neighbors.add(north);
        }

        if (east != null) {
            neighbors.add(east);
        }

        if (south != null) {
            neighbors.add(south);
        }

        if (west != null) {
            neighbors.add(west);
        }

        return neighbors;
    }


    public static void main(String[] args) {
        // Create the Maze to be solved
        Maze maze = new Maze("Resources/maze3.txt");

        // Create the MazeSolver object and give it the maze
        MazeSolver ms = new MazeSolver();
        ms.setMaze(maze);

        // Solve the maze using DFS and print the solution
        ArrayList<MazeCell> sol = ms.solveMazeDFS();
        maze.printSolution(sol);

        // Reset the maze
        maze.reset();

        // Solve the maze using BFS and print the solution
        sol = ms.solveMazeBFS();
        maze.printSolution(sol);
    }
}
