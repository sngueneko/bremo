package bremoswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.PrintStream;
import java.lang.ThreadGroup;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.LayoutStyle;
import javax.swing.Timer;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import BremoLogFile.LogFileWriter;
import bremo.main.Bremo;

/*
 * SwingBremo.java
 *
 */

/**
 * 
 * @author Ngueneko Steve
 */
public class SwingBremo extends JFrame {

	private static final long serialVersionUID = 1L;
	public static boolean ConsloseModeActive = false;
	/************************** Variables declaration ****************************************/
	private boolean control = false;
	private File[] files;
	public Double[] percentBremo;
	private String path;
	private static SucheBremo suche;
	public static ThreadGroup group;
	public static Bremo[] bremoThread;
	public static String[] bremoThreadFertig;
	public static JButton berechnen;
	// public JCheckBox konsole;
	public JTextArea grosArea;
	private JScrollPane jScrollPane1;
	private JScrollPane jScrollPane2;
	public JTextArea kleinArea;
	private JPanel konsole2;
	private JPanel manager;
	public static JButton stop;
	private JTextField textFile;
	public static JButton wahlFile;
	public static boolean DebuggingMode;
	public static int NrOfFile;
	public static int NrBremoAlive;
	public static JProgressBar progressBar;
	public static JProgressBar progressBarInd;
	// public static JButton pb;
	public static JLabel label;
	public static Timer timerUpdate;
	public static Timer timerCalcul;
	public static int percent;
	PrintStream outStream = new PrintStream(System.out) {

		@Override
		public void println(String s) {
			if (DebuggingMode) {
				ActiveConsole();
				grosArea.append(s + "\n");
				grosArea.setCaretPosition(grosArea.getDocument().getLength());
			} else {
				outStream.flush();
				ActiveConsole();
			}
		}

		@Override
		public void print(String s) {
			if (DebuggingMode) {
				ActiveConsole();
				grosArea.append(s);
				grosArea.setCaretPosition(grosArea.getDocument().getLength());
			} else {
				outStream.flush();
				ActiveConsole();
			}
		}
	};

	PrintStream errStream = new PrintStream(System.err) {

		@Override
		public void println(String s) {
			kleinArea.append(s + "\n");
			kleinArea.setCaretPosition(kleinArea.getDocument().getLength());

		}

		@Override
		public void print(String s) {
			kleinArea.append(s);
			kleinArea.setCaretPosition(kleinArea.getDocument().getLength());
		}
	};

	/************************** End of variables declaration ****************************************/

	/***** Creates new form SwingBremo *************************************************************/
	public SwingBremo() {
		initComponents();
		placeFrame();
	}

