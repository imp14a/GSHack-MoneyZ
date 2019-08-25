package mx.longbit.hackatonproject.retrofit;

import mx.longbit.hackatonproject.pojos.CustomerCredit;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RestClient {
    @GET("smartfolio")
    Call<CustomerCredit> getData();
}
