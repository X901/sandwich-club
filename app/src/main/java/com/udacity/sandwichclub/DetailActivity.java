package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    private TextView mDetailAlsoKnownAsLabel;
    private TextView mAlsoKnownTv;


    private TextView mDetailIngredientsLabel;
    private TextView mIngredientsTv;


    private TextView mDetailPlaceOfOriginLabel;
    private TextView mOriginTv;

    private TextView mdetailDescriptionLabel;
    private TextView mDescriptionTv;

    private ImageView mImageIv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

          mDetailAlsoKnownAsLabel = (TextView) findViewById(R.id.detail_also_known_as_label);
          mDetailIngredientsLabel = (TextView) findViewById(R.id.detail_ingredients_label);
          mDetailPlaceOfOriginLabel = (TextView) findViewById(R.id.detail_place_of_origin_label);
          mdetailDescriptionLabel = (TextView) findViewById(R.id.detail_description_label);
          mImageIv = (ImageView) findViewById(R.id.image_iv);
          mOriginTv = (TextView) findViewById(R.id.origin_tv);
          mDescriptionTv = (TextView) findViewById(R.id.description_tv);
          mIngredientsTv = (TextView) findViewById(R.id.ingredients_tv);
          mAlsoKnownTv = (TextView) findViewById(R.id.also_known_tv);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

        Log.i("myTag", String.valueOf(sandwich));


        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginTv.setVisibility(View.GONE);
            mDetailPlaceOfOriginLabel.setVisibility(View.GONE);
        } else {
            mOriginTv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getDescription().isEmpty()){
            mdetailDescriptionLabel.setVisibility(View.GONE);
            mDescriptionTv.setVisibility(View.GONE);
        }else {
            mDescriptionTv.setText(sandwich.getDescription());
        }

        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0){

            StringBuilder builder = new StringBuilder();

            if (sandwich.getAlsoKnownAs().size() == 1) {
                builder.append(sandwich.getAlsoKnownAs().get(0));


            } else {

                for (String alsoKnownA: sandwich.getAlsoKnownAs()) {
                    builder.append(alsoKnownA);
                    builder.append(", ");
                }

                mAlsoKnownTv.setText(builder.toString());

            }

            }else {
            mDetailAlsoKnownAsLabel.setVisibility(View.GONE);
            mAlsoKnownTv.setVisibility(View.GONE);

        }

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0){

            StringBuilder builder = new StringBuilder();

            if (sandwich.getAlsoKnownAs().size() == 1) {
                builder.append(sandwich.getIngredients().get(0));


            } else {

                for (String alsoKnownA: sandwich.getIngredients()) {
                    builder.append(alsoKnownA);
                    builder.append("\n");
                }

                mIngredientsTv.setText(builder.toString());

            }

        }else {
            mDetailIngredientsLabel.setVisibility(View.GONE);
            mIngredientsTv.setVisibility(View.GONE);

        }





        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageIv);



    }
}