	/***
	 * This method is called from within the constructor to initialize the form.
	 ***********************/
	private void initComponents() {

		System.setOut(outStream);
		System.setErr(errStream);

		manager = new JPanel();

		berechnen = new JButton();
		wahlFile = new JButton();
		stop = new JButton();

		textFile = new JTextField();

		// konsole = new JCheckBox();
		konsole2 = new JPanel();

		kleinArea = new JTextArea();
		grosArea = new JTextArea();

		jScrollPane1 = new JScrollPane();
		jScrollPane2 = new JScrollPane();

		progressBar = new JProgressBar(0, 100);
		progressBarInd = new JProgressBar(0, 100);

		label = new JLabel();
		percent = 0;
		path = ".";
		DebuggingMode = false;
		NrOfFile = 0;
		NrBremoAlive = 0;

		/****** HAUPT FRAME ****************************************************************/
		setTitle("Bremo 3.5");
		setBackground(new Color(255, 255, 255));
		setIconImage(new ImageIcon(getClass().getResource(
				"/bremoswing/bild/bremo1.png")).getImage());
		setName("BremoGraphik");
		setResizable(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				closeFrame();
			}
		});

		// manager.setBorder(BorderFactory.createTitledBorder(null, "Manager",
		// TitledBorder.DEFAULT_JUSTIFICATION,
		// TitledBorder.DEFAULT_POSITION, new Font("comic sans ms", 1, 14)));

		// manager.setLayout(new GridBagLayout());
		// FlowLayout experimentLayout = new FlowLayout();
		// experimentLayout.setAlignment(FlowLayout.LEADING);
		// manager.setLayout(experimentLayout);
		manager.setLayout(new GridBagLayout());
        //manager.setLayout(new BorderLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 0);
		// gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.weightx = 0;
		gc.gridx = 0;
		gc.gridy = 0;

		/************ BUTTON BERECHNEN ************************************/
		ImageIcon beri = new ImageIcon(getClass().getResource(
				"/bremoswing/bild/play_blue.png"));
		berechnen.setIcon(beri); // NOI18N
		berechnen.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/bremoswing/bild/play_blue2.png")));
		berechnen.setToolTipText("Berechnung ausf�hren");
		// berechnen.setContentAreaFilled(false);
		// berechnen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		// berechnen.setVerifyInputWhenFocusTarget(false);
		berechnen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// wahlFile.setVisible(false);
				// berechnen.setVisible(false);
				stop.setVisible(true);
				percent = 0;
				BerechnungPush(e);

			}
		});
		//berechnen.setMargin(new Insets(0, 0, 0, ));
		
		// berechnen.setBounds(10, 18, 33, 33);
		// berechnen.setSize(20, 25);
		// manager.add(berechnen, gc);
		manager.add(berechnen, gc);

		/************ BUTTON WAHLFILE ************************************/
		ImageIcon wfi = new ImageIcon(getClass().getResource(
				"/bremoswing/bild/folder_blue.png")); // folder_smart.png
		wahlFile.setIcon(wfi); // NOI18N
		// wahlFile.setRolloverEnabled(true);
		// ImageIcon wfiRO = new ImageIcon(getClass().getResource(
		// "/bremoswing/bild/folder_smart_Rollover.png"));
		wahlFile.setRolloverIcon(new ImageIcon(getClass().getResource(
				"/bremoswing/bild/folder_blue2.png")));
		// wahlFile.setPressedIcon(wfi);
		// wahlFile.setToolTipText("InputFile ausw�hlen");
		// wahlFile.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		// wahlFile.setBorderPainted(true);
		// wahlFile.setContentAreaFilled(false);
		// wahlFile.setOpaque(false);
		wahlFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wahlPush(e);
			}

		});
		//wahlFile.setMargin(new Insets(0, 0, 0, 10));
		// wahlFile.setBounds(70, 18, 33, 33);
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 0;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.ipadx = 0;
		manager.add(wahlFile, gc);

		/************ BUTTON STOP ************************************/
		stop.setIcon(new ImageIcon(getClass().getResource(
				"/bremoswing/bild/stop_blue.png")));
		stop.setToolTipText("Stop Bremo");
		// stop.setRolloverIcon(new ImageIcon(getClass().getResource(
		// "/bremoswing/bild/stop_red.png")));
		// stop.setContentAreaFilled(false);
		// stop.setMaximumSize(null);
		// stop.setMinimumSize(null);
		stop.setEnabled(false);
		stop.setMargin(new Insets(0, 0, 0, 0));
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				stopPush(e);
			}
		});
		//stop.setMargin(new Insets(0, 0, 0, 10));
		// ////stop.setVisible(false);
		// stop.setBounds(130, 18, 33, 33);

		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 0;
		gc.gridx = 2;
		gc.gridy = 0;
		gc.ipadx = 0;
		manager.add(stop, gc);

		/************ LABEL ************************************/
		// label.setBounds(330, 27, 160, 20);
		// label.setFont(new Font(null, 0, 14));
		label.setOpaque(true);
		label.setBackground(new Color(255, 255, 255));
		// Border border = LineBorder.createGrayLineBorder();
		// Border border = new LineBorder(Color.GRAY,2,true);
		// label.setBorder(border);

		// label.setIcon(new ImageIcon(getClass().getResource(
		// "/bremoswing/bild/label_blue.png")));
		// label.setHorizontalTextPosition(JLabel.LEFT);
		// label.setEnabled(false);
		// label.setSize(new Dimension(getSize().width/2,20));

		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(5, 10, 0, 155);
		gc.weightx = 0.5;
		gc.gridx = 4;
		gc.gridy = 0;
		gc.ipadx = 0;
		gc.ipady = 0;
		manager.add(label, gc);

		/************ PROGESSBAR ************************************/
		// progressBar.setBounds(520, 21, 100, 30);
		progressBarInd.setValue(100);
		progressBarInd.setIndeterminate(true);
		progressBarInd.setVisible(false);
       
		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(10, 0, 5, 0);
		gc.weightx = 0.1;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.gridx = 9;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.CENTER;
		manager.add(progressBarInd, gc);

		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setVisible(false);
		

		manager.add(progressBar, gc);

		timerCalcul = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				boolean bremoActiv = false;
				for (Bremo bremo : bremoThread) {
					if (bremo.get_myCaseState())
						bremoActiv = true;
				}
				if (bremoActiv) {
					// label.setForeground(new Color(25,49,187));
					label.setText(" Berechnung gestartet ");
					progressBar.setVisible(true);
					double Sum = 0;
					// pb.setVisible(false);
					progressBarInd.setVisible(false);
					stop.setEnabled(true);
					for (int i = 0; i < bremoThread.length; i++) {
						try {
							Sum = Sum
									+ (bremoThread[i].get_myCase()
											.get_aktuelle_Rechenzeit() / bremoThread[i]
											.get_myCase().SYS.RECHNUNGS_ENDE_DVA_SEC);
						} catch (Exception e) {

						}
					}
					percent = (int) ((Sum / NrBremoAlive) * 100);
					progressBar.setValue(percent);
					progressBar.repaint();
				} else {
					// label.setForeground(new Color(255,0,0));
					label.setText(" Initialization l�uft ... ");
				}
			}
		});

		/************ TEXTFIELD TEXTFILE ************************************/
		textFile.setEditable(false);
		textFile.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		textFile.setEnabled(false);
		textFile.setMaximumSize(new Dimension(0, 0));
		textFile.setMinimumSize(new Dimension(0, 0));
		textFile.setPreferredSize(new Dimension(0, 0));
		textFile.setBounds(180, 20, 0, 0);
		gc.fill = GridBagConstraints.LAST_LINE_END;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 0.5;
		gc.gridx = 4;
		gc.gridy = 0;
		gc.ipadx = 0;
		gc.ipady = 0;

		/************ PANEL KONSOLE ************************************/
		// konsole2.setBorder(BorderFactory.createTitledBorder(null, "Konsole",
		// TitledBorder.DEFAULT_JUSTIFICATION,
		// TitledBorder.DEFAULT_POSITION, new Font("comic sans ms", 1, 14))); //
		// NOI18N

		/************ TEXTAREA GROSAREA OUTPUT ************************************/
		grosArea.setColumns(20);
		grosArea.setEditable(false);
		// grosArea.setFont(new Font("comic sans ms", 3, 16)); // NOI18N
		grosArea.setRows(5);
		grosArea.setText("Programm l�uft... \nW�hlen Sie die Input Datei Und Dann Einfach die Berechnung Ausf�hren .");
		grosArea.setMinimumSize(new Dimension(76, 22));
		grosArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
					
					suche = new SucheBremo(grosArea);
				}
			}
		});
		konsole2.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 660;
		c.weighty = 420;

		jScrollPane1.setViewportView(grosArea);
		jScrollPane1.setBounds(13, 20, 660, 420);
		jScrollPane1.setVisible(false);
		konsole2.add(jScrollPane1, c);

		/************ TEXTAREA KLEINAREA OUTPUT ************************************/
		kleinArea.setColumns(20);
		kleinArea.setEditable(false);
		// kleinArea.setFont(new Font("comic sans ms", 3, 13)); // NOI18N
		kleinArea.setForeground(new Color(255, 0, 0));
		kleinArea.setRows(5);
		kleinArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
					
					suche = new SucheBremo(kleinArea);
				}
			}
		});

		jScrollPane2.setViewportView(kleinArea);
		c.gridwidth = 1;
		c.weighty = 180;
		jScrollPane2.setBounds(13, 450, 660, 180);
		konsole2.add(jScrollPane2, c);
		
