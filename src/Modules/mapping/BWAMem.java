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
 * Created by peltzer on 01.09.14
 */
public class BWAMem extends AModule {
    public static final int DEFAULT = 0;
    public int currentConfiguration = DEFAULT;
    public static final int PAIREDENDWITHOUTMERGE = 1;

    public BWAMem(Communicator c) {
        super(c);
    }

    public BWAMem(Communicator c, int config) {
        super(c);
        this.currentConfiguration = config;
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile = new ArrayList<String>();

        switch (currentConfiguration){
            case DEFAULT: this.parameters = getDefaultParameters();
                this.outputfile.add(getOutputfolder() + "/" + output_stem + ".bwamem.sam");
                break;
            case PAIREDENDWITHOUTMERGE: this.parameters = getPairedEndWithoutMergeParameters();
                this.outputfile.add(getOutputfolder() + "/" + output_stem + ".bwamem.sam");
                break;
        }


    }

    private String[] getPairedEndWithoutMergeParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String bwamem = "bwa mem" + " -t " + this.communicator.getCpucores() +
                " " + this.communicator.getGUI_reference() + " " +
                this.inputfile.get(0) + " " + this.inputfile.get(1) + " " + ">" + " " +
                getOutputfolder() + "/" + output_stem + ".bwamem.sam";
        String[] params = new String[]{"/bin/sh", "-c", bwamem};
        return params;
    }

    private String[] getDefaultParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String bwamem = "bwa mem" + " -t " + this.communicator.getCpucores() +
                " " + this.communicator.getGUI_reference() + " " +
                this.inputfile.get(0) + " " + ">" + " " +
                getOutputfolder() + "/" + output_stem + ".bwamem.sam";
        String[] params = new String[]{"/bin/sh", "-c", bwamem};
        return params;
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/3-Mapper";
    }



}

