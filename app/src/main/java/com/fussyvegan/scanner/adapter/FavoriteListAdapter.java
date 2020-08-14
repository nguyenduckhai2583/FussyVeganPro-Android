package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.activity.LocationAirportDetailActivity;
import com.fussyvegan.scanner.activity.ProductAirlineDetailActivity;
import com.fussyvegan.scanner.activity.ProductDetailActivity;
import com.fussyvegan.scanner.activity.ResortDetailActivity;
import com.fussyvegan.scanner.activity.RestaurantDetailActivity;
import com.fussyvegan.scanner.model.LocationAirport;
import com.fussyvegan.scanner.model.Product;
import com.fussyvegan.scanner.model.ProductAirline;
import com.fussyvegan.scanner.model.Resort;
import com.fussyvegan.scanner.model.favorite.Favorite;
import com.fussyvegan.scanner.model.restaurant.Restaurant;
import com.fussyvegan.scanner.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavoriteListAdapter extends RecyclerView.Adapter {

    private Context context;
    private List list;
    private List<Favorite> favoriteList;
    private List<String> listDistance;
    private boolean isEdit = false;
    onDeleteItemListener listener;

    public FavoriteListAdapter(Context context, List list, List<Favorite> favoriteList, List<String> listDistance, onDeleteItemListener listener) {
        this.context = context;
        this.list = list;
        this.favoriteList = favoriteList;
        this.listener = listener;
        this.listDistance = listDistance;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Product) {
            return Constant.FAVOR_FOODCHAIN;
        } else if (list.get(position) instanceof Resort) {
            return Constant.FAVOR_RESORT;
        } else if (list.get(position) instanceof Restaurant) {
            return Constant.FAVOR_RESTAURANT;
        } else if (list.get(position) instanceof ProductAirline) {
            return Constant.FAVOR_AIRLINE;
        } else {
            return Constant.FAVOR_AIRPORT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case Constant.FAVOR_FOODCHAIN:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
                return new ProductHolder(view);
            case Constant.FAVOR_RESORT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
                return new ResortHolder(view);
            case Constant.FAVOR_RESTAURANT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_restaurant, parent, false);
                return new RestaurantHolder(view);
            case Constant.FAVOR_AIRLINE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products_ari_line, parent, false);
                return new AirlineHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
                return new AirportHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int typeBind = favoriteList.get(position).getFavoriteable_type();
        int favoriteId = favoriteList.get(position).getFavorite_id();
        String distance = listDistance.get(position);

        switch (typeBind) {
            case Constant.FAVOR_PRODUCT:
                Product productFavor = (Product) list.get(position);
                ProductHolder productHolderFavor = (ProductHolder) holder;
                bindProduct(productFavor, productHolderFavor, favoriteId);
                break;
            case Constant.FAVOR_FOODCHAIN:
                Product product = (Product) list.get(position);
                ProductHolder productHolder = (ProductHolder) holder;
                bindProduct(product, productHolder, favoriteId);
                break;
            case Constant.FAVOR_RESORT:
                Resort resort = (Resort) list.get(position);
                ResortHolder resortHolder = (ResortHolder) holder;
                bindResort(resort, resortHolder, favoriteId, distance);
                break;
            case Constant.FAVOR_RESTAURANT:
                Restaurant restaurant = (Restaurant) list.get(position);
                RestaurantHolder restaurantHolder = (RestaurantHolder) holder;
                bindRestaurant(restaurant, restaurantHolder, favoriteId, distance);
                break;
            case Constant.FAVOR_INGREDIENT:
                Product productIngredient = (Product) list.get(position);
                ProductHolder productHolderIngredient = (ProductHolder) holder;
                bindProduct(productIngredient, productHolderIngredient, favoriteId);
                break;

            case Constant.FAVOR_AIRLINE:
                ProductAirline airline = (ProductAirline) list.get(position);
                AirlineHolder airlineHolder = (AirlineHolder) holder;
                bindAirline(airline, airlineHolder, favoriteId);
                break;
            case Constant.FAVOR_AIRPORT:
                LocationAirport airport = (LocationAirport) list.get(position);
                AirportHolder airportHolder = (AirportHolder) holder;
                bindAirport(airport, airportHolder, favoriteId, distance);
                break;
        }
    }

    public void onEdit() {
        isEdit = !isEdit;
        notifyDataSetChanged();
    }

    private void bindAirport(final LocationAirport airport, final AirportHolder airportHolder, final int favoriteId, String distance) {
        if (!airport.getLink_photo_small().isEmpty()) {
            Picasso.get()
                    .load(airport.getLink_photo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(airportHolder.imageView);
        }

        airportHolder.nameLocation.setText(airport.getName());
        airportHolder.location.setText(airport.getLocation());
        airportHolder.cuisineType.setText(airport.getCuisine_type());
        airportHolder.distance.setText(distance);
        airportHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LocationAirportDetailActivity.class);
                intent.putExtra("location", airport);
                context.startActivity(intent);
            }
        });

        int status = isEdit ? View.VISIBLE : View.GONE;
        airportHolder.imgBtShowDelete.setVisibility(status);

        if (status == View.GONE) {
            airportHolder.tvDelete.setVisibility(status);
        }

        airportHolder.imgBtShowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                airportHolder.imgBtShowDelete.setVisibility(View.GONE);
                airportHolder.tvDelete.setVisibility(View.VISIBLE);
            }
        });

        airportHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(favoriteId);
            }
        });

    }

    private void bindAirline(final ProductAirline airline, final AirlineHolder airlineHolder, final int favoriteId) {
        airlineHolder.nameProduct.setText(airline.getMealName());
        airlineHolder.mealCode.setText(airline.getMealCode());
        Picasso.get().load(airline.getAirlineLogo()).into(airlineHolder.imgIcon);

        int status = isEdit ? View.VISIBLE : View.GONE;
        airlineHolder.imgBtShowDelete.setVisibility(status);

        if (status == View.GONE) {
            airlineHolder.tvDelete.setVisibility(status);
        }

        airlineHolder.imgBtShowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                airlineHolder.imgBtShowDelete.setVisibility(View.GONE);
                airlineHolder.tvDelete.setVisibility(View.VISIBLE);
            }
        });

        airlineHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(favoriteId);
            }
        });

        airlineHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductAirlineDetailActivity.class);
                intent.putExtra("product airline", airline);
                context.startActivity(intent);
            }
        });
    }

    private void bindRestaurant(final Restaurant restaurant, final RestaurantHolder restaurantHolder, final int favoriteId, String distance) {
        Picasso.get().load(restaurant.getLink_photo_small()).placeholder(R.drawable.ic_app_150)
                .into(restaurantHolder.imgImage);
        restaurantHolder.tvRestaurant.setText(restaurant.getName());
        restaurantHolder.tvAddress.setText(restaurant.getLocation());
        restaurantHolder.tvDistance.setText(distance);
        restaurantHolder.tvTypeCusine.setText(restaurant.getCuisine_type());
        restaurantHolder.rlRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RestaurantDetailActivity.class);
                intent.putExtra("restaurant", restaurant);
                context.startActivity(intent);
            }
        });

        int status = isEdit ? View.VISIBLE : View.GONE;
        restaurantHolder.imgBtShowDelete.setVisibility(status);

        if (status == View.GONE) {
            restaurantHolder.tvDelete.setVisibility(status);
        }

        restaurantHolder.imgBtShowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantHolder.imgBtShowDelete.setVisibility(View.GONE);
                restaurantHolder.tvDelete.setVisibility(View.VISIBLE);
            }
        });

        restaurantHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(favoriteId);
            }
        });
    }

    private void bindResort(final Resort resort, final ResortHolder resortHolder, final int favoriteId, String distance) {
        if (!resort.getLink_photo_small().isEmpty()) {
            Picasso.get()
                    .load(resort.getLink_photo())
                    .placeholder(R.drawable.ic_app_150)
                    .into(resortHolder.imgResort);
        }

        resortHolder.tvName.setText(resort.getName());
        resortHolder.tvLocation.setText(resort.getLocation());
        resortHolder.tvTypeCusine.setVisibility(View.GONE);
        resortHolder. tvDistance.setText(distance);
        resortHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ResortDetailActivity.class);
                intent.putExtra("resort", resort);
                context.startActivity(intent);
            }
        });

        int status = isEdit ? View.VISIBLE : View.GONE;
        resortHolder.imgBtShowDelete.setVisibility(status);

        if (status == View.GONE) {
            resortHolder.tvDelete.setVisibility(status);
        }

        resortHolder.imgBtShowDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resortHolder.imgBtShowDelete.setVisibility(View.GONE);
                resortHolder.tvDelete.setVisibility(View.VISIBLE);
            }
        });

        resortHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(favoriteId);
            }
        });
    }

    private void bindProduct(final Product product, final ProductHolder productHolder, final int favoriteId) {
        if (product.getVeganStatus().equals("VEGAN")) {
            productHolder.imgVeganstatus.setImageResource(R.drawable.vegan);
        } else if (product.getVeganStatus().equals("NOT VEGAN")) {
            productHolder.imgVeganstatus.setImageResource(R.drawable.notvegan);
        } else {
            productHolder.imgVeganstatus.setImageResource(R.drawable.caution);
        }

        productHolder.llRate.setVisibility(View.VISIBLE);
        productHolder.txvName.setText(product.getName());
        if((int)product.getCountRatting()==0) {
            productHolder.tvSumRating.setText("No" + " Rating");
        } else if((int)product.getCountRatting()==1) {
            productHolder.tvSumRating.setText("1" + " Rating");
        } else {
            productHolder.tvSumRating.setText((int)product.getCountRatting() + " Ratings");
        }
        productHolder.rb_AveRating.setRating(product.getAvgRating());

        Picasso.get().cancelRequest(productHolder.imgProduct);
        if (!product.getLinkPhoto().isEmpty()) {
            Picasso.get()
                    .load(product.getLinkPhoto())
                    .placeholder(R.drawable.ic_app_150)
                    .into(productHolder.imgProduct);
        }

        productHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product", product);
                intent.putExtra("country", true);
                intent.putExtra("category", 2);
                context.startActivity(intent);
            }
        });

        int status = isEdit ? View.VISIBLE : View.GONE;
        productHolder.btnDelete.setVisibility(status);

        if (status == View.GONE) {
            productHolder.tvDelete.setVisibility(status);
        }

        productHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productHolder.btnDelete.setVisibility(View.GONE);
                productHolder.tvDelete.setVisibility(View.VISIBLE);
            }
        });

        productHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onDelete(favoriteId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ProductHolder extends RecyclerView.ViewHolder {

        ImageView btnDelete, imgProduct, imgVeganstatus;
        AppCompatRatingBar rb_AveRating;
        TextView tvSumRating, txvName, tvDelete;
        LinearLayout llRate;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            txvName = itemView.findViewById(R.id.txvName);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            rb_AveRating = itemView.findViewById(R.id.rb_AveRating);
            tvSumRating = itemView.findViewById(R.id.tvSumRating);
            imgVeganstatus = itemView.findViewById(R.id.imgVeganstatus);
            llRate = itemView.findViewById(R.id.linearLayoutRate);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    static class ResortHolder extends RecyclerView.ViewHolder {
        ImageView imgResort, imgBtShowDelete;
        TextView tvName, tvLocation, tvTypeCusine, tvDistance, tvDelete;

        public ResortHolder(@NonNull View itemView) {
            super(itemView);
            imgResort = itemView.findViewById(R.id.imgImage);
            tvName = itemView.findViewById(R.id.tvRestaurant);
            tvLocation = itemView.findViewById(R.id.tvAddress);
            tvTypeCusine = itemView.findViewById(R.id.tvTypeCusine);
            tvDistance= itemView.findViewById(R.id.tvDistance);
            imgBtShowDelete = itemView.findViewById(R.id.imgBtShowDelete);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    static class AirportHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imgBtShowDelete;
        TextView nameLocation, location, cuisineType, distance, tvDelete;

        public AirportHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imgImage);
            nameLocation = itemView.findViewById(R.id.tvRestaurant);
            location = itemView.findViewById(R.id.tvAddress);
            cuisineType = itemView.findViewById(R.id.tvCuisineType);
            distance = itemView.findViewById(R.id.tvDistance);
            imgBtShowDelete = itemView.findViewById(R.id.imgBtShowDelete);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    static class AirlineHolder extends RecyclerView.ViewHolder {
        TextView nameProduct, mealCode, tvDelete;
        ImageView imgIcon, imgBtShowDelete;

        public AirlineHolder(@NonNull View itemView) {
            super(itemView);
            nameProduct = itemView.findViewById(R.id.tv_name_product);
            mealCode = itemView.findViewById(R.id.tv_meal_code);
            imgIcon = itemView.findViewById(R.id.img_ari_line);
            imgBtShowDelete = itemView.findViewById(R.id.imgBtShowDelete);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    static class RestaurantHolder extends RecyclerView.ViewHolder {
        ImageView imgImage, imgBtShowDelete;
        TextView tvRestaurant, tvAddress, tvTypeCusine, tvDistance, tvDelete;
        RelativeLayout rlRestaurant;

        public RestaurantHolder(@NonNull View itemView) {
            super(itemView);
            imgImage = itemView.findViewById(R.id.imgImage);
            tvRestaurant = itemView.findViewById(R.id.tvRestaurant);
            tvDistance = itemView.findViewById(R.id.tvDistance);
            tvTypeCusine = itemView.findViewById(R.id.tvTypeCusine);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            rlRestaurant = itemView.findViewById(R.id.rlRestaurant);
            imgBtShowDelete = itemView.findViewById(R.id.imgBtShowDelete);
            tvDelete = itemView.findViewById(R.id.tvDelete);
        }
    }

    public interface onDeleteItemListener {
        void onDelete(int favoriteId);
    }
}
