import java.io.*;
public class DHMain extends javax.swing.JFrame
{    
	public DHMain() 
   {
        initComponents();
        //jDesktopPane1.add( new DHFrame());
        java.awt.Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setSize(610,455);
        setResizable(false);
    } 

   
    private void initComponents() 
     {
	
        
	jdp = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
	fileMenu1 = new javax.swing.JMenu();
        embedMenu = new javax.swing.JMenuItem();
        extractmenu = new javax.swing.JMenuItem();
        exitmenu = new javax.swing.JMenuItem();
	aboutMenu = new javax.swing.JMenuItem();
        piclabel=new javax.swing.JLabel(new javax.swing.ImageIcon("new2.jpg"));
	piclabel.setBounds(0,0,600,400);
	add(piclabel);
	setTitle("Audio-Stego");
        addWindowListener(new java.awt.event.WindowAdapter() 
	{
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        getContentPane().add(jdp, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");
	fileMenu1.setMnemonic('h');
	fileMenu1.setText("Help");
        embedMenu.setMnemonic('m');
        embedMenu.setText("Embed");
        embedMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                embedMenuActionPerformed(evt);
            }
        });
        fileMenu.add(embedMenu);
        extractmenu.setMnemonic('t');
        extractmenu.setText("Extract");
        extractmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extractmenuActionPerformed(evt);
            }
        });

        fileMenu.add(extractmenu);
        exitmenu.setMnemonic('x');
        exitmenu.setText("Exit");
        exitmenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            exitmenuActionPerformed(evt);
            }
        });
	fileMenu1.add(aboutMenu);
        aboutMenu.setMnemonic('a');
        aboutMenu.setText("About");
        aboutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            aboutMenuActionPerformed(evt);
            }
        });
        fileMenu.add(exitmenu);
        fileMenu1.add(aboutMenu);
	jMenuBar1.add(fileMenu);
	jMenuBar1.add(fileMenu1);
	
        setJMenuBar(jMenuBar1);
        pack();
    }	
	
   
  private void exitmenuActionPerformed(java.awt.event.ActionEvent evt) 
   {
   	      	
	  System.exit(0);
    }
	
    private void extractmenuActionPerformed(java.awt.event.ActionEvent evt) 
  {    
        piclabel.setVisible(false);
        WizardFrame wf = new ExtractAction(this).getWizardFrame();
       jdp.add(wf);
     wf.moveToFront();
    }
    private void embedMenuActionPerformed(java.awt.event.ActionEvent evt) 
  {
 
     System.out.println("Embed Action Selected..");
     
	piclabel.setVisible(false);
        WizardFrame wf = new EmbedAction(this).getWizardFrame();
        jdp.add(wf);
        wf.moveToFront();
    }

    private void aboutMenuActionPerformed(java.awt.event.ActionEvent evt) 
  {
  javax.swing.JOptionPane.showMessageDialog(this,"AUDIO HIDING USING STEGNOGRAPHY\n\n"+"Created by : Arunprasath Shankar","About",javax.swing.JOptionPane.PLAIN_MESSAGE);
    }	
    private void exitForm(java.awt.event.WindowEvent evt) {

        System.exit(0);

    }

      public static void main(String args[])
	 {
           new DHMain().show();
     }
                    
    
    private javax.swing.JMenuItem exitmenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu fileMenu1;
    private javax.swing.JDesktopPane jdp;
    private javax.swing.JMenuItem extractmenu;
    private javax.swing.JMenuItem embedMenu;
    private javax.swing.JMenuItem aboutMenu;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JLabel piclabel;
    
    
}
