package com.kolawolebalogun.sandbox;

import com.google.gson.Gson;
import com.kolawolebalogun.app.Variables;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.database.MongoDatabaseConnection;
import com.kolawolebalogun.pojo.Service;
import com.kolawolebalogun.rabbitmq.Consumer;
import com.kolawolebalogun.rabbitmq.Producer;
import com.kolawolebalogun.util.Util;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.jsmpp.session.SMPPSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by KolawoleBalogun on 7/15/17.
 */
public class sand {


    public static void main(String[] argv) throws Exception {

        MongoDatabase mongoDatabase = MongoDatabaseConnection.getConnection();
        MongoCollection collection = mongoDatabase.getCollection("service_confirmation");

        System.out.println(collection);



        MongoDatabase mongoDatabase2 = MongoDatabaseConnection.getConnection();
        MongoCollection collection2 = mongoDatabase2.getCollection("service_confirmation");

        System.out.println(collection2);
    }


}
