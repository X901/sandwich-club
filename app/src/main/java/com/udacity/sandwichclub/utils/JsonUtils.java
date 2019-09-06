package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {



    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject mainObject = new JSONObject(json);

            JSONObject name = mainObject.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray alsoKnownAsJSONArray = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = convertJSONArrayToList(alsoKnownAsJSONArray);

            String placeOfOrigin = mainObject.getString("placeOfOrigin");

            String description = mainObject.getString("description");

            String image = mainObject.getString("image");

            JSONArray ingredientsJSONArray = mainObject.getJSONArray("ingredients");
            List<String> ingredients = convertJSONArrayToList(ingredientsJSONArray);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> convertJSONArrayToList(JSONArray jsonArray) throws JSONException {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<jsonArray.length(); i++) {
            list.add( jsonArray.getString(i) );
        }
return list;
    }
}
