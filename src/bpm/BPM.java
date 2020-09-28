package bpm;

import TrackAnalyzer.TrackAnalyzer;
import at.ofai.music.beatroot.BeatRoot;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BPM {

    ArrayList<File> lista = new ArrayList<>();
    JSONArray my_nome = new JSONArray();
    JSONArray my_bpm = new JSONArray();
    
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        //args = "D:\\\\Offs\\\\Jhonathan\\\\R\\\\Rede\\\\01-MJC MUSIC-COMPLETA";
        //TrackAnalyzer.main(args);
        //TrackAnalyzer.main("D:\\Offs\\Jhonathan\\R\\Rede\\01-MJC MUSIC-COMPLETA");

        ArrayList<Arquivo> arq = new ArrayList<>();
        JSONObject my_obj = new JSONObject();
        
        String pasta;
        
        String saida_json = "C:\\Users\\Jhonathan Alves\\Documents\\NetBeansProjects\\bpm-master\\src\\saida.json";
        BPM programa = new BPM();
        programa.listar(new File("E:\\Trilhas\\InterVox"));

        programa.buscaMP3(programa, arq);
        
        File file = new File(saida_json);
        if (file.exists()) {
            if (file.length() > 0) {
                programa.busca(arq, programa.my_nome, programa.my_bpm, saida_json);
            }
        }
        if (arq.size() > 0) {
            programa.inserirJson(arq, programa.my_nome, programa.my_bpm, my_obj, saida_json);
        }
        

    }

    public void busca(ArrayList<Arquivo> arq, JSONArray my_nome, JSONArray my_bpm, String saida_json) throws FileNotFoundException, IOException, ParseException {
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        jsonObject = (JSONObject) parser.parse(new FileReader(
                saida_json));
        this.my_nome = (JSONArray) jsonObject.get("nome");
        this.my_bpm = (JSONArray) jsonObject.get("bpm");
        ArrayList<String> busca = new ArrayList<>();

        ArrayList<String> a = new ArrayList<>();

        if (this.my_nome != null) {
            for (int i = 0; i < this.my_nome.size(); i++) {
                a.add(this.my_nome.get(i).toString());
            }
            
            for (int i = 0; i < arq.size(); i++) {
                for (int j = 0; j < arq.get(i).arquivos.size(); j++) {
                    for (int k = 0; k < a.size(); k++) {
                        if (arq.get(i).arquivos.get(j).arquivo.getAbsolutePath().equals(a.get(k).toString())) {
                            arq.get(i).arquivos.remove(j);
                            j--;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void inserirJson(ArrayList<Arquivo> arq, JSONArray my_nome, JSONArray my_bpm, JSONObject my_obj, String saida_json) throws IOException, Exception {
        FileWriter writeFile = null;
        for (int i = 0; i < arq.size(); i++) {
            for (int j = 0; j < arq.get(i).arquivos.size(); j++) {

                TrackAnalyzer track = new TrackAnalyzer();
                Mp3 mp;
                
                mp = track.m("" + arq.get(i).arquivos.get(j).arquivo.getAbsolutePath());

                this.my_nome.add("" + mp.arquivo.getAbsolutePath());
                this.my_bpm.add("" + mp.bpm);
                //System.out.println(mp.arquivo.getAbsolutePath());
                my_obj.put("nome", this.my_nome);
                my_obj.put("bpm", this.my_bpm);
                writeFile = new FileWriter(saida_json);
                writeFile.write(my_obj.toJSONString());
                writeFile.close();
            }
        }
    }

    public void buscaMP3(BPM programa, ArrayList<Arquivo> arq) throws IOException {
        FilenameFilter mp3 = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".mp3");
            }
        };
        for (int i = 0; i < programa.lista.size(); i++) {
            File[] arquivos = programa.lista.get(i).listFiles(mp3);
            if (arquivos.length > 0) {
                Arquivo temp = new Arquivo();
                temp.caminho = "" + programa.lista.get(i).getCanonicalFile();
                //System.out.println(temp.caminho);
                for (int j = 0; j < arquivos.length; j++) {
                    temp.arquivos.add(new Mp3(new File(arquivos[j].getAbsolutePath())));
                    //System.out.println(temp.arquivos.get(j).arquivo.getAbsolutePath());
                }
                arq.add(temp);
            }
        }

    }

    public void listar(File directory) {
        if (directory.isDirectory()) {
            lista.add(new File(directory.getPath()));
            String[] subDirectory = directory.list();
            if (subDirectory != null) {
                for (String dir : subDirectory) {
                    listar(new File(directory + File.separator + dir));
                }
            }
        }
    }

}
