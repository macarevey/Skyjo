import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;

public class MouseListenerPanel extends JPanel implements MouseListener {
	private Deck d;
	private ArrayList<Hand> hands;
	private Deck deck;
	private Discard discard;
	private double ellipseW, ellipseH;
	private int players;
	private Robot[] robots;
	
	// Game state
	private int playerTurn;
	private String gameState;
	private Card deckCardPulled;
	private Card discardCardPulled;
	private boolean newRound;
	private ArrayList<Integer> scores;
	
	private boolean lastTurns;
	private int lastTurnIndex;
	
	private boolean gameEnd;
	private String winner;
	
	// Game state Methods
	public void switchTurn() {
		playerTurn++;
		if (playerTurn >= players) {
			playerTurn = 0;
		}
		System.out.println("Player turn: " + playerTurn);
	}
	
	public ArrayList<Integer> calcScores() {
		for (int i = 0; i < hands.size(); i++) {
			int score = 0;
			for (int j = 0; j < hands.get(i).getHand().size(); j++) {
				score += hands.get(i).getHand().get(j).getNum();
			}
			scores.set(i, score + scores.get(i));
		}
		return scores;
	}
	
	public void checkFlipCards() {
		for (int i = 0; i < hands.size(); i++) {
			if (hands.get(i).allFlipped()) {
				lastTurns = true;
				lastTurnIndex = i;
				System.out.println("lastTurns = true, index is " + lastTurnIndex);
			}
		}
	}
	
	public boolean getIsLastTurns() {
		return lastTurns;
	}
	
	public int getLastTurnIndex() {
		return lastTurnIndex;
	}
	
	public int getPlayerTurn() {
		return playerTurn;
	}
	
	public void makeGameEnd(String w) {
		gameEnd = true;
		winner = w;
	}
	
	public Robot[] getRobots() {
		return robots;
	}
	
	// Math for hands position
	public void doEllipse() {
	    int frameWidth = getWidth();
	    int frameHeight = getHeight();

	    // Ellipse radii
	    ellipseW = frameWidth - frameWidth / 3.5;
	    ellipseH = frameHeight - frameHeight / 3.5;
	    double horizontalRadius = ellipseW / 2;
	    double verticalRadius = ellipseH / 2;

	    // Ellipse center coordinates
	    double xCenter = frameWidth / 2;
	    double yCenter = frameHeight / 2;

	    int count = 90; // Start at 90 degrees (for the player's position at the bottom)
	    for (int i = 0; i < hands.size(); i++) {
	        // Calculate coordinates for each hand
	        hands.get(i).calculateCoords(count, horizontalRadius, verticalRadius, xCenter, yCenter, frameWidth, frameHeight);

	        // Adjust angle for the next hand
	        count += 360 / players;
	        if (count > 359) {
	            count -= 360;
	        }
	    }
	    deck.calculateCoords(frameWidth, frameHeight, xCenter, yCenter);
	    discard.calculateCoords(frameWidth, frameHeight, xCenter, yCenter);
	}
	
