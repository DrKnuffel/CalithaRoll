package de.calitha.croller;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RollCommand implements CommandExecutor {
    private final Pattern modifierPattern = Pattern.compile("^(\\d+)([+-]\\d+)?$", Pattern.CASE_INSENSITIVE); //erstelle das Pattern für einen Wurf mit Wert aus. Z.B. "/roll 50", "/roll 46 2b" oder "/roll 28+5"
    private final Pattern dicePattern = Pattern.compile("^((\\d*?)([dw])(\\d+)([+-]?\\d*?)?)?$", Pattern.CASE_INSENSITIVE);
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

       } else if (modifierMatcher.matches()) {

       } else {

       }

        return true;
    }
}