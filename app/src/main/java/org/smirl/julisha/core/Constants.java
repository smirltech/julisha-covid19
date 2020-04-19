package org.smirl.julisha.core;

import java.io.File;

public interface Constants {
    String BASE_URL = "https://api.smirl.org/"; //"http://192.168.13.102/api/
    String URL_API = BASE_URL + "covid19/v1/";

    String URL_WEB_APP = "https://apps.smirl.org/julisha";
    String URL_DB = BASE_URL + "query/v1/";
    String URL_RDC = BASE_URL + "rdc/";

    String URL_UPDATE = URL_WEB_APP + "/update.json";


}
