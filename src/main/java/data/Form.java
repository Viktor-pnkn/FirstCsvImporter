package data;

import java.util.Date;

public class Form {
    private String ssoid;
    private long ts;
    private String grp;
    private String type;
    private String subtype;
    private String url;
    private String orgid;
    private String formid;
    private String code;
    private Date date;

    public Form(String ssoid, long ts, String grp, String type, String subtype, String url, String orgid, String formid, String code, Date date) {
        this.ssoid = ssoid;
        this.ts = ts;
        this.grp = grp;
        this.type = type;
        this.subtype = subtype;
        this.url = url;
        this.orgid = orgid;
        this.formid = formid;
        this.code = code;
        this.date = date; //2017-07-11-09
    }


    @Override
    public String toString() {
        return "Data{" +
                "ssoid='" + ssoid +
                ", formid='" + formid + '\'' +
                ", code='" + code + '\'' +
                ", date=" + date +
                '}' + '\n';
    }

    public String getFormid() {
        return formid;
    }

    public String getSsoid() {
        return ssoid;
    }

    public Date getDate() {
        return date;
    }

    public String getSubtype() { return subtype;}
}
