package og.basics.gui.about;

import javax.swing.ImageIcon;

/**
 * 
 * Datenklasse zum Verwalten/Speichern von VersionsDetails
 * 
 * @author Oliver
 * 
 */
public class VersionDetails {
	private String version = "";
	private String copyright = "";
	private ImageIcon logo = null;
	private String company = "";
	private String date = "";
	private String applicationname = "";
	private String description = "";

	public VersionDetails() {

	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public ImageIcon getLogo() {
		return logo;
	}

	public void setLogo(ImageIcon logo) {
		this.logo = logo;
	}

	public String getApplicationname() {
		return applicationname;
	}

	public void setApplicationname(String applicationname) {
		this.applicationname = applicationname;
	}

	/**
	 * The description can be a long multiline String. Use the html-tag "br" for
	 * new lines!
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

}
