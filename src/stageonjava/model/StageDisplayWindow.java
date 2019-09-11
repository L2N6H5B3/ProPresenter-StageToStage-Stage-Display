package stageonjava.model;


import static stageonjava.util.ThreadUtil.sleep;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import stageonjava.Main;

/**
 * @author Luke Bradtke
 * @version 1.4
 * @since 1.0
 */

public class StageDisplayWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Initializes WindowFrame
	JFrame window;
	
	// Initializes Panels
	JPanel mainPanel;
	
	// Initializes Labels
	static JLabel currentSlideLabel, nextSlideLabel, clockLabel, messageLabel, videoCountdownLabel;
	
	// Initializes MessageStore Variable
	static String lastMessage;
	
	public StageDisplayWindow() {
		// Initializes "q" quit button listener
		KeyListener quitListener = setKeyListener();
		// Initializes MessageStore Variable
		lastMessage = "";
		
		// Creates a transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Initializes Font Size Variables
		int currentSlideFontSize = Main.getCurrentSlideFontSize();
		int nextSlideFontSize = Main.getNextSlideFontSize();
		int smallFontSize = Main.getSmallFontSize();
		int titleFontSize = Main.getTitleFontSize();
		
		// Initializes window
		window = new JFrame("ProPresenter StageToStage");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setLayout(new BorderLayout());
	    window.setUndecorated(true);
	    
		// Initializes panels
		JPanel mainPanel = new JPanel();
		mainPanel.setFocusable(true);
		mainPanel.addKeyListener(quitListener);
		
	    // Sets Font
	    Font currentSlideFont = new Font("Arial", Font.PLAIN, currentSlideFontSize);
	    Font nextSlideFont = new Font("Arial", Font.PLAIN, nextSlideFontSize);
	    Font smallFont = new Font("Arial", Font.PLAIN, smallFontSize);
	    Font titleFont = new Font("Arial", Font.PLAIN, titleFontSize);
	    
	    // Adds different stage elements
	    currentSlideLabel = new JLabel("");
	    currentSlideLabel.setFont(currentSlideFont);
	    currentSlideLabel.setForeground(Color.WHITE);
	    currentSlideLabel.setVerticalAlignment(SwingConstants.TOP);
	    nextSlideLabel = new JLabel("");
	    nextSlideLabel.setFont(nextSlideFont);
	    nextSlideLabel.setForeground(Color.WHITE);
	    nextSlideLabel.setVerticalAlignment(SwingConstants.TOP);
	    messageLabel = new JLabel("", SwingConstants.CENTER);
	    messageLabel.setFont(smallFont);
	    messageLabel.setForeground(Color.WHITE);
	    videoCountdownLabel = new JLabel("", SwingConstants.CENTER);
	    videoCountdownLabel.setFont(smallFont);
	    videoCountdownLabel.setForeground(Color.WHITE);
	    clockLabel = new JLabel("", SwingConstants.CENTER);
	    clockLabel.setFont(smallFont);
	    clockLabel.setForeground(Color.WHITE);
	    
	    // Creates Border Template for Stage Display Elements
	    Border newBorder = BorderFactory.createEmptyBorder();
	    MatteBorder mainGrayBorder = BorderFactory.createMatteBorder(20, 2, 2, 2, Color.GRAY);
	    // Creates a Border for each Element
	    TitledBorder currentSlideBorder = setBorder("Current Slide", newBorder, titleFont, mainGrayBorder);
	    TitledBorder nextSlideBorder = setBorder("Next Slide", newBorder, titleFont, mainGrayBorder);
	    TitledBorder messageBorder = setBorder("Message", newBorder, titleFont, mainGrayBorder);
	    TitledBorder videoCountdownBorder = setBorder("Video Countdown", newBorder, titleFont, mainGrayBorder);
	    TitledBorder clockBorder = setBorder("Clock", newBorder, titleFont, mainGrayBorder);
	    
	    // Adds a Border to each Element
	    currentSlideLabel.setBorder(currentSlideBorder);
	    nextSlideLabel.setBorder(nextSlideBorder);
	    messageLabel.setBorder(messageBorder);
	    videoCountdownLabel.setBorder(videoCountdownBorder);
	    clockLabel.setBorder(clockBorder);
	    
	    // Creates a Layout for All Elements
	    mainPanel.setLayout(new GridBagLayout());
	    
	    // Sets Element Layout for Secondary Elements (Clock, Message, VideoCounter, Etc)
	    GridBagConstraints secondaryConstraints = new GridBagConstraints();
        secondaryConstraints.fill = GridBagConstraints.HORIZONTAL;
	    secondaryConstraints.weightx = 1;
	    secondaryConstraints.weighty = 0;
	    secondaryConstraints.gridx = 0;
	    secondaryConstraints.gridy = 0;
	    mainPanel.add(messageLabel, secondaryConstraints);
	    secondaryConstraints.fill = GridBagConstraints.HORIZONTAL;
	    secondaryConstraints.weightx = 0;
	    secondaryConstraints.weighty = 0;
	    secondaryConstraints.gridx = 1;
	    secondaryConstraints.gridy = 0;
	    mainPanel.add(videoCountdownLabel, secondaryConstraints);
	    secondaryConstraints.fill = GridBagConstraints.HORIZONTAL;
	    secondaryConstraints.weightx = 0;
	    secondaryConstraints.weighty = 0;
	    secondaryConstraints.gridx = 2;
	    secondaryConstraints.gridy = 0;
	    mainPanel.add(clockLabel, secondaryConstraints);
	    
	    // Sets Element Layout for Primary Elements (CurrentSlide, NextSlide, Etc)
	    GridBagConstraints mainConstraints = new GridBagConstraints();
        mainConstraints.fill = GridBagConstraints.BOTH;
	    mainConstraints.weightx = 1;
	    mainConstraints.weighty = 1.01;
	    mainConstraints.gridx = 0;
	    mainConstraints.gridy = 1;
	    mainConstraints.gridwidth = 3;
	    mainPanel.add(currentSlideLabel, mainConstraints);
	    mainConstraints.fill = GridBagConstraints.BOTH;
	    mainConstraints.weightx = 1;
	    mainConstraints.weighty = 1;
	    mainConstraints.gridx = 0;
	    mainConstraints.gridy = 2;
	    mainConstraints.gridwidth = 3;
	    mainPanel.add(nextSlideLabel, mainConstraints);

	    // Sets Window and Panel Background Colour
	    window.setBackground(Color.BLACK);
	    window.setForeground(Color.BLACK);
	    mainPanel.setBackground(Color.BLACK);
	    
	    // Adds Main Element Panel to Main Window
	    window.add(mainPanel, BorderLayout.CENTER);
	    
	    // Configures Main Window Settings
	    window.getContentPane().setCursor(blankCursor);
	    window.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    window.pack();
	    
	    // Sets the Main Window visible
	    window.setVisible(true);
	    
	    // Refreshes Main Window contents
	    window.revalidate();
	    
    }
	
	public void setCurrentSlide(String slide) {
		currentSlideLabel.setText("<html>" + slide.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
		window.revalidate();
	}
	
	public void setNextSlide(String slide) {
		nextSlideLabel.setText("<html>" + slide.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
		window.revalidate();
	}
	
	public void setNextSlide(BufferedImage slide) {
		window.revalidate();
	}
	
	public void setClock(String time) {
		clockLabel.setText(time);
		window.revalidate();
	}
	
	public void setVideoCountdown(String slide) {
		videoCountdownLabel.setText(slide);
		window.revalidate();
	}
	
	public void setMessage(String slide) {
		messageLabel.setText(slide);
		if (!(lastMessage.equals(slide)) && !(slide.equals(" "))) {
			System.out.println("Message is:"+slide+". "+"LastMessage is:"+lastMessage+". ");
			flashMessage();
			lastMessage = slide;
		} else if (slide.equals(" ")) {
			lastMessage = slide;
		}
	}

	private TitledBorder setBorder(String text, Border newBorder, Font titleFont, MatteBorder mainGrayBorder) {
		TitledBorder border = BorderFactory.createTitledBorder(newBorder, text);
	    border.setTitleColor(Color.GRAY);
	    border.setTitleFont(titleFont);
	    border.setBorder(mainGrayBorder);
	    border.setTitlePosition(TitledBorder.TOP);
	    return border;
	}
	
	private KeyListener setKeyListener() {
		
		KeyListener listener = new KeyListener() {
			 
			@Override
			public void keyPressed(KeyEvent event) {
			}
			 
			@Override
			public void keyReleased(KeyEvent event) {
			    parseEventInfo(event);
			}
			
			@Override
			public void keyTyped(KeyEvent event) {
			}
			
			private void parseEventInfo(KeyEvent e) {
				if (e.getKeyCode() == 'Q') {
					System.exit(0);
				}
			}
		};
		return listener;
	}

	public void revalidateWindow() {
        window.revalidate();
	}

	public void disconnected() {
		setClock("- - : - - --");
		messageLabel.setText("[Error:] ProPresenter Unavailable - Check Network");
		setVideoCountdown("- - : - - : - -");
		setCurrentSlide("Attempting Connection to:"+"\n"+Main.PP.getHost()+":"+Main.PP.getPort()+"\n"+"Press Q to Close");
		setNextSlide("");
	}
	
	private void flashMessage() {
		messageLabel.setOpaque(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
            int counter = 0;
            while (counter < 3) {
        		messageLabel.setBackground(Color.LIGHT_GRAY);
        		window.revalidate();
        		sleep(500);
        		messageLabel.setBackground(Color.BLACK);
        		window.revalidate();
        		sleep(500);
        		counter++;
         	}
            }
        }).start();
	}
}