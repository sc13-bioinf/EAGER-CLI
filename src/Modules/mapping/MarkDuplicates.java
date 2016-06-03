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
 * Created by peltzer on 07/06/15.
 */
public class MarkDuplicates extends AModule {
    public MarkDuplicates(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output = getOutputfolder()+"/"+output_stem+"rmdup.MarkD.bam";
        String metricsout = getOutputfolder()+"/"+output_stem+".markdup.metrics";
        this.parameters = new String[]{"picard", "MarkDuplicates", "INPUT="+this.inputfile.get(0), "OUTPUT="+ output, "REMOVE_DUPLICATES=TRUE", "AS=TRUE", "METRICS_FILE=" + metricsout, "VALIDATION_STRINGENCY=SILENT"};
        this.outputfile = new ArrayList<>();
        this.outputfile.add(output);
    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/5-DeDup";
    }

}
