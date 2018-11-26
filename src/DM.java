import java.util.ArrayList;
import java.util.List;

public class DM {
	
	private Integer dmId;
	
	private String dmName;
	
	private List<Campaign> campaigns;
	
	public DM(String name, int id) {
		this.dmName = name;
		this.setDmId(id);
		campaigns = new ArrayList<Campaign>();
	}
	
	public void addCampaign(Campaign c) {
		campaigns.add(c);
	}

	public List<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(List<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

	public Integer getDmId() {
		return dmId;
	}

	public void setDmId(Integer dmId) {
		this.dmId = dmId;
	}
}
