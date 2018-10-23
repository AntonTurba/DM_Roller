import java.util.Comparator;
import java.util.List;

public class Campaign {
	
	private String campaignName;

	private List<Encounter> campaignEncounters;
	
	public Campaign(String name, List<Encounter> encounters) {
		setCampaignName(name);
		this.campaignEncounters = encounters;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}
	
	public void addEncounter(Encounter e) {
		this.campaignEncounters.add(e);
		
		sortEncounters();
	}
	
	public void sortEncounters() {
		campaignEncounters.sort(Comparator.comparing(Encounter::getEncounterName));
	}
}
