package id.prasetiyo.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;

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
                bmOut.setPixel(x, y, Color.rgb(R, G, B));
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

                R = Color.red(pixel);
                R = 255-R;
                //R = (R>255)?255:(R<0)?0:R;

                G = Color.green(pixel);
                G = 255-G;
                //G = (G>255)?255:(G<0)?0:G;

                B = Color.blue(pixel);
                B = 255-B;
                //B = (B>255)?255:(B<0)?0:B;

                bmOut.setPixel(x, y, Color.rgb(R, G, B));
            }
        }
        return bmOut;
    }

    public Bitmap autoLevel(Bitmap src) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        //int A;//, R, G, B;
        int pixel;
        int rmax,rmin,gmax,gmin,bmax,bmin;
        ArrayList<Integer> R = new ArrayList();
        ArrayList<Integer> G = new ArrayList();
        ArrayList<Integer> B = new ArrayList();

        for(int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel=src.getPixel(x,y);
                R.add(Color.red(pixel));
                G.add(Color.green(pixel));
                B.add(Color.blue(pixel));
            }
        }

        rmax=Collections.max(R);
        rmin=Collections.min(R);
        gmax=Collections.max(G);
        gmin=Collections.min(G);
        bmax=Collections.max(B);
        bmin=Collections.min(B);

        for(int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixel=src.getPixel(x,y);
                int r=255/(rmax-rmin)*(Color.red(pixel)-rmin);
                int g=255/(gmax-gmin)*(Color.green(pixel)-gmin);
                int b=255/(bmax-bmin)*(Color.blue(pixel)-bmin);
                bmOut.setPixel(x, y, Color.rgb(r, g, b));
            }
        }
        Log.d("minmax ",""+rmax+" "+rmin+" "+gmax+" "+gmin+" "+bmax+" "+bmin);
        return bmOut;
    }

    public Bitmap autoLevelRGB(Bitmap src) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A;//, R, G, B;
        int pixel,xmax,xmin,gray;
        ArrayList<Integer> R = new ArrayList();
        ArrayList<Integer> G = new ArrayList();
        ArrayList<Integer> B = new ArrayList();
        ArrayList<Integer> xG = new ArrayList();

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                gray = (Color.red(pixel)+Color.green(pixel)+Color.blue(pixel))/3;
                xG.add(gray);
            }
        }
        xmax=Collections.max(xG);
        xmin=Collections.min(xG);
        int d = xmax-xmin;
        int rmin=Collections.min(R);
        int gmin=Collections.min(G);
        int bmin=Collections.min(B);
        Log.d("nilai D",""+d);
        int r,g,b;
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                r=255/(xmax-xmin)*(Color.red(pixel)-rmin);
                g=255/(xmax-xmin)*(Color.green(pixel)-gmin);
                b=255/(xmax-xmin)*(Color.blue(pixel)-bmin);
                bmOut.setPixel(x, y, Color.rgb(r,g,b));
            }
        }
        // return final image
        return bmOut;
    }

    public Bitmap TransformLog(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B,gray,c=20;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                gray= (R+G+B)/3;
                gray=(int) (c * Math.log(gray+1));
                if (gray>255)gray=255;
                if(gray<0) gray=0;
                //A=(int) (2*Math.log(A + 1));
                bmOut.setPixel(x, y, Color.rgb(gray,gray,gray));
            }
        }
        return bmOut;
    }

    public Bitmap TransformInverseLog(Bitmap src){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B,gray,c=20,max=255;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                gray= (R+G+B)/3;
                gray=(int) (c * Math.log(max-(gray+1)));
                if (gray>255)gray=255;
                if(gray<0) gray=0;
                //A=(int) (2*Math.log(A + 1));
                bmOut.setPixel(x, y, Color.rgb(gray,gray,gray));
            }
        }
        return bmOut;
    }

    public Bitmap NTH(Bitmap src,int c,int yc){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B,gray;
        int pixel;
        double thc,thy;
        thc=c/100;
        thy=yc/100;
        Log.d("C dan Y",""+c+" "+yc);
        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                gray= (R+G+B)/3;
                gray=(int) (thc*Math.pow(gray,thy));
                if (gray>255)gray=255;
                if(gray<0) gray=0;
                //A=(int) (2*Math.log(A + 1));
                bmOut.setPixel(x, y, Color.rgb(gray,gray,gray));
            }
        }
        return bmOut;
    }

    public Bitmap NTHRoot(Bitmap src,int c,int yc){
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B,gray;
        int pixel;
        double thc,thy;
        thc=c/100;
        thy=yc/100;
        Log.d("C dan Y",""+c+" "+yc);
        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                gray= (R+G+B)/3;
                gray =(int) (thc*Math.pow(gray,(1/thy)));
                if (gray>255)gray=255;
                if(gray<0) gray=0;
                bmOut.setPixel(x, y, Color.rgb(gray,gray,gray));
            }
        }
        return bmOut;
    }

    public Bitmap TransparentGan(Bitmap src1,double op1,Bitmap src2,double op2){
        int width = src1.getWidth();
        int height = src1.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src1.getConfig());
        op1=op1/100;
        op2=op2/100;
        double R1, G1, B1,R2, G2, B2;
        int r,g,b;
        int pixel;
        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src1.getPixel(x, y);
                R1 = Color.red(pixel);
                G1 = Color.green(pixel);
                B1 = Color.blue(pixel);

                pixel = src2.getPixel(x, y);
                R2 = Color.red(pixel);
                G2 = Color.green(pixel);
                B2 = Color.blue(pixel);
                /*r=(int)((op1/100)*R1)+((op2/100)*R2);
                g=(int)((op1/100)*G1)+((op2/100)*G2);
                b=(int)((op1/100)*B1)+((op2/100)*B2);*/
                r=(int)((op1*R1)+(op2*R2));
                g=(int)((op1*G1)+(op2*G2));
                b=(int)((op1*B1)+(op2*B2));
                bmOut.setPixel(x, y, Color.rgb(r,g,b));
            }
        }
        return bmOut;
    }
}
