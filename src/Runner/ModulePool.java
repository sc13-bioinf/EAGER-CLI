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

package Runner;

import Modules.AModule;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by peltzer on 22.01.14.
 */
public class ModulePool {
    private ArrayList<ModulePool> listofPredecessors;
    private ArrayList<AModule> modulePool;
    private ArrayList<String> currentFilePath;
    private FileWriter fw;
    private BufferedWriter bfw;
    private static String EAGER_VERSION = "1.92.6";

    public ModulePool() {
        modulePool = new ArrayList<AModule>();
        listofPredecessors = new ArrayList<ModulePool>();
    }

    public void addModule(AModule mod) {
        modulePool.add(mod);
    }

    public void start() throws IOException, InterruptedException {
        this.setCurrentFilePath(this.getModulePoolPaths());
        fw = new FileWriter(modulePool.get(0).getResultfolder() + "/" + "execution_log.log", true);
        bfw = new BufferedWriter(fw);
        bfw.write("EAGER Version used for this run: " + EAGER_VERSION);

        for (AModule module : modulePool) {
            module.setInputfile(this.getCurrentFilePath());
            module.getCommunicator().setGUI_inputfiles(this.getModulePoolPaths());
            fw = new FileWriter(module.getResultfolder()+"/"+"execution_log.log", true); //append only, do not overwrite!
            bfw = new BufferedWriter(fw);
            System.out.println("ModulePoolPaths: " + Arrays.toString(this.getModulePoolPaths().toArray()));
            System.out.println("Module that will be now executed: "+ module.getModulename());
            bfw.write(getParameterString(module));
            bfw.flush();
            bfw.close();
            ModuleRunner modrunner = new ModuleRunner(module);
            this.setCurrentFilePath(module.getOutputfile());
            System.out.println("Outputpath of ModulePool right now: "+this.getCurrentFilePath());

        }
    }


    public void addPredecessor(ModulePool... pool) {
        for (ModulePool p : pool) {
            listofPredecessors.add(p);
        }
    }

    public ArrayList<AModule> getModules() {
        return this.modulePool;
    }

    public ArrayList<String> getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(ArrayList<String> currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    public ArrayList<String> getModulePoolPaths() {
        ArrayList<String> output = new ArrayList<String>();
        if (listofPredecessors.size() == 0) {
            return this.getCurrentFilePath();
        } else {
            for (ModulePool mp : listofPredecessors) {
                output.addAll(mp.getCurrentFilePath());
            }
        }
        return output;
    }

    private String getParameterString(AModule m){
        String out = m.getModulename() + " was executed with the following commandline:" + "\n";
        for(String s : m.getParameters()){
            out += s+" ";
        }
        return out+"\n";
    }
}
