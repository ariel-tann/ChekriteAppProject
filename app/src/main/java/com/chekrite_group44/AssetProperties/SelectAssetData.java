/*
 * Date: 2020.6.8
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.3
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

import java.util.ArrayList;

public class SelectAssetData {

    private int id;
    private String make;
    private String model;
    private String category;
    private int on_the_fly_assets_enabled;
    private ArrayList<SelectAssetAssets> assets = new ArrayList<>();


    public SelectAssetData(int id, String make, String model, String category, int on_the_fly_assets_enabled,
                           ArrayList<SelectAssetAssets> mAssets) {

        this.id = id;
        this.make = make;
        this.model = model;
        this.category = category;
        this.on_the_fly_assets_enabled = on_the_fly_assets_enabled;
        // add every asset in ArrayList
        for(int i = 0; i<mAssets.size(); i++) {
            assets.add(new SelectAssetAssets(mAssets.get(i).getId(), mAssets.get(i).getUnitNumber(), mAssets.get(i).getMake(),
                    mAssets.get(i).getModel(), mAssets.get(i).getPhoto()));
        }

    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getCategory() {
        return category;
    }

    public int getOnTheFlyAssetsEnabled() {
        return on_the_fly_assets_enabled;
    }

    public ArrayList<SelectAssetAssets> getAssets() {
        return assets;
    }
}
