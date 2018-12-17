package moka.mokapos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShoppingItem {

    @SerializedName("albunId")
    @Expose
    int albumId;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("thumbnailUrl")
    @Expose
    String thumbNailUrl;

    public int getAlbumId() {
        return albumId;
    }

    public int getId() {
        return id;
    }

    public String getThumbNailUrl() {
        return thumbNailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getDisplayTitle(){
        return String.format("%s "+ "%03d", "Item ", id);

    }

    public String getUrl() {
        return url;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setThumbNailUrl(String thumbNailUrl) {
        this.thumbNailUrl = thumbNailUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(" id - " + id);
        builder.append(" albumId - " + albumId);
        builder.append(" title - " + title);
        builder.append(" url - " + url);
        builder.append(" thumbnai - " + thumbNailUrl);
        return builder.toString();
    }
}
