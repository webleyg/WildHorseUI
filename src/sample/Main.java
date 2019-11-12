package sample;

import com.github.scribejava.apis.TwitterApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import com.github.scribejava.core.oauth.OAuthService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang.RandomStringUtils;
import org.json.JSONObject;
//import sun.security.ec.ECDSASignature;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.time.Instant;
import java.util.Formatter;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Wildhorse Campground");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
        ProductList();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public String nonceGenerate() {

        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);
        return generatedString;
    }

    private void ProductList() {
        Instant timeStamp = Instant.now();
        long timeStampSeconds = timeStamp.getEpochSecond();
        System.out.println("time: " + timeStampSeconds);

        HttpResponse<String> productRequestSig = null;
        try {
            productRequestSig = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/products/?oauth_consumer_key=ck_a3b097ed51398bdd6f4841411d6cb034e06d964a&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timeStampSeconds + "&oauth_nonce=" + nonceGenerate() + "&oauth_version=1.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Host", "wildhorsecampground.com")
                    .header("Content-Type", "multipart/form-data; boundary=--------------------------696823807593878751111857")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Length", "0")
                    .header("Connection", "keep-alive")
                    .header("cache-control", "no-cache")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        try {
            String signature = HmacSha1Signature.calculateRFC2104HMAC(productRequestSig.toString(), "key");
            System.out.println("Sig: " + signature);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpResponse<JsonNode> productRequest = null;
        try {
            productRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/products/?oauth_consumer_key=ck_a3b097ed51398bdd6f4841411d6cb034e06d964a&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + timeStampSeconds + "&oauth_nonce=" + nonceGenerate() + "&oauth_version=1.0&oauth_signature=" + productRequestSig)
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Host", "wildhorsecampground.com")
                    .header("Content-Type", "multipart/form-data; boundary=--------------------------696823807593878751111857")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Length", "0")
                    .header("Connection", "keep-alive")
                    .header("cache-control", "no-cache")
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        JSONObject productJson = new JSONObject(productRequest.getBody());


        System.out.println("Final Request: " + productJson.toString());


    }
}
