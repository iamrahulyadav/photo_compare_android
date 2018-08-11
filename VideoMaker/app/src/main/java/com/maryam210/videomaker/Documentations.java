package com.maryam210.videomaker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.Landmark;

public class Documentations {
//    private void detectFace() {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inMutable = true;
//        Bitmap myBitmap = BitmapFactory.decodeResource(
//                getApplicationContext().getResources(),
//                R.drawable.test,
//                options);
//
//        Paint myRectPaint = new Paint();
//        myRectPaint.setStrokeWidth(5);
//        myRectPaint.setColor(Color.RED);
//        myRectPaint.setStyle(Paint.Style.STROKE);
//        Bitmap tempBitmap = Bitmap.createBitmap(myBitmap.getWidth(), myBitmap.getHeight(), Bitmap.Config.RGB_565);
//        Canvas tempCanvas = new Canvas(tempBitmap);
//        tempCanvas.drawBitmap(myBitmap, 0, 0, null);
//
//
//        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
//        SparseArray<Face> faces = faceDetector.detect(frame);
//        for (int i = 0; i < faces.size(); i++) {
//            Face thisFace = faces.valueAt(i);
//            float x1 = thisFace.getPosition().x;
//            float y1 = thisFace.getPosition().y;
//            float x2 = x1 + thisFace.getWidth();
//            float y2 = y1 + thisFace.getHeight();
//            tempCanvas.drawRoundRect(new RectF(x1, y1, x2, y2), 2, 2, myRectPaint);
//        }
//        myImageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
//    }

   /* private void logFaceData(SparseArray<Face> mFaces) {
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
        PointF lfPoint = null;
        PointF rightPoint = null;

        PointF noseBasePoint = null;
        PointF bottomMouthPoint = null;

        PointF leftMouthPoint = null;
        PointF rightMouthPoint = null;
        for (int i = 0; i < mFaces.size(); i++) {
            Face detectFace = mFaces.get(i);
//            List<Landmark> landmarkList = face.getLandmarks();
            for (Landmark landmark : detectFace.getLandmarks()) {
                switch (landmark.getType()) {
                    case Landmark.LEFT_EYE:
                        lfPoint = landmark.getPosition();
                        break;
                    case Landmark.RIGHT_EYE:
                        rightPoint = landmark.getPosition();
                        break;
                    case Landmark.NOSE_BASE:
                        noseBasePoint = landmark.getPosition();
                        break;
                    case Landmark.BOTTOM_MOUTH:
                        bottomMouthPoint = landmark.getPosition();
                        break;
                    case Landmark.LEFT_MOUTH:
                        leftMouthPoint = landmark.getPosition();
                        break;
                    case Landmark.RIGHT_MOUTH:
                        rightMouthPoint = landmark.getPosition();
                        break;
                }
            }
        }
        if (lfPoint != null && rightPoint != null) {
            float eyeWidth = lfPoint.x - rightPoint.x;
            float eyeHeight = rightPoint.y - lfPoint.y;

            Log.e("Tuts+ Face Detection", "eyeWidth: " + eyeWidth);
            Log.e("Tuts+ Face Detection", "eyeHeight: " + eyeHeight);
        }
        if (noseBasePoint != null && bottomMouthPoint != null) {
            float noseToMouth = bottomMouthPoint.y - noseBasePoint.y;
            Log.e("Tuts+ Face Detection", "noseToMouth: " + noseToMouth);
        }
        if (leftMouthPoint != null && rightMouthPoint != null) {
            float leftMouthToRightMouth = leftMouthPoint.x - rightMouthPoint.x;
            Log.e("Tuts+ Face Detection", "leftMouthToRightMouth: " + leftMouthToRightMouth);
        }
//        Log.e("Tuts+ Face Detection", "landmark: " + landmark.toString());
        Log.e("Tuts+ Face Detection", "Smiling: " + smilingProbability);
        Log.e("Tuts+ Face Detection", "Left eye open: " + leftEyeOpenProbability);
        Log.e("Tuts+ Face Detection", "Right eye open: " + rightEyeOpenProbability);
        Log.e("Tuts+ Face Detection", "Euler Y: " + eulerY);
        Log.e("Tuts+ Face Detection", "Euler Z: " + eulerZ);
        Log.e("Tuts+ Face Detection", "getWidth: " + face.getWidth());
        Log.e("Tuts+ Face Detection", "getHeight: " + face.getHeight());
        Log.e("Tuts+ Face Detection", "---------------------------");
    }*/
}
