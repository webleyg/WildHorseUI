package com.wildhorsecampground;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.wildhorsecampground.basicAuth.BasicAuthHeader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;


class HttpRequests {

    ArrayList<String> ProductList() {

        ArrayList<String> productListArray = new ArrayList<>();
        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();
        productListArray.clear();

        try {
            HttpResponse<JsonNode> productRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/products/?status=publish")
                    .header("Authorization", basicAuthHeader.GetAuthHeader())
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Host", "wildhorsecampground.com")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Content-Length", "0")
                    .header("Connection", "keep-alive")
                    .header("cache-control", "no-cache")
                    .asJson();

            JsonNode wildhorseProductsJson = productRequest.getBody();
            JSONArray wildhorseProductsJsonArray = wildhorseProductsJson.getArray();

            for (int i = 0; i < wildhorseProductsJsonArray.length(); i++) {
                    if ((((JSONObject) wildhorseProductsJsonArray.get(i)).get("name")) != null) {
                        String name = ((((JSONObject) wildhorseProductsJsonArray.get(i)).get("name")).toString());
                        if (name != null && !name.isEmpty()) {
                            productListArray.add(name);
                        }
                    }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return productListArray;
    }

    ArrayList<String> OrdersList(String productName) {
        JSONArray ordersArray = new JSONArray();

        ArrayList<String> ordersArrayList = new ArrayList<>();
        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();

        try {

            HttpResponse<JsonNode> ordersRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/orders?per_page=100&name=" + URLEncoder.encode(productName))
                    .header("Authorization", basicAuthHeader.GetAuthHeader())
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Host", "wildhorsecampground.com")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Connection", "keep-alive")
                    .header("cache-control", "no-cache")
                    .asJson();

            JsonNode ordersJson = ordersRequest.getBody();
            ordersArray = ordersJson.getArray();

            System.out.println("Orders ArrayList Size: " + ordersArray.length());

//            for (int i = 0; i < ordersArray.length(); i++) {
//                System.out.println("Orders Array Size: " + ordersArray.length());
//
//                Object firstNameQuery = (((JSONObject) ((JSONObject) ordersArray.get(i)).get("billing")).get("first_name"));
//                Object lastNameQuery = ((JSONObject) ((JSONObject) ordersArray.get(i)).get("billing")).get("last_name");
//                ordersArrayList.add(firstNameQuery + " " + lastNameQuery);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(ordersArrayList.toString());
        return ordersArrayList;

    }

}
