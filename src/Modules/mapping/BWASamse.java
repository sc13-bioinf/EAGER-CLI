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
 * Created by peltzer on 24.01.14.
 */
public class BWASamse extends AModule {
    public static final int DEFAULT = 0;
    public static final int SAMSEMT = 1;
    public static final int SAMSEMTREMAP = 2;
    private int currentConfiguration = 0;

    public BWASamse(Communicator c) {
        super(c);
    }

    public BWASamse(Communicator c, int conf){
        super(c);
        this.currentConfiguration = conf;
    }

    @Override
    public void setParameters() {
        switch(currentConfiguration){
            case DEFAULT: this.parameters = getDefaultParameters();
                break;
            case SAMSEMT: this.parameters = getSamseMTParameters();
                break;
            default: this.parameters = getDefaultParameters();
        }
    }

    //Set the parameters accordingly
    private String[] getDefaultParameters(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile =  new ArrayList<String>();
        outputfile.add(getOutputfolder()+"/"+output_stem+".sam");
        return new String[]{"bwa", "samse", "-r", this.communicator.getMapper_readgroup(),
                this.communicator.getGUI_reference(),
                getOutputfolder() + "/" + output_stem + ".sai",
                this.inputfile.get(0), "-f", getOutputfolder() + "/" + output_stem + ".sam"};
    }

    private String[] getSamseMTParameters(){
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile = new ArrayList<String>();
        outputfile.add(getOutputfolder()+"/"+output_stem+".MT.sam");
        return new String[]{"bwa", "samse", "-r", this.communicator.getMapper_readgroup(),
                this.communicator.getCM_referencemt_elong(),
                getOutputfolder() + "/" + output_stem + ".MT.sai",
                this.inputfile.get(0), "-f", getOutputfolder() + "/" + output_stem + ".MT.sam"};
    }





    @Override
    public String getOutputfolder() {
        switch(currentConfiguration){
            case DEFAULT: return this.communicator.getGUI_resultspath() + "/3-Mapper";
            case SAMSEMT: return this.communicator.getGUI_resultspath() + "/3-Mapper";
            case SAMSEMTREMAP: return this.communicator.getGUI_resultspath() + "/3-Mapper";
            default: return this.communicator.getGUI_resultspath() + "/3-Mapper";
        }
    }



}