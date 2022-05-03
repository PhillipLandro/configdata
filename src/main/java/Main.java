import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<String> data = new ArrayList<>();

        // Datei auslesen und korrigieren
        try(BufferedReader reader = new BufferedReader(new FileReader("konfigurationsdaten.csv"))){
            String line = "";
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] content = line.split(";");
                content[0] = correctMistakes(content[0]);
                if(!ifMACExists(content[0], data)){
                    data.add(content[0]+";"+content[1]+";"+content[2]);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Schreiben der korrigierten der neuen Daten in eine neue Datei
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("korrigierte_Daten.csv"))){
            writer.write("MAC-Adresse;Name;Abteilung");
            writer.newLine();
            for(String s : data){
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Korrigiert die übergebene MAC nach bestimmten Konditionen
     * @param mac
     * @return
     */
    public static String correctMistakes(String mac){
        mac = "12:FA:8D:" + mac.substring(mac.length() - 8);
        char[] macChars = mac.toCharArray();
        macChars[11] = ':';
        macChars[14] = ':';
        return String.valueOf(macChars);
    }

    /**
     * Sucht in der übergebenen Liste, ob die übergebene MAC existiert oder nicht
     * @param mac
     * @param data
     * @return
     */
    public static boolean ifMACExists(String mac, List<String> data){
        for(String s: data){
            if(s.split(";")[0].equals(mac)){
                return true;
            }
        }
        return false;
    }


}
