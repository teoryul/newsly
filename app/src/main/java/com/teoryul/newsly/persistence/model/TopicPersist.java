package com.teoryul.newsly.persistence.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.teoryul.newsly.adapter.diffutil.RecyclerViewItemMarker;
import com.teoryul.newsly.utils.DateUtil;

/**
 * Model class for a favorite news topic, search result (Bitcoin, Apple, Microsoft).
 */
@Entity(tableName = "favorite_topics_table")
public class TopicPersist
        implements Parcelable, RecyclerViewItemMarker {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "topic_name")
    private String topicName;

    @ColumnInfo(name = "added_at")
    private long addedAt;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    public TopicPersist(String topicName, boolean isFavorite, long addedAt) {
        this.topicName = topicName;
        this.addedAt = addedAt;
        this.isFavorite = isFavorite;
    }

    @Ignore
    public TopicPersist(String topicName) {
        this.topicName = topicName;
        this.addedAt = DateUtil.getCurrentTimeAsMillis();
        this.isFavorite = false;
    }

    @Ignore
    private TopicPersist(Parcel parcel) {
        this.topicName = parcel.readString();
        this.addedAt = parcel.readLong();
        this.isFavorite = parcel.readByte() != 0;
    }

    public String getTopicName() {
        return topicName;
    }

    public long getAddedAt() {
        return addedAt;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int hashCode() {
        return topicName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (!(other instanceof TopicPersist)) return false;

        return this.topicName.equalsIgnoreCase(((TopicPersist) other).getTopicName());
    }

    @Ignore
    @Override
    public int describeContents() {
        return hashCode();
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.topicName);
        parcel.writeLong(this.addedAt);
        parcel.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    @Ignore
    public static final Parcelable.Creator<TopicPersist> CREATOR = new Parcelable.Creator<TopicPersist>() {

        @Override
        public TopicPersist createFromParcel(Parcel source) {
            return new TopicPersist(source);
        }

        @Override
        public TopicPersist[] newArray(int size) {
            return new TopicPersist[size];
        }
    };

    @Override
    public int getUniqueId() {
        return topicName.hashCode();
    }
}
