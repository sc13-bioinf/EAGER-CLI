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

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by peltzer on 25.07.14.
 */
public class ReportGenerator extends AModule {
    public ReportGenerator(Communicator c){
        super(c);
    }
    @Override
    public void setParameters() {
            File f = new File(this.communicator.getGUI_resultspath());
            //Get Report Foldername and use this for the Report_Filename.csv
            String[] project = f.getParent().split("/");
            String project_name = project[project.length-1];
            this.parameters = new String[]{"ReportTable", f.getParent()+"/"+"Report_"+project_name+".csv", new File(this.communicator.getGUI_resultspath()).getParent()};
            this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return new File(this.communicator.getGUI_resultspath()).getParent();
    }


}
