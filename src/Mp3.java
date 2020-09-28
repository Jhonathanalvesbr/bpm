

import java.io.File;

public class Mp3 {
    public File arquivo;
    double bpm;
    
    public Mp3(File arquivo) {
        this.arquivo = arquivo;
    }
    
    public Mp3(File arquivo, Double bpm) {
        this.arquivo = arquivo;
        this.bpm = bpm;
    }
}
