/*
 * Date: 2020.5.6
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import org.json.JSONObject;

public interface InspectionListener {
    // when user completes one item, then it will call Complete and submit response
    public void Completed(int type, int button_order,double button_value,
                          long start_timestamp, long response_timestamp);
    // type: use Control_TYPE to enter
    // button_value: indicate the value being clicked
}
