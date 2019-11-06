package com.teoryul.newsly.persistence.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.teoryul.newsly.adapter.diffutil.RecyclerViewItemMarker;

@Entity(tableName = "news_sources_table")
public class SourcePersist
        implements Parcelable, RecyclerViewItemMarker {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String language;

    @NonNull
    private String country;

    private boolean isFavorite;

    public SourcePersist(String id, String name, String language, String country, boolean isFavorite) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.country = country;
        this.isFavorite = isFavorite;
    }

    @Ignore
    public SourcePersist(String id, String name, String language, String country) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.country = country;
        this.isFavorite = false;
    }

    @Ignore
    private SourcePersist(Parcel parcel) {
        this.id = parcel.readString();
        this.name = parcel.readString();
        this.language = parcel.readString();
        this.country = parcel.readString();
        this.isFavorite = parcel.readByte() != 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result ^ id.hashCode();
        result = result ^ name.hashCode();
        result = result ^ language.hashCode();
        result = result ^ country.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof SourcePersist)) {
            return false;
        }

        return this.id.equalsIgnoreCase(((SourcePersist) other).getId())
                && this.name.equalsIgnoreCase(((SourcePersist) other).getName())
                && this.language.equalsIgnoreCase(((SourcePersist) other).getLanguage())
                && this.country.equalsIgnoreCase(((SourcePersist) other).getCountry());
    }

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.language);
        parcel.writeString(this.country);
        parcel.writeByte((byte) (this.isFavorite ? 1 : 0));
    }

    @Ignore
    public static final Parcelable.Creator<SourcePersist> CREATOR = new Parcelable.Creator<SourcePersist>() {

        @Override
        public SourcePersist createFromParcel(Parcel source) {
            return new SourcePersist(source);
        }

        @Override
        public SourcePersist[] newArray(int size) {
            return new SourcePersist[0];
        }
    };

    @Override
    public int getUniqueId() {
        return id.hashCode();
    }
}
