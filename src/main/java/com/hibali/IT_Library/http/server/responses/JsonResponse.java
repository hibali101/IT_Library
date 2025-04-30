package com.hibali.IT_Library.http.server.responses;

import java.nio.charset.StandardCharsets;

import com.hibali.IT_Library.models.classes.BaseModel;
import com.hibali.IT_Library.utilities.ObjectToJson;

public class JsonResponse extends BaseResponse {

    private static final String contentType = "application/json";

    private JsonResponse(int responseCode, byte[] data) {
        super(responseCode, data, contentType);
    }

    public static BaseResponse notFound(BaseModel model) {
        return null;
    }

    public static BaseResponse ok(BaseModel model) {
        // converting the model to json format
        String json = ObjectToJson.toJson(model);
        byte[] data = json.getBytes(StandardCharsets.UTF_8);
        BaseResponse response = new JsonResponse(200, data);
        return response;
    }
}
