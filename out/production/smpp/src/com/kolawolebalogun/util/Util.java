package com.kolawolebalogun.util;

import com.google.gson.Gson;
import com.kolawolebalogun.app.Variables;
import com.kolawolebalogun.constants.AppConstants;
import com.kolawolebalogun.constants.Queries;
import com.kolawolebalogun.constants.Setters;
import com.kolawolebalogun.database.DatabaseConnection;
import com.kolawolebalogun.database.MongoDatabaseConnection;
import com.kolawolebalogun.pojo.*;
import com.kolawolebalogun.processors.Processors;
import com.kolawolebalogun.rabbitmq.Producer;
import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.bson.Document;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.extra.SessionState;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by KolawoleBalogun on 7/11/17.
 */
public class Util {
    public static Boolean updateSubscriberLastContent(SubmitMessage submitMessage) throws SQLException {
        if(submitMessage.getService() != null) {
            Connection connection = null;
            PreparedStatement stmt = null;
            try {
                String query = " UPDATE `subscribers` SET `last_content_sent_at` = NOW() WHERE `msisdn` = ? AND `service_id` = ? ";
                DataSource dataSource = DatabaseConnection.getDataSource();
                connection = dataSource.getConnection();
                stmt = connection.prepareStatement(query);

                stmt.setString(1, submitMessage.getDestinationAddress());
                stmt.setInt(2, submitMessage.getService().getID());
                int output = stmt.executeUpdate();

                connection.commit();

                return (output == 1);
            } catch (Exception e) {
                if (connection != null) connection.rollback();
                if (AppConstants.showError) e.printStackTrace();
            } finally {
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            }
        }
        return false;
    }

    public static Boolean updateSubscriberLastBilled(SubmitMessage submitMessage) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            String query = " UPDATE `subscribers` SET `last_content_sent_at` = NOW(), `last_billed` = NOW(), `subscription_expiry` = NOW() + INTERVAL ? DAY WHERE `msisdn` = ? AND `service_id` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, submitMessage.getService().getBillingCycle());
            stmt.setString(2, submitMessage.getDestinationAddress());
            stmt.setInt(3, submitMessage.getService().getID());
            int output = stmt.executeUpdate();

            connection.commit();

