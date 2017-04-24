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
import java.util.Map;

/**
 * Created by peltzer on 24.01.14.
 */
public class GATKUnifiedGenotyper extends AModule {
    public static final int DEFAULT = 0;
    private int currentConfiguration = DEFAULT;
    public static final int EMIT_ALL_SITES_NODBSNP = 1;
    public static final int EMIT_ALL_SITES_DBSNP = 2;
    public static final int EMIT_CONF_SITES_DBSNP = 3;
    public static final int EMIT_CONF_SITES_NODBSNP = 4;
    public static final int EMIT_VARIANT_DBSNP = 5;
    public static final int EMIT_VARIANT_NODBSNP = 6;


    public GATKUnifiedGenotyper(Communicator c, int config){
        super(c);
        this.currentConfiguration = config;
    }

    @Override
    public void setProcessEnvironment (Map <String, String> env) {
        if ( !this.communicator.isUsesystemtmpdir() ) {
          AModule.setEnvironmentForParameterPrepend (env,
                                                     " ",
                                                     "JAVA_TOOL_OPTIONS",
                                                     "-Djava.io.tmpdir=" + getOutputfolder() + System.getProperty ("file.separator") + ".tmp");
        }
    }

    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        this.outputfile = new ArrayList<String>();
        this.outputfile.add(output_path + "/" + output_stem + ".unifiedgenotyper.vcf");

        switch (currentConfiguration){
            case DEFAULT: this.parameters = getEmitVariantDBSNP();
                getTargetInformation();
                break;
            //ALL_SITES
            case EMIT_ALL_SITES_NODBSNP: this.parameters = getEmitAllnoDBSNP();
                getTargetInformation();
                break;
            case EMIT_ALL_SITES_DBSNP: this.parameters = getEmitAllDBSNP();
                getTargetInformation();
                break;
            //CONF_SITES
            case EMIT_CONF_SITES_DBSNP: this.parameters = getEmitConfDBSNP();
                getTargetInformation();
                break;
            case EMIT_CONF_SITES_NODBSNP: this.parameters = getEmitConfNoDBSNP();
                getTargetInformation();
                break;
            //VARIANT_SITES
            case EMIT_VARIANT_DBSNP: this.parameters = getEmitVariantDBSNP();
                getTargetInformation();
                break;
            case EMIT_VARIANT_NODBSNP: this.parameters = getEmitVariantNoDBSNP();
                getTargetInformation();
                break;
        }
    }


    private String[] getEmitConfNoDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "UnifiedGenotyper", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "-o", output_path + "/" + output_stem + ".unifiedgenotyper.vcf",
                "-nt", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "--sample_ploidy", this.communicator.getGatk_ploidy(),
                "-dcov", this.communicator.getGatk_downsampling(),
                "--output_mode EMIT_ALL_CONFIDENT_SITES",
                this.communicator.getGatk_snp_advanced()};    }



    private String[] getEmitConfDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "UnifiedGenotyper", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "--dbsnp", this.communicator.getGUI_GATKSNPreference(),
                "-o", output_path + "/" + output_stem + ".unifiedgenotyper.vcf",
                "-nt", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "--sample_ploidy", this.communicator.getGatk_ploidy(),
                "-dcov", this.communicator.getGatk_downsampling(),
                "--output_mode EMIT_ALL_CONFIDENT_SITES",
                this.communicator.getGatk_snp_advanced()};
    }

    private String[] getEmitAllDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "UnifiedGenotyper", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "--dbsnp", this.communicator.getGUI_GATKSNPreference(),
                "-o", output_path + "/" + output_stem + ".unifiedgenotyper.vcf",
                "-nt", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "--sample_ploidy", this.communicator.getGatk_ploidy(),
                "-dcov", this.communicator.getGatk_downsampling(),
                "--output_Mode EMIT_ALL_SITES",
                this.communicator.getGatk_snp_advanced()};
    }



    private String[] getEmitAllnoDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "UnifiedGenotyper", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "-o", output_path + "/" + output_stem + ".unifiedgenotyper.vcf",
                "-nt", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "--sample_ploidy", this.communicator.getGatk_ploidy(),
                "-dcov", this.communicator.getGatk_downsampling(),
                "--output_mode EMIT_ALL_SITES",
                this.communicator.getGatk_snp_advanced()};

    }

    private String[] getEmitVariantDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "UnifiedGenotyper", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0), "--dbsnp", this.communicator.getGUI_GATKSNPreference(),
                "-o", output_path + "/" + output_stem + ".unifiedgenotyper.vcf",
                "-nt", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "--sample_ploidy", this.communicator.getGatk_ploidy(),
                "-dcov", this.communicator.getGatk_downsampling(),
                this.communicator.getGatk_snp_advanced()};
    }

    private String[] getEmitVariantNoDBSNP() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        String output_path = getOutputfolder();
        return new String[]{"gatk", "-T", "UnifiedGenotyper", "-R", this.communicator.getGUI_reference(),
                "-I", this.inputfile.get(0),
                "-o", output_path + "/" + output_stem + ".unifiedgenotyper.vcf",
                "-nt", this.communicator.getCpucores(),
                "-stand_call_conf", this.communicator.getGatk_standard_call_confidence(),
                "--sample_ploidy", this.communicator.getGatk_ploidy(),
                "-dcov", this.communicator.getGatk_downsampling(),
                this.communicator.getGatk_snp_advanced()};
    }





    @Override
    public String getOutputfolder() {
        return this.communicator.getGUI_resultspath() + "/10-GATKGenotyper";
    }


    private String getSubModuleName() {
        switch (currentConfiguration){
            case DEFAULT:
                return "EMIT_VARIANTS, Use DBSNP Reference";

            //EMIT_VARIANTS

            case EMIT_VARIANT_DBSNP:
                return " EMIT_VARIANTS, DBSNP";
            case EMIT_VARIANT_NODBSNP:
                return " EMIT_VARIANTS, NO DBSNP";

            //EMIT_ALL_SITES

            case EMIT_ALL_SITES_DBSNP:
                return "EMIT ALL POSITIONS, DBSNP";
            case EMIT_ALL_SITES_NODBSNP:
                return "EMIT ALL POSITIONS, NO DBSNP";

            //EMIT _CONF_ SITE S

            case EMIT_CONF_SITES_DBSNP:
                return "EMIT CONF SITES, DBSNP";
            case EMIT_CONF_SITES_NODBSNP:
                return "EMIT CONF SITES, NO DBSNP";

            default: return "default";
        }
    }

    @Override
    public String getModulename(){
        return super.getModulename() + getSubModuleName();
    }

    private void getTargetInformation(){
        if(this.communicator.isRun_mt_capture_mode()){
            ArrayList<String> tmp = new ArrayList<String>();

            for(String s : this.getParameters()){
                tmp.add(s);
            }
            tmp.add("-L");
            tmp.add(this.communicator.getFilter_for_mt());
            String[] output = new String[tmp.size()];
            tmp.toArray(output);
            this.parameters = output;
        }
        //no else required, we ain't do nothing in the other case.
    }
}
