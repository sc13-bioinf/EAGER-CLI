package Modules.filehandling;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 03/06/16.
 */
public class CleanSam extends AModule {

    public CleanSam(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output = getOutputfolder()+"/"+output_stem+".cleaned.bam";
        this.parameters = new String[]{"picard", "CleanSam", "INPUT="+this.inputfile.get(0), "OUTPUT="+ output, "VALIDATION_STRINGENCY=SILENT"};
        this.outputfile = new ArrayList<>();
        this.outputfile.add(output);
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/5-DeDup";
    }
}
