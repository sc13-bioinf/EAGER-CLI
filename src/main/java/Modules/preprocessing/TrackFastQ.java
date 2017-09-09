package Modules.preprocessing;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 26.04.17.
 */
public class TrackFastQ extends AModule{


    public TrackFastQ(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.parameters = getDefaultParameters();
        this.outputfile = this.inputfile;
    }

    private String[] getDefaultParameters() {

        String echo_info = "echo '";
        if (this.communicator.isMerge_keep_only_merged()) {
            echo_info += "only_merged";
        }
        else
        {
            echo_info += "all";
        }
        echo_info += "' > ";
        echo_info += getOutputfolder();
        echo_info += "/track_fastq.log";


                String[] params = new String[]{"/bin/sh", "-c", echo_info};
        return params;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath()+"/1-AdapClip/";
    }
}
