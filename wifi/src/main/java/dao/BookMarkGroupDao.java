package dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookMarkGroupDao {
    private int id;
    private String groupName;
    private int displayOrder;
    private Date regDate;
    private Date modDate;
    
	public BookMarkGroupDao(int id, String groupName, int displayOrder, Date regDate, Date modDate) {
		this.id = id;
		this.groupName = groupName;
		this.displayOrder = displayOrder;
		this.regDate = regDate;
		this.modDate = modDate;
	}


}
