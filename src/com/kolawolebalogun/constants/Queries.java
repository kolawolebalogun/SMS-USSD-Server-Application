package com.kolawolebalogun.constants;

/**
 * Created by KolawoleBalogun on 7/19/17.
 */
public class Queries {
    public final static  String SERVICE = " " +
            " SELECT `services`.*, " +
            " `opt_in_shortcode`.`shortcode`, `opt_in_shortcode`.`tariff`, `opt_in_shortcode`.`smpp_bind`, " +
            " `opt_out_shortcode`.`shortcode`, `opt_out_shortcode`.`tariff`, `opt_out_shortcode`.`smpp_bind`, " +
            " `billing_shortcode`.`shortcode`, `billing_shortcode`.`tariff`, `billing_shortcode`.`smpp_bind`, " +
            " `confirmation_shortcode`.`shortcode`, `confirmation_shortcode`.`tariff`, `confirmation_shortcode`.`smpp_bind`, " +
            " `help_shortcode`.`shortcode`, `help_shortcode`.`tariff`, `help_shortcode`.`smpp_bind`, " +
            " `content_shortcode`.`shortcode`, `content_shortcode`.`tariff`, `content_shortcode`.`smpp_bind`, " +
            " `billing_api`.`id`, `billing_api`.`name`, `billing_api`.`url`, `billing_api`.`type`, `billing_api`.`method`, `billing_api`.`body_type`, `billing_api`.`header`, `billing_api`.`body`, `billing_api`.`body_raw`, `billing_api`.`success_code`, `billing_api`.`insufficient_airtime_code`, `billing_api`.`response_code_match_type`, `billing_api`.`response_code_match_type_value`, `billing_api`.`response_description_match_type`, `billing_api`.`response_description_match_type_value`, `billing_api`.`response_transaction_id_match_type`, `billing_api`.`response_transaction_id_match_type_value`, `billing_api`.`bills`, " +
            " `subscription_api`.`id`, `subscription_api`.`name`, `subscription_api`.`url`, `subscription_api`.`type`, `subscription_api`.`method`, `subscription_api`.`body_type`, `subscription_api`.`header`, `subscription_api`.`body`, `subscription_api`.`body_raw`, `subscription_api`.`success_code`, `subscription_api`.`insufficient_airtime_code`, `subscription_api`.`response_code_match_type`, `subscription_api`.`response_code_match_type_value`, `subscription_api`.`response_description_match_type`, `subscription_api`.`response_description_match_type_value`, `subscription_api`.`response_transaction_id_match_type`, `subscription_api`.`response_transaction_id_match_type_value`, `subscription_api`.`bills`, " +
            " `unsubscription_api`.`id`, `unsubscription_api`.`name`, `unsubscription_api`.`url`, `unsubscription_api`.`type`, `unsubscription_api`.`method`, `unsubscription_api`.`body_type`, `unsubscription_api`.`header`, `unsubscription_api`.`body`, `unsubscription_api`.`body_raw`, `unsubscription_api`.`success_code`, `unsubscription_api`.`insufficient_airtime_code`, `unsubscription_api`.`response_code_match_type`, `unsubscription_api`.`response_code_match_type_value`, `unsubscription_api`.`response_description_match_type`, `unsubscription_api`.`response_description_match_type_value`, `unsubscription_api`.`response_transaction_id_match_type`, `unsubscription_api`.`response_transaction_id_match_type_value`, `unsubscription_api`.`bills`, " +
            " `content_api`.`id`, `content_api`.`name`, `content_api`.`url`, `content_api`.`type`, `content_api`.`method`, `content_api`.`body_type`, `content_api`.`header`, `content_api`.`body`, `content_api`.`body_raw`, `content_api`.`success_code`, `content_api`.`insufficient_airtime_code`, `content_api`.`response_code_match_type`, `content_api`.`response_code_match_type_value`, `content_api`.`response_description_match_type`, `content_api`.`response_description_match_type_value`, `content_api`.`response_transaction_id_match_type`, `content_api`.`response_transaction_id_match_type_value`, `content_api`.`bills` " +
            " FROM `services` " +
            " LEFT JOIN `shortcode` AS  `opt_in_shortcode` " +
            "   ON `opt_in_shortcode`.`id` = `services`.`opt_in_shortcode` " +
            " LEFT JOIN `shortcode` AS `opt_out_shortcode` " +
            "   ON `opt_out_shortcode`.`id` = `services`.`opt_out_shortcode` " +
            " LEFT JOIN `shortcode` AS `billing_shortcode` " +
            "   ON `billing_shortcode`.`id` = `services`.`billing_shortcode` " +
            " LEFT JOIN `shortcode` AS `confirmation_shortcode` " +
            "   ON `confirmation_shortcode`.`id` = `services`.`confirmation_shortcode` " +
            " LEFT JOIN `shortcode` AS `help_shortcode` " +
            "   ON `help_shortcode`.`id` = `services`.`help_shortcode` " +
            " LEFT JOIN `shortcode` AS `content_shortcode` " +
            "   ON `content_shortcode`.`id` = `services`.`content_shortcode` " +
            " LEFT JOIN `telco_apis` AS `billing_api` " +
            "   ON `billing_api`.`id` = `services`.`telco_api_billing` " +
            " LEFT JOIN `telco_apis` AS `subscription_api` " +
            "   ON `subscription_api`.`id` = `services`.`telco_api_subscription` " +
            " LEFT JOIN `telco_apis` AS `unsubscription_api` " +
            "   ON `unsubscription_api`.`id` = `services`.`telco_api_unsubscription` " +
            " LEFT JOIN `telco_apis` AS `content_api` " +
            "   ON `content_api`.`id` = `services`.`telco_api_content` ";
}
