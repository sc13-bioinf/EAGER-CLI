/*
 * Copyright (c) 2016. EAGER-CLI Alexander Peltzer
 * This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Modules.filehandling;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.Map;
import java.util.ArrayList;

/**
 * Created by peltzer on 24.01.14.
 */
public class SamtoolsSort extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int CIRCULARMAPPER = 1;
    public static final int UNFILTERED = 2;
    public static final int DEDUP = 3;

    public SamtoolsSort(Communicator c){
        super(c);
    }

    public SamtoolsSort(Communicator c, int config){
        super(c);
        this.currentConfiguration = config;
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
        this.outputfile = new ArrayList<String>();
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder()+"/"+output_stem+".sorted.bam";
        outputfile.add(output_path);

        this.parameters = new String[]{"samtools","sort","-@", this.communicator.getCpucores(),
                "-m", String.valueOf((Integer.parseInt(this.communicator.getMaxmemory())) / Integer.parseInt(communicator.getCpucores()))+"G", this.inputfile.get(0), "-o", output_path};
    }

    @Override
    public String getOutputfolder() {
        switch(currentConfiguration){
            case DEDUP:
                return this.communicator.getGUI_resultspath() + "/5-DeDup";
            case DEFAULT:
                return this.communicator.getGUI_resultspath() + "/4-Samtools";
            case CIRCULARMAPPER:
                return this.communicator.getGUI_resultspath() + "/4-Samtools";
            case UNFILTERED:
                return this.communicator.getGUI_resultspath() + "/4-Samtools";
            default:                 return this.communicator.getGUI_resultspath() + "/4-Samtools";

        }

    }

    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName();
    }

    private String getSubModuleName() {
        switch (currentConfiguration){
            case DEFAULT:
                return "default";
            case CIRCULARMAPPER:
                return "CircularMapper";
            case UNFILTERED:
                return "Unfiltered";
            case DEDUP:
                return "DeDup";
            default: return "default";
        }
    }
}
