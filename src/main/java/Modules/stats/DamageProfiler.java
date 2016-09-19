package Modules.stats;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.Map;

/**
 * Created by neukamm on 12.09.16.
 */


public class DamageProfiler extends AModule {
    public DamageProfiler(Communicator c) {
        super(c);
    }

    @Override
    public void setProcessEnvironment (Map<String, String> env) {
        if ( !this.communicator.isUsesystemtmpdir() ) {
            AModule.setEnvironmentForParameterReplace (env,
                    "TMPDIR",
                    getOutputfolder() + System.getProperty ("file.separator") + ".tmp");
        }
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.getInputfile().get(0));
        String output_path = getOutputfolder();
        this.parameters = new String[]{"java", "-jar", "/home/neukamm/IdeaProjects/DamageProfiler/out/artifacts/DamageProfilerv0_2_jar/DamageProfilerv0.2.jar", "-i", this.getInputfile().get(0), "-r", this.communicator.getGUI_reference(),
                "-l", this.communicator.getMapdamage_length(),
                "-o", output_path+"/"+output_stem,
                "-t", "25",
                this.communicator.getMapdamage_advanced()};
        this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/7-DnaDamage";
    }


}