package mx.longbit.hackatonproject.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.List;

import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;
import mx.longbit.hackatonproject.R;
import mx.longbit.hackatonproject.origination.OptionFragment;
import mx.longbit.hackatonproject.pojos.CustomerCredit;
import mx.longbit.hackatonproject.pojos.Profile;
import mx.longbit.hackatonproject.pojos.SmartObject;
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

    int oro, az, efect, libre, topOro, topAz, topEfect,topLibre =0;
    int dis =0;

    float billing, usedPorcent;


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

    SeekBar sbelek,sbOtro, sbOro, sbAva;

    TextView totalUsed, porOro, porVas, porNom, porDis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_smart, container, false);

        totalUsed = v.findViewById(R.id.textView2);
        porVas= v.findViewById(R.id.tvVas);
        porNom = v.findViewById(R.id.tvNom);
        porDis = v.findViewById(R.id.tvlibre);
        porOro= v.findViewById(R.id.tvOro);

        pieChart = v.findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);
        pieChart.setExtraOffsets(5,10,5,5);

        pieChart.setDragDecelerationFrictionCoef(0.65f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(30f);


        loadJSON();



        sbOro = v.findViewById(R.id.credit_oro);

        sbOro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 1,topOro);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

         sbelek = v.findViewById(R.id.credit_elektra);

        sbelek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 2, topAz);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

         sbOtro = v.findViewById(R.id.credit_otro);

        sbOtro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 3, topLibre);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

         sbAva= v.findViewById(R.id.credit_available);

        sbAva.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                setData(seekBar.getProgress(), 10f, 4, topLibre);
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


        Slider slider = v.findViewById(R.id.slider);

//create list of slides
        List<Slide> slideList = new ArrayList<>();
        slideList.add(new Slide(0,"https://firebasestorage.googleapis.com/v0/b/moneyzhackgs.appspot.com/o/slide01.png?alt=media&token=4270b0a6-2025-4207-9fd6-d45d7eb2a3c9" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(1,"https://firebasestorage.googleapis.com/v0/b/moneyzhackgs.appspot.com/o/slide02.png?alt=media&token=357ff06b-75b3-44dd-a08d-537157475d49" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(2,"https://firebasestorage.googleapis.com/v0/b/moneyzhackgs.appspot.com/o/slide04.png?alt=media&token=0e2c1dd7-a9cd-4b19-b061-346862b4f30f" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));
        slideList.add(new Slide(3,"https://firebasestorage.googleapis.com/v0/b/moneyzhackgs.appspot.com/o/slide05.png?alt=media&token=c49c7d68-7fb9-4bc0-b895-9a23289da73f" , getResources().getDimensionPixelSize(R.dimen.slider_image_corner)));

//handle slider click listener
        slider.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fragment fragment = OptionFragment.newInstance("asd");
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

//add slides to slider
        slider.addSlides(slideList);

        return v;
    }



    private void setData(int count, float range, int op, int top) {
        ArrayList<PieEntry> val = new ArrayList<>();
        //System.out.println(top);
        switch (op){
            case 1:
                oro = count;
                topOro = top;


                break;
            case 2:
                az = count;
                topAz = top;
                break;
            case 3:
                efect = count;
                topEfect= top;
                break;
            case 4:
                libre = count;
                topLibre = top;

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
        Call<SmartObject> call = restClient.getData();

        call.enqueue(new Callback<SmartObject>() {
            @Override
            public void onResponse(Call<SmartObject> call, Response<SmartObject> response) {
                switch (response.code()) {
                    case 200:
                        SmartObject data = response.body();
                        System.out.println(data.getCustomerCredits());
                        setInfo(data.getCustomerCredits());
                        available(data.getProfile());
                        porcents();
                        break;
                    case 401:

                        break;
                    default:

                        break;
                }
            }

            @Override
            public void onFailure(Call<SmartObject> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    private void available(Profile profile) {

        float todo = profile.getBillingCapacity();
        billing = todo;


        setData((int) (todo-dis),10f,4, 100);

        todo = ((dis/todo)*100);

        usedPorcent = todo;

        String formattedString = String.format("%.02f", todo);
        totalUsed.setText(formattedString+"%");

    }

    void porcents(){
        String formattedString = String.format("%.02f", (topOro/billing)*100);
        porOro.setText(formattedString+"%");
        formattedString = String.format("%.02f", (topAz/billing)*100);
        porVas.setText(formattedString+"%");
        formattedString = String.format("%.02f", (topEfect/billing)*100);
        porNom.setText(formattedString+"%");

        formattedString = String.format("%.02f", (100-usedPorcent));
        porDis.setText(formattedString+"%");
    }

    private void setInfo(List<CustomerCredit> data) {
        int x =1;

        for (int i=0; i<data.size(); i++){
            CustomerCredit credit = data.get(i);
            setData(credit.getBalance(),10f,x, credit.getTop());
            x++;
            dis += credit.getTop() ;
        }
    }
}
