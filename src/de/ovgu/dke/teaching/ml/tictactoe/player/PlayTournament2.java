package de.ovgu.dke.teaching.ml.tictactoe.player;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.game.LoadPlayer;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.game.Tournament;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.PropertyConfigurator;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayTournament2
/*     */ {
/*  26 */   private static final Logger logger = LoggerFactory.getLogger(PlayTournament2.class);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  40 */     ClassLoader loader = PlayMatch2.class.getClassLoader();
/*  41 */     Properties logging = new Properties();
/*     */     try
/*     */     {
/*  44 */       logging.load(loader
/*  45 */         .getResourceAsStream("log4j.tournament.properties"));
/*  46 */       PropertyConfigurator.configure(logging);
/*     */     }
/*     */     catch (Exception e) {
/*  49 */       System.out.println("Could not find file 'log4j.tournament.properites'. Check your classpath!");
/*     */       
/*  51 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  55 */     Properties props = new Properties();
/*     */     try {
/*  57 */       props.load(loader.getResourceAsStream("tictactoe1.properties"));
/*     */     } catch (Exception e) {
/*  59 */       logger.error("Couldn't find file 'tictactoe1.properties'. Check your classpath!");
/*     */       
/*  61 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  65 */     int boardsize = 0;
/*     */     try {
/*  67 */       boardsize = new Integer(props.getProperty("boardsize")).intValue();
/*     */     } catch (NumberFormatException e) {
/*  69 */       logger.error("Size of board is not a number! Check tictactoe.properties.", 
/*  70 */         e);
/*  71 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  75 */     int rounds = 0;
/*     */     try {
/*  77 */       rounds = new Integer(props.getProperty("rounds")).intValue();
/*     */     } catch (NumberFormatException e) {
/*  79 */       logger.error("Rounds is not a number! Check tictactoe.properties.", 
/*  80 */         e);
/*  81 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  85 */     String[] player_prop = props.getProperty("player").split(":");
/*  86 */     Set<IPlayer> player = new HashSet();
/*  87 */     String[] arrayOfString1; int j = (arrayOfString1 = player_prop).length; for (int i = 0; i < j; i++) { String path = arrayOfString1[i];
/*     */       try {
/*  89 */         IPlayer new_player = LoadPlayer.load(path);
/*  90 */         player.add(new_player);
/*  91 */         logger.info("Successfully loaded " + new_player.getName());
/*     */       } catch (Exception e) {
/*  93 */         logger.error("Could not load player '" + path + "'", e);
/*     */       }
/*     */     }
/*     */     
/*  97 */     if (player.size() < 2) {
/*  98 */       logger.error("Not enough player for a tournament!");
/*  99 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/* 103 */     IBoard board = new Board3D(boardsize);
/* 104 */     Tournament tournament = new Tournament(board, player, rounds);
/* 105 */     tournament.run();
/*     */   }
/*     */ }

/* Location:           C:\Users\Gaurav Sharma\Google Drive\DKE Study material\Machine learning\Assignments\Assignment 1\Program\lib\dke-ml-tictactoe-1.3.0.jar
 * Qualified Name:     de.ovgu.dke.teaching.ml.tictactoe.PlayTournament
 * Java Class Version: 7 (51.0)
 * JD-Core Version:    0.7.1
 */
