package de.calitha.croller;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

import java.util.stream.Collectors;

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
           String bonusStrafStr = diceMatcher.group(5);     //Bonus- oder Strafwürfel

           //Strings in Int konvertieren.
           int numberOfDice = numberOfDiceStr.isEmpty() ? 1 : Integer.parseInt(numberOfDiceStr);    //wandle String "numberOfDiceStr" in int "numberOfDice".
           int sides = Integer.parseInt(sidesStr);                                                  //wandle String "sidesStr" in int "sides" um.
           int modifier = modifierStr.isEmpty() ? 0 : Integer.parseInt(modifierStr);                //wenn "modifier" = leer, dann 0. sonst fülle wandle String "modifierStr" in int "modifier" um.

           List<List<Integer>> allRolls = new ArrayList<>(); //Eine Liste aus Listen
           List<Integer> totalValues = new ArrayList<>();

           //Bonus- oder Strafwürfel ermitteln
           boolean isBonus = false;
           boolean isStraf = false;

           if (bonusStrafStr != null){
               isBonus = bonusStrafStr.contains("b") || bonusStrafStr.contains("B");
               isStraf = bonusStrafStr.contains("s") || bonusStrafStr.contains("S");
           }
           //Anzahl Bonus- oder Strafwürfel
           int bonusStrafCount = 1; //Standardwert, wenn nichts angegeben.

           if (bonusStrafStr != null && !bonusStrafStr.isEmpty()){
               bonusStrafCount = Integer.parseInt(bonusStrafStr.substring(0, bonusStrafStr.length() - 1));
           }

           Random random = new Random();
           StringBuilder resultMessage = new StringBuilder();

           for (int bonusStrafIndex = 0; bonusStrafIndex <= bonusStrafCount; bonusStrafIndex++) {
               List<Integer> singleRollResults = new ArrayList<>();

               for (int i = 0; i < numberOfDice; i++) {
                   int roll = random.nextInt(sides) + 1;
                   singleRollResults.add(roll);
               }
               int totalValue = singleRollResults.stream().mapToInt(Integer::intValue).sum();

               // Hinzufügen der Einzelergebnisse und der Gesamtsumme zur Nachricht
               resultMessage.append(singleRollResults.stream().map(Object::toString).collect(Collectors.joining(" + ")))
                       .append(" = ")
                       .append(totalValue);
               // Hinzufügen eines Zeilenumbruchs, wenn es noch weitere Bonuswürfe gibt
               if (bonusStrafIndex < bonusStrafCount - 1) {
                   resultMessage.append("\n");
               }
           }
           int bestRoll = isBonus ? Collections.max(totalValues) : Collections.min(totalValues);
           int worstRoll = isBonus ? Collections.min(totalValues) : Collections.max(totalValues);

       } else if (modifierMatcher.matches()) {

       } else {

       }

        return true;
    }
}