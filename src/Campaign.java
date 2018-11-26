import java.util.Comparator;
import java.util.List;

public class Campaign {
	
	private int campId;
	
	private String campaignName;

	private List<Encounter> campaignEncounters;
	
	public Campaign(int id, String name) {
		this.setCampId(id);
		setCampaignName(name);
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	public void addEncounter(Encounter e) {
		this.getCampaignEncounters().add(e);
		
		sortEncounters();
	}
	
	public void sortEncounters() {
		getCampaignEncounters().sort(Comparator.comparing(Encounter::getEncounterName));
	}

	public int getCampId() {
		return campId;
	}

	public void setCampId(int campId) {
		this.campId = campId;
	}

	public List<Encounter> getCampaignEncounters() {
		return campaignEncounters;
	}

	public void setCampaignEncounters(List<Encounter> campaignEncounters) {
		this.campaignEncounters = campaignEncounters;
	}
}
