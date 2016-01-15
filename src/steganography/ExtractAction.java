
public class ExtractAction implements WizardButtonListener {

    public WizardFrame wf;
    public SelectInputFile sif;
    public SelectOutputFile sof;
    public EnterPassword ep;
    public EmbedExtractOptions options;
    public VerifyOptions vo;
    public ShowProcessDetails sepd;
    public ShowProcessDetails sef;



    /** Creates a new instance of EmbedAction */
    public ExtractAction(DHMain m) {
        wf = new WizardFrame("Extract Wizard",m);

        wf.addWizardButtonListener(this);
        sif = new SelectInputFile("1. Select an input audio file");
        wf.addWizPanel(sif);

        sof = new SelectOutputFile("2. Enter an output file name");
        wf.addWizPanel(sof);


        ep = new EnterPassword("3. Enter password");
        wf.addWizPanel(ep);

        vo = new VerifyOptions("4. Verify Options");
        wf.addWizPanel(vo);

        sepd = new ShowProcessDetails("5. Extracting data from the audio file");
        sepd.setDisplayText("Extracting data from the Audio");
        wf.addWizPanel(sepd);

        sef = new ShowProcessDetails("6. View Output File");
        wf.addWizPanel(sef);

        wf.fireSetFocus(0,1);
    }

    /** Getter for property wf.
     * @return Value of property wf.
     */
    public WizardFrame getWizardFrame() {
        return wf;
    }


    /** Event handling for the Wizard*/
    public void buttonClicked(WizardButtonEvent wbe) {
        if( wbe.getButtonType() == WizardButtonListener.NEXT  && wbe.getCard() == ep ){
            options = new EmbedExtractOptions();
            options.setInputFile( sif.getSelectedFile() );
            options.setOutputFile( sof.getOutputFile() );
            options.setPassword( ep.getPassword() );
            options.createExtractColumnData();
            vo.showChosenOptions(options);
        }else if( wbe.getButtonType() == WizardButtonListener.NEXT  && wbe.getCard() == vo ){
            ExtractProcess process = new ExtractProcess(options);
            java.io.ByteArrayOutputStream outBuffer = new java.io.ByteArrayOutputStream();
            java.io.OutputStream out = System.out;
            System.setOut( new java.io.PrintStream(outBuffer) );
            process.startExtract();
            System.out.println("Extraction Completed.");
            System.setOut( System.out );
            sepd.addOutputLine(new String(outBuffer.toByteArray()));
        }else if( wbe.getButtonType() == WizardButtonListener.NEXT  && wbe.getCard() == sepd ){
            try{
                java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(options.getOutputFile()));
                String line = null;
                while( (line = br.readLine()) != null )
                    sef.addOutputLine(line);
                br.close();
                sef.setDisplayText("Output File : "+options.getOutputFile().getAbsolutePath() );
            }catch(java.io.FileNotFoundException fnfe){
                fnfe.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null,"Output file not created!");
            }catch(java.io.IOException ie){
                ie.printStackTrace();
                javax.swing.JOptionPane.showMessageDialog(null,"File read error!");
            }

        }
    }
}
