package com.wildhorsecampground;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.wildhorsecampground.basicAuth.BasicAuthHeader;
import org.json.JSONArray;
import org.json.JSONException;
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

    ArrayList<String> OrdersList(Integer productID) {
        JSONArray ordersArray;

        ArrayList<String> ordersArrayList = new ArrayList<>();
        ArrayList<String> ordersNamesList = new ArrayList<>();
        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();

        try {

                HttpResponse<JsonNode> ordersRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/orders?per_page=100&page=1&product=" + productID)
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

            if (ordersArray.length() >=100) {

                int pageID =1;
                JsonNode ordersJson2;
                do {
                    pageID = pageID + 1;

                    HttpResponse<JsonNode> ordersRequest2 = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/orders?per_page=100&page=" + pageID + "&product=" + productID)
                            .header("Authorization", basicAuthHeader.GetAuthHeader())
                            .header("Accept", "*/*")
                            .header("Cache-Control", "no-cache")
                            .header("Host", "wildhorsecampground.com")
                            .header("Accept-Encoding", "gzip, deflate")
                            .header("Connection", "keep-alive")
                            .header("cache-control", "no-cache")
                            .asJson();
                    ordersJson2 = ordersRequest2.getBody();

                    for (int i = 0; i <ordersJson2.getArray().length() ; i++) {
                        ordersArray.put(ordersJson2.getArray().get(i));
                    }
                } while (!ordersJson2.toString().equals("[]"));
            }

            for (int i = 0; i <ordersArray.length() ; i++) {

                JSONObject billing = (JSONObject) ((JSONObject) ordersArray.get(i)).get("billing");



                ordersArrayList.add(billing.get("first_name") + " " + billing.get("last_name"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ordersArrayList;

    }

    Integer GetProductID(String productName) throws Exception {

        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();
        Integer productID = null;

        HttpResponse<JsonNode> ordersRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/products/?status=publish")
                .header("Authorization", basicAuthHeader.GetAuthHeader())
                .header("Accept", "*/*")
                .header("Cache-Control", "no-cache")
                .header("Host", "wildhorsecampground.com")
                .header("Accept-Encoding", "gzip, deflate")
                .header("Connection", "keep-alive")
                .header("cache-control", "no-cache")
                .asJson();

        JsonNode ordersJson = ordersRequest.getBody();
        JSONArray ordersArray = ordersJson.getArray();

        for (int i = 0; i < ordersArray.length() ; i++) {
            if (((JSONObject) ordersArray.get(i)).get("name").equals(productName)) {
                productID = (Integer) ((JSONObject) ordersArray.get(i)).get("id");
            }
        }
        return productID;
    }

}
