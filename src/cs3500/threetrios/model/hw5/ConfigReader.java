package cs3500.threetrios.model.hw5;

import java.io.File;

/**
 * An abstract class reads configuration files.
 */
public abstract class ConfigReader {

  final String filepath;

  /**
   * Constructor for the ConfigReader class.
   * @param filepath the path to a file that's meant to be read.
   */
  public ConfigReader(String filepath) {
    this.filepath = filepath;
  }

  /**
   * Determines whether the file exists.
   * @param path the path to the file.
   * @return a boolean whether the given path exists.
   */
  public static boolean doesFileExist(String path) {
    File file = new File(path);
    return file.exists();
  }

}
