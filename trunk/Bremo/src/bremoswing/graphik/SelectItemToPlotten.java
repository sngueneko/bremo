package bremoswing.graphik;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;

import bremoGraphik.bremoGraphik;

import bremo.main.Bremo;
import bremoExceptions.ParameterFileWrongInputException;
import bremoswing.SwingBremo;

/**
 *
 * @author Beaudin
 */
public class SelectItemToPlotten extends JFrame {

    /**
     * Creates new form SelectItemToPlotten
     */
    public SelectItemToPlotten() {
        initComponents();
        SwingBremo.placeFrame(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new JPanel(){
			@SuppressWarnings("null")
			public void paintComponent(Graphics g) 
            {
              
				URL url = getClass().getResource("/bremoswing/bild/Abstract_Frozen_Blue.jpg");
				ImageIcon icon = new ImageIcon(url);
			    Image img = icon.getImage();
			    BufferedImage buffImage = 
			    	      new BufferedImage(
			    	          img.getWidth(null), 
			    	          img.getHeight(null), 
			    	          BufferedImage.TYPE_INT_ARGB);
			    Graphics gr = buffImage.getGraphics();
			    gr.drawImage(img, 0, 0, null);
			    img = buffImage.getSubimage(200, 210, 500, 100);
				g.drawImage(img, 0, 0, null);
                
            } 
		};
        fileComboBox = new JComboBox();
        
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource(
				"/bremoswing/bild/bremo2.png")).getImage());
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(BorderFactory.createTitledBorder("W�hlen Input File zu plotten"));

       // fileComboBox.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        String[] tmp = SwingBremo.bremoThreadFertig;
        addFileItemToComboBox(fileComboBox, tmp);
        fileComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    File input = new File(SwingBremo.path+"/"+fileComboBox.getSelectedItem().toString());
			    String berechnungModell = "";
			    try {
			    	BufferedReader in = new BufferedReader(new FileReader(input.getPath()));
					String zeile = null;
					String [] header = null;
					while((zeile = in.readLine()) != null) {
						zeile=zeile.replaceAll(" ", "");
						zeile=zeile.replaceAll("\t", "");
						header = zeile.split(":");
						header[0] = header[0].replace("[", "");
						if (header[0].equals("berechnungsModell")){
							String[] tmp =  header[1].split("_");
							if (tmp[0].equals("DVA")){
								berechnungModell = "DVA";
							}
							else if (tmp[0].equals("APR")){
								berechnungModell = "APR";
							}
							
						}
					}
					// new bremoGraphik(input);
					if (berechnungModell.equals("DVA")){
						
						new DVA_ModellGraphik(input);
					}
					else if  (berechnungModell.equals("APR")){
						
						new APR_ModellGraphik(input); 
					}
					in.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParameterFileWrongInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
				
			}
		});
        
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileComboBox, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>
    
    /**
	 * f�ge elemente von Array to JComboBox als Item hinzu. Nur f�r File
	 * ComboBox
	 */
	public void addFileItemToComboBox(JComboBox cb, String[] item) {

		// cb.addItem("");
		for (int i = 0; i < item.length; i++) {

			cb.addItem(item[i]);
		}

	}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SelectItemToPlotten.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectItemToPlotten.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectItemToPlotten.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectItemToPlotten.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SelectItemToPlotten().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify
    private JComboBox fileComboBox;
    private JPanel jPanel1;
    // End of variables declaration
}