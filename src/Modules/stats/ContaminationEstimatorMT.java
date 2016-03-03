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
import com.google.common.io.Files;

/**
 * Created by peltzer on 01/12/15.
 */
public class ContaminationEstimatorMT extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int NOTPREDC = 1;


    public ContaminationEstimatorMT(Communicator c) {
        super(c);
    }

    public ContaminationEstimatorMT(Communicator c, int currentConfiguration){
        super(c);
        this.currentConfiguration = currentConfiguration;
    }





    @Override
    public void setParameters() {
        this.outputfile = this.inputfile;
        switch(currentConfiguration){
        case DEFAULT: getDefaultParams();
            break;
        case NOTPREDC: getNotPredCParams();
            break;
        }
        this.parameters = getDefaultParams();

    }


    private String[] getDefaultParams(){
        String prepend = "schmutzi --uselength --t " + this.communicator.getCpucores() + " --ref " + this.communicator.getSchmutzi_mt_ref() +
                " " + getOutputfolder()+"/outputdeam" + " " + this.communicator.getSchmutzi_mt_refDB() + " " + this.getInputfile().get(0) +
                " &> " + getOutputfolder()+"/schmutzi.log";
        String[] tmp = new String[]{"/bin/sh", "-c", prepend};
        return tmp;
    }

    private String[] getNotPredCParams(){
        String prepend = "schmutzi --uselength --notusepredC --t " + this.communicator.getCpucores() + " --ref " + this.communicator.getSchmutzi_mt_ref() +
                " " + getOutputfolder()+"/outputdeam" + " " + this.communicator.getSchmutzi_mt_refDB() + " " + this.getInputfile().get(0) +
                " &> " + getOutputfolder()+"/schmutzi.log";
        String[] tmp = new String[]{"/bin/sh", "-c", prepend};
        return tmp;
    }




    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath()+"/5-DeDup";
    }

    @Override
    public String getModulename(){
        switch(currentConfiguration){
            case DEFAULT: return super.getModulename() + "default";
            case NOTPREDC: return super.getModulename() + "notPredC";
            default: return super.getModulename();
        }
    }


}
