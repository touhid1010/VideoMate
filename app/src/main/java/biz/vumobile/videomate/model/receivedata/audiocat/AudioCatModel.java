
package biz.vumobile.videomate.model.receivedata.audiocat;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AudioCatModel {

    @SerializedName("category")
    @Expose
    private List<Category> category = null;

    public List<Category> getCategory() {
        return category;
    }

    public void setCategory(List<Category> category) {
        this.category = category;
    }

}
