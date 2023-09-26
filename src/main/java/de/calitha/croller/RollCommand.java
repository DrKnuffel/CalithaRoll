package de.calitha.croller;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RollCommand implements CommandExecutor {
    private final Pattern modifierPattern = Pattern.compile("^(\\d+)([+-]\\d+)?(\\d+[bs])?$", Pattern.CASE_INSENSITIVE); //erstelle das Pattern für einen Wurf mit Wert aus. Z.B. "/roll 50", "/roll 46 2b" oder "/roll 28+5"
    private final Pattern dicePattern = Pattern.compile("^(\\d*?)([dw])(\\d+)([+-]?\\d*?)(\\d+[bs]?)?$", Pattern.CASE_INSENSITIVE);
    private final boolean isGM;

    public RollCommand(boolean isGM){
        this.isGM = isGM;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
       if (!(sender instanceof Player)){ //Wenn der Sender kein Spieler ist.
           sender.sendMessage("Dieser Befehl kann nur von Spielern verwendet werden.");
           return true;
       }

       Player player = (Player) sender;

       if (args.length == 0){ //wenn der Spieler keine Argumente angegeben hat.
           player.sendMessage("Verwendung: /roll oder /gmroll <Wert> [b/n]");
           return true;
       }

       Matcher diceMatcher = dicePattern.matcher(args[0]);
       Matcher modifierMatcher = modifierPattern.matcher(args[0]);

       if (diceMatcher.matches()){
           String numberOfDiceStr = diceMatcher.group(1);   //Anzahl der Würfel.
           String sidesStr = diceMatcher.group(3);          //Anzahl der Seiten.
           String modifierStr = diceMatcher.group(4);       //Modifikator. (z.B. "+3", "-5")
           String bonusMalusStr = diceMatcher.group(5);     //Bonus- oder Strafwürfel

           //Strings in Int konvertieren.
           int numberOfDice = numberOfDiceStr.isEmpty() ? 1 : Integer.parseInt(numberOfDiceStr);
           int sides = Integer.parseInt(sidesStr);
           int modifier = modifierStr.isEmpty() ? 0 : Integer.parseInt(modifierStr);

           //Würfel simulieren.
           Random random = new Random();
           int[] rolls = new int[numberOfDice];
           for (int i  = 0; i < numberOfDice; i++){
               rolls[i] = random.nextInt(sides) + 1;
           }
       } else if (modifierMatcher.matches()) {

       } else {

       }

        return true;
    }
}