
public class WizardButtonEvent extends java.util.EventObject {
    
    private int buttonType;
    
    private java.awt.Component card;
    
  
    private int cardPosition;
    
    /** Creates a new instance of WizardButtonEvent */
    public WizardButtonEvent(WizardFrame wf,int buttonType,
           java.awt.Component card,int cardPosition) {
        super(wf);       
        this.buttonType = buttonType;       
        this.card = card;      
        this.cardPosition = cardPosition;
    }
    
    /** Getter for property buttonType.
     * @return Value of property buttonType.
     */
    public int getButtonType() {
        return buttonType;
    }
    
    /** Setter for property buttonType.
     * @param buttonType New value of property buttonType.
     */
    public void setButtonType(int buttonType) {
        this.buttonType = buttonType;
    }
    
    /** Getter for property card.
     * @return Value of property card.
     */
    public java.awt.Component getCard() {
        return card;
    }
    
    /** Setter for property card.
     * @param card New value of property card.
     */
    public void setCard(java.awt.Component card) {
        this.card = card;
    }
    
    /** Getter for property cardPosition.
     * @return Value of property cardPosition.
     */
    public int getCardPosition() {
        return cardPosition;
    }
    
    /** Setter for property cardPosition.
     * @param cardPosition New value of property cardPosition.
     */
    public void setCardPosition(int cardPosition) {
        this.cardPosition = cardPosition;
    }
    
}
