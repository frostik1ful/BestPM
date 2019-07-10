package juke.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "offices")
public class Office implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8373580386536924367L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "office_id")
	private Integer id;

	@Column(name = "office_name")
	private String officeName;

	@Column(name = "count_programmers")
	private Integer countProgrammers;

	@Column(name = "office_level")
	private Integer level;

	@Column(name = "office_price")
	private Integer price;
	
	@Column(name = "office_price_donate")
	private Integer priceDonate;

	@Column(name = "image_url")
	private String imageURL;

	@OneToMany(mappedBy = "office")
	private List<User> users = new ArrayList<>(10);

	public Office() {
	}

	public Integer getId() {
		return id;
	}

	public String getOfficeName() {
		return officeName;
	}

	public Integer getCountProgrammers() {
		return countProgrammers;
	}

	public Integer getLevel() {
		return level;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public void setCountProgrammers(Integer countProgrammers) {
		this.countProgrammers = countProgrammers;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Integer getPriceDonate() {
		return priceDonate;
	}

	public void setPriceDonate(Integer priceDonate) {
		this.priceDonate = priceDonate;
	}

}
