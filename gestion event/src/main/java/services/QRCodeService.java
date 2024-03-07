package services;


import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class QRCodeService {
    private static final String API_URL = "https://api.qrserver.com/v1/create-qr-code/";

    public static String generateQRCode(String data) throws IOException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        urlBuilder.addQueryParameter("size", "200x200");  // Adjust size as needed
        urlBuilder.addQueryParameter("data", data);

        Request request = new Request.Builder()
                .url(urlBuilder.build().toString())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Save the QR code as an image file
                String imagePath = saveQRCodeImage(response.body().byteStream());
                return imagePath;
            } else {
                throw new IOException("Unexpected response code: " + response);
            }
        }
    }

    private static String saveQRCodeImage(InputStream inputStream) throws IOException {
        // Specify the file path to save the QR code image
        String imagePath = "generated_qr_code.png";

        BufferedImage image = ImageIO.read(inputStream);

        // Write the BufferedImage to a file
        ImageIO.write(image, "png", new File(imagePath));

        return imagePath;
    }
}