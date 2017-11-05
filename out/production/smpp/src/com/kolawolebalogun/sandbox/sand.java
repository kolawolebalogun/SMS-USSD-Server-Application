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

import javax.json.Json;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
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

        String string = "outgoing_message_bind_id_3\t0";
        System.out.println(new Gson().toJson(string.replace("\\s+", " ")));

        System.out.println(Json.createObjectBuilder()
                .add("content", "Kola")
                .add("sender", "Balogun")
                .add("received", LocalTime.now().toString())
                .build().toString());
    }


}
