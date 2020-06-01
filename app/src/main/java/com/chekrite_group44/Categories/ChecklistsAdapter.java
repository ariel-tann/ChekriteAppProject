/*
 * Date: 2020.5.20
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Categories;

public class ChecklistsAdapter {


    private String id;
    private String unitNumber;
    private String make;
    private String model;
    private String photo;

    public ChecklistsAdapter(String id, String unitNumber, String make, String model, String photo) {
        this.id = id;
        this.unitNumber = unitNumber;
        this.make = make;
        this.model = model;
        this.photo = photo;
    }
}


//public class ChecklistsAdapter extends ArrayAdapter<Checklist> {
  //  public ChecklistsAdapter(@NonNull Context context, int resource) {
    //    super(context, resource);
    //}
//}
