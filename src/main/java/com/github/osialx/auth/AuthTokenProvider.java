package com.github.osialx.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class AuthTokenProvider {

    public String obtainToken(String domain, String clientId, String clientSecret) throws AuthenticationException {
        HttpPost post = new HttpPost(domain + "/oauth2/v1/token");

        JSONObject keyString = new JSONObject();
        keyString.put("client_id", clientId);
        keyString.put("client_secret", clientSecret);
        keyString.put("grant_type", "client_credentials");

        StringEntity entity = new StringEntity(keyString.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), Consts.UTF_8));
                String result = br.readLine();
                JSONObject object = JSON.parseObject(result);

                String newAccessToken = object.getString("access_token");
                if (newAccessToken == null || newAccessToken.isEmpty()) {
                    throw new AuthenticationException(result);
                }
                return newAccessToken;
            } else {
                throw new AuthenticationException("Authentication failed with code: " + statusCode);
            }
        } catch (Exception e) {
            throw new AuthenticationException("Authentication failed", e);
        } finally {
            post.releaseConnection();
            HttpClientUtils.closeQuietly(httpClient);
        }
    }
}
