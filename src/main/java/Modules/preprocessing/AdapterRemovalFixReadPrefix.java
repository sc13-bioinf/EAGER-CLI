package Modules.preprocessing;

import IO.Communicator;
import Modules.AModule;
import com.google.common.io.Files;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by peltzer on 19.04.17.
 */
public class AdapterRemovalFixReadPrefix extends AModule {


    public AdapterRemovalFixReadPrefix(Communicator c) { super(c);}


    @Override
    public void setParameters() {
        String output_stem = Files.getNameWithoutExtension(this.inputfile.get(0));
        this.outputfile = new ArrayList<String>();

        //System.out.println("AdapterRemovalFixReadPrefix inputfile: ");
        //for (int di = 0; di < this.inputfile.size(); di ++) {
        //    System.out.println("di: "+di+" "+this.inputfile.get(di));
        //}
        this.parameters = getDefaultParameterList();
    }


    private String[] getDefaultParameterList(){

        String fix_prefix_cmd = "";

        for (int di = 0; di < this.inputfile.size(); di ++) {
            //System.out.println("di: "+di+" "+this.inputfile.get(di));


            String output_stem = Files.getNameWithoutExtension(this.inputfile.get(di));
            String ext = Files.getFileExtension(this.inputfile.get(di));
            String outputfile = getOutputfolder()+output_stem +".prefixed." + ext;
            this.outputfile.add(outputfile);

            fix_prefix_cmd += "AdapterRemovalFixPrefix "+this.inputfile.get(di)+" "+outputfile;

            if ( di < (this.inputfile.size() - 1) ) {
                fix_prefix_cmd += " && ";
            }

        }

        return new String[]{"/bin/sh", "-c", fix_prefix_cmd};
    }

    @Override
    public String getOutputfolder() {
        return  this.communicator.getGUI_resultspath()+"/1-AdapClip/";
    }

}
