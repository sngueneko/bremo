package bremoswing.graphik;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class ItemChooseFrame extends JFrame implements ActionListener {
	
	private JPanel Panel ;                 
    private JButton OkButton;
    private JLabel y_achse_1_label;
    private JLabel y_achse_2_label;
    private JLabel y_achse_3_label;
    private JLabel y_achse_4_label;
    public JList<String> y_achse_1;
    public JList<String> y_achse_2;
    public JList<String> y_achse_3;
    public JList<String> y_achse_4;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JSeparator separator;
    
    Font font ;
    BremoView parent;
    List<List<String>> selectedItemList ;
    private BremoViewResource resource ;
    
    
    public ItemChooseFrame(BremoView parent) {
    	this.parent = parent;
        initComponents();
        selectedItemList = null;
    }
                        
    private void initComponents() {
    	
    	Panel = new JPanel(){
			public void paintComponent(Graphics g) 
            {
              
				URL url = getClass().getResource(resource.getIconBackgroungColored_1());
				ImageIcon icon = new ImageIcon(url);
			    Image img = icon.getImage();
			    BufferedImage buffImage = 
			    	      new BufferedImage(
			    	          img.getWidth(null), 
			    	          img.getHeight(null), 
			    	          BufferedImage.TYPE_INT_ARGB);
			    Graphics gr = buffImage.getGraphics();
			    gr.drawImage(img, 0, 0, null);
			    //img = buffImage.getSubimage(475, 50, 1280, 280);
			    img = buffImage.getSubimage(40, 40, 1280, 280);
				g.drawImage(img, 0, 0, null);
                
            } 
		};
		
		setPreferredSize(new Dimension (1280,280));

        jScrollPane1 = new JScrollPane();
        y_achse_1 = new JList<String>();
        jScrollPane2 = new JScrollPane();
        y_achse_2 = new JList<String>();
        jScrollPane3 = new JScrollPane();
        y_achse_3 = new JList<String>();
        jScrollPane4 = new JScrollPane();
        y_achse_4 = new JList<String>();
        OkButton = new JButton();
        separator = new JSeparator();
        y_achse_1_label = new JLabel();
        y_achse_2_label = new JLabel();
        y_achse_3_label = new JLabel();
        y_achse_4_label = new JLabel();
        
        font = new Font("Tahoma", 1, 14);
        y_achse_1_label.setFont(font);
        y_achse_2_label.setFont(font);
        y_achse_3_label.setFont(font);
        y_achse_4_label.setFont(font);
        
        y_achse_1.setSelectionBackground(Color.RED); 
        y_achse_2.setSelectionBackground(Color.BLUE); 
        y_achse_3.setSelectionBackground(new Color(0,102,0));  
        y_achse_4.setSelectionBackground(Color.MAGENTA);  
        
        resource = new BremoViewResource() ;
        
        OkButton.setName("OkButton");
        OkButton.addActionListener(this);
        
        
        
        
        //setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon(getClass().getResource(
				resource.getIconBremoSwing())).getImage());

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
		});
        jScrollPane1.setViewportView(y_achse_1);

        jScrollPane2.setViewportView(y_achse_2);

        jScrollPane3.setViewportView(y_achse_3);

        jScrollPane4.setViewportView(y_achse_4);

        lockAxe("1");
        
        OkButton.setText("OK");
        OkButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
        
        y_achse_1_label.setText("1. Y-Axe : ");

        y_achse_2_label.setText("2. Y-Axe : ");

        y_achse_3_label.setText("3. Y-Axe : ");

        y_achse_4_label.setText("4. Y-Axe : ");

        GroupLayout layout = new GroupLayout(Panel);
        Panel.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(separator, GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(y_achse_1_label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 88, Short.MAX_VALUE)
                .addComponent(y_achse_2_label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 88,Short.MAX_VALUE)
                .addComponent(y_achse_3_label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(10, 88, Short.MAX_VALUE)
                .addComponent(y_achse_4_label)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(616, 616, Short.MAX_VALUE)
                .addComponent(OkButton)
                .addContainerGap(615, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(y_achse_1_label)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(y_achse_2_label)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(y_achse_3_label)
                    .addComponent(y_achse_4_label))
                .addGap(18, 18, 18)
                .addComponent(separator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OkButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        getContentPane().add(Panel);
        pack();
        
    }
    /**
     * Lock the Axe when nbr of axe small as 4
     */
    public void lockAxe(String nbr) {
    	
    	switch (nbr) {
		case "1":
			y_achse_2.setEnabled(false);
			y_achse_3.setEnabled(false);
			y_achse_4.setEnabled(false);
			break;
        case "2":
        	y_achse_2.setEnabled(true);
        	y_achse_3.setEnabled(false);
        	y_achse_4.setEnabled(false);
			break;
        case "3":
        	y_achse_2.setEnabled(true);
        	y_achse_3.setEnabled(true);
        	y_achse_4.setEnabled(false);			
			break;
        default:
        	y_achse_2.setEnabled(true);
        	y_achse_3.setEnabled(true);
        	y_achse_4.setEnabled(true);
			break;
		}
    }
    void addItemtoListBox(String [] item) {
    	y_achse_1.removeAll();
		y_achse_1.setModel(new JList<String>(item).getModel());
		y_achse_2.removeAll();
		y_achse_2.setModel(new JList<String>(item).getModel());
		y_achse_3.removeAll();
		y_achse_3.setModel(new JList<String>(item).getModel());
    	y_achse_4.removeAll();
		y_achse_4.setModel(new JList<String>(item).getModel());
    }
    
    void SelectIndiceOnList() {
    	
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		getChartForBremoView();
	}
	 public void getChartForBremoView() {
		 
		selectedItemList = new ArrayList<List<String>>();
			selectedItemList.add(y_achse_1.getSelectedValuesList());
			if (y_achse_2.isEnabled())
				selectedItemList.add(y_achse_2.getSelectedValuesList());
			if (y_achse_3.isEnabled())
				selectedItemList.add(y_achse_3.getSelectedValuesList());
			if (y_achse_4.isEnabled())
				selectedItemList.add(y_achse_4.getSelectedValuesList());
			
			parent.controller.chooseFrameData(selectedItemList);
			parent.controller.hide();
			parent.controller.Chart();
	 }

}