	private void runRobotTurn() {
	    for (int i = 1; i < players; i++) {
	        final int currentRobotIndex = i - 1;

	        Timer timer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	//System.out.println("Timer");
	                boolean doneWithTurn = false;

	                if (playerTurn != 0) {
	                    robots[currentRobotIndex].updateInfo(robots[currentRobotIndex].getGameState(), hands.get(playerTurn), deck, discard, MouseListenerPanel.this);
	                    robots[currentRobotIndex].makeDecision();
	                    doneWithTurn = robots[currentRobotIndex].getDoneWithTurn();

	                    if (doneWithTurn) {
	                        switchTurn();
	                        robots[currentRobotIndex].setGameState("Normal");
	                    }
	                }

	                if (doneWithTurn || playerTurn == 0) {
	                	robots[currentRobotIndex].setDoneWithTurn(false);
	                	checkFlipCards();
	                    ((Timer) e.getSource()).stop();
	                }
	            }
	        });

	        timer.setRepeats(true);
	        timer.start();
	    }
	}

	
	public MouseListenerPanel(ArrayList<Hand> h, Deck d, Discard disc, int p, ArrayList<Integer> s, int plyrTurn) {
		addMouseListener(this);
		hands = h;
		deck = d;
		discard = disc;
		players = p;
		playerTurn = 0;
		gameState = "Normal";
		newRound = false;
		scores = s;
		robots = new Robot[players-1];
		playerTurn = plyrTurn;
		System.out.println("Starting player turn in " + plyrTurn);
		for (int i = 0; i < players-1; i++) {
			robots[i] = new Robot("Robot " + (i+1));
		}
		doEllipse();
		if (playerTurn != 0) {
			runRobotTurn();
		}
	}
	
	@Override
	public void paint(Graphics g) {
	    super.paintComponent(g);
	    if (!gameEnd) {
		    doEllipse();
		    //double x = (getWidth() - ellipseW) / 2;
		    //double y = (getHeight() - ellipseH) / 2;
		    g.setColor(Color.blue);
		    //g.drawOval((int)x, (int)y, (int)ellipseW, (int)ellipseH);
		    
		    // Drawing scores
		    int scoresX = getWidth() / 40;
		    int scoresY = getHeight() / 20;
		    for (int i = 0; i < scores.size(); i++) {
		        int score = scores.get(i);
		        if (i == 0) {
		            g.drawString("Player:", scoresX, scoresY);
		            g.drawString("" + score, scoresX*2, scoresY);
		        } else {
		            g.drawString("Bot " + i + ":", scoresX, i*scoresY+scoresY);
		            g.drawString("" + score, scoresX*2, i*scoresY+scoresY);
		        }
		    }
		    
		    // Drawing hands
		    int handCount = 1;
		    for (Hand h : hands) {
		        if (h.getIsPlayer()) {
		            g.drawString("Player", (int)h.getX(), (int)h.getY());
		        } else {
		            g.drawString("Bot " + handCount, (int)h.getX(), (int)h.getY());
		            handCount++;
		        }
	
		        for (Card c : h.getHand()) {
		            if (c.isOnBack()) {
		                g.drawImage(c.getBack(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
		            } else {
		                g.drawImage(c.getImage(), c.getX(), c.getY(), c.getWidth(), c.getHeight(), null);
		            }
		        }
		    }
	
		    // Drawing deck, discard piles
		    int deckSize = deck.getDeck().size();
		    int discardSize = discard.getPile().size();
		    Card deckCard = null;
		    Card discardCard = null;
		    try {
		        deckCard = deck.getDeck().get(deckSize-1);
		        discardCard = discard.getPile().get(discardSize-1);
		    } catch (Exception e) {}
	
		    if (discardCard != null && discardCard.isOnBack()) {
		        g.drawImage(discardCard.getBack(), discard.getX(), discard.getY(), discardCard.getWidth(), discardCard.getHeight(), null);
		    } else if (discardCard != null) {
		        g.drawImage(discardCard.getImage(), discard.getX(), discard.getY(), discardCard.getWidth(), discardCard.getHeight(), null);
		    }
	
		    if (deckCard != null && deckCard.isOnBack()) {
		        g.drawImage(deckCard.getBack(), deck.getX(), deck.getY(), deckCard.getWidth(), deckCard.getHeight(), null);
		    } else if (deckCard != null) {
		        g.drawImage(deckCard.getImage(), deck.getX(), deck.getY(), deckCard.getWidth(), deckCard.getHeight(), null);
		    }
	
		    if (deckCardPulled != null) {
		        g.drawImage(deckCardPulled.getImage(), deckCardPulled.getX(), deckCardPulled.getY(), deckCardPulled.getWidth(), deckCardPulled.getHeight(), null);
		    }
	
		    if (discardCardPulled != null) {
		        g.drawImage(discardCardPulled.getImage(), discardCardPulled.getX(), discardCardPulled.getY(), discardCardPulled.getWidth(), discardCardPulled.getHeight(), null);
		    }
		} else {
			g.drawString((winner + " wins!"), 100, 100);
		}

	    //g.drawString(".", getWidth() / 2, getHeight() / 2);
	}
	
	public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
    	int y = e.getY();
    	boolean cardsChanged = false;
    	if (playerTurn == 0 && e.getButton() == e.BUTTON1) { // If it's the players turn and he left clicked
	    	if (gameState.equals("Normal")) {
		    	int deckSize = deck.getDeck().size();
		    	Card deckCard = deck.getDeck().get(deckSize-1);
		    	if (x >= deck.getX() && x <= (deckCard.getWidth() + deck.getX()) && y >= deck.getY() && y <= (deckCard.getHeight() + deck.getY())) {
		    		//System.out.println("Clicked on the deck, card is " +  deckCard.getNum());
		    		gameState = "Deck";
		    		Card cardPulled = deck.getCards(1).get(0);
		    		cardPulled.setCoords(deckCard.getX(), deckCard.getY()+deckCard.getHeight()+5);
		    		deckCardPulled = cardPulled;
		    		repaint();
		    	}
		    	
		    	int discardSize = discard.getPile().size();
		    	Card discardCard = discard.getPile().get(discardSize-1);
		    	if (discard.getPile().size() > 0 && x >= discard.getX() && x <= (discardCard.getWidth() + discard.getX()) && y >= discard.getY() && y <= (discardCard.getHeight() + discard.getY())) {
		    		gameState = "Discard";
		    		Card cardPulled = discard.takeCard();
		    		cardPulled.setCoords(cardPulled.getX(), cardPulled.getY() + cardPulled.getHeight() + 5);
		    		discardCardPulled = cardPulled;
		    		repaint();
		    	}
	    	} else if (gameState.equals("Deck")) {
	    		int discardSize = discard.getPile().size();
		    	Card discardCard = discard.getPile().get(discardSize-1);
		    	
		    	if (x >= discard.getX() && x <= (discardCard.getWidth() + discard.getX()) && y >= discard.getY() && y <= (discardCard.getHeight() + discard.getY())) {
		    		deckCardPulled.setCoords(discardCard.getX(), discardCard.getY());
		    		discard.addCard(deckCardPulled);
		    		gameState = "FlipCard";
		    		repaint();
		    	}
		    	
		    	if (!gameState.equals("FlipCard")) { // Check if the code above did not fire
		    		Hand h = hands.get(0);
			    	if (x >= h.getX() && x <= (h.getWidth() + h.getX()) && y >= h.getY() && y <= (h.getHeight() + h.getY())) {
			    		for (Card c : h.getHand()) {
			    			if (x >= c.getX() && x <= (c.getWidth() + c.getX()) && y >= c.getY() && y <= (c.getHeight() + c.getY())) {
			    				h.replaceCard(c, deckCardPulled);
			    				discard.addCard(c);
			    				gameState = "Normal";
			    				deckCardPulled = null;
			    				cardsChanged = true;
			    				repaint();
			    			}
			    		}
			    	}
		    	}
	    	} else if (gameState.equals("FlipCard")) {
	    		Hand h = hands.get(0);
		    	if (x >= h.getX() && x <= (h.getWidth() + h.getX()) && y >= h.getY() && y <= (h.getHeight() + h.getY())) {
		    		for (Card c : h.getHand()) {
		    			if (c.isOnBack() && x >= c.getX() && x <= (c.getWidth() + c.getX()) && y >= c.getY() && y <= (c.getHeight() + c.getY())) {
		    				c.flipCard();
		    				gameState = "Normal";
		    				cardsChanged = true;
		    				repaint();
		    			}
		    		}
		    	}
		    } else if (gameState.equals("Discard")) {
		    	Hand h = hands.get(0);
		    	if (x >= h.getX() && x <= (h.getWidth() + h.getX()) && y >= h.getY() && y <= (h.getHeight() + h.getY())) {
		    		for (Card c : h.getHand()) {
		    			if (x >= c.getX() && x <= (c.getWidth() + c.getX()) && y >= c.getY() && y <= (c.getHeight() + c.getY())) {
		    				c.flipCard();
		    				h.replaceCard(c, discardCardPulled);
		    				discard.addCard(c);
		    				discardCardPulled = null;
		    				gameState = "Normal";
		    				cardsChanged = true;
		    				repaint();
		    			}
		    		}
		    	}
		    }
    	}
    	if (cardsChanged) {
            hands.get(0).checkColumns();
            switchTurn();
            checkFlipCards();
            runRobotTurn();
        }
    }
}
