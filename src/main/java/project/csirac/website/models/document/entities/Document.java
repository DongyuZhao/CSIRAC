package project.csirac.website.models.document.entities;

import java.util.Date;

/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */


public class Document {
    private String title;

    private String body;

    private Date date;

    public Document() {

    }

    public Document(String title, String body, Date date) {
        this.title = title;
        this.body = body;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
