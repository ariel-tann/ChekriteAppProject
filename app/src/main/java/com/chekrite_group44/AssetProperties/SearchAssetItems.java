/*
 * Date: 2020.6.1
 * This file is created by Kai.
 * Summary:
 */

/*
 * Date: 2020.5.18
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.AssetProperties;

public class SearchAssetItems {


    private int id;
    private String unitNumber;
    private String make;
    private String model;
    private String photo;

    public SearchAssetItems(int id, String unitNumber, String make, String model, String photo) {
        this.id = id;
        this.unitNumber = unitNumber;
        this.make = make;
        this.model = model;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getPhoto() {
        return photo;
    }
}
