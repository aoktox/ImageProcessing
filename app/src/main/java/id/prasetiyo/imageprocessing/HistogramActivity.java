package id.prasetiyo.imageprocessing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
        GraphView graph = (GraphView) findViewById(R.id.graph);
        gambarkucing = (ImageView) findViewById(R.id.gambarKucing);

        try {
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            kucing = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        gambarkucing.setImageBitmap(kucing);
//        kucing = (Bitmap) ((BitmapDrawable)gambarkucing.getDrawable()).getBitmap();
        graph.setTitle("Histogram");
        h=new float[256];
        for(i=0;i<256;i++)h[i]=0;
        int R,G,B;
        for(int x = 0; x < kucing.getWidth(); ++x) {
            for(int y = 0; y < kucing.getHeight(); ++y) {
                // get pixel color
                pixel = kucing.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // set new color pixel to output bitmap
                gray=(int)(R+G+B)/3;
                h[gray]++;
            }
        }
        DataPoint x[];
        x = new DataPoint[256];
        for(i=0;i<256;i++){
            x[i]= new DataPoint(i,h[i]);
        }

        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(x);
        graph.addSeries(series);
//        graph.getViewport().setScalable(true);
//        graph.getViewport().setScrollable(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(256);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxY(15000);
    }
}