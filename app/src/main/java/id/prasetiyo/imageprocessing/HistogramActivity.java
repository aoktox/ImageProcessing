package id.prasetiyo.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class HistogramActivity extends AppCompatActivity {
    private ImageView gambarkucing;
    private int i,j,A,gray;
    Bitmap kucing;
    float h[];
    int pixel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        GraphView graphGray = (GraphView) findViewById(R.id.graphGray);
        GraphView graphRGB = (GraphView) findViewById(R.id.graphRGB);
        gambarkucing = (ImageView) findViewById(R.id.gambarKucing);

        try {
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            kucing = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        gambarkucing.setImageBitmap(kucing);
//        kucing = (Bitmap) ((BitmapDrawable)gambarkucing.getDrawable()).getBitmap();
        graphGray.setTitle("Histogram GRAYSCALE");
        graphRGB.setTitle("Histogram RGB");
        h=new float[256];
        for(i=0;i<256;i++)h[i]=0;

        ArrayList<Integer> R = new ArrayList<Integer>();
        ArrayList<Integer> G = new ArrayList<Integer>();
        ArrayList<Integer> B = new ArrayList<Integer>();
        int[] hR = new int[256];
        int[] hG = new int[256];
        int[] hB = new int[256];
        for(int x = 0; x < kucing.getWidth(); ++x) {
            for(int y = 0; y < kucing.getHeight(); ++y) {
                // get pixel color
                pixel = kucing.getPixel(x, y);
                gray=(int)(Color.red(pixel)+Color.green(pixel)+Color.blue(pixel))/3;
                h[gray]++;
                hR[(int)Color.red(pixel)]++;
                hG[(int)Color.green(pixel)]++;
                hB[(int)Color.blue(pixel)]++;
            }
        }
        DataPoint x[],gR[],gG[],gB[];
        x = new DataPoint[256];
        gR = new DataPoint[256];
        gG = new DataPoint[256];
        gB = new DataPoint[256];
        for(i=0;i<256;i++){
            x[i]= new DataPoint(i,h[i]);
        }
        for(i=0;i<256;i++){
            gR[i]= new DataPoint(i,hR[i]);
        }
        for(i=0;i<256;i++){
            gG[i]= new DataPoint(i,hG[i]);
        }
        for(i=0;i<256;i++){
            gB[i]= new DataPoint(i,hB[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(x);
        LineGraphSeries<DataPoint> seriesR = new LineGraphSeries<DataPoint>(gR);
        LineGraphSeries<DataPoint> seriesG = new LineGraphSeries<DataPoint>(gG);
        LineGraphSeries<DataPoint> seriesB = new LineGraphSeries<DataPoint>(gB);
        seriesR.setColor(Color.RED);
        seriesG.setColor(Color.GREEN);
        seriesB.setColor(Color.BLUE);

        graphGray.addSeries(series);
        graphGray.getViewport().setXAxisBoundsManual(true);
        graphGray.getViewport().setMaxX(256);
        //graphGray.getViewport().setYAxisBoundsManual(true);
        //graphGray.getViewport().setMaxY(10000);

        graphRGB.addSeries(seriesR);
        graphRGB.addSeries(seriesG);
        graphRGB.addSeries(seriesB);

//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScrollable(true);
        graphRGB.getViewport().setXAxisBoundsManual(true);
        graphRGB.getViewport().setMaxX(256);
        //graphRGB.getViewport().setYAxisBoundsManual(true);
        //graphRGB.getViewport().setMaxY(15000);
    }
}