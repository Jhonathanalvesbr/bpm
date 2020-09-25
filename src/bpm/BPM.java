/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bpm;

import at.ofai.music.beatroot.BeatRoot;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class BPM {

    /**
     * @param args the command line arguments
     */
    ArrayList<String> files = new ArrayList<>();
    List<File> resultados = new ArrayList<File>();

    public static void main(String[] args) {
        // TODO code application logic here
        File pasta = new File("C:\\Users\\admin\\Desktop\\trackanalyzer-master");
        BPM a = new BPM();
        a.buscaRecursiva(pasta, "");
        System.out.println("sss");
        System.out.println("");
    }

    public List<File> buscaRecursiva(File pasta, String ext) {
        for (File f : pasta.listFiles()) {
            if (f.isDirectory()) {
                System.out.println(f.getAbsolutePath());
                resultados.addAll(buscaRecursiva(f, ext));
            } else if (f.getName().endsWith(ext)) {
                resultados.add(f);
            }

        }
        return resultados;
    }
}
