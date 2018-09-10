package bibliotech4android.com.bibliotechforandroid;

import android.content.Context;

public class Book {
    private Integer id;
    private String title;
    private String publisher;
    private String isbnNo;
    private Integer issueYear;
    private Integer authorid;
    private String isLent;
    private String whoLent;
    private String tags;
    private String notes;
    private String localization;


    public Book(String title, String publisher, Integer issueYear,String isbnNo, Integer authorid, String tags, String notes,String isLent,
                String whoLent, String localization) {
        this.title = title;
        this.publisher = publisher;
        this.isbnNo = isbnNo;
        this.issueYear = issueYear;
        this.authorid = authorid;
        this.tags = tags;
        this.notes = notes;
        this.isLent = isLent;
        this.whoLent = whoLent;
        this.localization = localization;
    }

    public Book(Integer id, String title, String publisher, Integer issueYear, String isbnNo, Integer authorid,  String tags, String notes,
                String isLent, String whoLent, String localization) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.isbnNo = isbnNo;
        this.issueYear = issueYear;
        this.authorid = authorid;
        this.isLent = isLent;
        this.whoLent = whoLent;
        this.tags = tags;
        this.notes = notes;
        this.localization = localization;
    }

    public String toString(Context context) {
        ConnectorForAuthors cfa = new ConnectorForAuthors(context);
        return title +", "+ cfa.searchAuthorById(authorid).toString()+", "+publisher+", "+issueYear;
    }

    public Integer getId() { return id; }

    public String getTitle() {
        return title;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbnNo() {
        return isbnNo;
    }

    public Integer getIssueYear() {
        return issueYear;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public String getWhoLent() {
        return whoLent;
    }

    public String getIsLent(){ return isLent; }

    public String getTags() {
        return tags;
    }

    public String getNotes() {
        return notes;
    }

    public String getLocalization() {
        return localization;
    }
}
