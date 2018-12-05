package catFacts;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Perty on 27-Oct-18.
 */
public class Http_cats_facts {

    public static String url_adress = "https://cat-fact.herokuapp.com/facts/random?animal=cat&amount=1";

    public String GetApiData(String urlString) throws Exception{
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        try{
            url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(15 * 1000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e){
            throw e;
        } finally {
            if (reader != null)
                try{
                    reader.close();
                }catch (IOException ee){
                    ee.printStackTrace();
                }
        }
    }

    public static class CatData{
        public String catText;
    }

    public  CatData GetCatData(String rawData){
        JSONObject obj = new JSONObject(rawData);
        String text = obj.getString("text");
        CatData catData = new CatData();
        catData.catText = text;
        return catData;
    }

    public void PrintFact (CatData data){
        System.out.println("New fact about Cat: \n" + data.catText);
    }

    public static void main(String[] args) throws Exception {
        //----1----
        Http_cats_facts fact = new Http_cats_facts();
        String data = fact.GetApiData(url_adress);

        //----2----
        CatData catData = fact.GetCatData(data);

        //-----3----
        fact.PrintFact(catData);
    }
}