            return (output == 1);

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();

        }
        return false;
    }

    public static Boolean updateSubscriberStatusPerService(int status, String msisdn, int service_id) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        String query;
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            if (status == AppConstants.SUBSCRIBER_ACTIVE) {
                query = " UPDATE `subscribers` SET `status` = ?, `opt_in_at` = NOW() WHERE `msisdn` = ? AND `service_id` = ? ";
            } else {
                query = " UPDATE `subscribers` SET `status` = ?, `opt_out_at` = NOW() WHERE `msisdn` = ? AND `service_id` = ? ";
            }
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, status);
            stmt.setString(2, msisdn);
            stmt.setInt(3, service_id);
            int output = stmt.executeUpdate();

            connection.commit();

            return (output == 1);

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return false;
    }

    public static Integer updateSubscriberStatusForAllService(Integer status, String msisdn) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            String query = " UPDATE `subscribers` SET `status` = ? WHERE `msisdn` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, status);
            stmt.setString(2, msisdn);
            int output = stmt.executeUpdate();

            connection.commit();

            return output;
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return 0;
    }

    public static List<SMPPBind> getSMPPAccounts() throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        List<SMPPBind> smppBinds = new ArrayList<>();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `smpp_bind`.`id` AS `id`, `ip`, `port`, `tps`, `max_conn`, `type`, `address_ton`, `address_npi`, `source_address_ton`, `source_address_npi`, `destination_address_ton`, `destination_address_npi`, `account_name`, `account_password`, `system_type`, `telco`, `telco`.`name` AS `telco_name` " +
                    " FROM `smpp_bind` " +
                    " LEFT JOIN `telco` ON `telco`.`id` = `smpp_bind`.`telco` " +
                    " WHERE NOT `smpp_bind`.`delete` ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                smppBinds.add(Setters.setSMPPFromResultSet(rs));
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return smppBinds;
    }

    public static List<TelcoAPI> getTelcoAPIs() throws SQLException {
        List<TelcoAPI> telcoAPIs = new ArrayList<>();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `id`, `name`, `api_category`, `max_connection` FROM `telco_apis` WHERE NOT `delete` ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                TelcoAPI telcoAPI = new TelcoAPI();
                telcoAPI.setID(rs.getInt("id"));
                telcoAPI.setName(rs.getString("name"));
                telcoAPI.setApiCategory(rs.getInt("api_category"));
                telcoAPI.setMaxConnection(rs.getInt("max_connection"));

                telcoAPIs.add(telcoAPI);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return telcoAPIs;
    }

    public static SMPPBind getSMPPAccount(int ID) throws SQLException {
        SMPPBind smppBind = new SMPPBind();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `smpp_bind`.`id` AS `id`, `ip`, `port`, `tps`, `max_conn`, `type`, `address_ton`, `address_npi`, `source_address_ton`, `source_address_npi`, `destination_address_ton`, `destination_address_npi`, `account_name`, `account_password`, `system_type`, `telco`, `telco`.`name` AS `telco_name` " +
                    " FROM `smpp_bind` " +
                    " LEFT JOIN `telco` ON `telco`.`id` = `smpp_bind`.`telco` " +
                    " WHERE NOT `smpp_bind`.`delete` AND `smpp_bind`.`id` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, ID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                smppBind = Setters.setSMPPFromResultSet(rs);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return smppBind;
    }

    public static ShortCode getShortCodeFromTelcoAndShortCode(int telcoID, String ShortCode) throws SQLException {
        ShortCode shortCode = new ShortCode();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `id`, `shortcode`, `tariff`, `telco`, `smpp_bind`, `delete`, `created_at`, `updated_at`, `deleted_at` FROM `shortcode` WHERE `telco` = ? AND `shortcode` = ? AND NOT `delete` LIMIT 1";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, telcoID);
            stmt.setString(2, ShortCode);
            rs = stmt.executeQuery();
            while (rs.next()) {
                shortCode.setID(rs.getInt("id"));
                shortCode.setShortCode(rs.getString("shortcode"));
                shortCode.setTariff(rs.getFloat("tariff"));
                shortCode.setTelco(rs.getInt("telco"));
                shortCode.setSmppBind(rs.getInt("smpp_bind"));
                shortCode.setDelete(rs.getBoolean("delete"));
                shortCode.setCreatedAt(rs.getString("created_at"));
                shortCode.setDeletedAt(rs.getString("deleted_at"));
                shortCode.setUpdatedAt(rs.getString("updated_at"));
            }

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return shortCode;
    }


    public static int getSMPPBindFromTelcoAndShortCode(int telcoID, String ShortCode) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = " SELECT `smpp_bind` FROM `shortcode` WHERE `telco` = ? AND `shortcode` = ? LIMIT 1";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, telcoID);
            stmt.setString(2, ShortCode);
            rs = stmt.executeQuery();
            if (rs != null) {
                rs.next();
                return rs.getInt("smpp_bind");
            }

        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return 0;
    }


    public static Telco getTelcoBySMPPBindID(int bindID) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        Telco telco = new Telco();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `telco`.`id`, `telco`.`name`, `telco`.`contact_number`, `telco`.`contact_email`, `telco`.`logo`, `telco`.`delete`, `telco`.`created_at`, `telco`.`updated_at`, `telco`.`deleted_at` FROM `telco` LEFT JOIN `smpp_bind` ON `smpp_bind`.`telco` = `telco`.`id` WHERE `smpp_bind`.`id` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, bindID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                telco.setID(rs.getInt("id"));
                telco.setName(rs.getString("name"));
                telco.setContactNumber(rs.getString("contact_number"));
                telco.setContactEmail(rs.getString("contact_email"));
                telco.setLogo(rs.getString("logo"));
                telco.setDelete(rs.getBoolean("delete"));
                telco.setDeletedAt(rs.getString("deleted_at"));
                telco.setCreatedAt(rs.getString("created_at"));
                telco.setUpdatedAt(rs.getString("updated_at"));
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }


        return telco;
    }


    public static Telco getTelcoBySMPPSession(int bindID) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        return Util.getTelcoBySMPPBindID(bindID);
    }

    public static Service getServiceByKeyword(String keyword, int telcoID, String shortCode) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        Service service = new Service();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = Queries.SERVICE +
                    " WHERE ( ? REGEXP `services`.`opt_in_keyword` OR ? REGEXP `services`.`opt_out_keyword` OR ? REGEXP `services`.`help_keyword` OR ? REGEXP `services`.`confirm_keyword` ) " +
                    " AND ( `services`.`telco` = ? ) " +
                    " AND ( `opt_in_shortcode`.`shortcode` = ? OR `opt_out_shortcode`.`shortcode` = ? OR `help_shortcode`.`shortcode` = ? OR `confirmation_shortcode`.`shortcode` = ?) " +
                    " AND NOT `services`.`delete` ";

            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
            stmt.setString(3, keyword);
            stmt.setString(4, keyword);
            stmt.setInt(5, telcoID);
            stmt.setString(6, shortCode);
            stmt.setString(7, shortCode);
            stmt.setString(8, shortCode);
            stmt.setString(9, shortCode);
            rs = stmt.executeQuery();

            while (rs.next()) {
                service = Setters.setServiceFromResultSet(rs);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return service;
    }

    public static Service getServiceByID(int serviceID) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException {
        Service service = new Service();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = Queries.SERVICE +
                    " WHERE `services`.`id` = ? " +
                    " AND NOT `services`.`delete` ";

            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, serviceID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                service = Setters.setServiceFromResultSet(rs);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return service;
    }

    public static List<Subscriber> getEligibleSubscribersByServiceID(int serviceID) throws SQLException {
        List<Subscriber> subscribers = new ArrayList<>();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = " SELECT `id`, `msisdn`, `service_id`, `status`, `last_billed`, `subscription_expiry`, `last_content_sent_at`, `opt_in_at`, `opt_out_at`, `created_at`, `updated_at`, `delete`, `deleted_at` FROM `subscribers` WHERE status = ? AND service_id = ? AND DATEDIFF(DATE(last_content_sent_at), NOW()) <= ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, AppConstants.SUBSCRIBER_ACTIVE);
            stmt.setInt(2, serviceID);
            stmt.setInt(3, AppConstants.CHURN_LIMIT);
            rs = stmt.executeQuery();


            while (rs.next()) {
                subscribers.add(Setters.setSubscriberFromResultSet(rs));
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();

        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return subscribers;
    }

    public static Subscriber getServiceSubscriber(int serviceID, String msisdn) throws SQLException {
        Subscriber subscriber = new Subscriber();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String query = " SELECT `id`, `msisdn`, `service_id`, `status`, `last_billed`, `subscription_expiry`, `last_content_sent_at`, `opt_in_at`, `opt_out_at`, `created_at`, `updated_at`, `delete`, `deleted_at` FROM `subscribers` WHERE `service_id` = ? AND `msisdn` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, serviceID);
            stmt.setString(2, msisdn);
            rs = stmt.executeQuery();

            while (rs.next()) {
                subscriber = Setters.setSubscriberFromResultSet(rs);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return subscriber;
    }

    public static Subscriber getActiveServiceSubscriber(int serviceID, String msisdn) throws SQLException {
        Subscriber subscriber = new Subscriber();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `id`, `msisdn`, `service_id`, `status`, `last_billed`, `subscription_expiry`, `last_content_sent_at`, `opt_in_at`, `opt_out_at`, `created_at`, `updated_at`, `delete`, `deleted_at` FROM `subscribers` WHERE `service_id` = ? AND `msisdn` = ? AND `status` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, serviceID);
            stmt.setString(2, msisdn);
            stmt.setInt(3, AppConstants.SUBSCRIBER_ACTIVE);
            rs = stmt.executeQuery();

            while (rs.next()) {
                subscriber = Setters.setSubscriberFromResultSet(rs);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return subscriber;
    }

    public static List<String> getGeneralHelpMessage(int telcoID) throws SQLException {
        List<String> helpMessages = new ArrayList<>();
        String sms = "";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `help_message` FROM `services` " +
                    " WHERE `include_service_in_general_help` AND NOT `delete` " +
                    " AND `telco` = ? ";
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setInt(1, telcoID);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("help_message") != null) {
                    if ((sms.length() + rs.getString("help_message").length()) < AppConstants.SMS_LENGTH) {
                        sms += rs.getString("help_message").trim() + "\n";
                    } else {
                        helpMessages.add(sms);
                        sms = rs.getString("help_message");
                    }
                }
            }

            if (!sms.trim().equalsIgnoreCase("")) {
                helpMessages.add(sms);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return helpMessages;
    }

    public static List<String> getGeneralStopMessage(String msisdn) throws SQLException {
        List<String> stopMessages = new ArrayList<>();
        String sms = "";

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = " SELECT `subscribers`.`id`, `services`.`stop_message` " +
                    " FROM `subscribers` LEFT JOIN `services` ON `services`.`id` = `subscribers`.`service_id` " +
                    " WHERE `subscribers`.`msisdn` = ? AND `subscribers`.`status` = ? " +
                    " AND NOT `subscribers`.`delete` AND NOT `services`.`delete` ";

            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = connection.prepareStatement(query);

            stmt.setString(1, msisdn);
            stmt.setInt(2, AppConstants.SUBSCRIBER_ACTIVE);


            rs = stmt.executeQuery();

            while (rs.next()) {
                if (rs.getString("services.stop_message") != null) {
                    if ((sms.length() + rs.getString("services.stop_message").length()) < AppConstants.SMS_LENGTH) {
                        sms += rs.getString("services.stop_message") + ". ";
                    } else {
                        stopMessages.add(sms);
                        sms = rs.getString("services.stop_message");
                    }
                }
            }

            if (!sms.trim().equalsIgnoreCase("")) {
                stopMessages.add(sms);
            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return stopMessages;
    }

    public static Subscriber subscribeUsers(int serviceID, String msisdn, Boolean billed) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Service service = Util.getServiceByID(serviceID);
        Subscriber subscriber = Util.getServiceSubscriber(serviceID, msisdn);
        String query;

        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            DataSource dataSource = DatabaseConnection.getDataSource();
            connection = dataSource.getConnection();
            stmt = null;

            if (service.getID() != 0) {
                if (billed && (subscriber.getID() == 0)) {
                    query = " INSERT INTO `subscribers` (`msisdn`, `service_id`, `status`, `last_billed`, `subscription_expiry`, `opt_in_at`, `last_content_sent_at`) VALUES (?, ?, ?, NOW(), NOW() + INTERVAL ? DAY, NOW(), NOW()) ";
                    stmt = connection.prepareStatement(query);

                    stmt.setString(1, msisdn);
                    stmt.setInt(2, serviceID);
                    stmt.setInt(3, AppConstants.SUBSCRIBER_ACTIVE);
                    stmt.setInt(4, service.getBillingCycle());
                } else if (!billed && subscriber.getID() == 0) {
                    query = " INSERT INTO `subscribers` (`msisdn`, `service_id`, `status`, `opt_in_at`, `last_content_sent_at`) VALUES (?, ?, ?, NOW(), NOW()) ";
                    stmt = connection.prepareStatement(query);

                    stmt.setString(1, msisdn);
                    stmt.setInt(2, serviceID);
                    stmt.setInt(3, AppConstants.SUBSCRIBER_ACTIVE);

                } else if (subscriber.getID() != 0) {
                    if (subscriber.getStatus() != AppConstants.SUBSCRIBER_ACTIVE) {
                        if (Util.updateSubscriberStatusPerService(AppConstants.SUBSCRIBER_ACTIVE, msisdn, serviceID)) {
                            subscriber.setStatus(AppConstants.SUBSCRIBER_ACTIVE);
                        }
                    } else {
                        subscriber.setAlreadySubscribed(true);
                    }
                }

                if (stmt != null) {
                    int update = stmt.executeUpdate();

                    connection.commit();
                    if (update == 1) {
                        subscriber = Util.getServiceSubscriber(serviceID, msisdn);
                    }
                }

            }
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            if (AppConstants.showError) e.printStackTrace();

        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return subscriber;
    }


    public static Service getUserFromConfirmation(String msisdn) {
        final Service[] service = {new Service()};
        MongoDatabase mongoDatabase = MongoDatabaseConnection.getConnection();
        MongoCollection collection = mongoDatabase.getCollection("service_confirmation");

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                service[0] = new Gson().fromJson(new Gson().toJson(document.get("service")), Service.class);
            }
        };

        collection.find(eq("msisdn", msisdn)).forEach(printBlock);
        collection.deleteMany(eq("msisdn", msisdn));

        return service[0];
    }


    public static AwaitingAsync getAwaitingAsync(String transactionID) {
        final AwaitingAsync[] awaitingAsync = {new AwaitingAsync()};
        MongoDatabase mongoDatabase = MongoDatabaseConnection.getConnection();
        MongoCollection collection = mongoDatabase.getCollection("awaiting_async");

        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                SubmitMessage outGoingMessages = new Gson().fromJson(new Gson().toJson(document.get("message")), SubmitMessage.class);
                awaitingAsync[0].setSubmitMessage(outGoingMessages);
            }
        };

        collection.find(eq("message.transactionID", transactionID)).forEach(printBlock);


        return awaitingAsync[0];
    }

    public static void insertIntoServiceConfirmation(String msisdn, Service service) {
        MongoDatabase mongoDatabase = MongoDatabaseConnection.getConnection();
        MongoCollection collection = mongoDatabase.getCollection("service_confirmation");

        Document document = new Document("msisdn", msisdn)
                .append("service", (DBObject) JSON.parse(new Gson().toJson(service)));

        collection.insertOne(document);
    }

    public static String getUserInputAction(String input, Service service) {
        String action = null;

        try {
            Pattern r;
            Matcher m;

            if (service.getOptInKeyword() != null) {
                r = Pattern.compile(service.getOptInKeyword(), Pattern.CASE_INSENSITIVE);
                m = r.matcher(input);
                if (m.find()) {
                    action = AppConstants.USER_INPUT_ACTION_OPT_IN;
                }
            }

            if (service.getOptOutKeyword() != null) {
                r = Pattern.compile(service.getOptOutKeyword(), Pattern.CASE_INSENSITIVE);
                m = r.matcher(input);
                if (m.find()) {
                    action = AppConstants.USER_INPUT_ACTION_OPT_OUT;
                }
            }

            if (service.getHelpKeyword() != null) {
                r = Pattern.compile(service.getHelpKeyword(), Pattern.CASE_INSENSITIVE);
                m = r.matcher(input);
                if (m.find()) {
                    action = AppConstants.USER_INPUT_ACTION_HELP;
                }
            }

            if (service.getConfirmKeyword() != null) {
                r = Pattern.compile(service.getConfirmKeyword(), Pattern.CASE_INSENSITIVE);
                m = r.matcher(input);
                if (m.find()) {
                    action = AppConstants.USER_INPUT_ACTION_CONFIRM;
                }
            }
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }

        return action;
    }


    public static String replaceStr(String value, Map<String, String> replaceString) {

        if(replaceString != null) {
            for (Map.Entry<String, String> entry : replaceString.entrySet()) {
                value = value.replace(entry.getKey(), entry.getValue());
            }
        }

        return value;
    }


    public static String sendPost(String url, Map<String, String> headers, String body) throws IOException {
        System.out.println("[x] Sending POST Request to " + url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);

        // Add Headers
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                post.addHeader(entry.getKey(), entry.getValue());
            }
        }

        post.setEntity(new StringEntity(body));

        HttpResponse response = httpClient.execute(post);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public static String sendPost(String url, Map<String, String> headers, List<NameValuePair> params) throws IOException {
        System.out.println("[x] Sending POST Request to " + url);
        System.out.println("[x] Header " + headers);
        System.out.println("[x] Body " + params.toString());
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost post = new HttpPost(url);

            // Add Headers
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    post.addHeader(entry.getKey(), entry.getValue());
                }
            }

            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            HttpResponse response = httpClient.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String sendGet(String url, Map<String, String> headers) throws IOException {
        System.out.println("[x] Sending GET Request to " + url);
        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpGet get = new HttpGet(url);

            // Add Headers to curl
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    get.setHeader(entry.getKey(), entry.getValue());
                }
            }

            HttpResponse response = httpClient.execute(get);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static String sendToThirdPartyAPI(SubmitMessage submitMessage) throws IOException {
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair(submitMessage.getService().getExternalAPIDestinationAddressParam(), submitMessage.getSourceAddress()));
        params.add(new BasicNameValuePair(submitMessage.getService().getExternalAPISourceAddressParam(), submitMessage.getDestinationAddress()));
        params.add(new BasicNameValuePair(submitMessage.getService().getExternalAPIMessageParam(), submitMessage.getKeyword()));
        params.add(new BasicNameValuePair("status", submitMessage.getExternalAPIType()));
        params.add(new BasicNameValuePair("service_id", String.valueOf(submitMessage.getService().getID())));

        JSONParser parser = new JSONParser();
        if (!submitMessage.getService().getTelcoParams().trim().equalsIgnoreCase("")) {
            JSONArray json = null;
            try {
                json = (JSONArray) parser.parse(submitMessage.getService().getTelcoParams());
            } catch (ParseException e) {
                if(AppConstants.showError) {
                    e.printStackTrace();
                }
            }
            if(json != null) {
                for (Object aJson : json) {
                    JSONObject objects = (JSONObject) aJson;
                    if (objects.get("send_to_external_param").toString() != null && Boolean.parseBoolean(objects.get("send_to_external_param").toString())) {
                        params.add(new BasicNameValuePair(objects.get("key").toString(), objects.get("value").toString()));
                    }
                }
            }
        }
        return Util.sendPost(submitMessage.getService().getExternalAPI(), null, params);
    }

    public static void sendToContentProviderReceiptQueue(SubmitMessage submitMessage) throws IOException, TimeoutException {
        Producer producer = new Producer(String.format("%s", AppConstants.MESSAGE_BROKER_QUEUE_CONTENT_PROVIDER_RECEIPT), true);
        producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
        producer.close();
    }


    public static String telcoAPIRequest(TelcoAPI telcoAPI, SubmitMessage submitMessage) throws ParseException, IOException {
        String apiResponse = null;
        String url = telcoAPI.getURL();
        String method = telcoAPI.getMethod();
        String bodyType = telcoAPI.getBodyType();
        String header = telcoAPI.getHeader();
        String body = telcoAPI.getBody();
        String bodyRaw = telcoAPI.getBodyRaw();

        Map<String, String> replaceStr = new HashMap<>();
        JSONParser parser = new JSONParser();

        if (submitMessage.getService().getTelcoParams() != null) {
            if (!submitMessage.getService().getTelcoParams().trim().equalsIgnoreCase("")) {
                JSONArray json = (JSONArray) parser.parse(submitMessage.getService().getTelcoParams());
                for (Object aJson : json) {
                    JSONObject objects = (JSONObject) aJson;
                    replaceStr.put(String.format("`%s`", objects.get("key").toString()), objects.get("value").toString());
                }
            }
            replaceStr.put("`msisdn`", Util.internationalNumberFormat(submitMessage.getDestinationAddress()));
            replaceStr.put("`nat-msisdn`", Util.nationalNumberFormat(submitMessage.getDestinationAddress()));
        }

        // headers
        Map<String, String> headers = new HashMap<>();
        if (header != null) {
            JSONArray json = (JSONArray) parser.parse(header);

            for (Object aJson : json) {
                JSONObject objects = (JSONObject) aJson;
                if (objects != null) {
                    headers.put(objects.get("key").toString(), Util.replaceStr(objects.get("value").toString(), replaceStr));
                }
            }
        }
        if (method != null) {
            if (method.equalsIgnoreCase("post")) {
                if (bodyType.trim().equalsIgnoreCase("raw")) {
                    bodyRaw = Util.replaceStr(bodyRaw, replaceStr);
                    apiResponse = Util.sendPost(url, headers, bodyRaw);
                } else if (bodyType.trim().equalsIgnoreCase("x-www-form-urlencoded") || bodyType.trim().equalsIgnoreCase("form-data")) {
                    if (body != null) {
                        JSONArray json = (JSONArray) parser.parse(body);
                        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
                        for (Object aJson : json) {
                            JSONObject objects = (JSONObject) aJson;
                            params.add(new BasicNameValuePair(objects.get("key").toString(), Util.replaceStr(objects.get("value").toString(), replaceStr)));
                            apiResponse = Util.sendPost(url, headers, params);
                        }
                    }
                }
            } else if (method.equalsIgnoreCase("get")) {
                url = Util.replaceStr(url, replaceStr);
                apiResponse = Util.sendGet(url, headers);
            }
        }
        return apiResponse;
    }

    public static String getValueFromResponse(String matchType, String matchValue, String source) throws ParseException {
        String value = null;
        if (matchType.equalsIgnoreCase(AppConstants.API_MATCH_TYPE_JSON)) {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(source);
            String[] keys = matchValue.split("\\.");
            Object responseObject = json;
            for (String k : keys) {
                responseObject = json.get(k);
            }
            value = (responseObject != null) ? responseObject.toString() : null;
        } else if (matchType.equalsIgnoreCase(AppConstants.API_MATCH_TYPE_REGREX)) {
            Pattern r = Pattern.compile(matchValue, Pattern.CASE_INSENSITIVE);
            Matcher m = r.matcher(source);
            if (m.find()) {
                value = m.group(1);
            }
        }

        return value;
    }

    public static void logReport(String[] response, String status, SubmitMessage submitMessage) {
        MongoDatabase mongoDatabase = MongoDatabaseConnection.getConnection();
        try {
            Util.systemOut((char) 27 + String.format("[%s;%sm[x] Logging report as %s ", AppConstants.OUTPUT_BRIGHT, AppConstants.OUTPUT_COLOR_WHITE, status));
            MongoCollection collection = mongoDatabase.getCollection("report");

            Date now = new Date();

            Document document = new Document("messageID", response[0])
                    .append("errorID", response[1])
                    .append("message", (DBObject) JSON.parse(new Gson().toJson(submitMessage)))
                    .append("status", status)
                    .append("date", now);

            collection.insertOne(document);

        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }


    public static void logAwaitingAsync(SubmitMessage outGoingMessages) {
        MongoDatabase mongoDatabase = MongoDatabaseConnection.getConnection();
        try {
            MongoCollection collection = mongoDatabase.getCollection("awaiting_async");

            Date now = new Date();

            Document document = new Document("message", (DBObject) JSON.parse(new Gson().toJson(outGoingMessages)))
                    .append("date", now);
            collection.insertOne(document);

        } catch (Exception e) {
            if (AppConstants.showError) {
                e.printStackTrace();
            }
        }
    }

    public static Boolean getPushSMSStatus(String[] response) {
        return response[0] != null || Objects.equals(response[1], AppConstants.ERROR_SEND_MESSAGE_TIMEOUT);
    }

    public static int getPushSMSState(String[] response) {
        if (response[1] != null && Objects.equals(response[1], AppConstants.ERROR_SEND_MESSAGE_TIMEOUT)) {
            return 1;
        } else if (response[1] != null && Objects.equals(response[1], AppConstants.ERROR_INSUFFICIENT_BALANCE)) {
            return 2;
        } else if (response[0] != null) {
            return 3;
        }

        return 0;
    }

    public static String internationalNumberFormat(String msisdn) {
        if (!msisdn.startsWith(AppConstants.COUNTRY_CODE)) {
            if (msisdn.startsWith(AppConstants.NUMBER_PREFIX)) {
                return AppConstants.COUNTRY_CODE + msisdn.substring(AppConstants.NUMBER_PREFIX.length());
            } else if (msisdn.length() == AppConstants.NATIONAL_NUMBER_LENGTH) {
                return AppConstants.COUNTRY_CODE + msisdn;
            }
        }
        return msisdn;
    }

    public static String nationalNumberFormat(String msisdn) {
        if (msisdn.startsWith(AppConstants.COUNTRY_CODE)) {
            return AppConstants.NUMBER_PREFIX + msisdn.substring(AppConstants.COUNTRY_CODE.length());
        }
        return msisdn;
    }

    public static void sendMultipleMessages(List<String> messages, Producer producer, SubmitMessage submitMessage) throws IOException, TimeoutException {
        if (messages.size() > 0) {
            for (String sms : messages) {
                submitMessage.setSms(sms);
                producer.sendMessage(new Gson().toJson(submitMessage), AppConstants.MO_PRIORITY);
            }
        }
    }

    public static void systemOut(String text) {
        System.out.println(text);
        System.out.print((char) 27 + String.format("[%sm", AppConstants.OUTPUT_CLEAR_FORMATTING));
    }

    public static void setActionForContent(SubmitMessage submitMessage) throws IOException, TimeoutException {
        if (submitMessage.getService().getContentShouldBill() || submitMessage.getService().getBillingCycle() != AppConstants.DAILY) {
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_CONTENT);
            Processors.processContent(submitMessage);
        } else if (submitMessage.getService().getBillingCycle() == AppConstants.DAILY) {
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT);
            Processors.processBilling(submitMessage);
        }
    }

    public static void setActionForContent(SubmitMessage submitMessage, Producer producer) throws IOException, TimeoutException {
        if (submitMessage.getService().getContentShouldBill() || submitMessage.getService().getBillingCycle() != AppConstants.DAILY) {
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_CONTENT);
            submitMessage.setSourceAddress(submitMessage.getService().getContentShortCode());
            Processors.processContent(submitMessage, producer);
        } else if (submitMessage.getService().getBillingCycle() == AppConstants.DAILY) {
            submitMessage.setAction(AppConstants.OUTGOING_MESSAGES_BILL_AND_CONTENT);
            submitMessage.setSourceAddress(submitMessage.getService().getBillingShortCode());
            Processors.processBilling(submitMessage, producer);
        }
    }
}
