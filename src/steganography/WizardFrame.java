
public class WizardFrame extends javax.swing.JInternalFrame
 {

    private java.util.ArrayList panelList;
    private int cardCount;
    private static int currentCard;
	DHMain mm;
    /** Creates new form DHWizardFrame */
    public WizardFrame(String title,DHMain m)
 {
        super(title,true,true);
        	mm=m;
        initComponents();
        panelList = new java.util.ArrayList();
        finishButton.setVisible(false);
        setSize(600,400);
        setVisible(true);
    }

    public void addWizPanel(WizardPanel panel)
{
        String display = panel.getStepText();
        javax.swing.JLabel label = new javax.swing.JLabel();
        label.setText(display);
        label.setFont(new java.awt.Font("Dialog", 0, 12));
        label.setBackground(new java.awt.Color(204,204, 204));
        label.setForeground(new java.awt.Color(255, 255, 255));
			if(display.length()/2 > lineLabel.getText().length())
	{
            char[] lineFill =new char[display.length()*75/100];
            java.util.Arrays.fill( lineFill ,'_');
            lineLabel.setText( new String(lineFill) );
        }
        	stepsPanel.add(label);
        panel.setWizardLabel(label);
        	wizardPanel.add(panel,"card"+ ++cardCount);
    }
    
    private void initComponents()
 	{
        stepsPanel= new javax.swing.JPanel();
        stepsLabel = new javax.swing.JLabel();
        lineLabel = new javax.swing.JLabel();
        blankLabel = new javax.swing.JLabel();
        wizardPanel = new javax.swing.JPanel();
        buttonsPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        finishButton = new 	javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        	setPreferredSize(new java.awt.Dimension(450, 300));        		
			stepsPanel.setLayout(new javax.swing.BoxLayout(stepsPanel,javax.swing.BoxLayout.Y_AXIS));
        	stepsPanel.setBackground(new java.awt.Color(0, 0, 0));
        	stepsLabel.setBackground(new java.awt.Color(204, 204, 	204));        		
			stepsLabel.setForeground(new java.awt.Color(255, 255, 255));
            stepsLabel.setText("   Steps");
            stepsPanel.add(stepsLabel);

        lineLabel.setBackground(new java.awt.Color(204, 204, 204));
        lineLabel.setFont(new java.awt.Font("Dialog", 1, 18));
        lineLabel.setForeground(new java.awt.Color(255, 255, 255));
        lineLabel.setText("_____");
        stepsPanel.add(lineLabel);

        stepsPanel.add(blankLabel);

        getContentPane().add(stepsPanel, java.awt.BorderLayout.WEST);

        wizardPanel.setLayout(new java.awt.CardLayout());

        wizardPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        getContentPane().add(wizardPanel, java.awt.BorderLayout.CENTER);

        buttonsPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        backButton.setMnemonic('B');
        backButton.setText("< Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        buttonsPanel.add(backButton);

        nextButton.setMnemonic('N');
        nextButton.setText("Next >");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        buttonsPanel.add(nextButton);

        finishButton.setMnemonic('F');
        finishButton.setText("Finish");
        finishButton.setEnabled(false);
        finishButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishButtonActionPerformed(evt);
            }
        });

        buttonsPanel.add(finishButton);

        cancelButton.setMnemonic('C');
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        buttonsPanel.add(cancelButton);

        getContentPane().add(buttonsPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // Add your handling code here:
        fireButtonClickedEvent(WizardButtonListener.CANCEL,wizardPanel.getComponents()[currentCard-1],currentCard);
        mm.piclabel.setVisible(true);
        this.dispose();
        }//GEN-LAST:event_cancelButtonActionPerformed

    private void finishButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishButtonActionPerformed
        // Add your handling code here:
        //fireButtonClickedEvent(WizardButtonListener.FINISH,wizardPanel.getComponents()[currentCard-1],currentCard);
    }//GEN-LAST:event_finishButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        // Add your handling code here:
        if( ! checkValidationStatus() ) return;
        if(cardCount == currentCard) return;
        ((java.awt.CardLayout)wizardPanel.getLayout()).next(wizardPanel);
        fireButtonClickedEvent(WizardButtonListener.NEXT,wizardPanel.getComponents()[currentCard-1],currentCard);
        fireSetFocus(currentCard,currentCard+1);
    }//GEN-LAST:event_nextButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // Add your handling code here:
        //if( ! checkValidationStatus() ) return;
        if(currentCard == 1) return;
        ((java.awt.CardLayout)wizardPanel.getLayout()).previous(wizardPanel);
        fireButtonClickedEvent(WizardButtonListener.BACK,wizardPanel.getComponents()[currentCard-1],currentCard);
        fireSetFocus(currentCard,currentCard-1);
    }//GEN-LAST:event_backButtonActionPerformed
    private boolean checkValidationStatus(){
        java.awt.Component[] components = wizardPanel.getComponents();
        return ((WizardPanel)components[currentCard-1]).doValidation();
    }
    public void fireSetFocus(int fromCard,int toCard){

        java.awt.Component[] components = wizardPanel.getComponents();


        if(toCard > cardCount || toCard <= 0) return;
        ((WizardPanel)components[toCard-1]).focusGained(
        new java.awt.event.FocusEvent(components[toCard-1],java.awt.event.FocusEvent.FOCUS_GAINED));

        currentCard = toCard;

        if(cardCount == currentCard) finishButton.setEnabled(true);
        else finishButton.setEnabled(false);

        if(currentCard == 1) backButton.setEnabled(false);
        else backButton.setEnabled(true);

        if(currentCard == cardCount){
            cancelButton.setText("Finish");
            cancelButton.setMnemonic('F');
            nextButton.setEnabled(false);
        }else{
            cancelButton.setText("Cancel");
            cancelButton.setMnemonic('C');
            nextButton.setEnabled(true);
        }


        //System.out.println(fromCard+":"+toCard+":"+currentCard+":"+cardCount);


        if(fromCard > cardCount || fromCard <= 0) return;
        ((WizardPanel)components[fromCard-1]).focusLost(
        new java.awt.event.FocusEvent(components[fromCard-1],java.awt.event.FocusEvent.FOCUS_GAINED));





        /*
        for(int i=0;i< components.length;i++){
            if( components[i] instanceof WizardPanel || components[i].isShowing() || components[i].isVisible() ){
                System.out.println(i+":"+focus);
                if(focus)
                    ((WizardPanel)components[i]).focusGained(
                        new java.awt.event.FocusEvent(components[i],java.awt.event.FocusEvent.FOCUS_GAINED));
                else
                    ((WizardPanel)components[i]).focusLost(
                        new java.awt.event.FocusEvent(components[i],java.awt.event.FocusEvent.FOCUS_LOST));
                break;
            }
        }*/

    }

    /** Registers WizardButtonListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addWizardButtonListener(WizardButtonListener listener) {
        if (listenerList == null ) {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(WizardButtonListener.class, listener);
    }

    /** Removes WizardButtonListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeWizardButtonListener(WizardButtonListener listener) {
        listenerList.remove(WizardButtonListener.class, listener);
    }



    protected void fireButtonClickedEvent(int buttonType,java.awt.Component card,int cardPosition) {

        Object[] listeners = listenerList.getListenerList();
        if(listeners == null) return;
        for (int i = 0; i<listeners.length; i++) {
            if (listeners[i] instanceof WizardButtonListener) {
                WizardButtonEvent wbEvent = new WizardButtonEvent(this,buttonType,card,cardPosition);

                ((WizardButtonListener)listeners[i]).buttonClicked(wbEvent);
            }
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton finishButton;
    private javax.swing.JLabel lineLabel;
    private javax.swing.JPanel stepsPanel;
    private javax.swing.JPanel wizardPanel;
    private javax.swing.JLabel stepsLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JLabel blankLabel;
    // End of variables declaration//GEN-END:variables

    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList =  null;

}
