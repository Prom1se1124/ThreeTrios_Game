package cs3500.threetrios.model.hw5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class meant to read in board configuration files.
 */
public class BoardConfigReader extends ConfigReader {

  /**
   * A constructor for the BoardConfigReader class that takes in a String representing the path
   * to a file to be read.
   * @param filepath a string representing the path to a particular file.
   */
  public BoardConfigReader(String filepath) {
    super(filepath);
  }

  /**
   * Converts a file, using the filepath field to find the path to the file, into a
   * 2D array of Status representing the board's statuses.
   * @return a 2D array representing a board of statuses.
   */
  public int[][] convertFile() {
    try {
      if (!doesFileExist(this.filepath)) {
        throw new IllegalArgumentException("must input valid filepath");
      }
      BufferedReader reader = new BufferedReader(new FileReader(this.filepath));
      String line = reader.readLine();
      String[] rowscols = line.split("\\s+");
      int rows = Integer.parseInt(rowscols[0]);
      int cols = Integer.parseInt(rowscols[1]);
      int[][] statusBoard = new int[rows][cols];
      while ((line = reader.readLine()) != null) {
        for (int rowIdx = 0; rowIdx < rows; rowIdx++) {
          for (int colIndex = 0; colIndex < cols; colIndex++) {
            Character value = line.charAt(colIndex);
            if (value.equals('X')) {
              statusBoard[rowIdx][colIndex] = -1;
            }
            if (value.equals('C')) {
              statusBoard[rowIdx][colIndex] = 0;
            }
          }
          line = reader.readLine();
        }
      }
      reader.close();
      return statusBoard;
    } catch (IOException e) {
      throw new IllegalArgumentException("filepath cannot be found");
    }
  }

}
