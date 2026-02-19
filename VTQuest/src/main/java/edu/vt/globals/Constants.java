/*
 * Created by Osman Balci on 2025.10.15
 * Copyright © 2025 Osman Balci. All rights reserved.
 */
package edu.vt.globals;

import java.math.BigDecimal;

public class Constants {
    /*
    -----------------------------------------
    Dr. Balci's Google Cloud Platform Account
    -----------------------------------------
    Project Name:	 VTQuest
    Project Number:	 1042281989478
    Project ID:	     vtquest-329013
    Credit Card:	 VISA (Nothing is charged since usage is very low.)

    Enabled Google APIs under this account all accessed with the same API key:
        * Directions API
        * Geocoding API
        * Maps Embed API
        * Maps JavaScript API
        * Maps Static API
        * Places API
    ============================================================================================
    1. Create your own Google Cloud Platform account using your personal Google (Gmail) account.
       Your VT Google account will not work since it requires authorization by Virginia Tech.
    2. Go to your Google Cloud Platform Console
    3. Create a project and obtain your API key.
    4. Click APIs & Services → Dashboard → Enable APIs & Services
    5. Enable the APIs listed above.
    ============================================================================================
     */
    public static final String GOOGLE_API_KEY = "";

    // Virginia Tech Campus Center Geolocation
    public static final BigDecimal VT_MAP_CENTER_LATITUDE = BigDecimal.valueOf(37.227264);
    public static final BigDecimal VT_MAP_CENTER_LONGITUDE = BigDecimal.valueOf(-80.420745);

}
