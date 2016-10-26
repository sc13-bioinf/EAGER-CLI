package Modules.stats;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.Map;

/**
 * Created by neukamm on 26.10.16.
 */
public class PmdTools extends AModule {

    public PmdTools(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        if ( this.communicator.isPMDSFilter() ) {
            this.parameters = getParamsPmdsFilter();
        } else {
            this.parameters = getParamsCpGRestriction();
        }
        //this.outputfile = new ArrayList<String>();
        //this.outputfile.add(getOutputfolder()+"/"+output_stem+"_rmdup.bam");
        this.outputfile = this.inputfile;
    }



    private String[] getParamsPmdsFilter(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String command = "samtools view -h " + this.inputfile.get(0) +
                " | pmdtools --threshold " + this.communicator.getPmdtoolsThreshold() + " --header | samtools view -Sb - > " +
                output_stem + ".pmds" + this.communicator.getPmdtoolsThreshold() + "filter.bam" ;
        String[] params = new String[]{"/bin/sh", "-c", command};

        return params;

    }


    private String[] getParamsCpGRestriction(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String command = "samtools view " + this.inputfile.get(0) +
                " | pmdtools --deamination --range " + this.communicator.getCpGRange() + " --CpG";
        String[] params = new String[]{"/bin/sh", "-c", command};

        return params;

    }

    @Override
    public void setProcessEnvironment (Map<String, String> env) {
        if ( !this.communicator.isUsesystemtmpdir() ) {
            AModule.setEnvironmentForParameterPrepend (env,
                    " ",
                    "JAVA_TOOL_OPTIONS",
                    "-Djava.io.tmpdir=" + getOutputfolder() + System.getProperty ("file.separator") + ".tmp");
        }
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/5-DeDup";
    }
}
