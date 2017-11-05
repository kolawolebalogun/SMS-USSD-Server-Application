package com.kolawolebalogun.constants;

/**
 * Created by KolawoleBalogun on 7/10/17.
 */
public class AppConstants {
    public static final Boolean showError = true;

    public static final String COUNTRY_CODE = "234";
    public static final String NUMBER_PREFIX = "0";
    public static final int NATIONAL_NUMBER_LENGTH = 10;

    public static final String MESSAGE_BROKER_HOST = "localhost";
    public static final String MESSAGE_BROKER_EXCHANGE_NAME = "smpp_exchange";

    // Queues
    public static final String MESSAGE_BROKER_QUEUE_DND_UPLOAD = "dnd_upload";
    public static final String MESSAGE_BROKER_QUEUE_DND_ASSEMBLY = "dnd_assembly";
    public static final String MESSAGE_BROKER_QUEUE_INCOMING_MESSAGES = "incoming_messages";
    public static final String MESSAGE_BROKER_QUEUE_CONTENT_UPLOAD = "content_upload";
    public static final String MESSAGE_BROKER_QUEUE_AWAITING_ASYNC = "awaiting_async";
    public static final String MESSAGE_BROKER_QUEUE_CONTENT_PROVIDER_RECEIPT = "content_provider_receipt";

    // Workers Count
    public static final int MESSAGE_BROKER_QUEUE_DND_UPLOAD_WORKER = 1;
    public static final int MESSAGE_BROKER_QUEUE_DND_ASSEMBLY_WORKER = 20;
    public static final int MESSAGE_BROKER_QUEUE_CONTENT_UPLOAD_WORKER = 1;
    public static final int MESSAGE_BROKER_QUEUE_INCOMING_MESSAGES_WORKER = 5;
    public static final int MESSAGE_BROKER_QUEUE_AWAITING_ASYNC_WORKER = 5;
    public static final int MESSAGE_BROKER_QUEUE_CONTENT_PROVIDER_RECEIPT_WORKER = 10;
    public static final int MESSAGE_BROKER_PRODUCER_TIMEOUT_COUNT = 200000;

    // Database Connection
    public static final String DB_USER = "root";
    public static final String DB_PASS = "abz@2017";
    public static final String DB_CONNECTION_URI = "jdbc:mysql://localhost:3306/smpp";
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    // MongoDB Host
    public static final String MONGODB_HOST = "localhost";
    public static final Integer MONGODB_PORT = 27017;
    public static final String MONGODB_DB_NAME = "smpp";

    // Subscriber Status
    public static final Integer SUBSCRIBER_ACTIVE = 1;
    public static final Integer SUBSCRIBER_INACTIVE = 2;
    public static final Integer SUBSCRIBER_DND = 3;
    public static final Integer SUBSCRIBER_DNC = 4;

    public static final int SMS_LENGTH = 160;


    // Subscriber Churn
    public static final int CHURN_LIMIT = 90;

    // Prefix
    public static final String PREFIX_OUTGOING_BIND = "outgoing_message_bind_id_";
    public static final String PREFIX_TELCO_API_REQUEST = "telco_api_request_";

    // Outgoing messages actions
    public static final int OUTGOING_MESSAGES_DEFAULT = 0;
    public static final int OUTGOING_MESSAGES_BILL = 1;
    public static final int OUTGOING_MESSAGES_CONTENT = 2;
    public static final int OUTGOING_MESSAGES_SUBSCRIBE = 3;
    public static final int OUTGOING_MESSAGES_BILL_AND_SUBSCRIBE = 4;
    public static final int OUTGOING_MESSAGES_BILL_AND_CONTENT = 5;
    public static final int OUTGOING_MESSAGES_UNSUBSCRIBE = 6;
    public static final int OUTGOING_MESSAGES_BILL_AND_UNSUBSCRIBE = 7;

    // SMPP ERROR CODES
    public static final String ERROR_INSUFFICIENT_BALANCE = "00000c1d";
    public static final String ERROR_SEND_MESSAGE_TIMEOUT = "00000000";

    // Keywords
    public static final String KEYWORD_GENERAL_HELP = "help";
    public static final String KEYWORD_GENERAL_STOP = "stop";


    // User Input Action
    public static final String USER_INPUT_ACTION_OPT_IN = "opt-in";
    public static final String USER_INPUT_ACTION_OPT_OUT = "opt-out";
    public static final String USER_INPUT_ACTION_HELP = "help";
    public static final String USER_INPUT_ACTION_CONFIRM = "confirm_keyword";

    // Queue Priority
    public static int MO_PRIORITY = 10;
    public static int HIGH_PRIORITY = 9;

    // Telco API
    public static final int API_SYNCHRONOUS = 1;
    public static final int API_ASYNCHRONOUS = 2;

    public static final String API_MATCH_TYPE_REGREX = "1";
    public static final String API_MATCH_TYPE_JSON = "2";

    // SMS Status
    public static final String SMS_STATUS_BILLED = "BILLED";
    public static final String SMS_STATUS_SENT = "SENT";
    public static final String SMS_STATUS_FAILED = "FAILED";
    public static final String SMS_STATUS_INSUFFICIENT_FUND = "INSUFFICIENT_FUND";
    public static final String SMS_STATUS_RECEIVED = "RECEIVED";
    public static final String SMS_STATUS_SUBSCRIBED = "SUBSCRIBED";

    // SMS PROTOCOL ID
    public static final int SUBMIT_SM_0 = 0;
    public static final int SUBMIT_SM_64 = 64;


    // Output Formatting Code
    public static final int OUTPUT_COLOR_BLACK = 30;
    public static final int OUTPUT_COLOR_RED = 31;
    public static final int OUTPUT_COLOR_GREEN = 32;
    public static final int OUTPUT_COLOR_YELLOW = 33;
    public static final int OUTPUT_COLOR_BLUE = 34;
    public static final int OUTPUT_COLOR_MAGENTA = 35;
    public static final int OUTPUT_COLOR_CYAN = 36;
    public static final int OUTPUT_COLOR_WHITE = 37;
    public static final int OUTPUT_BG_COLOR_BLACK = 40;
    public static final int OUTPUT_BG_COLOR_RED = 41;
    public static final int OUTPUT_BG_COLOR_GREEN = 42;
    public static final int OUTPUT_BG_COLOR_YELLOW = 43;
    public static final int OUTPUT_BG_COLOR_BLUE = 44;
    public static final int OUTPUT_BG_COLOR_MAGENTA = 45;
    public static final int OUTPUT_BG_COLOR_CYAN = 46;
    public static final int OUTPUT_BG_COLOR_WHITE = 47;
    public static final int OUTPUT_BRIGHT = 1;
    public static final int OUTPUT_STOP_BRIGHT = 21;
    public static final int OUTPUT_UNDERLINE = 4;
    public static final int OUTPUT_STOP_UNDERLINE = 24;
    public static final int OUTPUT_CLEAR_FORMATTING = 0;


    public static final int DAILY = 1;

    // Telco API Category
    public static final int TELCO_API_SUBSCRIBE = 1;
    public static final int TELCO_API_BILL = 2;
    public static final int TELCO_API_CONTENT = 3;

    // Mongo DB init
    public static final int MONGODB_MAX_CONNECTION = 50;


    // Monitoring Tool WS URI
    public static final String MONITORING_TOOL_URI = "ws://localhost:8088/ws/data-sync/abzafa/";
    public static final String MONITORING_WS_USERNAME = "abzafa";
}
