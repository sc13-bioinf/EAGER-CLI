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

package Modules;

import IO.Communicator;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by peltzer on 22.01.14.
 * Abstract Class for Modules that are run using the Runner.ModuleRunner Class.
 *
 */
public abstract class AModule {
    protected Communicator communicator;
    protected String modulename;
    protected ArrayList<String> outputfile;
    protected ArrayList<String> inputfile;
    protected String[] parameters;

    public AModule(Communicator c){
        this.communicator = c;
        this.modulename = this.getClass().getSimpleName();
    }

    public boolean hasbeenExecuted(){
        String regEx = "DONE."+this.getModulename();
        if(regEx.contains("Report")){
            regEx = "";
        }

        String path = this.getOutputfolder();
        if(path == null || regEx == null) {
            return false;
        }
        File f = new File(path);
        for(File file  : f.listFiles()){
            if(file.getName().matches(regEx)){
                return true;
            }
        }
        return false;
    }


    public String[] getParameters() {
        return this.parameters;
    }

    public String getModulename() {
        return this.modulename;
    }

    public ArrayList<String> getOutputfile() {
        return outputfile;
    }

    public void setOutputfile(ArrayList<String> outputfile) {
        this.outputfile = outputfile;
    }

    public ArrayList<String> getInputfile() {
        return inputfile;
    }

    public void setInputfile(ArrayList<String> inputfile) {
        this.inputfile = inputfile;
        setParameters();
    }

    public String getResultfolder() {
        return communicator.getGUI_resultspath();
    }

    public abstract void setParameters();

    public void setProcessEnvironment (Map <String, String> env) {};

    public Communicator getCommunicator(){
        return this.communicator;
    }

    public abstract String getOutputfolder();

    public static void setEnvironmentForParameterPrepend (Map <String, String> env, String separator, String key, String value) {
        String resolvedValue = value;
        if ( env.containsKey(key) ) {
            resolvedValue = value + separator + env.get(key);
        }
        env.put(key, resolvedValue);
    }

    public static void setEnvironmentForParameterReplace (Map <String, String> env, String key, String value) {
        env.put(key, value);
    }
}
