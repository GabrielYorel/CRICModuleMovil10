package com.deinteti.gb.cricmodulemovil10;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PictureTools {
    private static final String TAG = "PictureTools";

    private static int getCameraPhotoOrientation(Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.v(TAG, "Exif orientation: " + orientation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap getImageStandarCricModule(Uri url) {
        return PictureTools.decodeSampledBitmapFromUri(url.getPath(), 200, 200);
    }

    public static Bitmap getImageStandarCricModule(String path) {
        return PictureTools.decodeSampledBitmapFromUri(path, 200, 200);
    }

    public static String getBase64Image(Bitmap photo) {
        byte[] photoasbytearray = null;
        //getting photo as byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        photoasbytearray = stream.toByteArray();

        String encodedImage = Base64.encodeToString(photoasbytearray, Base64.DEFAULT);
        return encodedImage;
    }

    public static Bitmap decodeSampledBitmapFromUri(String dir, int Width, int Height) {
        Bitmap rotatedBitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(dir, options);

            options.inSampleSize = calculateInSampleSize(options, Width, Height);
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeFile(dir, options);
            Uri pictureUri = Uri.parse(dir);
            Matrix matrix = new Matrix();
            matrix.postRotate(PictureTools.getCameraPhotoOrientation(pictureUri, dir));
            rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            return null;
        }
        return rotatedBitmap;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int Width, int Height) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int size_inicialize = 1;

        if (height > Height || width > Width) {
            if (width > height) {
                size_inicialize = Math.round((float) height / (float) Height);
            } else {
                size_inicialize = Math.round((float) width / (float) Width);
            }
        }
        return size_inicialize;
    }

    public static byte[] convertImageToByte(Uri uri, Context ctx) {
        byte[] data = null;
        try {
            ContentResolver cr = ctx.getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(ctx, "No se pudo obtener la foto, intente de nuevo", Toast.LENGTH_SHORT);
            return null;
        }
        return data;
    }

    public static Bitmap GetImageFromByte(Context ctx, byte[] array) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeByteArray(array, 0, array.length);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(ctx, "No se pudo convertir a imagen, intente de nuevo", Toast.LENGTH_SHORT);
            return null;
        }
        return bmp;
    }
}
