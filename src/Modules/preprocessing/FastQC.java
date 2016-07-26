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

package Modules.preprocessing;
import IO.Communicator;
import Modules.AModule;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by peltzer on 22.01.14.
 */
public class FastQC extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int AFTERMERGING = 1;

    public FastQC(Communicator c) {
        super(c);
        setParameters();
    }

    public FastQC(Communicator c, int currentConf){
        super(c);
        this.currentConfiguration = currentConf;
    }

    @Override
    public void setProcessEnvironment (Map <String, String> env) {
        if ( !this.communicator.isUsesystemtmpdir() ) {
          AModule.setEnvironmentForParameterPrepend (env,
                                                     " ",
                                                     "JAVA_TOOL_OPTIONS",
                                                     "-Djava.io.tmpdir=" + getOutputfolder() + System.getProperty ("file.separator") + ".tmp");
        }
    }

    @Override
    public void setParameters() {

        switch(currentConfiguration){
            case DEFAULT: this.parameters =  new String[]{"fastqc","-o", getOutputfolder(),"--extract",this.communicator.getFastqc_advanced(), "-f","fastq"};
            case AFTERMERGING: this.parameters = new String[]{"fastqc","-o", getOutputfolder(),"--extract",this.communicator.getFastqc_advanced(), "-f","fastq"};
        }
        addFiles();
        this.outputfile = this.inputfile;

    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath()+ "/0-FastQC";
    }


    private void addFiles(){
        if(!(this.inputfile == null)){
            int count = this.inputfile.size();
            String[] newParams = Arrays.copyOf(this.parameters, count+parameters.length);
            for(int i = 0; i < count; i++){
                newParams[parameters.length+i] = this.inputfile.get(i);
            }
            this.parameters = newParams;
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
            case AFTERMERGING:
                return "After Merging";
            default: return "default";
        }
    }


}
