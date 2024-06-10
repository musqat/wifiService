package dao;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BookMarkDao {
    private int id;
    private String wifiId;
    private String groupId;
    private Date regDate;
    private String groupName; 

    public BookMarkDao() {
    }

    public BookMarkDao(int id, String wifiId, String groupId, Date regDate) {
        this.id = id;
        this.wifiId = wifiId;
        this.groupId = groupId;
        this.regDate = regDate;
    }
}
