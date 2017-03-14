package Modules.stats;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.io.File;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by neukamm on 26.10.16.
 */
public class PmdTools extends AModule {

    public static final int PMDS_FILTER = 0;
    public static final int CALC_RANGE = 1;
    int runTarget = 0;

    public PmdTools(Communicator c, int runTarget) {
        super(c);
        if ( !(runTarget == PmdTools.PMDS_FILTER || runTarget == PmdTools.CALC_RANGE) ) {
            throw new RuntimeException("Invalid runTarget for PmdTools");
        }
        this.runTarget = runTarget;
    }

    @Override
    public void setParameters() {
        if ( this.runTarget == PmdTools.PMDS_FILTER ) {
            this.parameters = getParamsPmdsFilter();

            String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));

            this.outputfile = new ArrayList<String>();
            this.outputfile.add(getOutputfolder()+"/"+output_stem+ ".pmds." + this.communicator.getPmdtoolsThreshold() + ".filter.bam");
        } else if( this.runTarget == PmdTools.CALC_RANGE ){
            this.parameters = getParamsCalcRange();
            this.outputfile = this.inputfile;
        } else {
            this.outputfile = this.inputfile;
        }
    }

    private String getDataDependentOptions() {
        String data_dependent_options = "--CpG";
        if ( this.communicator.getUdgtreatment().equals("half-UDG Treated") ) {
            data_dependent_options = "--UDGhalf";
        } else if ( this.communicator.getUdgtreatment().equals("non-UDG Treated") ) {
            data_dependent_options = "--UDGminus";
        }

        if ( this.communicator.isSnpcapturedata() && this.communicator.getGUI_reference_mask() != null ) {
            data_dependent_options += " --refseq " + this.communicator.getGUI_reference_mask();
        }

        if ( !this.communicator.getPmdtools_advanced().isEmpty() ) {
            data_dependent_options = this.communicator.getPmdtools_advanced();
        }

        return data_dependent_options;
    }

    private String[] getParamsPmdsFilter(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();

        String command = "samtools view -h " + this.inputfile.get(0) +
                " | pmdtools --threshold " + this.communicator.getPmdtoolsThreshold() + " " + getDataDependentOptions() + " --header | samtools view -Sb - > " +
                output_path + File.separator + output_stem + ".pmds." + this.communicator.getPmdtoolsThreshold() + ".filter.bam" ;
        String[] params = new String[]{"/bin/sh", "-c", command};

        return params;

    }


    private String[] getParamsCalcRange(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        String command = "samtools view " + this.inputfile.get(0) +
                " | pmdtools --deamination --range " + this.communicator.getCpGRange() + " " + getDataDependentOptions() + " > " + output_path +
                File.separator + output_stem + ".cpg.range" + this.communicator.getCpGRange() + ".txt";
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

    private String getSubModuleName() {
        switch (runTarget){
            case PMDS_FILTER:
                return "PMD_FILTER";

            case CALC_RANGE:
                return "PMD_RANGE";

            default: return "PMD_DEFAULT";
        }
    }

    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName() ;
    }
}
