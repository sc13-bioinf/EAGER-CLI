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

import java.util.ArrayList;

/**
 * Created by peltzer on 24.01.14.
 */
public class SamtoolsSort extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int CIRCULARMAPPER = 1;

    public SamtoolsSort(Communicator c){
        super(c);
    }

    public SamtoolsSort(Communicator c, int config){
        super(c);
        this.currentConfiguration = config;
    }


    @Override
    public void setParameters() {
        this.outputfile = new ArrayList<String>();
        switch (currentConfiguration){
            case DEFAULT: this.parameters = getDefaultParameters();
            break;
            case CIRCULARMAPPER: this.parameters = getCMParameters();
                break;
        }
    }

    private String[] getCMParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        String[] output =  new String[]{"samtools","sort","-@", this.communicator.getCpucores(),
                "-m",this.communicator.getMaxmemory()+"G", this.inputfile.get(0), "-o", output_path+"/"+output_stem+".sorted.bam"};
        outputfile.add(output_path+"/"+output_stem+".sorted.bam");
        return output;
    }


    private String[] getDefaultParameters(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        String[] output =  new String[]{"samtools","sort","-@", this.communicator.getCpucores(),
                "-m",this.communicator.getMaxmemory()+"G", this.inputfile.get(0), "-o", output_path+"/"+output_stem+".sorted.bam"};
        outputfile.add(output_path+"/"+output_stem+".sorted.bam");
        return output;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/4-Samtools";
    }


}
