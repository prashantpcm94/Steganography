public class EmbedAction  implements WizardButtonListener
 {
    public WizardFrame wf;
    public SelectInputFile sif;
    public SelectOutputDirectory sof;
    public SelectDataToEmbed sdte;
    public EnterOtherOptions eoo;
    public EmbedExtractOptions options;
    public VerifyOptions vo;
    public ShowProcessDetails sepd;
    public ShowEmbeddedFile sopf;
    DHMain mm;
            
    public EmbedAction(DHMain m) {
		mm=m;
        wf = new WizardFrame("Embed Wizard",m);
        wf.addWizardButtonListener(this);
        sif = new SelectInputFile("1. Select an input audio file");
        wf.addWizPanel(sif);
        sof = new SelectOutputDirectory("2. Select an output directory");
        wf.addWizPanel(sof);
        sdte = new SelectDataToEmbed("3. Select data to embed");
        wf.addWizPanel(sdte);
        eoo = new EnterOtherOptions("4. Enter password/comment/quality");
        wf.addWizPanel(eoo);
        vo = new VerifyOptions("5. Verify Options");
        wf.addWizPanel(vo);
        sepd = new ShowProcessDetails("6. Embedding data into the audio");
        wf.addWizPanel(sepd);
        sopf = new ShowEmbeddedFile("7. View Output File");
        wf.addWizPanel(sopf);
        wf.fireSetFocus(0,1);
    }

    
    public WizardFrame getWizardFrame() {
        return wf;
    }


   
    public void buttonClicked(WizardButtonEvent wbe) {
        if( wbe.getButtonType() == WizardButtonListener.NEXT  && wbe.getCard() == eoo ){
            options = new EmbedExtractOptions();
            options.setInputFile( sif.getSelectedFile() );
            options.setOutputDirectory( sof.getOutputDirectory() );
            options.setEmbedTextOrFile( sdte.isTextOrFile() );
            if( sdte.isTextOrFile() ){
                options.setEmbedText( sdte.getEmbeddedText() );
            }else{
                options.setEmbedFile( sdte.getSelectedFile() );
            }
            options.setPassword( eoo.getPassword() );
            options.setComment( eoo.getComment() );
            options.setQuality( eoo.getQuality() );
            options.createEmbedColumnData();
            vo.showChosenOptions(options);
        }else if( wbe.getButtonType() == WizardButtonListener.NEXT  && wbe.getCard() == vo ){
            EmbedProcess process = new EmbedProcess(options);
            java.io.ByteArrayOutputStream outBuffer = new java.io.ByteArrayOutputStream();
            java.io.OutputStream out = System.out;
            System.setOut( new java.io.PrintStream(outBuffer) );
            process.startEmbed();
            System.out.println("Embedding Process Completed.");
            System.setOut( System.out );
            sepd.addOutputLine(new String(outBuffer.toByteArray()));
        }else if( wbe.getButtonType() == WizardButtonListener.NEXT  && wbe.getCard() == sepd ){
            sopf.setImageFiles(options.getInputFile(),options.getOutputFile());
        }
    }


}
