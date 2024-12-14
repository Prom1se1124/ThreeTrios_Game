package cs3500.threetrios.controller;

import cs3500.threetrios.model.hw5.AIPlayer;
import cs3500.threetrios.model.hw5.BattleRule;
import cs3500.threetrios.model.hw5.PlusBattleRule;
import cs3500.threetrios.model.hw5.ReverseBattleRule;
import cs3500.threetrios.model.hw5.FallenAceBattleRule;
import cs3500.threetrios.model.hw5.CardColor;
import cs3500.threetrios.model.hw5.CornerAI;
import cs3500.threetrios.model.hw5.DefaultBattleRule;
import cs3500.threetrios.model.hw5.HumanPlayer;
import cs3500.threetrios.model.hw5.IPlayer;
import cs3500.threetrios.model.hw5.MaxFlipAI;
import cs3500.threetrios.model.hw5.SameBattleRule;

public class CommandLineHandler {

  public static CommandLineConfig parseArgs(String[] args) {
    if (args.length < 2) {
      throw new IllegalArgumentException("Usage: <player1> <player2> [--rule <rules>]");
    }

    // Parse players
    IPlayer player1 = parsePlayer(args[0], CardColor.RED);
    IPlayer player2 = parsePlayer(args[1], CardColor.BLUE);

    // Parse rules
    BattleRule battleRule = parseRules(args);

    // Return configuration
    return new CommandLineConfig(player1, player2, battleRule);
  }


  private static IPlayer parsePlayer(String playerArg, CardColor color) {
    switch (playerArg.toLowerCase()) {
      case "human":
        return new HumanPlayer(color == CardColor.RED ? "Player 1" : "Player 2", color);
      case "strategy1":
        return new AIPlayer(color == CardColor.RED ? "AI 1" : "AI 2", color, new MaxFlipAI(),
                null);
      case "strategy2":
        return new AIPlayer(color == CardColor.RED ? "AI 1" : "AI 2", color, new CornerAI(),
                null);
      default:
        throw new IllegalArgumentException("Invalid player type: " + playerArg);
    }
  }

  private static BattleRule parseRules(String[] args) {
    // Look for `--rule` argument
    for (int i = 2; i < args.length; i++) {
      if (args[i].equalsIgnoreCase("--rule")) {
        if (i + 1 >= args.length) {
          throw new IllegalArgumentException("Expected rules after --rule");
        }
        String[] ruleArgs = args[i + 1].split(",");
        return createBattleRule(ruleArgs);
      }
    }
    return new DefaultBattleRule(); // Default if no rules specified
  }


  private static BattleRule createBattleRule(String[] ruleArgs) {
    // Combine rules dynamically
    BattleRule rule = new DefaultBattleRule();

    boolean sameChosen = false;
    boolean plusChosen = false;

    for (String ruleArg : ruleArgs) {
      switch (ruleArg.toLowerCase()) {
        case "reverse":
          rule = new ReverseBattleRule(rule);
          System.out.println("CommandLineHandler Rule applied: Reverse");
          break;
        case "fallen-ace":
          rule = new FallenAceBattleRule(rule);
          System.out.println("CommandLineHandler Rule applied: Fallen Ace");
          break;
        case "same":
          if (plusChosen) {
            throw new IllegalArgumentException("Cannot use both 'same' and 'plus' rules together.");
          }
          sameChosen = true;
          break;
        case "plus":
          if (sameChosen) {
            throw new IllegalArgumentException("Cannot use both 'same' and 'plus' rules together.");
          }
          plusChosen = true;
          break;
        default:
          throw new IllegalArgumentException("Unknown rule: " + ruleArg);
      }
    }

    // Apply the level 2 rule after all level 1 rules have been applied
    if (sameChosen) {
      rule = new SameBattleRule(rule);
      System.out.println("CommandLineHandler Rule applied: Same");
    } else if (plusChosen) {
      rule = new PlusBattleRule(rule);
      System.out.println("CommandLineHandler Rule applied: Plus");
    }

    return rule;
  }

  // Inner class to hold parsed configuration
  public static class CommandLineConfig {
    private final IPlayer player1;
    private final IPlayer player2;
    private final BattleRule battleRule;

    public CommandLineConfig(IPlayer player1, IPlayer player2, BattleRule battleRule) {
      this.player1 = player1;
      this.player2 = player2;
      this.battleRule = battleRule;
    }

    public IPlayer getPlayer1() {
      return player1;
    }

    public IPlayer getPlayer2() {
      return player2;
    }

    public BattleRule getBattleRule() {
      return battleRule;
    }
  }


}
