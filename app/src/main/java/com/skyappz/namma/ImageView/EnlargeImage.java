package com.skyappz.namma.ImageView;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Gopi on 4/5/2016.
 */
public class EnlargeImage implements Parcelable {

    public EnlargeImage() {
    }

    String image;

    protected EnlargeImage(Parcel in) {
        image = in.readString();
    }

    public static final Creator<EnlargeImage> CREATOR = new Creator<EnlargeImage>() {
        @Override
        public EnlargeImage createFromParcel(Parcel in) {
            return new EnlargeImage(in);
        }

        @Override
        public EnlargeImage[] newArray(int size) {
            return new EnlargeImage[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return getImage();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EnlargeImage))
            return false;

        return image.equals(((EnlargeImage) obj).getImage());
    }

    @Override
    public int hashCode() {
        return (image == null) ? 0 : image.hashCode();
    }

}
