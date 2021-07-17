package com.maba.osads.services;


import com.maba.osads.helper.IdsHelper;
import com.maba.osads.persistence.Campaign;
import com.maba.osads.persistence.CampaignRepository;
import com.maba.osads.persistence.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CampaignService {

    private final int CAMPAIGN_ACTIVE_FOR_DAYS = 10;

    @Autowired
    private ProductService productService;

    @Autowired
    private CampaignRepository campaignRepository;

    public Campaign create(String name, Date startDate, List<String> productIdsToPromote, Long bid){
        Campaign campaign = new Campaign();
        campaign.setName(name);
        campaign.setStartDate(startDate);
        campaign.setProductIds(IdsHelper.mapListToCommaSeparatedIds(productIdsToPromote));
        campaign.setBid(bid);

        return campaign;
    }

    public Product findHighestBidCampaignProduct(String category) {
        List<Campaign> sortedByBidCampaigns = campaignRepository.findAll().stream() //all campaigns stream
                .filter(this::isActiveCampaign) // filter active campaigns
                .sorted(Comparator.comparingLong(Campaign::getBid)) // Sort by bid
                .collect(Collectors.toList());
        for (int i=0 ; i < sortedByBidCampaigns.size(); i++){
            Product product = productService.getProductByCategory(sortedByBidCampaigns.get(i), category);
            if (product != null)
                return product;
        }
        return productService.getAnyProductFromCampaign(sortedByBidCampaigns.get(0).getProductIds());
    }

    //I would use some scheduler to make campaign non active (using kafka/rabbitmq etc...)
    private boolean isActiveCampaign(Campaign campaign){
        Date nowDate = Calendar.getInstance().getTime();
        Long timeDifference = nowDate.getTime() - campaign.getStartDate().getTime();
        Long daysDifference = (timeDifference
                / (1000 * 60 * 60 * 24))
                % 365;
        return daysDifference < CAMPAIGN_ACTIVE_FOR_DAYS;
    }
}
