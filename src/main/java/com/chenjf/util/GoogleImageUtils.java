package com.chenjf.util;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * 调用google api进行图片相关识别
 * https://cloud.google.com/vision/docs/
 * 注:需要翻墙
 * Created by chenjf on 2017-09-11.
 */
public class GoogleImageUtils {

    /**
     * Detecting text in a local image
     * @param filePath
     * @param out
     * @throws Exception
     */
    public static void detectText(String filePath, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

        Image img = Image.newBuilder().setContent(imgBytes).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    out.printf("Text: %s\n", annotation.getDescription());
                    out.printf("Position : %s\n", annotation.getBoundingPoly());
                }
            }
        }
    }

    /**
     * Detecting text in a remote image
     * @param gcsPath
     * @param out
     * @throws Exception
     * @throws IOException
     */
    public static void detectTextGcs(String gcsPath, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setGcsImageUri(gcsPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    out.printf("Text: %s\n", annotation.getDescription());
                    out.printf("Position : %s\n", annotation.getBoundingPoly());
                }
            }
        }
    }

    /**
     * 识别图片中的文字（uri）
     * Detecting text in a remote image
     * @param uriPath
     * @param out
     * @throws Exception
     * @throws IOException
     */
    public static void detectTextUri(String uriPath, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(uriPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getTextAnnotationsList()) {
                    out.printf("Text: %s\n", annotation.getDescription());
                    out.printf("Position : %s\n", annotation.getBoundingPoly());
                }
            }
        }
    }

    /**
     * 识别图片中的logo（uri）
     * Detecting logos in a remote image
     * @param uriPath
     * @param out
     * @throws Exception
     * @throws IOException
     */
    public static void detectLogosUri(String uriPath, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(uriPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.LOGO_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (EntityAnnotation annotation : res.getLogoAnnotationsList()) {
                    out.println(annotation.getDescription());
                }
            }
        }
    }

    /**
     * 识别图片中的实体（uri）
     * Running Web Detection on a remote image
     * @param uriPath
     * @param out
     * @throws Exception
     */
    public static void detectWebDetectionsUri(String uriPath, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(uriPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.WEB_DETECTION).build();
        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // Search the web for usages of the image. You could use these signals later
                // for user input moderation or linking external references.
                // For a full list of available annotations, see http://g.co/cloud/vision/docs
                WebDetection annotation = res.getWebDetection();
                out.println("Entity:Id:Score");
                out.println("===============");
                for (WebDetection.WebEntity entity : annotation.getWebEntitiesList()) {
                    out.println(entity.getDescription() + " : " + entity.getEntityId() + " : "
                            + entity.getScore());
                }
                out.println("\nPages with matching images: Score\n==");
                for (WebDetection.WebPage page : annotation.getPagesWithMatchingImagesList()) {
                    out.println(page.getUrl() + " : " + page.getScore());
                }
                out.println("\nPages with partially matching images: Score\n==");
                for (WebDetection.WebImage image : annotation.getPartialMatchingImagesList()) {
                    out.println(image.getUrl() + " : " + image.getScore());
                }
                out.println("\nPages with fully matching images: Score\n==");
                for (WebDetection.WebImage image : annotation.getFullMatchingImagesList()) {
                    out.println(image.getUrl() + " : " + image.getScore());
                }
            }
        }
    }

    /**
     * 识别图片中的人脸（uri）
     * Detecting Faces in a remote image
     * @param uriPath
     * @param out
     * @throws Exception
     * @throws IOException
     */
    public static void detectFacesUri(String uriPath, PrintStream out) throws Exception {
        List<AnnotateImageRequest> requests = new ArrayList<>();

        ImageSource imgSource = ImageSource.newBuilder().setImageUri(uriPath).build();
        Image img = Image.newBuilder().setSource(imgSource).build();
        Feature feat = Feature.newBuilder().setType(Feature.Type.FACE_DETECTION).build();

        AnnotateImageRequest request =
                AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
        requests.add(request);

//        GoogleCredential credential = GoogleCredential.fromStream(in).createScoped(DriveScopes.all());

//        GoogleCredentialsProvider.

        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

//            GoogleCredentialsProvider bulid = new GoogleCredentialsProvider();
//            FixedCredentialsProvider.create(GoogleCredentials.of(""))
//            GoogleCredentialsProvider provider=bulid.
//            client.getSettings().toBuilder().setCredentialsProvider(new DefaultCredentialsProvider());

            BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    out.printf("Error: %s\n", res.getError().getMessage());
                    return;
                }

                // For full list of available annotations, see http://g.co/cloud/vision/docs
                for (FaceAnnotation annotation : res.getFaceAnnotationsList()) {
                    out.printf(
                            "anger: %s\njoy: %s\nsurprise: %s\nposition: %s",
                            annotation.getAngerLikelihood(),
                            annotation.getJoyLikelihood(),
                            annotation.getSurpriseLikelihood(),
                            annotation.getBoundingPoly());
                }
            }
        }
    }

    /** Authorizes the installed application to access user's protected data. */
//    private static Credential authorize() throws Exception {
//        // load client secrets
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//                new InputStreamReader(CalendarSample.class.getResourceAsStream("/client_secrets.json")));
//        // set up authorization code flow
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                httpTransport, JSON_FACTORY, clientSecrets,
//                Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
//                .build();
//        // authorize
//        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//    }

    public static void main(String[] args) throws Exception {
//        detectFacesUri("https://contestimg.wish.com/api/webimage/570f3530a97e2e39da33f569-large.jpg", System.out);
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//
//        com.google.api.gax.paging.Page<Bucket> buckets = storage.list();
//        for (Bucket bucket : buckets.iterateAll()) {
//            System.out.println(bucket.getName());
        detectLogosUri("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2655901464,955748581&fm=58&s=0AAE7A22DE9EFF920CE9DCD7000080B0", System.out);
//        }
    }
}
