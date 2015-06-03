package info.androidhive.proclubDaiict.service;

/**
 * Created by NEHAL on 5/28/2015.
 */
public class Blog {
    private int id;
    private String usrername;
    private String title;
    private String comment;
    private String content;
    private String tag;

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public String getUsrername() {
        return usrername;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUsrername(String usrername) {
        this.usrername = usrername;
    }
}
