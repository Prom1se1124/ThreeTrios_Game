package cs3500.threetrios.provider.model;

/**
 * Constructs the enum representing the card numbers with the
 * corresponding values. Note that Ten is an 'A.'
 */
public enum CardNum {

  One(1), Two(2), Three(3), Four(4), Five(5),
  Six(6), Seven(7), Eight(8), Nine(9), Ten(10);

  private final int value;

  /**
   * Constructs the enum representing the card numbers with the
   * corresponding values. Note that Ten is an 'A.'
   *
   * @param value is the integer value
   */
  CardNum(int value) {
    this.value = value;
  }

  /**
   * given the number, convert it to a CardNum.
   *
   * @param dirNum the number
   * @return the CardNum
   */
  public static CardNum getValueOf(int dirNum) {
    switch (dirNum) {
      case 1:
        return One;
      case 2:
        return Two;
      case 3:
        return Three;
      case 4:
        return Four;
      case 5:
        return Five;
      case 6:
        return Six;
      case 7:
        return Seven;
      case 8:
        return Eight;
      case 9:
        return Nine;
      default:
        return Ten;
    }
  }

  /**
   * Gets the value corresponding to the number.
   *
   * @return integer value
   */
  public int getValue() {
    return value;
  }

  /**
   * Returns the string form of the integer, if it is 10 it returns "A".
   *
   * @return string form
   */
  @Override
  public String toString() {
    if (this.value == 10) {
      return "A";
    } else {
      return Integer.toString(this.value);
    }
  }

}
