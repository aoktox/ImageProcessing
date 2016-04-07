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
        // color information
        int A, R, G, B;
        int pixel;
        int Rmin=0;
        int Rmax=0;
        int Gmin=0;
        int Gmax=0;
        int Bmin=0;
        int Bmax=0;
        int Xmin=0,Xmax=0,gray;

        /*for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);

                if (x==0&&y==0){
                    Rmin=Color.red(pixel);
                    Rmax=Color.red(pixel);
                    Gmin=Color.green(pixel);
                    Gmax=Color.green(pixel);
                    Bmin=Color.blue(pixel);
                    Bmax=Color.blue(pixel);
                }
                else {
                    if (Color.red(pixel)<Rmin){
                        Rmin=Color.red(pixel);
                    }
                    if (Color.red(pixel)>Rmax){
                        Rmax=Color.red(pixel);
                    }

                    if (Color.green(pixel)<Gmin){
                        Rmin=Color.red(pixel);
                    }
                    if (Color.green(pixel)>Gmax){
                        Rmax=Color.red(pixel);
                    }

                    if (Color.blue(pixel)<Bmin){
                        Rmin=Color.red(pixel);
                    }
                    if (Color.blue(pixel)>Bmax){
                        Rmax=Color.red(pixel);
                    }
                }
            }
        }*/
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                gray = (Color.red(pixel)+Color.green(pixel)+Color.blue(pixel))/3;
                if (x==0&&y==0){
                    Xmin=gray;
                    Xmax=gray;
                }
                else {
                    if (gray<Xmin){
                        Xmin=gray;
                    }
                    if (gray>Xmax){
                        Xmax=gray;
                    }
                }
            }
        }
        int d = Xmin-Xmax;
        Log.d("nilai D",""+d);
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                gray = (Color.red(pixel)+Color.green(pixel)+Color.blue(pixel))/3;
                gray=(int)((float)(255/d)*(gray-Xmax));
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.rgb(gray,gray,gray));
            }
        }
//
//        // scan through all pixels
//        for(int x = 0; x < width; ++x) {
//            for(int y = 0; y < height; ++y) {
//                // get pixel color
//                pixel = src.getPixel(x, y);
//                // apply filtering on each channel R, G, B
//                A = Color.alpha(pixel);
//                R = (int)(Color.red(pixel) * red);
//                G = (int)(Color.green(pixel) * green);
//                B = (int)(Color.blue(pixel) * blue);
//                // set new color pixel to output bitmap
//                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
//            }
//        }
        // return final image
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
        int pixel;
        ArrayList<Integer> R = new ArrayList();
        ArrayList<Integer> G = new ArrayList();
        ArrayList<Integer> B = new ArrayList();

        int dR,dG,dB;
        int xmax=0,xmin=255;
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
//                R.add(Color.red(pixel));
//                G.add(Color.green(pixel));
//                B.add(Color.blue(pixel));
//                if (x==0&&y==0){
//                    Rmin=Color.red(pixel);
//                    Rmax=Color.red(pixel);
//                    Gmin=Color.green(pixel);
//                    Gmax=Color.green(pixel);
//                    Bmin=Color.blue(pixel);
//                    Bmax=Color.blue(pixel);
//                }
                /*if (Color.red(pixel)<Rmin){
                    Rmin=Color.red(pixel);
                }
                if (Color.red(pixel)>Rmax){
                    Rmax=Color.red(pixel);
                }

                if (Color.green(pixel)<Gmin){
                    Rmin=Color.red(pixel);
                }
                if (Color.green(pixel)>Gmax){
                    Rmax=Color.red(pixel);
                }

                if (Color.blue(pixel)<Bmin){
                    Rmin=Color.red(pixel);
                }
                if (Color.blue(pixel)>Bmax){
                    Rmax=Color.red(pixel);
                }*/
                if (Color.red(pixel)<xmin){
                    xmin=Color.red(pixel);
                }
                if (Color.red(pixel)>xmax){
                    xmax=Color.red(pixel);
                }

                if (Color.green(pixel)<xmin){
                    xmin=Color.red(pixel);
                }
                if (Color.green(pixel)>xmax){
                    xmax=Color.red(pixel);
                }

                if (Color.blue(pixel)<xmin){
                    xmin=Color.red(pixel);
                }
                if (Color.blue(pixel)>xmax){
                    xmax=Color.red(pixel);
                }
            }
        }
        /*
        int Rmax=Collections.max(R);
        int Rmin=Collections.min(R);
        int Gmax=Collections.max(G);
        int Gmin=Collections.min(G);
        int Bmax=Collections.max(B);
        int Bmin=Collections.min(B);*/
        int d =xmax-xmin;
        /*dR = Rmax-Rmin;
        dG = Gmax-Gmin;
        dB = Bmax-Bmin;
        Log.d("minmax ",""+"dR :"+dR+"dG :"+dG+"dB: "+dB); */
        Log.d("Nilai :","XMAX : "+xmax+" XMIN : "+xmin);
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                int nR = Color.red(pixel);
                int nG = Color.green(pixel);
                int nB = Color.blue(pixel);
                nR=255*(nR-xmin)/d;
                nG=255*(nG-xmin)/d;
                nB=255*(nB-xmin)/d;
                /*nR=(int)((float)(255/dR)*(nR-Rmax));
                nG=(int)((float)(255/dG)*(nG-Gmax));
                nB=(int)((float)(255/dB)*(nB-Bmax));*/


                //gray=(int)((float)(255/d)*(gray-Xmax));

                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A,nR, nG, nB));
            }
        }
        // return final image
        return bmOut;
    }

    public Bitmap TransformLog(Bitmap src){
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
                A= (R+G+B)/3;
                A=(int) (2*Math.log(A + 1));
                if(A>255) A=255;
                if(A<0) A=0;

                bmOut.setPixel(x, y, Color.rgb(A,A,A));
            }
        }
        return bmOut;
    }
}
