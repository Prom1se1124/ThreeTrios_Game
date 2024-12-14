package cs3500.threetrios.model.hw5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that reads configuration.
 */
public class CardConfigReader extends ConfigReader {

  /**
   * A constructor for the CardConfigReader class that takes the path
   * to a file to be read.
   *
   * @param filepath a string representing the path to a particular file.
   */
  public CardConfigReader(String filepath) {
    super(filepath);
  }

  /**
   * Converts a file into a list of three trios cards.
   *
   * @return a list of three trio cards.
   */
  public List<Card> convertFile(int requiredCards) {
    try {
      if (!doesFileExist(this.filepath)) {
        throw new IllegalArgumentException("must input valid filepath");
      }
      BufferedReader reader = new BufferedReader(new FileReader(this.filepath));
      String line;
      List<Card> outputList = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        line = line.trim();

        // Skip empty lines
        if (line.isEmpty()) {
          continue;
        }

        // Split the line by whitespace
        String[] tempList = line.split("\\s+");

        // Check if the line has exactly 5 elements
        if (tempList.length != 5) {
          throw new IllegalArgumentException("Invalid card format at line: '" + line + "'. Each line must have exactly 5 values.");
        }


        outputList.add(new Card(
                tempList[0], stringToInt(tempList[1]), stringToInt(tempList[2]),
                stringToInt(tempList[3]), stringToInt(tempList[4])));
      }
      reader.close();
      if (outputList.size() < requiredCards) {
        throw new IllegalArgumentException("Not enough cards in the file. Expected at least " + requiredCards);
      }
      return outputList;
    } catch (IOException | InvalidPathException e) {
      throw new IllegalArgumentException("filepath cannot be found");
    }
  }

  private int stringToInt(String num) {
    switch (num) {
      case "1":
        return 1;
      case "2":
        return 2;
      case "3":
        return 3;
      case "4":
        return 4;
      case "5":
        return 5;
      case "6":
        return 6;
      case "7":
        return 7;
      case "8":
        return 8;
      case "9":
        return 9;
      case "A":
        return 10;
      default:
        throw new IllegalArgumentException("invalid string");
    }
  }

}
