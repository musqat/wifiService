package dao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WifiDao {
    private int id;  
    private String X_SWIFI_MGR_NO;
    private String X_SWIFI_WRDOFC;
    private String X_SWIFI_MAIN_NM;
    private String X_SWIFI_ADRES1;
    private String X_SWIFI_ADRES2;
    private String X_SWIFI_INSTL_FLOOR;
    private String X_SWIFI_INSTL_TY;
    private String X_SWIFI_INSTL_MBY;
    private String X_SWIFI_SVC_SE;
    private String X_SWIFI_CMCWR;
    private String X_SWIFI_CNSTC_YEAR;
    private String X_SWIFI_INOUT_DOOR;
    private String X_SWIFI_REMARS3;
    private String LAT;
    private String LNT;
    private String WORK_DTTM;
    private double distance;

    public WifiDao(String X_SWIFI_MGR_NO, String X_SWIFI_WRDOFC, String X_SWIFI_MAIN_NM, String X_SWIFI_ADRES1,
                    String X_SWIFI_ADRES2, String X_SWIFI_INSTL_FLOOR, String X_SWIFI_INSTL_TY, String X_SWIFI_INSTL_MBY,
                    String X_SWIFI_SVC_SE, String X_SWIFI_CMCWR, String X_SWIFI_CNSTC_YEAR, String X_SWIFI_INOUT_DOOR,
                    String X_SWIFI_REMARS3, String LAT, String LNT, String WORK_DTTM) {
        this.X_SWIFI_MGR_NO = X_SWIFI_MGR_NO;
        this.X_SWIFI_WRDOFC = X_SWIFI_WRDOFC;
        this.X_SWIFI_MAIN_NM = X_SWIFI_MAIN_NM;
        this.X_SWIFI_ADRES1 = X_SWIFI_ADRES1;
        this.X_SWIFI_ADRES2 = X_SWIFI_ADRES2;
        this.X_SWIFI_INSTL_FLOOR = X_SWIFI_INSTL_FLOOR;
        this.X_SWIFI_INSTL_TY = X_SWIFI_INSTL_TY;
        this.X_SWIFI_INSTL_MBY = X_SWIFI_INSTL_MBY;
        this.X_SWIFI_SVC_SE = X_SWIFI_SVC_SE;
        this.X_SWIFI_CMCWR = X_SWIFI_CMCWR;
        this.X_SWIFI_CNSTC_YEAR = X_SWIFI_CNSTC_YEAR;
        this.X_SWIFI_INOUT_DOOR = X_SWIFI_INOUT_DOOR;
        this.X_SWIFI_REMARS3 = X_SWIFI_REMARS3;
        this.LAT = LAT;
        this.LNT = LNT;
        this.WORK_DTTM = WORK_DTTM;
    }
}
