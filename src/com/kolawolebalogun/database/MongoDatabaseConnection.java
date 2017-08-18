package com.kolawolebalogun.database;

/**
 * Created by KolawoleBalogun on 7/12/17.
 */

import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.util.Util;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MongoDatabaseConnection {

    private static Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    private static MongoDatabase mongodb = null;
    private static MongoClient mongoClient = null;

    static {
        Util.systemOut((char) 27 + String.format("[%s;%sm[x] Initializing Mongo Connections ...", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_YELLOW));
        root.setLevel(Level.ERROR);

        MongoClientOptions.Builder options_builder = new MongoClientOptions.Builder();
        options_builder.maxConnectionIdleTime(10000);
        options_builder.minConnectionsPerHost(5);
        options_builder.connectionsPerHost(AppConstants.MONGODB_MAX_CONNECTION);
        MongoClientOptions options = options_builder.build();

        mongoClient = new MongoClient(AppConstants.MONGODB_HOST, options);
        mongodb = mongoClient.getDatabase(AppConstants.MONGODB_DB_NAME);
    }


    public static MongoDatabase getConnection() {
        return mongodb;
    }


    public void close() {
        mongoClient.close();
    }

}
