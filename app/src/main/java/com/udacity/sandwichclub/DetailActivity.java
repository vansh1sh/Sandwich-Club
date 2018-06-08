package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView tvOrigin;
    TextView tvAlsoKnownAs;
    TextView tvIngredients;
    TextView tvDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        tvOrigin = findViewById(R.id.origin_tv);
        tvAlsoKnownAs = findViewById(R.id.also_known_tv);
        tvIngredients = findViewById(R.id.ingredients_tv);
        tvDescription = findViewById(R.id.description_tv);

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin != null && !placeOfOrigin.isEmpty()) {
            tvOrigin.setText(placeOfOrigin);
        } else {
            tvOrigin.setText(getString(R.string.detail_error_message));
        }

        List<String> aka = sandwich.getAlsoKnownAs();
        if (aka.size() > 0) {
            tvAlsoKnownAs.setText(TextUtils.join(", ", sandwich.getAlsoKnownAs()));
        } else {
            tvAlsoKnownAs.setText(getString(R.string.detail_error_message));
        }

        String description = sandwich.getDescription();
        if (description != null && !description.isEmpty()) {
            tvDescription.setText(description);
        } else {
            tvDescription.setText(getString(R.string.detail_error_message));
        }

        List<String> ingredients = sandwich.getIngredients();
        if (ingredients.size() > 0) {
            tvIngredients.setText(TextUtils.join(", ", sandwich.getIngredients()));
        } else {
            tvIngredients.setText(getString(R.string.detail_error_message));
        }

    }
}
