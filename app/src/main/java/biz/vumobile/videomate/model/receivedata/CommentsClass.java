package biz.vumobile.videomate.model.receivedata;

/**
 * Created by toukirul on 11/1/2018.
 */

public class CommentsClass {

    private String user_name;
    private String user_comment;
    private String date_time;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }
}
