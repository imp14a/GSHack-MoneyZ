
package mx.longbit.hackatonproject.pojos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmartObject {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("profile")
    @Expose
    private Profile profile;
    @SerializedName("customerCredits")
    @Expose
    private List<CustomerCredit> customerCredits = null;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<CustomerCredit> getCustomerCredits() {
        return customerCredits;
    }

    public void setCustomerCredits(List<CustomerCredit> customerCredits) {
        this.customerCredits = customerCredits;
    }

}
