package com.maryam210.videomaker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView myImageView;
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
                compareFaces();

               /* new Thread(new Runnable() {
                    @Override
                    public void run() {

                        compareFaces();
                    }
                }).start();*/
            }
        });

        myImageView = (ImageView) findViewById(R.id.imgview);

    }

    private void compareFaces() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap myBitmap1 = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.sumon_1,
                options);
        Bitmap myBitmap2 = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.sumon_2,
                options);
       /* if (myBitmap1.sameAs(myBitmap2)) {
            Toast.makeText(this, "Same", Toast.LENGTH_SHORT).show();
        }*/
        SparseArray<Face> photo1 = getFaceDetails(myBitmap1);
        SparseArray<Face> photo2 = getFaceDetails(myBitmap2);
        logFaceData(photo1);
        logFaceData(photo2);
        float widthDiff = 0f;
        float heightDiff = 0f;
        if (photo1.get(0).getWidth() > photo2.get(0).getWidth()) {

            widthDiff = photo1.get(0).getWidth() - photo2.get(0).getWidth();
            heightDiff = photo1.get(0).getHeight() - photo2.get(0).getHeight();
        } else {
            widthDiff = photo2.get(0).getWidth() - photo1.get(0).getWidth();
            heightDiff = photo2.get(0).getHeight() - photo1.get(0).getHeight();

        }
        Log.e("Tuts+ Face Detection", "widthDiff: " + widthDiff);
        Log.e("Tuts+ Face Detection", "heightDiff: " + heightDiff);
    }

    private void detectFace() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap myBitmap = BitmapFactory.decodeResource(
                getApplicationContext().getResources(),
                R.drawable.test,
                options);

        Paint myRectPaint = new Paint();
        myRectPaint.setStrokeWidth(5);
        myRectPaint.setColor(Color.RED);
        myRectPaint.setStyle(Paint.Style.STROKE);
        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(myBitmap, 0, 0, null);


        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
        SparseArray<Face> faces = faceDetector.detect(frame);
        for (int i = 0; i < faces.size(); i++) {
            Face thisFace = faces.valueAt(i);
            float x1 = thisFace.getPosition().x;
            float y1 = thisFace.getPosition().y;
            float x2 = x1 + thisFace.getWidth();
            float y2 = y1 + thisFace.getHeight();
            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
        }
        myImageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }

    void initFaceDetector() {
        faceDetector = new
                FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false)
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


    private void logFaceData(SparseArray<Face> mFaces) {
        float smilingProbability;
        float leftEyeOpenProbability;
        float rightEyeOpenProbability;
        float eulerY;
        float eulerZ;
        Face face = mFaces.valueAt(0);
        smilingProbability = face.getIsSmilingProbability();
        leftEyeOpenProbability = face.getIsLeftEyeOpenProbability();
        rightEyeOpenProbability = face.getIsRightEyeOpenProbability();
        eulerY = face.getEulerY();
        eulerZ = face.getEulerZ();

//        Log.e("Tuts+ Face Detection", "landmark: " + landmark.toString());
        Log.e("Tuts+ Face Detection", "Smiling: " + smilingProbability);
        Log.e("Tuts+ Face Detection", "Left eye open: " + leftEyeOpenProbability);
        Log.e("Tuts+ Face Detection", "Right eye open: " + rightEyeOpenProbability);
        Log.e("Tuts+ Face Detection", "Euler Y: " + eulerY);
        Log.e("Tuts+ Face Detection", "Euler Z: " + eulerZ);
        Log.e("Tuts+ Face Detection", "getWidth: " + face.getWidth());
        Log.e("Tuts+ Face Detection", "getHeight: " + face.getHeight());
        Log.e("Tuts+ Face Detection", "---------------------------");
    }
}
