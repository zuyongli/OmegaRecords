package com.learningandroid.omegarecords;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learningandroid.omegarecords.domain.Address;
import com.learningandroid.omegarecords.domain.Company;
import com.learningandroid.omegarecords.domain.Geography;
import com.learningandroid.omegarecords.domain.User;
import com.learningandroid.omegarecords.utils.GsonParser;
import com.squareup.picasso.Picasso;

public class ViewUserDetailsActivity extends AppCompatActivity {

    private User user = null;
    private static final String USER_URL = "https://robohash.org/";
    private static final String ADDRESS_URL = "https://picsum.photos/200/200?random=";
    private static final String COM_URL = "https://source.unsplash.com/random/200x200?sig=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_details);

        fetchData();
        setData();
    }

    private void fetchData() {
        if(!getIntent().hasExtra("user")) {
            Toast.makeText(this, "No Data", Toast.LENGTH_LONG).show();
        } else {
            Bundle args = getIntent().getBundleExtra("user");
            String userJsonString = args.getString("user_details");
            user = GsonParser.getGsonParser().fromJson(userJsonString, User.class);
        }
    }

    private void setData() {
        if (user != null) {
            // set up the personal info card view
            ((TextView) findViewById(R.id.user_details_name)).setText(user.getName());
            ((TextView) findViewById(R.id.user_details_phone)).setText(user.getPhone());
            ((TextView) findViewById(R.id.user_details_email)).setText(user.getEmail());
            ((TextView) findViewById(R.id.user_details_website)).setText(user.getWebsite());
            ImageView userDetailsPhoto = findViewById(R.id.user_details_photo);
            Picasso.get().load(USER_URL + user.getName()).into(userDetailsPhoto);

            // set up the address info card view
            Address address = user.getAddress();
            ((TextView) findViewById(R.id.user_details_address_street_and_suite))
                    .setText(String.format("%s, %s", address.getStreet(), address.getSuite()));
            ((TextView) findViewById(R.id.user_details_address_city_and_zipcode))
                    .setText(String.format("%s, %s", address.getCity(), address.getZipcode()));
            Geography geo = address.getGeo();
            ((TextView) findViewById(R.id.user_details_address_geo))
                    .setText(String.format("%s, %s", geo.getLat(), geo.getLng()));
            ImageView userAddressPhoto = findViewById(R.id.user_details_address_photo);
            Picasso.get().load(ADDRESS_URL + user.getId()).into(userAddressPhoto);

            // set up the company info card view
            Company company = user.getCompany();
            ((TextView) findViewById(R.id.user_details_com_name)).setText(company.getName());
            ((TextView) findViewById(R.id.user_details_com_catch_phrase))
                    .setText(company.getCatchPhrase());
            ((TextView) findViewById(R.id.user_details_com_business)).setText(company.getBs());
            ImageView userCompanyPhoto = findViewById(R.id.user_details_com_photo);
            Picasso.get().load(COM_URL + user.getId()).into(userCompanyPhoto);
        }
    }
}