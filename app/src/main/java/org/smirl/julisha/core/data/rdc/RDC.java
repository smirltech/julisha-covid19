package org.smirl.julisha.core.data.rdc;


import static org.smirl.julisha.core.Constants.URL_RDC;

public interface RDC {
    String RDC_API_URL = URL_RDC;
    String DBNAME = "smirlorg_rdc";


    String PROVINCE_TABLE = DBNAME + ".province";
    String VILLE_TABLE = DBNAME + ".ville";
    String COMMUNE_TABLE = DBNAME + ".commune";


    String PROVINCE_URL = RDC_API_URL + "province.php";
    String VILLE_URL = RDC_API_URL + "ville.php";
    String COMMUNE_URL = RDC_API_URL + "commune.php";
}
