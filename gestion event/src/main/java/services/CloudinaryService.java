package services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import java.io.File;


public class CloudinaryService {
    private static final String CLOUDINARY_API_URL = "https://api.cloudinary.com/v1_1/dhlglkirf/image/upload";

    private static final String API_KEY = "448517963854328";
    private static final String API_SECRET = "NzE9cJCvPEkSUCMLHbM5hz7Y__g";
    private static final String CLOUD_NAME = "dhlglkirf";
    private static final String UPLOAD_PRESET = "lewlsutt";


    public CloudinaryService(){}
    public static String uploadImage(String imagePath) {
        try {
            HttpResponse<JsonNode> response = Unirest.post(CLOUDINARY_API_URL)
                    .field("file", new File(imagePath))
                    .field("upload_preset", UPLOAD_PRESET)
                    .basicAuth(API_KEY, API_SECRET)
                    .asJson();

            int statusCode = response.getStatus();
            return switch (statusCode) {
                case 200 -> response.getBody().getObject().getString("secure_url");
                case 400 ->
                        throw new RuntimeException("Bad request. Check the Cloudinary request parameters." + response.getBody());
                case 401 ->
                        throw new RuntimeException("Authorization required. Check your Cloudinary API credentials.");
                case 403 -> throw new RuntimeException("Not allowed. Ensure that you have the necessary permissions.");
                case 404 -> throw new RuntimeException("Not found. The requested resource could not be found.");
                case 409 ->
                        throw new RuntimeException("Already exists. The resource you are trying to create already exists.");
                default ->
                        throw new RuntimeException("Failed to upload image to Cloudinary. HTTP status: " + statusCode);
            };
        } catch (UnirestException e) {
            throw new RuntimeException("Error uploading image to Cloudinary", e);
        }
    }



}
