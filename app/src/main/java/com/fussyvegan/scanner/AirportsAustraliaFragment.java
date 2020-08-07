package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.AirportsAdapter;

import java.util.ArrayList;
import java.util.List;

public class AirportsAustraliaFragment extends Fragment {

    private static final String NAME_COUNTRY_AIRPORT= "name airport";

    private ListView lvMajorAirports;
    private ListView lvNewSouthWales;
    private ListView lvQueensland;
    private ListView lvSouthAustralia;
    private ListView lvTasmania;
    private ListView lvVictoria;
    private ListView lvWesternAustralia;

    List<String> nameMajorAirports; List<String> codeMajorAirports;
    List<String> nameNewSouthWales; List<String> codeNewSouthWales;
    List<String> nameQueensland; List<String> codeQueensland;
    List<String> nameSouthAustralia; List<String> codeSouthAustralia;
    List<String> nameTasmania; List<String> codeTasmania;
    List<String> nameVictoria; List<String> codeVictoria;
    List<String> nameWesternAustralia; List<String> codeWesternAustralia;

    LinearLayout lnAsp;
    LinearLayout lnCbr;
    LinearLayout lnDrw;
    LinearLayout lnNlk;

    MainActivity activity;

    private String mNameAirport;

    public AirportsAustraliaFragment() {
        nameMajorAirports = new ArrayList<>(); codeMajorAirports = new ArrayList<>();
        nameNewSouthWales = new ArrayList<>(); codeNewSouthWales = new ArrayList<>();
        nameQueensland = new ArrayList<>(); codeQueensland = new ArrayList<>();
        nameSouthAustralia = new ArrayList<>(); codeSouthAustralia = new ArrayList<>();
        nameTasmania = new ArrayList<>(); codeTasmania = new ArrayList<>();
        nameVictoria = new ArrayList<>(); codeVictoria = new ArrayList<>();
        nameWesternAustralia = new ArrayList<>(); codeWesternAustralia = new ArrayList<>();
        getListAirPorts();

    }

    private void getListAirPorts(){
        nameMajorAirports.add("Brisbane"); codeMajorAirports.add("BNE");
        nameMajorAirports.add("Melbourne"); codeMajorAirports.add("MEL");
        nameMajorAirports.add("Perth"); codeMajorAirports.add("PER");
        nameMajorAirports.add("Sydney"); codeMajorAirports.add("SYD");

        nameNewSouthWales.add("Albury"); codeNewSouthWales.add("ABX");
        nameNewSouthWales.add("Armidale"); codeNewSouthWales.add("ARM");
        nameNewSouthWales.add("Ballina Byron Gateway"); codeNewSouthWales.add("BNK");
        nameNewSouthWales.add("Broken Hill"); codeNewSouthWales.add("BHQ");
        nameNewSouthWales.add("Coffs Harbour"); codeNewSouthWales.add("CFS");
        nameNewSouthWales.add("Dubbo"); codeNewSouthWales.add("DBO");
        nameNewSouthWales.add("Lord Howe Island"); codeNewSouthWales.add("LDH");
        nameNewSouthWales.add("Merimbula"); codeNewSouthWales.add("MIM");
        nameNewSouthWales.add("Moruya"); codeNewSouthWales.add("MYA");
        nameNewSouthWales.add("Newcastle"); codeNewSouthWales.add("NTL");
        nameNewSouthWales.add("Port Macquarie"); codeNewSouthWales.add("PQQ");
        nameNewSouthWales.add("Shellharbour"); codeNewSouthWales.add("WOL");
        nameNewSouthWales.add("Snowy Mountains"); codeNewSouthWales.add("OOM");
        nameNewSouthWales.add("Sydney"); codeNewSouthWales.add("SYD");
        nameNewSouthWales.add("Tamworth"); codeNewSouthWales.add("TMW");
        nameNewSouthWales.add("Wagga Wagga"); codeNewSouthWales.add("WGA");

        nameQueensland.add("Brisbane"); codeQueensland.add("BNE");
        nameQueensland.add("Bundaberg"); codeQueensland.add("BDB");
        nameQueensland.add("Cairns"); codeQueensland.add("CNS");
        nameQueensland.add("Emerald"); codeQueensland.add("EMD");
        nameQueensland.add("Gladstone"); codeQueensland.add("GLT");
        nameQueensland.add("Gold Coast"); codeQueensland.add("OOL");
        nameQueensland.add("Hamilton Island"); codeQueensland.add("HTI");
        nameQueensland.add("Hervey Bay"); codeQueensland.add("HVB");
        nameQueensland.add("Horn Island"); codeQueensland.add("HID");
        nameQueensland.add("Longreach"); codeQueensland.add("LRE");
        nameQueensland.add("Mackay"); codeQueensland.add("MKY");
        nameQueensland.add("Mount Isa"); codeQueensland.add("ISA");
        nameQueensland.add("Rockhampton"); codeQueensland.add("ROK");
        nameQueensland.add("Sunshine Coast"); codeQueensland.add("MCY");
        nameQueensland.add("Townsville"); codeQueensland.add("TSV");
        nameQueensland.add("Whitsunday Coast"); codeQueensland.add("PPP");

        nameSouthAustralia.add("Adelaide"); codeSouthAustralia.add("ADL");
        nameSouthAustralia.add("Kingscote"); codeSouthAustralia.add("KGC");
        nameSouthAustralia.add("Port Lincoln"); codeSouthAustralia.add("PLO");
        nameSouthAustralia.add("Whyalla"); codeSouthAustralia.add("WYA");

        nameTasmania.add("Burnie"); codeTasmania.add("BWT");
        nameTasmania.add("Devonport"); codeTasmania.add("DBO");
        nameTasmania.add("Hobart"); codeTasmania.add("HBA");
        nameTasmania.add("King Island"); codeTasmania.add("KNS");
        nameTasmania.add("Launceston"); codeTasmania.add("LST");

        nameVictoria.add("Avalon"); codeVictoria.add("AVV");
        nameVictoria.add("Bendigo"); codeVictoria.add("BXG");
        nameVictoria.add("Melbourne"); codeVictoria.add("MEL");
        nameVictoria.add("Mildura"); codeVictoria.add("MQL");

        nameWesternAustralia.add("Broome"); codeWesternAustralia.add("BME");
        nameWesternAustralia.add("Esperance"); codeWesternAustralia.add("EPR");
        nameWesternAustralia.add("Geraldton"); codeWesternAustralia.add("GET");
        nameWesternAustralia.add("Kalgoorlie"); codeWesternAustralia.add("KGI");
        nameWesternAustralia.add("Karratha"); codeWesternAustralia.add("KTA");
        nameWesternAustralia.add("Perth"); codeWesternAustralia.add("PER");
        nameWesternAustralia.add("Port Healand"); codeWesternAustralia.add("PHE");


    }

