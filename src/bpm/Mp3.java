package bpm;

import java.io.File;

public class Mp3 {
    public File arquivo;
    String bpm = null;
    
    public Mp3(File arquivo) {
        this.arquivo = arquivo;
    }
    
    public Mp3(File arquivo, String bpm) {
        this.arquivo = arquivo;
        this.bpm = bpm;
    }
}
