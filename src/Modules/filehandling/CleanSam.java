package Modules.filehandling;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 03/06/16.
 */
public class CleanSam extends AModule {

    public static final int DEFAULT = 0;
    public static final int SET_UNMAPPED_MAPQ_TO_ZERO_ONLY = 1;
    private int currentconfiguration = DEFAULT;

    public CleanSam(Communicator c) {
        super(c);
    }
    public CleanSam(Communicator c, int configuration) {
        super(c);
        this.currentconfiguration = configuration;
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output = getOutputfolder()+"/"+output_stem+".cleaned.bam";
        if ( this.currentconfiguration == SET_UNMAPPED_MAPQ_TO_ZERO_ONLY ) {
            this.parameters = new String[]{"CleanSamUnmappedOnly", this.inputfile.get(0), output};
        } else {
            this.parameters = new String[]{"picard", "CleanSam", "INPUT="+this.inputfile.get(0), "OUTPUT="+ output};
        }
        this.outputfile = new ArrayList<>();
        this.outputfile.add(output);
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/5-DeDup";
    }
}
