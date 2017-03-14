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

/**
 * Created by peltzer on 27.01.14.
 */
public class SamtoolsIndex extends AModule {
    public static final int DEFAULT = 0;
    public static final int DEDUP = 1;
    public static final int SCHMUTZI = 2;
    public static final int UNFILTERED = 3;
    private int currentconf = DEFAULT;
    private String[] schmutziParameters;

    public SamtoolsIndex(Communicator c) {
        super(c);
    }

    public SamtoolsIndex(Communicator c, int currentconf) {
        super(c);
        this.currentconf = currentconf;
    }


    @Override
    public void setParameters() {
        this.outputfile = this.inputfile;
        this.parameters = new String[]{"samtools","index", this.inputfile.get(0)};
    }

    @Override
    public String getOutputfolder() {
        switch(currentconf){
            case DEFAULT:
                return this.communicator.getGUI_resultspath() + "/4-Samtools";
            case DEDUP:
                return this.communicator.getGUI_resultspath() + "/5-DeDup";
            case SCHMUTZI:
                return this.communicator.getGUI_resultspath()+ "/5-DeDup";
        }
        return this.communicator.getGUI_resultspath() + "/5-DeDup";
    }


    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName();
    }

    private String getSubModuleName() {
        switch (currentconf){
            case DEFAULT:
                return "default";
            case DEDUP:
                return "Dedup";
            case SCHMUTZI:
                return "Schmutzi";
            case UNFILTERED:
                return "Unfiltered";
            default: return "default";
        }
    }
}
