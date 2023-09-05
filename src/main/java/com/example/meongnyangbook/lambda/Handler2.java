package com.example.meongnyangbook.lambda;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class Handler2 implements RequestHandler<S3Event, String> {

  private static final float MAX_WIDTH = 500;
  private static final float MAX_HEIGHT = 500;
  private final String JPG_TYPE = (String) "jpg";
  private final String JPG_MIME = (String) "image/jpeg";
  private final String JPEG_TYPE = (String) "jpeg";
  private final String JPEG_MIME = (String) "image/jpeg";
  private final String PNG_TYPE = (String) "png";
  private final String PNG_MIME = (String) "image/png";
  private final String GIF_TYPE = (String) "gif";
  private final String GIF_MIME = (String) "image/gif";

  public String handleRequest(S3Event s3event, Context context) {
    LambdaLogger logger = context.getLogger();
    try {
      S3EventNotificationRecord record = s3event.getRecords().get(0);
      String srcBucket = record.getS3().getBucket().getName();
      // Object key may have spaces or unicode non-ASCII characters.
      String key = record.getS3().getObject().getUrlDecodedKey();
      String dstBucket = srcBucket.replace("temp", "resized");
      // Infer the image type.
      Matcher matcher = Pattern.compile(".*\\.([^\\.]*)").matcher(key);
      if (!matcher.matches()) {
        logger.log("Unable to infer image type for key " + key);
        return "";
      }
      String imageType = matcher.group(1);
      if (!(JPG_TYPE.equals(imageType)) && !(JPEG_TYPE.equals(imageType))
          && !(PNG_TYPE.equals(imageType)) && !(GIF_TYPE.equals(imageType))) {
        logger.log("Skipping non-image " + key);
        return "";
      }
      // Download the image from S3 into a stream
      AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
      S3Object s3Object = s3Client.getObject(new GetObjectRequest(
          srcBucket, key));
      InputStream objectData = s3Object.getObjectContent();
      // Read the source image
      BufferedImage srcImage = ImageIO.read(objectData);
      int srcHeight = srcImage.getHeight();
      int srcWidth = srcImage.getWidth();
      // Infer the scaling factor to avoid stretching the image
      // unnaturally
      float scalingFactor = Math.min(MAX_WIDTH / srcWidth, MAX_HEIGHT / srcHeight);
      int width = (int) (scalingFactor * srcWidth);
      int height = (int) (scalingFactor * srcHeight);
      BufferedImage resizedImage = new BufferedImage(width, height,
          BufferedImage.TYPE_INT_RGB);
      Graphics2D g = resizedImage.createGraphics();
      // Fill with white before applying semi-transparent (alpha) images
      g.setPaint(Color.white);
      g.fillRect(0, 0, width, height);
      // Simple bilinear resize
      g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
          RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      g.drawImage(srcImage, 0, 0, width, height, null);
      g.dispose();
      // Re-encode image to target format
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(resizedImage, imageType, os);
      InputStream is = new ByteArrayInputStream(os.toByteArray());
      // Set Content-Length and Content-Type
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentLength(os.size());
      if (JPG_TYPE.equals(imageType)) {
        meta.setContentType(JPG_MIME);
      }
      if (JPEG_TYPE.equals(imageType)) {
        meta.setContentType(JPEG_MIME);
      }
      if (PNG_TYPE.equals(imageType)) {
        meta.setContentType(PNG_MIME);
      }
      if (GIF_TYPE.equals(imageType)) {
        meta.setContentType(GIF_MIME);
      }
      // Uploading to S3 destination bucket
      logger.log("Writing to: " + dstBucket + "/" + key);
      try {
        dstBucket += "-resized";
        s3Client
            .putObject(new PutObjectRequest(dstBucket, key, is, meta).withCannedAcl(
                CannedAccessControlList.PublicRead));
      } catch (AmazonServiceException e) {
        logger.log(e.getErrorMessage());
        System.exit(1);
      }
      logger.log(
          "Successfully resized " + srcBucket + "/" + key + " and uploaded to " + dstBucket
              + "/" + key);
      return "Ok";
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}