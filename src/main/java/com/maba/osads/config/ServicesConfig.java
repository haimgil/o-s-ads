package com.maba.osads.config;

import com.maba.osads.services.CampaignService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {

    @Bean
    public CampaignService campaignService(){
        return new CampaignService();
    }
}
