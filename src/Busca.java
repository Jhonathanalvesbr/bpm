import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Busca {

    public static void main(String[] args) throws IOException, ParseException {
        JSONArray my_nome = new JSONArray();
        JSONArray my_bpm = new JSONArray();
        String saida_json = "C:\\Users\\Jhonathan Alves\\Documents\\NetBeansProjects\\bpm-master\\src\\data.json";
        ArrayList<Mp3> mp3 = new ArrayList<>();
        JSONObject jsonObject;
        JSONParser parser = new JSONParser();
        jsonObject = (JSONObject) parser.parse(new FileReader(
                saida_json));
        my_nome = (JSONArray) jsonObject.get("nome");
        my_bpm = (JSONArray) jsonObject.get("bpm");
        
        for (int i = 0; i < my_nome.size(); i++) {
            Mp3 mp = new Mp3(new File(""+my_nome.get(i)),Double.parseDouble(""+my_bpm.get(i)));
            mp3.add(mp);
        }
        
        String in = "#EXTM3U\n";
        String src = "#EXTINF:0,";
        String srcFim = "\">";
        String musicas = "";
        
        FileWriter arq = new FileWriter("E:\\Trilhas\\Musicas.m3u");
        PrintWriter gravarArq = new PrintWriter(arq);
        
        for (int i = 0; i < mp3.size(); i++) {
            
                if(mp3.get(i).bpm >= 135 && mp3.get(i).bpm <= 145){
                    System.out.println(mp3.get(i).bpm);
                    System.out.println(mp3.get(i).arquivo.getAbsolutePath());
                    musicas += (src+mp3.get(i).arquivo.getName()+"\n"+mp3.get(i).arquivo.getAbsolutePath()+"\n\n");
                    break;
                }
        }
        
        gravarArq.print(in+musicas);
        arq.close();
        java.awt.Desktop.getDesktop().open( new File( "E:\\Trilhas\\Musicas.m3u" ) );
        System.out.println(my_bpm.size() + "---" + my_nome.size());
    }
    
}