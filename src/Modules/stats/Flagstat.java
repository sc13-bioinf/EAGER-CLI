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

package Modules.stats;

import IO.Communicator;
import Modules.AModule;

/**
 * Created by peltzer on 1/28/15.
 */
public class Flagstat extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int FILTERED = 1;

    public Flagstat(Communicator c){
        super(c);
    }

    public Flagstat(Communicator c, int currentConfiguration){
        super(c);
        this.currentConfiguration = currentConfiguration;
    }



    @Override
    public void setParameters() {
        String toFireBash = "";
        switch(currentConfiguration){
            case DEFAULT :
                toFireBash = "samtools flagstat "+this.inputfile.get(0)+" > "+this.inputfile.get(0)+".stats";
                this.parameters =  new String[]{"/bin/sh", "-c", toFireBash};
                this.outputfile = this.inputfile;
                break;
            case FILTERED : toFireBash = "samtools flagstat "+this.inputfile.get(0)+" > "+this.inputfile.get(0)+".qF.stats";
                this.parameters =  new String[]{"/bin/sh", "-c", toFireBash};
                this.outputfile = this.inputfile;
                break;
        }

    }

    @Override
    public String getOutputfolder() {
        return  this.communicator.getGUI_resultspath() + "/4-Samtools";
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
