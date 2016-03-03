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

import java.util.ArrayList;

/**
 * Created by peltzer on 22.07.14
 */
public class StampyIndex extends AModule {

    public StampyIndex(Communicator c) {
        super(c);
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.communicator.getGUI_reference());
        String output_ext = Files.getFileExtension(this.communicator.getGUI_reference());
        this.parameters = new String[]{"stampy", "-G", this.communicator.getGUI_reference(), this.communicator.getGUI_reference() };
        this.outputfile = this.inputfile;
    }

    @Override
    public String getOutputfolder() {
        return null;
    }

}
