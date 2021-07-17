package com.maba.osads.services;


import com.maba.osads.helper.ConstantsHelper;
import com.maba.osads.persistence.Campaign;

import java.util.Date;
import java.util.List;

public class CampaignService {

    public Campaign create(String name, Date startDate, List<String> productIdsToPromote, Long bid){
        Campaign campaign = new Campaign();
        campaign.setName(name);
        campaign.setStartDate(startDate);
        campaign.setProductIds(mapListToCommaSeparatedIds(productIdsToPromote));
        campaign.setBid(bid);

        return campaign;
    }

    private String mapListToCommaSeparatedIds(List<String> productIdsToPromote) {
        String ids = "";

        for (int i=0; i < productIdsToPromote.size(); i++){
            if (i == productIdsToPromote.size()-1){
                ids.concat(productIdsToPromote.get(i));
            }
            else {
                ids.concat(productIdsToPromote.get(i) + ConstantsHelper.COMMA);
            }
        }
        return ids;
    }
}
