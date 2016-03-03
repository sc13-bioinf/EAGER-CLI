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

package Modules.filehandling;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.ArrayList;

/**
 * Created by peltzer on 04.02.14.
 */
public class MergedReadExtractor extends AModule {
    public MergedReadExtractor(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
      this.parameters = new String[]{"MergedReadExtractor", this.inputfile.get(0),
                                        this.communicator.getGUI_resultspath() + "/7-MapDamage/"};
        String filename = Files.getNameWithoutExtension(this.inputfile.get(0));
        String fileExtension = Files.getFileExtension(this.inputfile.get(0));
        String outputfile = this.communicator.getGUI_resultspath() + "/7-MapDamage/" + filename+"_onlyMerged."+fileExtension;
        this.communicator.setMapdamage_onlymerged(outputfile);
        this.outputfile = this.inputfile;

    }

    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/7-MapDamage/";
    }


}

