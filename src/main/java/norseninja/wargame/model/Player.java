package norseninja.wargame.model;

import norseninja.wargame.model.unit.Unit;
import norseninja.wargame.model.unit.commander.CommanderUnit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class Player {

    private final Game game;
    private BufferedReader input;
    private BufferedWriter output;

    public Player(Game game) {
        this.game = game;
        this.input = game.getInput();
        this.output = game.getOutput();
    }

    public boolean takeTurn(Unit unit) throws IOException {
        boolean playersTurn = true;
        boolean moved = false;
        boolean attacked = false;

        while (playersTurn) {

            output.write(unit.getField().getAsString() + "\n");
            output.write("Acting unit: " + unit.getName() + ", at " + unit.getLocation().toString() + ".\n\n" + "Enter command:\n");
            output.flush();
            boolean validCommand = false;
            String[] command = new String[0];
            while (!validCommand) {
                output.flush();
                command = input.readLine().trim().split(" ");
                if (validCommand(command)) {
                    validCommand = true;
                } else {
                    output.write("Unrecognized command. Type 'help' for a list of valid commands.\n\n");
                }
            }
            //execute command.
            switch (command[0]) {
                case "help","HELP","Help" -> showHelp();
                case "move", "MOVE", "Move" -> {
                    if (!moved && move(unit, Location.parseLocation(command[1]))) {
                        moved = true;
                    } else if (moved) {
                        output.write("Already moved this turn.\n\n");
                    }
                }
                case "attack", "ATTACK", "Attack" -> {
                    if (!attacked && attack(unit, Location.parseLocation(command[1]))) {
                        attacked = true;
                    } else if (attacked) {
                        output.write("Already attacked this turn.\n\n");
                    }
                }
                case "cast", "CAST", "Cast" -> cast(unit);
                case "end", "END", "End" -> playersTurn = false;
                case "auto", "AUTO", "Auto" -> {unit.act(); playersTurn = false;}
                default -> throw new IllegalArgumentException();
            }
            if (moved && (unit.getValidTargets().isEmpty() || attacked)) {
                playersTurn = false;
            }
        }
        return true;
    }

    private void cast(Unit unit) throws IOException {
        if (unit instanceof CommanderUnit) {
            CommanderUnit commander = (CommanderUnit) unit;
            if (commander.enableAura()) {
                output.write(commander.getName() + " cast " + commander.getAbility().getName() + ".\n\n");
            }
        } else {
            output.write(unit.getName() + " has no abilities to cast.\n\n");
        }
    }

    private boolean attack(Unit unit, Location targetLocation) throws IOException {
        boolean success = false;
        Unit opponent = (Unit) unit.getField()
                .getObjectAt(targetLocation);
        if (null != opponent) {
            int damage = unit.attack(opponent);
            if (damage >= 0) {
                output.write(unit.getName() + " attacked " + opponent.getName() + " for " + damage + " damage.\n\n");
                success = true;
            } else {
                output.write(opponent.getName() + " is not in attack range.\n\n");
            }
        } else {
            output.write("No opponents found at these coordinates.\n\n");
        }
        return success;
    }

    private boolean move(Unit unit, Location targetLocation) throws IOException {
        if (unit.moveTo(targetLocation)) {
            output.write(unit.getName() + " moved to " + targetLocation.toString() + ".\n\n");
            return true;
        } else {
            output.write(targetLocation.toString() + " is not a valid location.\n\n");
        }
        return false;
    }

    private void showHelp() {
        try {
            output.write(
                    "Valid commands:\n\n" +
                            "attack: attack [defender location] (example: attack 2,3)\n" +
                            "cast: activates the ability of the unit, if it has one.\n" +
                            "move: move [target location] (example: move 3,3)\n" +
                            "end: ends the player's turn.\n" +
                            "auto: lets the computer handle this turn.\n" +
                            "help: display this list.\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Valid commands:
     * move [unitLocation] to [targetLocation] (example: move 2,3 to 3,3)
     * attack [attacker] [defender] (example: attack Unit3251 Unit5231)
     * @param commands the command string
     * @return true if command is valid.
     */
    private boolean validCommand(String[] commands) {
        if (null != commands && commands.length > 0) {
            return switch (commands[0]) {
                case "move","MOVE","Move", "attack", "ATTACK", "Attack" -> commands.length == 2 && commands[1].contains(",");
                case "help","HELP","Help", "end", "END", "End", "cast", "CAST", "Cast", "auto", "AUTO", "Auto" -> commands.length == 1;
                default -> false;
            };
        } else {
            return false;
        }
    }
}
