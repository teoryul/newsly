package com.teoryul.newsly.persistence.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.teoryul.newsly.adapter.item.NewsFeedMultiItem;
import com.teoryul.newsly.adapter.viewholder.NewsFeedViewHolderTypes;

@Entity(tableName = "news_articles_table",
        foreignKeys = @ForeignKey(entity = NewsFeedPersist.class,
                parentColumns = "news_feed_title",
                childColumns = "parent_news_feed_title",
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE),
        indices = {@Index(value = {"parent_news_feed_title"})})
public class ArticlePersist extends NewsFeedMultiItem
        implements Parcelable, Comparable<ArticlePersist> {

    @PrimaryKey
    @ColumnInfo(name = "news_article_id")
    private int newsArticleId;

    @ColumnInfo(name = "parent_news_feed_title")
    private String parentNewsFeedTitle;

    /**
     * Stores the news article source (ie. Bild.de, NY Times).
     */
    @NonNull
    @ColumnInfo(name = "source_name")
    private String sourceName;

    @NonNull
    @ColumnInfo(name = "news_title")
    private String newsTitle;

    @NonNull
    @ColumnInfo(name = "web_url")
    private String webUrl;

    @ColumnInfo(name = "img_url")
    private String imgUrl;

    @ColumnInfo(name = "published_at")
    private long publishedAt;

    @ColumnInfo(name = "is_bookmarked")
    private boolean isBookmarked;

    public ArticlePersist(int newsArticleId, String parentNewsFeedTitle, String sourceName, String newsTitle, String webUrl, String imgUrl, long publishedAt, boolean isBookmarked) {
        this.newsArticleId = newsArticleId;
        this.parentNewsFeedTitle = parentNewsFeedTitle;
        this.sourceName = sourceName;
        this.newsTitle = newsTitle;
        this.webUrl = webUrl;
        this.imgUrl = imgUrl;
        this.publishedAt = publishedAt;
        this.isBookmarked = isBookmarked;
    }

    @Ignore
    public ArticlePersist(String sourceName, String newsTitle, String webUrl, String imgUrl, long publishedAt) {
        this.sourceName = sourceName;
        this.newsTitle = newsTitle;
        this.webUrl = webUrl;
        this.imgUrl = imgUrl;
        this.publishedAt = publishedAt;
        this.isBookmarked = false;
    }

    @Ignore
    private ArticlePersist(Parcel parcel) {
        this.newsArticleId = parcel.readInt();
        this.parentNewsFeedTitle = parcel.readString();
        this.sourceName = parcel.readString();
        this.newsTitle = parcel.readString();
        this.webUrl = parcel.readString();
        this.imgUrl = parcel.readString();
        this.publishedAt = parcel.readLong();
        this.isBookmarked = parcel.readByte() != 0;
    }

    /**
     * Must be called if the ignored constructor is used to instantiate this object.
     *
     * @param parentNewsFeedTitle
     */
    @Ignore
    public void setIds(String parentNewsFeedTitle) {
        this.parentNewsFeedTitle = parentNewsFeedTitle;
        this.newsArticleId = calculateNewsArticleIdHashCode(parentNewsFeedTitle, webUrl);
    }

    @Ignore
    private int calculateNewsArticleIdHashCode(String parentNewsFeedTitle, String webUrl) {
        int result = 1;
        result = 7 ^ result ^ parentNewsFeedTitle.hashCode();
        result = 7 ^ result ^ webUrl.hashCode();
        return result;
    }

    public int getNewsArticleId() {
        return newsArticleId;
    }

    public String getParentNewsFeedTitle() {
        return parentNewsFeedTitle;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getImgUrl() {
        return imgUrl == null ? "" : imgUrl;
    }

    public long getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(long publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        isBookmarked = bookmarked;
    }

    /**
     * {@link #parentNewsFeedTitle} and {@link #webUrl} are not used for the calculations
     * because they have already been calculated in {@link #newsArticleId}.
     * See {@link #calculateNewsArticleIdHashCode(String, String)}.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int result = 1;
        result = result ^ newsArticleId;
        result = result ^ sourceName.hashCode();
        result = result ^ newsTitle.hashCode();
        result = result ^ imgUrl.hashCode();
        return result;
    }

    /**
     * {@link #parentNewsFeedTitle} and {@link #webUrl} are not checked
     * because they have already been calculated in {@link #newsArticleId}.
     * See {@link #calculateNewsArticleIdHashCode(String, String)}.
     *
     * @param other
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (!(other instanceof ArticlePersist)) return false;

        return this.newsArticleId == ((ArticlePersist) other).getNewsArticleId()
                && this.sourceName.equals(((ArticlePersist) other).getSourceName())
                && this.newsTitle.equals(((ArticlePersist) other).getNewsTitle())
                && this.imgUrl.equals(((ArticlePersist) other).getImgUrl());
    }

    @Ignore
    @Override
    public int getViewType() {
        return NewsFeedViewHolderTypes.ARTICLE;
    }

    @Ignore
    @Override
    public int describeContents() {
        return hashCode();
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.newsArticleId);
        parcel.writeString(this.parentNewsFeedTitle);
        parcel.writeString(this.sourceName);
        parcel.writeString(this.newsTitle);
        parcel.writeString(this.webUrl);
        parcel.writeString(this.imgUrl);
        parcel.writeLong(this.publishedAt);
        parcel.writeByte((byte) (this.isBookmarked ? 1 : 0));
    }

    @Ignore
    public static final Parcelable.Creator<ArticlePersist> CREATOR = new Parcelable.Creator<ArticlePersist>() {

        @Override
        public ArticlePersist createFromParcel(Parcel source) {
            return new ArticlePersist(source);
        }

        @Override
        public ArticlePersist[] newArray(int size) {
            return new ArticlePersist[size];
        }
    };

    @Override
    public int getUniqueId() {
        return newsArticleId;
    }

    @Override
    public int compareTo(ArticlePersist other) {
        return Long.compare(other.getPublishedAt(), publishedAt);
    }
}
