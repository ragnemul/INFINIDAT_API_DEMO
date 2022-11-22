package org.example;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Base64;

public class INFINIDAT {

    private String BasicAuth;
    private String connURL = "http://172.20.95.219/api/rest/";
    private String cmd;

    INFINIDAT(String user, String passwd) {
        this.setBasicAuth(user, passwd);
        Unirest.setTimeouts(0, 0);
    }

    public INFINIDAT() {
        this.BasicAuth = "Basic bHVpc21nOjI0ZDUzRTQxISE=";
        Unirest.setTimeouts(0, 0);
    }

    private static String getBasicAuthenticationHeader(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public void setBasicAuth(String user, String passwd) {
        this.BasicAuth = getBasicAuthenticationHeader(user, passwd);
    }

    public void setConnURL(String connURL) {
        this.connURL = connURL;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public void showVols(String cmd) {
        try {
            HttpResponse<JsonNode> response = Unirest.get(this.connURL + cmd)
                    .header("Authorization", this.BasicAuth)
                    .asJson();

            JsonNode result = response.getBody();
            String res_string = result.toString();

            JSONObject jsonobj = new JSONObject(res_string);
            JSONArray jsonArray = jsonobj.getJSONArray("result");

            String[] names = new String[jsonArray.length()];
            long[] used = new long[jsonArray.length()];

            System.out.printf("\n%55s \t %s\n", "nombre", "tamaño (B)");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                names[i] = jsonObject.getString("name");
                used[i] = jsonObject.getLong("used");
                System.out.printf("%55s \t %d\n", names[i], used[i]);
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getPoolId (String pool_name) {
        int pool_id = -1;
        try {
            HttpResponse<JsonNode> response = Unirest.get(this.connURL + "pools?name=eq:" + pool_name)
                    .header("Authorization", this.BasicAuth)
                    .asJson();

            JsonNode result = response.getBody();
            String res_string = result.toString();

            JSONObject jsonobj = new JSONObject(res_string);
            JSONArray jsonArray = jsonobj.getJSONArray("result");

            JSONObject jsonObject = jsonArray.getJSONObject(0);
            pool_id = jsonObject.getInt("id");

        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pool_id;
    }
}
