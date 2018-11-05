package com.semicolstudio.android.agsemi;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.*;

public class PostString {
    public static final MediaType TEXT = MediaType.parse("text/plain");

    //OkHttpClient client = new OkHttpClient();
    OkHttpClient client = new OkHttpClient.Builder()
            .connectionSpecs(Arrays.asList(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT, ConnectionSpec.COMPATIBLE_TLS))
            .build();

    String post(String url, RequestBody sRequestbody) throws IOException {
        //RequestBody body = RequestBody.create(TEXT, sRequestbody);
        Request request = new Request.Builder()
                .url(url)
                .post(sRequestbody)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        return response.body().string();

    }


   /* public static void main(String args,String UrlApi) throws IOException {
        PostString example = new PostString();

        String response = example.post(UrlApi, args);
        System.out.println(response);
    }*/
}