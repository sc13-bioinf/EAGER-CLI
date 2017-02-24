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

import java.util.ArrayList;

/**
 * Created by peltzer on 22.01.14.
 */
public class CreateResultsDirectories extends AModule {

    public CreateResultsDirectories(Communicator c){
        super(c);
        this.inputfile = new ArrayList<String>();
        inputfile = c.getGUI_inputfiles();
        this.outputfile = c.getGUI_inputfiles();
        setParameters();
    }

    @Override
    public void setParameters() {
        String useOutputFolderAsTmp = System.getProperty ("file.separator") + ".tmp";
        if ( this.communicator.isUsesystemtmpdir () ) {
            useOutputFolderAsTmp = "";
        }

        ArrayList<String> listOfFolders = new ArrayList<String>();
        listOfFolders.add("mkdir");
        listOfFolders.add("-p");
        if(this.communicator.isRun_fastqc()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/0-FastQC"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_clipandmerge()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/1-ClipAndMerge"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_qualityfilter()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/2-QualityTrimming"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_mapping()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/3-Mapper"+useOutputFolderAsTmp);
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/4-Samtools"+useOutputFolderAsTmp);
        }
        //Always create this, we need the folder for some parts :-)
        listOfFolders.add(this.communicator.getGUI_resultspath()+"/5-DeDup"+useOutputFolderAsTmp);

        if(this.communicator.isRun_coveragecalc()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/6-QualiMap"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_mapdamage()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/7-DnaDamage"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_complexityestimation()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/8-Preseq"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_gatksnpcalling()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/9-GATKBasics"+useOutputFolderAsTmp);
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/10-GATKGenotyper"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_gatksnpfiltering()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/11-GATKVariantFilter"+useOutputFolderAsTmp);
        }
        if(this.communicator.isRun_vcf2draft()){
            listOfFolders.add(this.communicator.getGUI_resultspath()+"/12-VCF2Genome"+useOutputFolderAsTmp);
        }


        this.parameters = listOfFolders.toArray(new String[listOfFolders.size()]);

    }

    @Override
    public String getOutputfolder() {
        return null;
    }



}
