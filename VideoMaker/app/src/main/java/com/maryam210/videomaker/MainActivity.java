package com.maryam210.videomaker;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    //private ImageView myImageView;
    private FaceDetector faceDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.button);
        initFaceDetector();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // detectFace();
                //compareFaces();
            }
        });

        // myImageView = (ImageView) findViewById(R.id.imgview);
        setGridAdapter("/storage/sdcard/DCIM/");

    }

    /*
    prediction base result for unsimilar faces:
    face width difference <30 pix
    face nose to mouth difference >20 and <30 pix
    face mouth difference >20 and<30 pix
    face eye width difference >20 and <30
    ---------------------------------------------------
    prediction base result for similar faces:
    face width difference <20 pix
    face nose to mouth difference <20 pix
    face mouth difference <20 pix
    face eye width difference <20
     */
    private boolean compareFaces(Bitmap myBitmap1, Bitmap myBitmap2, String imagePath) {

        /*Bitmap myBitmap1 = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.sumon_2,
                options);
        Bitmap myBitmap2 = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.ic_sumon4,
                options);*/
        boolean isRecognize = false;
        SparseArray<Face> photo1 = getFaceDetails(myBitmap1);
        SparseArray<Face> photo2 = getFaceDetails(myBitmap2);
        float widthDiff = 0f;
        float heightDiff = 0f;
        if ((photo1 != null && photo2 != null) && (photo1.size() > 0 && photo2.size() > 0)) {
            if (photo1.get(0).getWidth() > photo2.get(0).getWidth()) {

                widthDiff = photo1.get(0).getWidth() - photo2.get(0).getWidth();
                heightDiff = photo1.get(0).getHeight() - photo2.get(0).getHeight();
            } else {
                widthDiff = photo2.get(0).getWidth() - photo1.get(0).getWidth();
                heightDiff = photo2.get(0).getHeight() - photo1.get(0).getHeight();
            }
            float eyeWidthDifference = 0f;
            float noseToMouthDifference = 0f;
            float mouthDifference = 0f;

            Face face1 = photo1.valueAt(0);
            PointF lfPointFace1 = null;
            PointF rightPointFace1 = null;

            PointF noseBasePointFace1 = null;
            PointF bottomMouthPointFace1 = null;

            PointF leftMouthPointFace1 = null;
            PointF rightMouthPointFace1 = null;
            for (int i = 0; i < photo1.size(); i++) {
                Face detectFace = photo1.get(i);
                for (Landmark landmark : detectFace.getLandmarks()) {
                    switch (landmark.getType()) {
                        case Landmark.LEFT_EYE:
                            lfPointFace1 = landmark.getPosition();
                            break;
                        case Landmark.RIGHT_EYE:
                            rightPointFace1 = landmark.getPosition();
                            break;
                        case Landmark.NOSE_BASE:
                            noseBasePointFace1 = landmark.getPosition();
                            break;
                        case Landmark.BOTTOM_MOUTH:
                            bottomMouthPointFace1 = landmark.getPosition();
                            break;
                        case Landmark.LEFT_MOUTH:
                            leftMouthPointFace1 = landmark.getPosition();
                            break;
                        case Landmark.RIGHT_MOUTH:
                            rightMouthPointFace1 = landmark.getPosition();
                            break;
                    }
                }
            }
            float eyeWidth1 = 0f;
            float eyeWidth2 = 0f;
            float noseToMouth1 = 0f;
            float noseToMouth2 = 0f;
            float leftMouthToRightMouth1 = 0f;
            float leftMouthToRightMouth2 = 0f;
            if (lfPointFace1 != null && rightPointFace1 != null) {
                eyeWidth1 = lfPointFace1.x - rightPointFace1.x;
            }
            if (noseBasePointFace1 != null && bottomMouthPointFace1 != null) {
                noseToMouth1 = bottomMouthPointFace1.y - noseBasePointFace1.y;
            }
            if (leftMouthPointFace1 != null && rightMouthPointFace1 != null) {
                leftMouthToRightMouth1 = leftMouthPointFace1.x - rightMouthPointFace1.x;
            }

            Face face2 = photo2.valueAt(0);
            PointF lfPointFace2 = null;
            PointF rightPointFace2 = null;

            PointF noseBasePointFace2 = null;
            PointF bottomMouthPointFace2 = null;

            PointF leftMouthPointFace2 = null;
            PointF rightMouthPointFace2 = null;
            for (int i = 0; i < photo2.size(); i++) {
                Face detectFace = photo2.get(i);
                for (Landmark landmark : detectFace.getLandmarks()) {
                    switch (landmark.getType()) {
                        case Landmark.LEFT_EYE:
                            lfPointFace2 = landmark.getPosition();
                            break;
                        case Landmark.RIGHT_EYE:
                            rightPointFace2 = landmark.getPosition();
                            break;
                        case Landmark.NOSE_BASE:
                            noseBasePointFace2 = landmark.getPosition();
                            break;
                        case Landmark.BOTTOM_MOUTH:
                            bottomMouthPointFace2 = landmark.getPosition();
                            break;
                        case Landmark.LEFT_MOUTH:
                            leftMouthPointFace2 = landmark.getPosition();
                            break;
                        case Landmark.RIGHT_MOUTH:
                            rightMouthPointFace2 = landmark.getPosition();
                            break;
                    }
                }
            }
            if (lfPointFace2 != null && rightPointFace2 != null) {
                eyeWidth2 = lfPointFace2.x - rightPointFace2.x;
            }
            if (noseBasePointFace2 != null && bottomMouthPointFace2 != null) {
                noseToMouth2 = bottomMouthPointFace2.y - noseBasePointFace2.y;
            }
            if (leftMouthPointFace2 != null && rightMouthPointFace2 != null) {
                leftMouthToRightMouth2 = leftMouthPointFace2.x - rightMouthPointFace2.x;
            }
        /*    if (photo2.get(0).getWidth() > photo1.get(0).getWidth()) {
                eyeWidthDifference = (eyeWidth1 > eyeWidth2) ? eyeWidth1 - eyeWidth2 : eyeWidth2 - eyeWidth1;
                noseToMouthDifference = (noseToMouth1 * 2 > noseToMouth2) ? noseToMouth1 * 2 - noseToMouth2 : noseToMouth2 - noseToMouth1 * 2;
                mouthDifference = (leftMouthToRightMouth1 * 2 > leftMouthToRightMouth2) ? leftMouthToRightMouth1 * 2 - leftMouthToRightMouth2 : leftMouthToRightMouth2 - leftMouthToRightMouth1 * 2;
            } else {
                eyeWidthDifference = (eyeWidth1 > eyeWidth2) ? eyeWidth1 - eyeWidth2 : eyeWidth2 - eyeWidth1;
                noseToMouthDifference = (noseToMouth1 > noseToMouth2) ? noseToMouth1 - noseToMouth2 : noseToMouth2 - noseToMouth1;
                mouthDifference = (leftMouthToRightMouth1 > leftMouthToRightMouth2) ? leftMouthToRightMouth1 - leftMouthToRightMouth2 : leftMouthToRightMouth2 - leftMouthToRightMouth1;

            }*/
            eyeWidthDifference = (eyeWidth1 > eyeWidth2) ? eyeWidth1 - eyeWidth2 : eyeWidth2 - eyeWidth1;
            noseToMouthDifference = (noseToMouth1 > noseToMouth2) ? noseToMouth1 - noseToMouth2 : noseToMouth2 - noseToMouth1;
            mouthDifference = (leftMouthToRightMouth1 > leftMouthToRightMouth2) ? leftMouthToRightMouth1 - leftMouthToRightMouth2 : leftMouthToRightMouth2 - leftMouthToRightMouth1;

            boolean isEyeSimilar = false;
            boolean isNoseToMouthDifferentSame = false;
            boolean isMouthDifferenceSame = false;
            boolean isFaceWidthOK = false;
            if (eyeWidthDifference < 20) {
                isEyeSimilar = true;
            }
            if (noseToMouthDifference < 20) {
                isNoseToMouthDifferentSame = true;
            }
            if (mouthDifference < 20) {
                isMouthDifferenceSame = true;
            }
            if (widthDiff < 20) {
                isFaceWidthOK = true;
            }
            if (isEyeSimilar && isFaceWidthOK && isNoseToMouthDifferentSame && isMouthDifferenceSame) {
                //Toast.makeText(this, "Same Face", Toast.LENGTH_SHORT).show();
                isRecognize = true;
                recognizeListener.isRecognize();

            } else if (isEyeSimilar && isFaceWidthOK && isNoseToMouthDifferentSame) {
                isRecognize = true;
                recognizeListener.isRecognize();

                //Toast.makeText(this, "Same Face", Toast.LENGTH_SHORT).show();
            } else if (isEyeSimilar && isFaceWidthOK) {
                //Toast.makeText(this, "Same Face", Toast.LENGTH_SHORT).show();
                isRecognize = true;
                recognizeListener.isRecognize();
            } else {
                isRecognize = false;
                ///Toast.makeText(this, "Not Recognize", Toast.LENGTH_SHORT).show();
            }
            Log.e("Tuts+ Face Detection", "Face1: getWidth: " + face1.getWidth());
            Log.e("Tuts+ Face Detection", "Face1: getHeight: " + face1.getHeight());
            Log.e("Tuts+ Face Detection", "---------------------------");
            Log.e("Tuts+ Face Detection", "Face2: getWidth: " + face2.getWidth());
            Log.e("Tuts+ Face Detection", "Face2: getHeight: " + face2.getHeight());
            Log.e("Tuts+ Face Detection", "---------------------------");
            Log.e("Tuts+ Face Detection", "eyeWidthDifference: " + eyeWidthDifference);
            Log.e("Tuts+ Face Detection", "noseToMouthDifference: " + noseToMouthDifference);
            Log.e("Tuts+ Face Detection", "mouthDifference: " + mouthDifference);
            Log.e("Tuts+ Face Detection", "widthDiff: " + widthDiff);
            Log.e("Tuts+ Face Detection", "heightDiff: " + heightDiff);
            Log.e("Tuts+ Face Detection", "---------------------------");
            if (isRecognize) {
                Bitmap actualImage = BitmapHelper.decodeBitmapFromFile(imagePath,
                        50,
                        50);
                final GridViewItem item = new GridViewItem(imagePath, false, actualImage);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        adapter.updateData(item);
                    }
                });
            }
            return isRecognize;
        } else {
            //Toast.makeText(this, "Face problem found in photos", Toast.LENGTH_SHORT).show();
            return isRecognize;
        }
    }

    void initFaceDetector() {
        faceDetector = new
                FaceDetector.Builder(getApplicationContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .setMode(FaceDetector.FAST_MODE)
                .build();
        if (!faceDetector.isOperational()) {
            new AlertDialog.Builder(this).setMessage("Could not set up the face detector!").show();
            return;
        }
    }

    private SparseArray<Face> getFaceDetails(Bitmap myBitmap) {
        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        return faceDetector.detect(frame);
    }

    List<GridViewItem> gridItems;
    MyGridAdapter adapter;

    /**
     * This will create our GridViewItems and set the adapter
     *
     * @param path The directory in which to search for images
     */
    private void setGridAdapter(String path) {
        // Create a new grid adapter
        createGridItems(path);
        gridItems = new ArrayList<>();
        GridView gridView = (GridView) findViewById(R.id.gridView);
        adapter = new MyGridAdapter(this, gridItems);
        /*new MainActivity().setRecognizeListener(new RecognizeListener() {
            @Override
            public void isRecognize() {

            }

            @Override
            public void getImage(GridViewItem item) {
                gridItems.add(item);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(gridItems);
                    }
                });
            }
        });*/

        // Set the grid adapter
        gridView.setAdapter(adapter);

        // Set the onClickListener
        gridView.setOnItemClickListener(this);
    }


    /**
     * Go through the specified directory, and create items to display in our
     * GridView
     */
    private void createGridItems(String directoryPath) {
        final List<GridViewItem> items = new ArrayList<GridViewItem>();
/*
        // List all the items within the folder.
        File[] files = new File(directoryPath).listFiles(new ImageFileFilter());
        for (File file : files) {

            // Add the directories containing images or sub-directories
            if (file.isDirectory()
                    && file.listFiles(new ImageFileFilter()).length > 0) {

                items.add(new GridViewItem(file.getAbsolutePath(), true, null));
            }
            // Add the images
            else {
                Bitmap image = BitmapHelper.decodeBitmapFromFile(file.getAbsolutePath(),
                        50,
                        50);
                items.add(new GridViewItem(file.getAbsolutePath(), false, image));
            }
        }*/
        ArrayList<String> listOfImages = getAllShownImagesPath(MainActivity.this);
        for (final String imagePath : listOfImages) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inMutable = true;
//            Bitmap image1 = BitmapFactory.decodeFile(imagePath, options);
            /*Bitmap image1 = BitmapFactory.decodeFile(
                    imagePath,
                    options);*/
            final Bitmap imageRww = BitmapFactory.decodeResource(
                    getApplicationContext().getResources(),
                    R.drawable.sumon_2,
                    options);
            //final Bitmap image1 = Bitmap.createBitmap(imageRww, 0, 0, 512, 512);
            final Bitmap image2 = BitmapHelper.decodeBitmapFromFile(imagePath,
                    512,
                    512);
            if (image2 != null)
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        compareFaces(imageRww, image2, imagePath);
                    }
                }).start();

            /*new MainActivity().setRecognizeListener(new RecognizeListener() {
                @Override
                public void isRecognize() {
                    Bitmap actualImage = BitmapHelper.decodeBitmapFromFile(imagePath,
                            50,
                            50);
                    GridViewItem item = new GridViewItem(imagePath, false, actualImage);
                    recognizeListener.getImage(item);
                }

                @Override
                public void getImage(GridViewItem item) {
                    //items.add(item);
                }
            });*/

        }
        //return items;
    }

    private ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

    /**
     * Checks the file to see if it has a compatible extension.
     */
    private boolean isImageFile(String filePath) {
        if (filePath.endsWith(".jpg") || filePath.endsWith(".png"))
        // Add other formats as desired
        {
            return true;
        }
        return false;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (gridItems.get(position).isDirectory()) {
            setGridAdapter(gridItems.get(position).getPath());
        } else {
            // Display the image
        }

    }

    /**
     * This can be used to filter files.
     */
    private class ImageFileFilter implements FileFilter {

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            } else if (isImageFile(file.getAbsolutePath())) {
                return true;
            }
            return false;
        }
    }

    public void setRecognizeListener(RecognizeListener recognizeListener) {
        this.recognizeListener = recognizeListener;
    }

    private RecognizeListener recognizeListener;

    public interface RecognizeListener {
        void isRecognize();

        void getImage(GridViewItem item);
    }
}
