package mx.longbit.hackatonproject.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import mx.longbit.hackatonproject.R;
import mx.longbit.hackatonproject.pojos.CustomerCredit;
import mx.longbit.hackatonproject.retrofit.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static retrofit2.converter.gson.GsonConverterFactory.*;

public class SmartFragment extends Fragment {

    PieChart pieChart;

    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    int oro, az, efect, libre =0;


    public SmartFragment() {
        // Required empty public constructor
    }

    public static SmartFragment newInstance(String param1) {
        SmartFragment fragment = new SmartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    PieEntry p1,p2,p3,p4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_smart, container, false);

        pieChart = v.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        //pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.65f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(30f);

//https://us-central1-moneyzhackgs.cloudfunctions.net/smartfolio


        loadJSON();



        SeekBar sbOro = v.findViewById(R.id.credit_oro);

        sbOro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar sbelek = v.findViewById(R.id.credit_elektra);

        sbelek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar sbOtro = v.findViewById(R.id.credit_otro);

        sbOtro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 3);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        SeekBar sbAva= v.findViewById(R.id.credit_available);

        sbAva.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 4);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TextView tvSave = v.findViewById(R.id.guardarCambios);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Listo",Toast.LENGTH_LONG).show();
            }
        });

        return v;
    }



    private void setData(int count, float range, int op) {
        ArrayList<PieEntry> val = new ArrayList<>();
        switch (op){
            case 1:
                oro = count;
                break;
            case 2:
                az = count;
                break;
            case 3:
                efect = count;
                break;
            case 4:
                libre = count;
                break;
        }

        p1 = new PieEntry(oro,"TDC Oro");
        p2 = new PieEntry(az,"TDC Oro VAS");
        p3 = new PieEntry(efect,"Crédito de Nómina");
        p4 = new PieEntry(libre,"Disponible ");
        val.add(p1);
        val.add(p2);
        val.add(p3);
        val.add(p4);



        PieDataSet dataSet = new PieDataSet(val,"items");
        dataSet.setSliceSpace(4f);
        //dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
    }


    private void loadJSON(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-moneyzhackgs.cloudfunctions.net/")
                .addConverterFactory(create(gson))
                .build();

        RestClient restClient = retrofit.create(RestClient.class);
        Call<CustomerCredit> call = restClient.getData();

        call.enqueue(new Callback<CustomerCredit>() {
            @Override
            public void onResponse(Call<CustomerCredit> call, Response<CustomerCredit> response) {
                switch (response.code()) {
                    case 200:
                        CustomerCredit data = response.body();
                        System.out.println(data);

                        break;
                    case 401:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<CustomerCredit> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

}
