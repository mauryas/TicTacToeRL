package de.ovgu.dke.teaching.ml.tictactoe.player;

/*     */ 
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.api.IBoard;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.api.IPlayer;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.game.Board3D;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.game.LoadPlayer;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.game.Match;
/*     */ import de.ovgu.dke.teaching.ml.tictactoe.player.RandomPlayer;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Properties;
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
/*     */ 
/*     */ 
/*     */ public class PlayMatch2
/*     */ {
/*  27 */   private static final Logger logger = LoggerFactory.getLogger(PlayMatch2.class);
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
/*  41 */     ClassLoader loader = PlayMatch2.class.getClassLoader();
/*  42 */     Properties logging = new Properties();
/*     */     try
/*     */     {
/*  45 */       logging.load(loader.getResourceAsStream("log4j.match.properties"));
/*  46 */       PropertyConfigurator.configure(logging);
/*     */     } catch (Exception e) {
/*  48 */       System.out.println("Could not find file 'log4j.match.properites'. Check your classpath!");
/*     */       
/*  50 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  54 */     Properties props = new Properties();
/*     */     try {
/*  56 */       props.load(loader.getResourceAsStream("tictactoe1.properties"));
/*     */     } catch (Exception e) {
/*  58 */       logger.error("Couldn't find file 'tictactoe1.properties'. Check your classpath!");
/*     */       
/*  60 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  64 */     int boardsize = 0;
/*     */     try {
/*  66 */       boardsize = new Integer(props.getProperty("boardsize")).intValue();
/*     */     } catch (NumberFormatException e) {
/*  68 */       logger.error("Size of board is not a number! Check tictactoe.properties.", 
/*  69 */         e);
/*  70 */       System.exit(0);
/*     */     }
/*     */     
/*     */ 
/*  74 */     String[] player = props.getProperty("player").split(":");
/*  75 */     IPlayer player1 = null;
/*  76 */     IPlayer player2 = null;
/*  77 */     if (player.length == 0) {
/*  78 */       logger.info("No players provided, loading two Random Player.");
/*  79 */       player1 = new RandomPlayer();
/*  80 */       player2 = new RandomPlayer();
/*  81 */     } else if (player.length == 1) {
/*  82 */       logger.info("Only one player provided, loading RandomPlayer as opponent");
/*     */       try
/*     */       {
/*  85 */         player1 = LoadPlayer.load(player[0]);
/*     */       } catch (Exception e) {
/*  87 */         logger.error("Could not load player", e);
/*  88 */         System.exit(0);
/*     */       }
/*  90 */       player2 = new RandomPlayer();
/*  91 */     } else if (player.length >= 2) {
/*  92 */       logger.info("Two player provided, loading both");
/*     */       try {
/*  94 */         player1 = LoadPlayer.load(player[0]);
/*  95 */         logger.info("Successfully loaded " + player1.getName() + 
/*  96 */           " as player 1");
/*     */         
/*  98 */         player2 = LoadPlayer.load(player[1]);
/*  99 */         logger.info("Successfully loaded " + player2.getName() + 
/* 100 */           " as player 2");
/*     */       } catch (Exception e) {
/* 102 */         logger.error("Could not load player", e);
/* 103 */         System.exit(0);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 108 */     logger.info("Match starts");
/* 109 */     IBoard board = new Board3D(boardsize);
/* 110 */     Match match = new Match(board, player1, player2);
/* 111 */     match.play();
/*     */   }
/*     */ }
