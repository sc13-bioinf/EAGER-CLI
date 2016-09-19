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

package Modules.mapping;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 27.01.14.
 */
public class CircularMapperRealigner extends AModule{
        public static final int DEFAULT = 0;
        private int currentConfiguration = DEFAULT;
        public static final int FILTERED = 1;


    public CircularMapperRealigner(Communicator c) {
        super(c);
    }

    public CircularMapperRealigner(Communicator c, int currConf){
        super(c);
        this.currentConfiguration = currConf;
    }

    @Override
    public void setParameters() {
        switch(currentConfiguration){
            case DEFAULT: getDefaultParameters();
                break;
            case FILTERED: getFilteredParameters();
                break;
        }

    }

    private void getFilteredParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.parameters = new String[]{"realignsamfile", "-e",String.valueOf(this.communicator.getCM_elongationfac()), "-i",
                this.inputfile.get(0), "-r", this.communicator.getGUI_reference(), "-f", "true", "-x", "false"};
        this.communicator.setCM_referencemt_elong(this.communicator.getGUI_reference() + "_" + this.communicator.getCM_elongationfac() + ".fa");
        this.communicator.setCM_realigned(getOutputfolder()+"/"+output_stem+"_realigned.bam");
        this.outputfile = new ArrayList<>();
        outputfile.add(this.getOutputfolder()+"/"+output_stem+"_realigned.bam");
    }

    private void getDefaultParameters(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.parameters = new String[]{"realignsamfile", "-e",String.valueOf(this.communicator.getCM_elongationfac()), "-i",
                this.inputfile.get(0), "-r", this.communicator.getGUI_reference()};
        this.communicator.setCM_referencemt_elong(this.communicator.getGUI_reference() + "_" + this.communicator.getCM_elongationfac() + ".fa");
        this.communicator.setCM_realigned(getOutputfolder()+"/"+output_stem+"_realigned.bam");
        this.outputfile = new ArrayList<>();
        outputfile.add(this.getOutputfolder()+"/"+output_stem+"_realigned.bam");
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/3-Mapper";
    }



    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName();
    }

    private String getSubModuleName() {
        switch (currentConfiguration){
            case DEFAULT:
                return "default";
            case FILTERED:
                return "filtered";
            default: return "default";
        }
    }

}
