package com.fussyvegan.scanner;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.model.ChainFastFood;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.ChainFoodAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ChainFastFoodFragment extends Fragment implements ChainFoodAdapter.IChainListener {
    // TODO: Customize parameter argument names
    private static final String NAME_COUNTRY = "Country";
    private static final String NAME_OF_COUNTRY = "NameCountry";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    public String searchScope = "search";
    APIInterface apiInterface;
    List<ChainFastFood> mChains = new ArrayList<>();
    MainActivity activity;
    RecyclerView recyclerView;
    RelativeLayout rlSearchAll;
    TextView tvNameCountry;
    View header;
    SearchView searchView;
    ChainFoodAdapter mAdapter;
    String keyWord;
    String mNameCountry;
    String mNameOfCountry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNameCountry = getArguments().getString(NAME_COUNTRY);
            mNameOfCountry = getArguments().getString(NAME_OF_COUNTRY);
        }
        activity = (MainActivity) this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_fastfood, container, false);
        recyclerView = view.findViewById(R.id.rcNameChain);
        rlSearchAll = view.findViewById(R.id.rlSearchAll);
        tvNameCountry = view.findViewById(R.id.tvNameCountry);
        mAdapter = new ChainFoodAdapter(getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        tvNameCountry.setText(mNameOfCountry);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        //searchView.setQuery("", false);
        mChains.clear();
        onClickView();
        switch (mNameCountry) {
            case "Australia":
                initDataAU();
                break;
            case "Canada":
                initDataCA();
                break;
            case "New Zealand":
                initDataNZ();
                break;
            case "UK":
                initDataUK();
                break;
            case "USA":
                initDataUS();
                break;

            default:
                break;

        }
    }


    void initDataAU() {
        mChains.add(new ChainFastFood("Bakers Delight", "ic_bakers_delight"));
        mChains.add(new ChainFastFood("Boost Juice", "ic_boost_juice"));
        mChains.add(new ChainFastFood("Banjo’s Bakery Cafe", "ic_banjo"));
        mChains.add(new ChainFastFood("Baskin Robbins", "ic_baskin_robbins"));
        mChains.add(new ChainFastFood("BCC Cinemas", "ic_bcc_cinema"));
        mChains.add(new ChainFastFood("Brumby’s", "ic_brumbys"));
        mChains.add(new ChainFastFood("Bubba Pizza ", "ic_bubba_pizza"));
        mChains.add(new ChainFastFood("Burger Urge ", "ic_burger_urge"));
        mChains.add(new ChainFastFood("Burrito Bar ", "ic_burrito_bar"));
        mChains.add(new ChainFastFood("Chatime ", "ic_chatime"));
        mChains.add(new ChainFastFood("Chicken Treat ", "ic_chicken_treat"));
        mChains.add(new ChainFastFood("Cold Rock ", "ic_cold_rock"));
        mChains.add(new ChainFastFood("Crust Gourmet Pizza Bar ", "ic_crust_pizza"));
        mChains.add(new ChainFastFood("Domino’s ", "ic_dominos"));
        mChains.add(new ChainFastFood("Event Cinemas ", "ic_event_cinemas"));
        mChains.add(new ChainFastFood("Gelatissimo ", "ic_gelatissimo"));
        mChains.add(new ChainFastFood("Gloria Jean’s ", "ic_gloria_jeans"));
        mChains.add(new ChainFastFood("Grill’d ", "ic_grilld"));
        mChains.add(new ChainFastFood("Guzman y Gomez ", "ic_gyg"));
        mChains.add(new ChainFastFood("Hog’s Breath Cafe", "ic_hogs_breath"));
        mChains.add(new ChainFastFood("Hungry Jack’s ", "ic_hungry_jacks"));
        mChains.add(new ChainFastFood("KFC ", "ic_kfc"));
        mChains.add(new ChainFastFood("Krispy Kreme ", "ic_krispy_kreme"));
        mChains.add(new ChainFastFood("La Porchetta ", "ic_la_porchetta"));
        mChains.add(new ChainFastFood("Little Caesars ", "ic_little_caesars"));
        mChains.add(new ChainFastFood("Lord Of The Fries ", "ic_lotf"));
        mChains.add(new ChainFastFood("Mad Mex ", "ic_mad_mex"));
        mChains.add(new ChainFastFood("McDonald’s ", "ic_mcdonalds"));
        mChains.add(new ChainFastFood("Miss India ", "ic_miss_india"));
        mChains.add(new ChainFastFood("Montezuma’s ", "ic_montezumas"));
        mChains.add(new ChainFastFood("Muffin Break ", "ic_muffin_break"));
        mChains.add(new ChainFastFood("Nando’s ", "ic_nandos"));
        mChains.add(new ChainFastFood("Noodle Box ", "ic_noodle_box"));
        mChains.add(new ChainFastFood("Oliver’s ", "ic_olivers"));
        mChains.add(new ChainFastFood("Oporto ", "ic_oporto"));
        mChains.add(new ChainFastFood("The Pancake Parlour ", "ic_pancake_parlour"));
        mChains.add(new ChainFastFood("Pie Face ", "ic_pie_face"));
        mChains.add(new ChainFastFood("Pizza Capers ", "ic_pizza_capers"));
        mChains.add(new ChainFastFood("Pizza Hut ", "ic_pizza_hut"));
        mChains.add(new ChainFastFood("Red Rooster ", "ic_red_rooster"));
        mChains.add(new ChainFastFood("Roll’d ", "ic_rolld"));
        mChains.add(new ChainFastFood("San Churro ", "ic_san_churro"));
        mChains.add(new ChainFastFood("Schnitz ", "ic_schnitz"));
        mChains.add(new ChainFastFood("Subway ", "ic_subway"));
        mChains.add(new ChainFastFood("Sumo Salad ", "ic_sumo_salad"));
        mChains.add(new ChainFastFood("Taco Bell ", "ic_taco_bell"));
        mChains.add(new ChainFastFood("Wara Sushi ", "ic_wara_sushi"));
        mChains.add(new ChainFastFood("Wendy’s ", "ic_wendys_au"));
        mChains.add(new ChainFastFood("Zambrero ", "ic_zambrero"));
        mChains.add(new ChainFastFood("Zarraffa’s ", "ic_zarraffas"));
        mAdapter.updateData(mChains);
    }

    // Change data name and icon follow country.
    void initDataUS() {
        mChains.add(new ChainFastFood("A&W ", "ic_aw"));
        mChains.add(new ChainFastFood("Arby’s ", "ic_arby"));
        mChains.add(new ChainFastFood("Auntie Anne’s ", "ic_auntie_anne"));
        mChains.add(new ChainFastFood("Blaze Pizza ", "ic_blaze_pizza"));
        mChains.add(new ChainFastFood("Bojangles’ ", "ic_bojangles"));
        mChains.add(new ChainFastFood("Boston Market ", "ic_boston_market"));
        mChains.add(new ChainFastFood("Burger King ", "ic_burger_king"));
        mChains.add(new ChainFastFood("Captain D’s ", "ic_captain_d"));
        mChains.add(new ChainFastFood("Carl’s Jr.", "ic_carls_junior"));
        mChains.add(new ChainFastFood("Checker’s ", "ic_checkers"));
        mChains.add(new ChainFastFood("Chick-Fil-A ", "ic_chick_fill_a"));
        mChains.add(new ChainFastFood("Chipotle ", "ic_chipotle"));
        mChains.add(new ChainFastFood("Church’s Chicken", "ic_churchs_chicken"));
        mChains.add(new ChainFastFood("Culver’s ", "ic_culver"));
        mChains.add(new ChainFastFood("Dairy Queen ", "ic_dairy_queen"));
        mChains.add(new ChainFastFood("Del Taco ", "ic_del_taco"));
        mChains.add(new ChainFastFood("Disney", "ic_disney"));
        mChains.add(new ChainFastFood("Domino’s ", "ic_dominos"));
        mChains.add(new ChainFastFood("Dunkin’ ", "ic_dunkin"));
        mChains.add(new ChainFastFood("El Pollo Loco ", "ic_el_pollo_loco"));
        mChains.add(new ChainFastFood("Firehouse Subs ", "ic_firehouse_subs"));
        mChains.add(new ChainFastFood("Five Guys ", "ic_five_guys"));
        mChains.add(new ChainFastFood("Friendly’s ", "ic_friendlys"));
        mChains.add(new ChainFastFood("Hardee’s ", "ic_hardees"));
        mChains.add(new ChainFastFood("IN-N-OUT Burger ", "ic_in_n_out"));
        mChains.add(new ChainFastFood("Jack In The Box ", "ic_jack_inthebox"));
        mChains.add(new ChainFastFood("Jamba Juice ", "ic_jamba_juice"));
        mChains.add(new ChainFastFood("Jason’s Deli", "ic_jasons_deli"));
        mChains.add(new ChainFastFood("Jersey Mike’s ", "ic_jersey_mikes"));
        mChains.add(new ChainFastFood("Jimmy John’s ", "ic_jimmy_johns"));
        mChains.add(new ChainFastFood("KFC ", "ic_kfc"));
        mChains.add(new ChainFastFood("McAlister’s Deli ", "ic_mcalisters"));
        mChains.add(new ChainFastFood("McDonald’s ", "ic_mcdonalds"));
        mChains.add(new ChainFastFood("Moe’s Southwest Grill", "ic_moes_southwest"));
        mChains.add(new ChainFastFood("Noodles World Kitchen ", "ic_noodles_world"));
        mChains.add(new ChainFastFood("Panda Express ", "ic_panda_express"));
        mChains.add(new ChainFastFood("Papa Gino’s ", "ic_papa_ginos"));
        mChains.add(new ChainFastFood("Papa John’s Pizza ", "ic_papa_john"));
        mChains.add(new ChainFastFood("Papa Murphy’s ", "ic_papa_murphy"));
        mChains.add(new ChainFastFood("Pizza Hut ", "ic_pizza_hut"));
        mChains.add(new ChainFastFood("Popeyes ", "ic_popeyes"));
        mChains.add(new ChainFastFood("QDOBA ", "ic_qdoba"));
        mChains.add(new ChainFastFood("Rally’s ", "ic_rallys"));
        mChains.add(new ChainFastFood("Starbucks ", "ic_starbucks"));
        mChains.add(new ChainFastFood("Steak ’n Shake ", "ic_steak_shake"));
        mChains.add(new ChainFastFood("Subway ", "ic_subway"));
        mChains.add(new ChainFastFood("Taco Bell ", "ic_taco_bell"));
        mChains.add(new ChainFastFood("Tim Hortons ", "ic_tim_hortons"));
        mChains.add(new ChainFastFood("Wendy’s ", "ic_wendys"));
        mChains.add(new ChainFastFood("White Castle ", "ic_white_castle"));
        mChains.add(new ChainFastFood("Wing Stop ", "ic_wingstop"));
        mChains.add(new ChainFastFood("Zaxby’s ", "ic_zaxby"));



        mAdapter.updateData(mChains);
    }

    void initDataNZ() {
        mChains.add(new ChainFastFood("BurgerFuel ", "ic_burger_fuel"));
        mChains.add(new ChainFastFood("Burger King ", "ic_burger_king"));
        mChains.add(new ChainFastFood("Burger Wisconsin ", "ic_burger_wisconsin"));
        mChains.add(new ChainFastFood("Carl’s Junior ", "ic_carls_junior"));
        mChains.add(new ChainFastFood("Domino’s ", "ic_dominos"));
        mChains.add(new ChainFastFood("Dunkin’ ", "ic_dunkin"));
        mChains.add(new ChainFastFood("Hell Pizza ", "ic_hell_pizza"));
        mChains.add(new ChainFastFood("McDonald’s ", "ic_mcdonalds"));
        mChains.add(new ChainFastFood("Oporto ", "ic_oporto"));
        mChains.add(new ChainFastFood("Pita Pit ", "ic_pita_pit"));
        mChains.add(new ChainFastFood("Pizza Hut ", "ic_pizza_hut"));
        mChains.add(new ChainFastFood("Subway", "ic_subway"));
        mChains.add(new ChainFastFood("Wendy’s ", "ic_wendys"));
        mAdapter.updateData(mChains);
    }

    void initDataUK() {
        mChains.add(new ChainFastFood("Burger King ", "ic_burger_king"));
        mChains.add(new ChainFastFood("Costa ", "ic_costa"));
        mChains.add(new ChainFastFood("Domino’s ", "ic_dominos"));
        mChains.add(new ChainFastFood("Five Guys ", "ic_five_guys"));
        mChains.add(new ChainFastFood("Greggs ", "ic_bubba_pizza"));
        mChains.add(new ChainFastFood("KFC ", "ic_kfc"));
        mChains.add(new ChainFastFood("McDonald’s ", "ic_mcdonalds"));
        mChains.add(new ChainFastFood("Nando’s ", "ic_nandos"));
        mChains.add(new ChainFastFood("Pizz Hut ", "ic_pizza_hut"));
        mChains.add(new ChainFastFood("Subway ", "ic_subway"));
        mAdapter.updateData(mChains);
    }

    void initDataCA() {
        mChains.add(new ChainFastFood("Arby’s ", "ic_arby"));
        mChains.add(new ChainFastFood("McDonald’s ", "ic_mcdonalds"));
        mChains.add(new ChainFastFood("Subway ", "ic_subway"));
        mAdapter.updateData(mChains);
    }


    void onClickView() {
        rlSearchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(NAME_COUNTRY, mNameCountry);
                SearchFilterFragment fragment = new SearchFilterFragment();
                fragment.setArguments(bundle);
                String tag = "ProductFragment";
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();
            }
        });
    }


    @Override
    public void onClickChange(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("keySearch", mChains.get(position).getName());
        bundle.putString(NAME_COUNTRY, mNameCountry);
        SearchFilterFragment fragment = new SearchFilterFragment();
        fragment.setArguments(bundle);
        String tag = "ProductFragment";
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment, tag).addToBackStack(tag).commit();

    }
}
