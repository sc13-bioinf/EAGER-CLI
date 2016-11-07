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

/**
 * Created by peltzer on 07.11.16
 */
public class ComplexityPlotting extends AModule {

    public ComplexityPlotting(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        this.outputfile = this.inputfile;
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));

        File f_ccurve = new File(getOutputfolder() + "/" + output_stem + ".ccurve");
        File f_extrap = new File(getOutputfolder() + "/" + output_stem + ".lcextrap");

        if(f_ccurve.exists() && !f_ccurve.isDirectory()) {
           this.parameters = new String[]{"lcp", f_ccurve.getAbsolutePath(), f_extrap.getAbsolutePath(), getOutputfolder()};
        } else {
           this.parameters = new String[]{"lcp", f_ccurve.getAbsolutePath(), getOutputfolder()};
        }
    }



    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/8-Preseq";
    }


}
