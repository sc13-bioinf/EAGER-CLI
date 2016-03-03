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

package Modules.indexing;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.io.File;

/**
 * Created by peltzer on 27.01.14.
 */
public class BWAIndex extends AModule {
    public static final int DEFAULT = 0;
    public static final int MT = 1;
    private int currentconf = DEFAULT;

    public BWAIndex(Communicator c) {
        super(c);
    }

    public BWAIndex(Communicator c, int currentconf) {
        super(c);
        this.currentconf = currentconf;
    }


    @Override
    public void setParameters() {
        this.outputfile = this.inputfile;
        switch (currentconf) {
            case DEFAULT:
                this.parameters = getDefaultParameters();
                this.outputfile = this.inputfile;
                break;
            case MT:
                this.parameters = getMTParameters();
                this.outputfile = this.inputfile;
                break;
        }
    }

    //Set parameters accordingly

    private String[] getDefaultParameters() {
        return new String[]{"bwa", "index", this.communicator.getGUI_reference()};
    }

    private String[] getMTParameters() {
        String output_path = new File(this.communicator.getGUI_reference()).getParent();
        String output_stem = Files.getNameWithoutExtension(this.communicator.getGUI_reference());
        String extension = Files.getFileExtension(this.communicator.getGUI_reference());
        return new String[]{"bwa", "index", output_path + "/" +output_stem + "_"+ this.communicator.getCM_elongationfac()+ "." +extension};
    }

    @Override
    public String getOutputfolder() {
        switch(currentconf){
            case DEFAULT:
                File f = new File(this.communicator.getGUI_reference());
                String parent = f.getParent();
                return parent;
            case MT:
                File f2 = new File(this.communicator.getCM_referencemt_elong());
                String parent2 = f2.getParent();
                return parent2;
            default:
                File f3 = new File(this.communicator.getGUI_reference());
                String parent3= f3.getParent();
                return parent3;
        }
    }


}
