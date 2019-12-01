package com.wildhorsecampground;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.wildhorsecampground.basicAuth.BasicAuthHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HttpRequests {

    ArrayList<String> ProductList() {

        ArrayList<String> productListArray = new ArrayList<>();
        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();

        try {
            HttpResponse<JsonNode> productRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/products/")
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
                if (((JSONObject) wildhorseProductsJsonArray.get(i)).get("status").equals("publish")) {
                    if ((((JSONObject) wildhorseProductsJsonArray.get(i)).get("name")) != null) {
                        String name = ((((JSONObject) wildhorseProductsJsonArray.get(i)).get("name")).toString());
                        if (name != null && !name.isEmpty()) {
                            productListArray.add(name);
                        }
                    }
                }
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return productListArray;
    }

    ArrayList<String> OrdersList(String productID) {

        ArrayList<String> ordersArrayList = new ArrayList<>();
        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();

        StringBuilder nameStringBuilder = new StringBuilder();

        try {

            System.out.println("Product ID: " + productID);

            HttpResponse<JsonNode> ordersRequest = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/orders?product=" + productID)
                    .header("Authorization", basicAuthHeader.GetAuthHeader())
                    .header("User-Agent", "PostmanRuntime/7.19.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Host", "wildhorsecampground.com")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Connection", "keep-alive")
                    .header("cache-control", "no-cache")
                    .asJson();

            System.out.println("Raw Body: " + ordersRequest.getBody());
            JsonNode ordersJson = ordersRequest.getBody();
            JSONArray ordersArray = ordersJson.getArray();


//            for (int i = 0; i < wildhorseProductsJsonArray.length(); i++) {
//                if (((JSONObject) wildhorseProductsJsonArray.get(i)).get("status").equals("publish")) {
//                    if ((((JSONObject) wildhorseProductsJsonArray.get(i)).get("name")) != null) {
//                        String name = ((((JSONObject) wildhorseProductsJsonArray.get(i)).get("name")).toString());
//                        if (name != null && !name.isEmpty()) {
//                            productListArray.add(name);
//                        }
//                    }
//                }
//            }




            for (int i = 0; i < ordersArray.length(); i++) {
                nameStringBuilder.setLength(0);
                Object firstNameQuery = ((JSONObject) ((JSONObject) ordersArray.get(i)).get("billing"));
                System.out.println("Billing: " + firstNameQuery);
//                Object lastNameQuery = ((JSONObject) ((JSONObject) ordersArray.get(i)).get("billing")).get("last_name");

//                nameStringBuilder.append(firstNameQuery).append(" ").append(lastNameQuery);
                ordersArrayList.add(nameStringBuilder.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return ordersArrayList;

    }

    String GetOrderID(String productName) {

        BasicAuthHeader basicAuthHeader = new BasicAuthHeader();

        String productID = null;

        try {
            HttpResponse<JsonNode> response = Unirest.get("https://wildhorsecampground.com/wp-json/wc/v3/products")
                    .header("Authorization", basicAuthHeader.GetAuthHeader())
                    .header("User-Agent", "PostmanRuntime/7.19.0")
                    .header("Accept", "*/*")
                    .header("Cache-Control", "no-cache")
                    .header("Host", "wildhorsecampground.com")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Connection", "keep-alive")
                    .header("cache-control", "no-cache")
                    .asJson();

            JSONArray jsonArray = response.getBody().getArray();

            for (int i = 0; i < jsonArray.length(); i++) {
                if (((JSONObject) jsonArray.get(i)).get("name") == productName) {
                    productID = ((JSONObject) ((JSONObject) jsonArray.get(i)).get("name")).get("id").toString();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productID;
    }

}
