package com.maba.osads.controllers;

import com.maba.osads.persistence.Campaign;
import com.maba.osads.persistence.CampaignRepository;
import com.maba.osads.services.CampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/v0")
public class CampaignController {

    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    @Autowired
    private CampaignRepository repository;
    @Autowired
    private CampaignService campaignService;

    @PostMapping("/advertise/create")
    public ResponseEntity<Campaign> createCampaign(@RequestParam String name, @RequestParam Date start_date, @RequestParam List<String> productIds, @RequestParam Long bid){
        if (name == null || start_date == null || productIds == null || bid == null){
            ResponseEntity.badRequest().body("Either name, startDate, bid and productIds list must be provided");
        }
        if (productIds == null || productIds.isEmpty()){
            logger.warn("Are you sure you want to create campaign with no products specified?!?!");
        }
        Campaign campaign = campaignService.create(name, start_date, productIds, bid);

        return ResponseEntity.ok(campaign);
    }

    @GetMapping("/advertise/campaigns")
    public ResponseEntity<List<Campaign>> serveAds(@RequestParam String category){
        return null;
    }
}
;