//		 JToolBar toolBar = new JToolBar("BremoManager");
//	     toolBar.setFloatable(true);
//	     toolBar.setRollover( true);
//	     toolBar.setMargin(new Insets(5,10 , 5, 10));
//	     
//	     toolBar.add(berechnen);
//	     toolBar.add(wahlFile);
//	     toolBar.add(stop);
//	     toolBar.addSeparator(new Dimension(100,0));
//	     toolBar.add(label);
//	     toolBar.addSeparator(new Dimension(100,0));
//	     
//	     toolBar.add(progressBarInd);
//	     toolBar.add(progressBar);
	     
	     
         
	   //  manager.add(toolBar, BorderLayout.PAGE_START);
		/************ FRAME LAYOUT EINSTELUNG ************************************/
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.TRAILING)
										.addComponent(manager,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 685,
												Short.MAX_VALUE)
										.addComponent(konsole2,
												GroupLayout.Alignment.LEADING,
												GroupLayout.DEFAULT_SIZE, 685,
												Short.MAX_VALUE))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(manager,
										GroupLayout.PREFERRED_SIZE, 38,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(konsole2,
										GroupLayout.DEFAULT_SIZE, 180,
										Short.MAX_VALUE).addContainerGap()));
		/*****************************************************************************************/
		pack();

	}

	/** Warning Massage **********/
	protected void messsage() {
		JOptionPane.showMessageDialog(this,
				"Berechnung l�uf gerade... Warten Sie bis Ende ", "Achtung",
				JOptionPane.WARNING_MESSAGE);
	}

	/**** Button Setting To Run Bremo Thread / to Run the calcul ***********/
	private void BerechnungPush(ActionEvent e) {
		grosArea.setText("");
		kleinArea.setText("");
		if (textFile.getText().equals("")) {
			JOptionPane.showMessageDialog(this,
					"�berpr�fen Sie Bitte die InputDatei !!!", "Achtung",
					JOptionPane.WARNING_MESSAGE);
			wahlFile.setVisible(true);
			berechnen.setVisible(true);
		} else {
			LogFileWriter.reinitialisierung();
			berechnen.setEnabled(false);
			wahlFile.setEnabled(false);
			stop.setEnabled(false);
			control = true;

			try {
				for (int i = 0; i < files.length; i++) {

					bremoThread[i].start();

				}

				progressBarInd.setVisible(true);

				label.setText(" Operation gestartet .... ");
				timerCalcul.start();

			} catch (IllegalThreadStateException e1) {
				e1.printStackTrace();
				ActiveIcon();

				label.setText(" Bitte InputFile Neu Ausw�hlen ! ");
			}
		}

	}

	/*********** Button Setting To Choose a File *****************************************/
	private void wahlPush(ActionEvent e2) {
		grosArea.setText("");
		kleinArea.setText("");
		berechnen.setEnabled(false);
		label.setText("");

		DebuggingMode = false;
		ActiveConsole();
		progressBar.setValue(0);
		JFileChooser fileChooser = new JFileChooser(path);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(true);
		ExtensionFileFilter txtFilter = new ExtensionFileFilter(null,
				new String[] { "txt" });

		fileChooser.addChoosableFileFilter(txtFilter);
		try {
			// label.setForeground(new Color (165,42,42));
			label.setText(" Datei Werden geladen ... ");
			// label.setEnabled(true);

			int status = fileChooser.showOpenDialog(getRootPane());

			if (status == JFileChooser.APPROVE_OPTION) {
				if (fileChooser.getSelectedFile() != null) {
					files = fileChooser.getSelectedFiles();
					textFile.setText(files[0].getPath());
					path = files[0].getParent();
					bremoThread = new Bremo[files.length];
					group = new ThreadGroup("BremoFamily");
					for (int i = 0; i < bremoThread.length; i++) {
						bremoThread[i] = new Bremo(group, files[i]);
					}
					bremoThreadFertig = new String[files.length];
					NrOfFile = files.length;
					NrBremoAlive = files.length;
					if (NrOfFile == 1)
						SetDebbugingMode(true);
					// label.setForeground(new Color(0,0,255));
					label.setText(" Datei mit Erfolg Importiert ! ");

					ActiveConsole();
				}
			} else if (status == JFileChooser.CANCEL_OPTION) {
				// label.setForeground(new Color (165,42,42));
				label.setText(" Import von Datei Unterbrechen ! ");

				fileChooser.cancelSelection();
			}
		} catch (Exception e) {
			// label.setForeground(new Color (255,0,0));
			label.setText(" Fehler aufgetreten ! ");
			e.printStackTrace();
		}
		berechnen.setEnabled(true);
	}

	/********** Button Setting To Stop Running of Bremo Thread **************/
	private void stopPush(ActionEvent e) {
		if (!control) {
			JOptionPane.showMessageDialog(this,
					"Starten Sie Zu erst die Berechnung.", "Achtung",
					JOptionPane.WARNING_MESSAGE);
		} else {
			errStream.flush();
			group.interrupt();
			errStream.close();
			outStream.close();
			control = false;
			ActiveIcon();
			// bremoThread[0].stopMe();
		}
	}

	/****** Active Icon *********************************/
	public static void ActiveIcon() {
		timerCalcul.stop();
		wahlFile.setEnabled(true);
		berechnen.setEnabled(true);
		stop.setEnabled(false);
		label.setText(" Operation beendet. ");
		progressBar.setValue(0);
		progressBar.setVisible(false);
		progressBarInd.setVisible(false);
	}

	/****** Change the Mode of The Console When DebbugMode is Active ***********/
	public void ActiveConsole() {

		if (NrOfFile > 1) {
			jScrollPane1.setVisible(false);
			setSize(710, 320);
			DebuggingMode = false;
		} else if (NrOfFile == 1) {
			if (!DebuggingMode) {
				jScrollPane1.setVisible(false);
				setSize(710, 320);
			} else {
				jScrollPane1.setVisible(true);
				setSize(710, 750);
			}
		}
	}

	public static void SetDebbugingMode(Boolean mode) {
		if (NrOfFile == 1) {
			DebuggingMode = mode;
		}
	}

	public static void setNrOfBremoAlive() {
		NrBremoAlive--;
	}

	public static void PutInBremoThreadFertig(String s) {
		for (int i = 0; i < bremoThreadFertig.length; i++) {
			if (bremoThreadFertig[i] != null)
				continue;
			else
				bremoThreadFertig[i] = s;
			break;

		}
	}

	/****** SEE IF THE RUNNING OPERATION ARE FINISHED *******************************/
	public static void StateBremoThread() {

		if (group.activeCount() <= 1) {
			timerCalcul.stop();
			// stop.setEnabled(false);
			JFrame popup = new JFrame();
			if (bremoThreadFertig[0] != null) {
				// label.setForeground(new Color(25,49,187));
				label.setText(" Berechnung Fertig ! ");
				JOptionPane.showMessageDialog(popup,
						"Die Berechnung ist Fertig !!!", "Zustand Berechnung",
						JOptionPane.INFORMATION_MESSAGE);
			}
			for (String str : bremoThreadFertig) {
				if (str != null)
					System.err.println(str);
			}
			ActiveIcon();
			progressBar.setVisible(false);
			progressBarInd.setVisible(false);
		}
	}

	/************************ place Frame to the center ************************/
	private void placeFrame() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle window = getBounds();
		this.setLocation((screen.width - window.width) / 2,
				(screen.height - window.height) / 2);
	}

	/************************* close the Frame ********************************/
	public void closeFrame() {
		final JOptionPane optionPane = new JOptionPane(
				"Wollen Sie Wirklich das Program beendet ?",
				JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);

		final JDialog dialog = optionPane.createDialog(this, "Exit");

		optionPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				String prop = e.getPropertyName();

				if (dialog.isVisible() && (e.getSource() == optionPane)
						&& (prop.equals(JOptionPane.VALUE_PROPERTY))) {
					dialog.setVisible(false);
				}
			}
		});
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);

		int value = ((Integer) optionPane.getValue()).intValue();
		if (value == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else if (value == JOptionPane.NO_OPTION) {

		}
	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the
		 * default look and feel. For details see
		 * http://download.oracle.com/javase
		 * /tutorial/uiswing/lookandfeel/plaf.html
		 */
		UIManager.put("nimbusOrange", new Color(28, 138, 224)); // (25,49,187));//Color(110,170,0));
		try {
			for (UIManager.LookAndFeelInfo info : UIManager
					.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(SwingBremo.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(SwingBremo.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(SwingBremo.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(SwingBremo.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}

		/* Create and display the form */
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				new SwingBremo().setVisible(true);

			}
		});
	}

}