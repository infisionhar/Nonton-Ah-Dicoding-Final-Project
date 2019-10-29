package com.hariz.noah.Model.Response;

import android.os.Parcel;
import android.os.Parcelable;

import com.hariz.noah.Model.TvModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TvResponse implements Parcelable {

    public final static Parcelable.Creator<TvResponse> CREATOR = new Creator<TvResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TvResponse createFromParcel(Parcel in) {
            return new TvResponse(in);
        }

        public TvResponse[] newArray(int size) {
            return (new TvResponse[size]);
        }

    };
    private Integer page;
    private Integer totalResults;
    private Integer totalPages;
    private List<TvModel> results = null;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    protected TvResponse(Parcel in) {
        this.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalResults = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.results, (TvModel.class.getClassLoader()));
        this.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
    }

    public TvResponse() {
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<TvModel> getResults() {
        return results;
    }

    public void setResults(List<TvModel> results) {
        this.results = results;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

}