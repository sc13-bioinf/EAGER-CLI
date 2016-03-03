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

package Modules.genotyping;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 24.01.14.
 */
public class GATKVariantFilter extends AModule {
    public GATKVariantFilter(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        this.parameters = new String[]{"gatk", "-R", this.communicator.getGUI_reference(), "-T", "VariantFiltration",
                                        "-o", output_path+"/"+output_stem+".filtered.vcf",
                                        "--variant", this.inputfile.get(0),
                                        "--filterName", "\'CoverageFilter\'",
                                        "--filterExpression", "\"DP>" + this.communicator.getGatk_variantfilter_coveragefilter() + "\"",
                                        "--filterName", "\'BaseQualityFilter\'",
                                        "--filterExpression", "\"QUAL>" + this.communicator.getGatk_variantfilter_qualityfilter() + "\""
        };
        this.outputfile = new ArrayList<String>();
        outputfile.add(output_path+"/"+output_stem+".filtered.vcf");

    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/11-GATKVariantFilter";
    }

}
