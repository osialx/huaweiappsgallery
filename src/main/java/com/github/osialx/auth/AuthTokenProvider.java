package com.github.osialx.auth;

import com.github.osialx.http.JsonApiResponseProcessor;
import com.google.gson.JsonObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.nio.charset.Charset;

public class AuthTokenProvider {

    public String obtainToken(String domain, String clientId, String clientSecret) throws AuthenticationException {
        HttpPost post = new HttpPost(domain + "/oauth2/v1/token");

        JsonObject keyString = new JsonObject();
        keyString.addProperty("client_id", clientId);
        keyString.addProperty("client_secret", clientSecret);
        keyString.addProperty("grant_type", "client_credentials");

        StringEntity entity = new StringEntity(keyString.toString(), Charset.forName("UTF-8"));
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        post.setEntity(entity);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                JsonObject object = new JsonApiResponseProcessor<JsonObject>(JsonObject.class).process(response);
                String newAccessToken = object.get("access_token").getAsString();
                if (newAccessToken == null || newAccessToken.isEmpty()) {
                    throw new AuthenticationException(object.toString());
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
