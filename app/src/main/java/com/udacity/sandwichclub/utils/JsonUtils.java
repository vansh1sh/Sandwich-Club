package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String KEY_AKA = "alsoKnownAs";
    private static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_NAME = "name";
    private static final String KEY_MAIN_NAME = "mainName";
    private static final String KEY_IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) {

        try {
            //Init a model class object and JSONObject which returns results from the JSON
            Sandwich sandwich = new Sandwich();
            JSONObject sObject = new JSONObject(json);
            sandwich.setImage(sObject.getString(KEY_IMAGE));
            sandwich.setDescription(sObject.getString(KEY_DESCRIPTION));
            sandwich.setPlaceOfOrigin(sObject.getString(KEY_PLACE_OF_ORIGIN));
            //Getting nested JSON content
            JSONObject sandwichNameObj = sObject.getJSONObject(KEY_NAME);
            sandwich.setMainName(sandwichNameObj.getString(KEY_MAIN_NAME));
            List<String> aka = new ArrayList<>();
            JSONArray akaJSONArray = sandwichNameObj.getJSONArray(KEY_AKA);
            for (int i = 0; i < akaJSONArray.length(); i++) {
                aka.add(akaJSONArray.getString(i));
            }
            sandwich.setAlsoKnownAs(aka);

            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientArray = sObject.getJSONArray(KEY_INGREDIENTS);
            for (int i = 0; i < ingredientArray.length(); i++) {
                ingredients.add(ingredientArray.getString(i));
            }
            sandwich.setIngredients(ingredients);

            return sandwich;
        } catch (JSONException je) {
            je.printStackTrace();
            return null;
        }
    }
}
