package fr.mrallan140.SBT;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin implements Listener
{
  ScoreboardManager manager;
  Scoreboard board;
  Objective objective;
  Score score;
  Server server = getServer();
    World world = server.getWorld("world");
  public int Ligne;
  public int Tps;
  public int OldLigne;
  public String[] Text = { ChatColor.GREEN + "Théme de cette Année :", ChatColor.AQUA + "Villes De France","-------------------","Une ligne","Une ligne","Une ligne"};
  public int LigneduSB = 6;
  public String NameSB = ChatColor.BLUE + "Festival Du Jeu";

  public void onEnable()
  {
    Bukkit.getPluginManager().registerEvents(this, this);

    this.manager = Bukkit.getScoreboardManager();
    this.board = this.manager.getNewScoreboard();
    this.objective = this.board.registerNewObjective("Ligne", "dummy");
    this.Ligne = 0;

    this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    this.objective.setDisplayName(this.NameSB);

    Bukkit.getLogger().log(Level.INFO, "-------- Le Plugin ScoreBoardTest a ete chargé ! -------");
  }

  public void refresh() {
	  for(Player p : Bukkit.getOnlinePlayers()) {
	  p.sendMessage(ChatColor.BLUE + "Ajouté au scoreboard");	  
	  p.setScoreboard(manager.getNewScoreboard());
      p.setScoreboard(board);
      
      Ligne = 1;
      while (Ligne <= LigneduSB)
      {
        p.sendMessage(ChatColor.GOLD + "Text = " + "Ligne " + Text[(Ligne - 1)]);
        score = objective.getScore(Text[(Ligne - 1)]);
        score.setScore(0 - Ligne);
        Ligne += 1;
      }
	  }

  }
  
  
  
  public void onJoin(PlayerJoinEvent e) {  
	  Player p = e.getPlayer();
	  p.setGameMode(GameMode.ADVENTURE);
	  refresh();
  }
  
  public boolean onCommand(CommandSender sender, Command command, String alias, String[] args)
  {
    final Player p = (Player)sender;
    	
      if (command.getName().equalsIgnoreCase("spect") && p.hasPermission("SBT.spect")) {
    	  p.setGameMode(GameMode.SPECTATOR);
    	  p.sendMessage(ChatColor.BLUE + "Vous Etes Maintenant " + ChatColor.GOLD + "Spéctateur");
      }

      else if (command.getName().equalsIgnoreCase("survie") && p.hasPermission("SBT.spect")) {
    	  p.setGameMode(GameMode.SURVIVAL);
    	  p.sendMessage(ChatColor.BLUE + "Vous Etes Maintenant En " + ChatColor.GOLD + "Survie");
      }
      
      else if (command.getName().equalsIgnoreCase("crea") && p.hasPermission("SBT.spect")) {
    	  p.setGameMode(GameMode.CREATIVE);
    	  p.sendMessage(ChatColor.BLUE + "Vous Etes Maintenant En " + ChatColor.GOLD + "Creatif");
      }
      else if (command.getName().equalsIgnoreCase("adven") && p.hasPermission("SBT.spect")) {
    	  p.setGameMode(GameMode.ADVENTURE);
    	  p.sendMessage(ChatColor.BLUE + "Vous Etes Maintenant En " + ChatColor.GOLD + "Aventure");
      }
    	
      
      
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("Start"))
        {
          p.setScoreboard(this.manager.getNewScoreboard());
          p.setScoreboard(this.board);
          p.sendMessage(ChatColor.BLUE + "Ajouté au scoreboard");
          return false;
        }
        else {
          if (args[0].equalsIgnoreCase("Set"))
          {
            Ligne = 1;
            while (this.Ligne <= this.LigneduSB)
            {
              p.sendMessage(ChatColor.GOLD + "Text = " + "Ligne " + this.Text[(this.Ligne - 1)]);
              this.score = this.objective.getScore(this.Text[(this.Ligne - 1)]);
              this.score.setScore(0 - this.Ligne);
              this.Ligne += 1;
            }

            return false;
          }

          if (args[0].equalsIgnoreCase("RS"))
          {
            this.Ligne = 1;
            while (this.Ligne <= this.LigneduSB)
            {
              this.board.resetScores(this.Text[(this.Ligne - 1)]);
              this.Ligne += 1;
            }

          }
        }

      }
      else if (args.length != 2)
      {
        p.sendMessage(ChatColor.RED + "Essayer Plutot /sbt Start ou /sbt set");
      }
    

    return false;
  }
}