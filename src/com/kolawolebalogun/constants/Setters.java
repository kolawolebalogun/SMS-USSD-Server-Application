package com.kolawolebalogun.constants;

import com.kolawolebalogun.pojo.SMPPBind;
import com.kolawolebalogun.pojo.Service;
import com.kolawolebalogun.pojo.Subscriber;


import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by KolawoleBalogun on 7/19/17.
 */
public class Setters {
    public static Service setServiceFromResultSet(ResultSet rs) throws SQLException {
        Service service = new Service();
        service.setID(rs.getInt("services.id"));
        service.setServiceName(rs.getString("services.service_name"));
        service.setStage(rs.getInt("services.stage"));
        service.setContentProviderID(rs.getInt("services.content_provider_id"));
        service.setTelco(rs.getInt("services.telco"));
        service.setBearer(rs.getString("services.bearer"));
        service.setOptInKeyword(rs.getString("services.opt_in_keyword"));
        service.setOptOutKeyword(rs.getString("services.opt_out_keyword"));
        service.setHelpKeyword(rs.getString("services.help_keyword"));
        service.setConfirmKeyword(rs.getString("services.confirm_keyword"));
        service.setTelcoAPISubscriptionID(rs.getInt("services.telco_api_subscription"));
        service.setCategoryID(rs.getInt("services.category"));
        service.setParentID(rs.getInt("services.parent"));
        service.setTariff(rs.getFloat("services.tariff"));
        service.setOptInShortCodeID(rs.getInt("services.opt_in_shortcode"));
        service.setOptInShortCode(rs.getString("opt_in_shortcode.shortcode"));
        service.setOptInShortCodeTariff(rs.getFloat("opt_in_shortcode.tariff"));
        service.setOptInShortCodeBind(rs.getInt("opt_in_shortcode.smpp_bind"));
        service.setOptOutShortCodeID(rs.getInt("services.opt_out_shortcode"));
        service.setOptOutShortCode(rs.getString("opt_out_shortcode.shortcode"));
        service.setOptOutShortCodeTariff(rs.getFloat("opt_out_shortcode.tariff"));
        service.setOptOutShortCodeBind(rs.getInt("opt_out_shortcode.smpp_bind"));
        service.setBillingShortCodeID(rs.getInt("services.billing_shortcode"));
        service.setBillingShortCode(rs.getString("billing_shortcode.shortcode"));
        service.setBillingShortCodeTariff(rs.getFloat("billing_shortcode.tariff"));
        service.setBillingShortCodeBind(rs.getInt("billing_shortcode.smpp_bind"));
        service.setTelcoAPIBillingID(rs.getInt("services.telco_api_billing"));
        service.setConfirmationShortCodeID(rs.getInt("services.confirmation_shortcode"));
        service.setConfirmationShortCode(rs.getString("confirmation_shortcode.shortcode"));
        service.setConfirmationShortCodeTariff(rs.getFloat("confirmation_shortcode.tariff"));
        service.setConfirmationShortCodeBind(rs.getInt("confirmation_shortcode.smpp_bind"));
        service.setHelpShortCodeID(rs.getInt("services.help_shortcode"));
        service.setHelpShortCode(rs.getString("help_shortcode.shortcode"));
        service.setHelpShortCodeTariff(rs.getFloat("help_shortcode.tariff"));
        service.setHelpShortCodeBind(rs.getInt("help_shortcode.smpp_bind"));
        service.setContentShortCodeID(rs.getInt("services.content_shortcode"));
        service.setContentShortCode(rs.getString("content_shortcode.shortcode"));
        service.setContentShortCodeTariff(rs.getFloat("content_shortcode.tariff"));
        service.setContentShortCodeBind(rs.getInt("content_shortcode.smpp_bind"));
        service.setTelcoAPIContentID(rs.getInt("services.telco_api_content"));
        service.setOptInMessage(rs.getString("services.opt_in_message"));
        service.setOptOutMessage(rs.getString("services.opt_out_message"));
        service.setConfirmationMessage(rs.getString("services.confirmation_message"));
        service.setHelpMessage(rs.getString("services.help_message"));
        service.setInsufficientBalanceMessage(rs.getString("services.insufficient_balance_message"));
        service.setAlreadySubscribedMessage(rs.getString("services.already_subscribed_message"));
        service.setStopMessage(rs.getString("services.stop_message"));
        service.setRenewalMessage(rs.getString("services.renewal_message"));
        service.setChargingNotificationMessage(rs.getString("services.billing_notification"));
        service.setBillingCycle(rs.getInt("services.billing_cycle"));
        service.setTelcoParams(rs.getString("services.telco_params"));
        service.setExternalAPI(rs.getString("services.external_api"));
        service.setExternalAPIMethod(rs.getString("services.external_api_method"));
        service.setExternalAPISourceAddressParam(rs.getString("services.external_api_source_address_param"));
        service.setExternalAPIDestinationAddressParam(rs.getString("services.external_api_destination_address_param"));
        service.setExternalAPIMessageParam(rs.getString("services.external_api_message_param"));
        service.setExternalAPIProcessing(rs.getInt("services.external_api_processing"));
        service.setContentShouldBill(rs.getBoolean("services.content_should_bill"));
        service.setRenewalDoneByTelco(rs.getBoolean("services.renewal_done_by_telco"));
        service.setIncludeServiceInGeneralHelp(rs.getBoolean("services.include_service_in_general_help"));
        service.setSendBillingNotification(rs.getBoolean("services.send_billing_notification"));
        service.setIgnoreMessagesOnSubscription(rs.getBoolean("services.ignore_messages_on_subscription"));
        service.setCreatedAt(rs.getString("services.created_at"));
        service.setUpdatedAt(rs.getString("services.updated_at"));
        service.setDelete(rs.getBoolean("services.updated_at"));
        service.setDeletedAt(rs.getString("services.delete"));
        service.setTelcoAPIBillingID(rs.getInt("billing_api.id"));
        service.setTelcoAPIBillingName(rs.getString("billing_api.name"));
        service.setTelcoAPIBillingURL(rs.getString("billing_api.url"));
        service.setTelcoAPIBillingType(rs.getInt("billing_api.type"));
        service.setTelcoAPIBillingMethod(rs.getString("billing_api.method"));
        service.setTelcoAPIBillingBodyType(rs.getString("billing_api.body_type"));
        service.setTelcoAPIBillingHeader(rs.getString("billing_api.header"));
        service.setTelcoAPIBillingBody(rs.getString("billing_api.body"));
        service.setTelcoAPIBillingBodyRaw(rs.getString("billing_api.body_raw"));
        service.setTelcoAPIBillingSuccessCode(rs.getString("billing_api.success_code"));
        service.setTelcoAPIBillingInsufficientAirtimeCode(rs.getString("billing_api.insufficient_airtime_code"));
        service.setTelcoAPIBillingResponseCodeMatchType(rs.getString("billing_api.response_code_match_type"));
        service.setTelcoAPIBillingResponseCodeMatchValue(rs.getString("billing_api.response_code_match_type_value"));
        service.setTelcoAPIBillingResponseDescriptionMatchType(rs.getString("billing_api.response_description_match_type"));
        service.setTelcoAPIBillingResponseDescriptionMatchValue(rs.getString("billing_api.response_description_match_type_value"));
        service.setTelcoAPIBillingResponseTransactionIDMatchType(rs.getString("billing_api.response_transaction_id_match_type"));
        service.setTelcoAPIBillingResponseTransactionIDMatchValue(rs.getString("billing_api.response_transaction_id_match_type_value"));
        service.setTelcoAPIBillingBills(rs.getBoolean("billing_api.bills"));
        service.setTelcoAPISubscriptionID(rs.getInt("subscription_api.id"));
        service.setTelcoAPISubscriptionName(rs.getString("subscription_api.name"));
        service.setTelcoAPISubscriptionURL(rs.getString("subscription_api.url"));
        service.setTelcoAPISubscriptionType(rs.getInt("subscription_api.type"));
        service.setTelcoAPISubscriptionMethod(rs.getString("subscription_api.method"));
        service.setTelcoAPISubscriptionBodyType(rs.getString("subscription_api.body_type"));
        service.setTelcoAPISubscriptionHeader(rs.getString("subscription_api.header"));
        service.setTelcoAPISubscriptionBody(rs.getString("subscription_api.body"));
        service.setTelcoAPISubscriptionBodyRaw(rs.getString("subscription_api.body_raw"));
        service.setTelcoAPISubscriptionSuccessCode(rs.getString("subscription_api.success_code"));
        service.setTelcoAPISubscriptionInsufficientAirtimeCode(rs.getString("subscription_api.insufficient_airtime_code"));
        service.setTelcoAPISubscriptionResponseCodeMatchType(rs.getString("subscription_api.response_code_match_type"));
        service.setTelcoAPISubscriptionResponseCodeMatchValue(rs.getString("subscription_api.response_code_match_type_value"));
        service.setTelcoAPISubscriptionResponseDescriptionMatchType(rs.getString("subscription_api.response_description_match_type"));
        service.setTelcoAPISubscriptionResponseDescriptionMatchValue(rs.getString("subscription_api.response_description_match_type_value"));
        service.setTelcoAPISubscriptionResponseTransactionIDMatchType(rs.getString("subscription_api.response_transaction_id_match_type"));
        service.setTelcoAPISubscriptionResponseTransactionIDMatchValue(rs.getString("subscription_api.response_transaction_id_match_type_value"));
        service.setTelcoAPISubscriptionBills(rs.getBoolean("subscription_api.bills"));
        service.setTelcoAPIUnsubscriptionID(rs.getInt("unsubscription_api.id"));
        service.setTelcoAPIUnsubscriptionName(rs.getString("unsubscription_api.name"));
        service.setTelcoAPIUnsubscriptionURL(rs.getString("unsubscription_api.url"));
        service.setTelcoAPIUnsubscriptionType(rs.getInt("unsubscription_api.type"));
        service.setTelcoAPIUnsubscriptionMethod(rs.getString("unsubscription_api.method"));
        service.setTelcoAPIUnsubscriptionBodyType(rs.getString("unsubscription_api.body_type"));
        service.setTelcoAPIUnsubscriptionHeader(rs.getString("unsubscription_api.header"));
        service.setTelcoAPIUnsubscriptionBody(rs.getString("unsubscription_api.body"));
        service.setTelcoAPIUnsubscriptionBodyRaw(rs.getString("unsubscription_api.body_raw"));
        service.setTelcoAPIUnsubscriptionSuccessCode(rs.getString("unsubscription_api.success_code"));
        service.setTelcoAPIUnsubscriptionInsufficientAirtimeCode(rs.getString("unsubscription_api.insufficient_airtime_code"));
        service.setTelcoAPIUnsubscriptionResponseCodeMatchType(rs.getString("unsubscription_api.response_code_match_type"));
        service.setTelcoAPIUnsubscriptionResponseCodeMatchValue(rs.getString("unsubscription_api.response_code_match_type_value"));
        service.setTelcoAPIUnsubscriptionResponseDescriptionMatchType(rs.getString("unsubscription_api.response_description_match_type"));
        service.setTelcoAPIUnsubscriptionResponseDescriptionMatchValue(rs.getString("unsubscription_api.response_description_match_type_value"));
        service.setTelcoAPIUnsubscriptionResponseTransactionIDMatchType(rs.getString("unsubscription_api.response_transaction_id_match_type"));
        service.setTelcoAPIUnsubscriptionResponseTransactionIDMatchValue(rs.getString("unsubscription_api.response_transaction_id_match_type_value"));
        service.setTelcoAPIUnsubscriptionBills(rs.getBoolean("unsubscription_api.bills"));
        service.setTelcoAPIContentID(rs.getInt("content_api.id"));
        service.setTelcoAPIContentName(rs.getString("content_api.name"));
        service.setTelcoAPIContentURL(rs.getString("content_api.url"));
        service.setTelcoAPIContentType(rs.getInt("content_api.type"));
        service.setTelcoAPIContentMethod(rs.getString("content_api.method"));
        service.setTelcoAPIContentBodyType(rs.getString("content_api.body_type"));
        service.setTelcoAPIContentHeader(rs.getString("content_api.header"));
        service.setTelcoAPIContentBody(rs.getString("content_api.body"));
        service.setTelcoAPIContentBodyRaw(rs.getString("content_api.body_raw"));
        service.setTelcoAPIContentSuccessCode(rs.getString("content_api.success_code"));
        service.setTelcoAPIContentInsufficientAirtimeCode(rs.getString("content_api.insufficient_airtime_code"));
        service.setTelcoAPIContentResponseCodeMatchType(rs.getString("content_api.response_code_match_type"));
        service.setTelcoAPIContentResponseCodeMatchValue(rs.getString("content_api.response_code_match_type_value"));
        service.setTelcoAPIContentResponseDescriptionMatchType(rs.getString("content_api.response_description_match_type"));
        service.setTelcoAPIContentResponseDescriptionMatchValue(rs.getString("content_api.response_description_match_type_value"));
        service.setTelcoAPIContentResponseTransactionIDMatchType(rs.getString("content_api.response_transaction_id_match_type"));
        service.setTelcoAPIContentResponseTransactionIDMatchValue(rs.getString("content_api.response_transaction_id_match_type_value"));
        service.setTelcoAPIContentBills(rs.getBoolean("content_api.bills"));

        return service;
    }

