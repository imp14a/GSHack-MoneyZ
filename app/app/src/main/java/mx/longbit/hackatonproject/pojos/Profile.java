
package mx.longbit.hackatonproject.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Profile {

    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("billingCapacity")
    @Expose
    private Integer billingCapacity;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getBillingCapacity() {
        return billingCapacity;
    }

    public void setBillingCapacity(Integer billingCapacity) {
        this.billingCapacity = billingCapacity;
    }

}
