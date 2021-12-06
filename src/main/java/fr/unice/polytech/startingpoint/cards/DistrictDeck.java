package fr.unice.polytech.startingpoint.cards;

import java.util.ArrayList;
import java.util.List;

public class DistrictDeck {
    /**
     * Includes all the colors of the different quartoers
     */
    private List<IDistrict> districtList;

    /**
     * Constructor
     * @param districtList
     */
    public DistrictDeck(List<IDistrict> districtList) {
        this.districtList = districtList;
    }

    /**
     * Send n district
     * @param lengthOfTheListToReturn
     * @return
     */
    public List<IDistrict> giveDistrict(int lengthOfTheListToReturn){
        List<IDistrict> districtProposal = new ArrayList<IDistrict>();
        if(this.districtList.size() == 0 || lengthOfTheListToReturn < 0)
            return districtProposal;


        if(lengthOfTheListToReturn >= this.districtList.size())
            lengthOfTheListToReturn = this.districtList.size();

        for(int i = 0; i < lengthOfTheListToReturn; i++) {
            districtProposal.add(this.districtList.get(i));
        }

        for(int i = 0; i < lengthOfTheListToReturn; i++) {
            this.districtList.remove(0);
        }

        return districtProposal;
    }

    /**
     * Returns the size of the district list
     * @return
     */
    public int getDeckSize(){
        return this.districtList.size();
    }
    public void addDistricts(List<IDistrict> list){
        districtList.addAll(list);
    }

    public List<IDistrict> getDistrictList() {
        return districtList;
    }
}