    public static Subscriber setSubscriberFromResultSet(ResultSet rs) throws SQLException {
        Subscriber subscriber = new Subscriber();
        subscriber.setID(rs.getInt("id"));
        subscriber.setMsisdn(rs.getString("msisdn"));
        subscriber.setServiceID(rs.getInt("service_id"));
        subscriber.setLastBilled(rs.getString("last_billed"));
        subscriber.setSubscriptionExpiry(rs.getString("subscription_expiry"));
        subscriber.setLastContentSent(rs.getString("last_content_sent_at"));
        subscriber.setOptInAt(rs.getString("opt_in_at"));
        subscriber.setOptOutAt(rs.getString("opt_out_at"));
        subscriber.setCreatedAt(rs.getString("created_at"));
        subscriber.setUpdatedAt(rs.getString("updated_at"));
        subscriber.setDelete(rs.getBoolean("delete"));
        subscriber.setDeletedAt(rs.getString("deleted_at"));
        subscriber.setStatus(rs.getInt("status"));

        return subscriber;
    }


    public static SMPPBind setSMPPFromResultSet(ResultSet rs) throws SQLException {
        SMPPBind smppBind = new SMPPBind();
        smppBind.setID(rs.getInt("id"));
        smppBind.setIp(rs.getString("ip"));
        smppBind.setPort(rs.getInt("port"));
        smppBind.setTps(rs.getInt("tps"));
        smppBind.setMaxConnections(rs.getInt("max_conn"));
        smppBind.setType(rs.getInt("type"));
        smppBind.setAddressTON(rs.getInt("address_ton"));
        smppBind.setAddressNPI(rs.getInt("address_npi"));
        smppBind.setSourceAddressTON(rs.getInt("source_address_ton"));
        smppBind.setSourceAddressNPI(rs.getInt("source_address_npi"));
        smppBind.setDestinationAddressTON(rs.getInt("destination_address_ton"));
        smppBind.setDestinationAddressNPI(rs.getInt("destination_address_npi"));
        smppBind.setAccountName(rs.getString("account_name"));
        smppBind.setAccountPassword(rs.getString("account_password"));
        smppBind.setSystemType(rs.getString("system_type"));
        smppBind.setTelcoName(rs.getString("telco_name"));
        smppBind.setTelcoLogo(rs.getString("telco_logo"));

        return smppBind;
    }
}
