
public interface WizardButtonListener extends java.util.EventListener{
    
    int BACK = 0;
    int NEXT = 1;
    int FINISH = 2;
    int CANCEL = 3;
    
    void buttonClicked(WizardButtonEvent wbe);
    
}
