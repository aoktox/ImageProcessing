package id.prasetiyo.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.widget.ImageView;

/**
 * Created by aoktox on 14/03/16.
 */
public class Image_tools {
    public BitmapDrawable flipH(BitmapDrawable d)
    {
        Matrix m = new Matrix();
        m.preScale(-1, 1);
        Bitmap src = d.getBitmap();
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return new BitmapDrawable(dst);
    }

    public BitmapDrawable flipV(BitmapDrawable d)
    {
        Matrix m = new Matrix();
        m.preScale(1, -1);
        Bitmap src = d.getBitmap();
        Bitmap dst = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), m, false);
        dst.setDensity(DisplayMetrics.DENSITY_DEFAULT);
        return new BitmapDrawable(dst);
    }

    public void draw(ImageView v,Bitmap b){
        v.setImageBitmap(b);
    }

    public Bitmap setColorFilter(Bitmap src, double red, double green, double blue) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * red);
                G = (int)(Color.green(pixel) * green);
                B = (int)(Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        // return final image
        return bmOut;
    }

    public Bitmap toGray(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);

                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // set new color pixel to output bitmap
                A= (R+G+B)/3;

                bmOut.setPixel(x, y, Color.rgb(A,A,A));
            }
        }
        return bmOut;
    }

    public Bitmap rotateImage(Bitmap src,float derajat){
        Matrix m = new Matrix();
        m.postRotate(derajat);
        return Bitmap.createBitmap(src,0,0,src.getWidth(),src.getHeight(),m,true);
    }

    public Bitmap toBiner128(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // set new color pixel to output bitmap
                A= (((R+G+B)/3)<128)?1:255;

                bmOut.setPixel(x, y,Color.rgb(A,A,A));
            }
        }
        return bmOut;
    }

    public Bitmap toBinerRata(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int t=0, R=0, G=0, B=0, rata,A;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R +=Color.red(pixel);
                G += Color.green(pixel);
                B += Color.blue(pixel);
                t++;
            }
        }
        rata=R/t;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // set new color pixel to output bitmap
                A= (((R+G+B)/3)<rata)?1:255;

                bmOut.setPixel(x, y,Color.rgb(A,A,A));
            }
        }
        return bmOut;
    }

    public Bitmap kuantisasi(Bitmap src,int q){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B,Q;
        int pixel;
        int th;

        th=256/q;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);

                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // set new color pixel to output bitmap
                A= (R+G+B)/3;
                Q=th*(A/th);
                bmOut.setPixel(x, y, Color.rgb(Q,Q,Q));
            }
        }
        return bmOut;
    }

    public Bitmap brightness(Bitmap src,int brightnessVal){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);

                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // set new color pixel to output bitmap
                //A= (R+G+B)/3;
                R= ((R+brightnessVal)>255)?255:(R+brightnessVal);
                G= ((G+brightnessVal)>255)?255:(G+brightnessVal);
                B= ((B+brightnessVal)>255)?255:(B+brightnessVal);
                bmOut.setPixel(x, y, Color.rgb(R,G,B));
            }
        }
        return bmOut;
    }

    public Bitmap contrast(Bitmap src,double contrastVal){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        double contrast = Math.pow((100 + contrastVal) / 100, 2);
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);

                A = Color.alpha(pixel);
                R = Color.red(pixel);
                //R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                R = R*(int)contrastVal;
                R = (R>255)?255:(R<0)?0:R;

                G = Color.green(pixel);
                //G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                G = G*(int)contrastVal;
                G = (G>255)?255:(G<0)?0:G;

                B = Color.blue(pixel);
                //B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                B = B*(int)contrastVal;
                B = (B>255)?255:(B<0)?0:B;

                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        return bmOut;
    }

    public Bitmap invers(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);

                A = Color.alpha(pixel);
                R = Color.red(pixel);
                R = 255-R;
                //R = (R>255)?255:(R<0)?0:R;

                G = Color.green(pixel);
                G = 255-G;
                //G = (G>255)?255:(G<0)?0:G;

                B = Color.blue(pixel);
                B = 255-B;
                //B = (B>255)?255:(B<0)?0:B;

                bmOut.setPixel(x, y, Color.argb(A,R,G,B));
            }
        }
        return bmOut;
    }
}