    public static AirportsAustraliaFragment newInstance(String param1, String param2) {
        AirportsAustraliaFragment fragment = new AirportsAustraliaFragment();
        Bundle args = new Bundle();
        args.putString(NAME_COUNTRY_AIRPORT, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameAirport = getArguments().getString(NAME_COUNTRY_AIRPORT);
        }
        activity = (MainActivity) this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.australia_airport, container, false);
        initView(view);

        lvMajorAirports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationAirportFragment fragment = new LocationAirportFragment();
                String tag = "AirportsAustraliaFragment";
                Bundle args = new Bundle();
                args.putString("name location airport", nameMajorAirports.get(position));
                args.putString("code location airport", codeMajorAirports.get(position));
                fragment.setArguments(args);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });
        activity.showNothing();
        activity.visibleBackItem(false);
        activity.invalidateOptionsMenu();

        return view;
    }

    private void initView(View view){
        lvMajorAirports = view.findViewById(R.id.lvMajorAirports);
        lvNewSouthWales = view.findViewById(R.id.lvNewSouthWales);
        lvQueensland = view.findViewById(R.id.lvQueensland);
        lvSouthAustralia = view.findViewById(R.id.lvSouthAustralia);
        lvTasmania = view.findViewById(R.id.lvTasmania);
        lvVictoria = view.findViewById(R.id.lvVictoria);
        lvWesternAustralia = view.findViewById(R.id.lvWesternAustralia);

        AirportsAdapter adapter = new AirportsAdapter(nameMajorAirports, codeMajorAirports);
        lvMajorAirports.setAdapter(adapter);

        AirportsAdapter adapter1 = new AirportsAdapter(nameNewSouthWales, codeNewSouthWales);
        lvNewSouthWales.setAdapter(adapter1);

        AirportsAdapter adapter2 = new AirportsAdapter(nameQueensland, codeQueensland);
        lvQueensland.setAdapter(adapter2);

        AirportsAdapter adapter3 = new AirportsAdapter(nameSouthAustralia, codeSouthAustralia);
        lvSouthAustralia.setAdapter(adapter3);

        AirportsAdapter adapter4 = new AirportsAdapter(nameTasmania, codeTasmania);
        lvTasmania.setAdapter(adapter4);

        AirportsAdapter adapter5 = new AirportsAdapter(nameVictoria, codeVictoria);
        lvVictoria.setAdapter(adapter5);

        AirportsAdapter adapter6 = new AirportsAdapter(nameWesternAustralia, codeWesternAustralia);
        lvWesternAustralia.setAdapter(adapter6);

        lnAsp = view.findViewById(R.id.lnAsp);
        lnCbr = view.findViewById(R.id.lnCbr);
        lnDrw = view.findViewById(R.id.lnDrw);
        lnNlk = view.findViewById(R.id.lnNlk);

        TextView nameAirPort = view.findViewById(R.id.tv_name_airport);
        nameAirPort.setText(mNameAirport);

    }
}