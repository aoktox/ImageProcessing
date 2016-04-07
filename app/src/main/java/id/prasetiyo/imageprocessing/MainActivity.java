package id.prasetiyo.imageprocessing;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private ImageView imageView;
    private final static int RESULT_LOAD_IMAGE = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    CoordinatorLayout view;
    Button balik_H, balik_V,red,green,blue,reset, gray_btn, rotate90,rotate180,biner_rata,biner_128,q16,q4,q2,contrast_btn,brightness_btn,invers_btn;
    Bitmap gambar;
    Image_tools Toolsku;
    private BitmapDrawable drawable;
    private ProgressBar spinner;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ku, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        final Context context = this;
        Intent intent;
        switch (item.getItemId()) {
            case R.id.histogram_gan:
//                intent = new Intent(context, HistogramActivity.class);
//                startActivity(intent);
                return true;
            case R.id.transparan_gan:
//                intent = new Intent(context, TransparentActivity.class);
//                startActivity(intent);
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        view= (CoordinatorLayout) findViewById(R.id.coor);
        Toolsku = new Image_tools();
        initializeButton();
    }

    private void initializeButton(){
        spinner = (ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        balik_H = (Button) findViewById(R.id.balikH_btn);
        balik_V = (Button) findViewById(R.id.balikV_btn);
        imageView = (ImageView) findViewById(R.id.imageView);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab1 = (FloatingActionButton)findViewById(R.id.fab1);
        fab2 = (FloatingActionButton)findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        red = (Button) findViewById(R.id.red_btn);
        green = (Button) findViewById(R.id.gren_btn);
        blue = (Button) findViewById(R.id.blue_btn);
        reset = (Button) findViewById(R.id.reset_btn);
        gray_btn = (Button) findViewById(R.id.gray_btn);
        rotate90=(Button) findViewById(R.id.rotate90_btn);
        rotate180=(Button) findViewById(R.id.rotate180_btn);
        biner_rata=(Button) findViewById(R.id.Biner1_btn);
        biner_128=(Button) findViewById(R.id.Biner2_btn);
        q16=(Button) findViewById(R.id.q16_btn);
        q4=(Button) findViewById(R.id.q4_btn);
        q2=(Button) findViewById(R.id.q2_btn);
        contrast_btn=(Button) findViewById(R.id.contrast_btn);
        brightness_btn=(Button) findViewById(R.id.brightness_btn);
        invers_btn=(Button) findViewById(R.id.invers_btn);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        balik_V.setOnClickListener(this);
        balik_H.setOnClickListener(this);
        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        reset.setOnClickListener(this);
        gray_btn.setOnClickListener(this);
        rotate90.setOnClickListener(this);
        rotate180.setOnClickListener(this);
        biner_rata.setOnClickListener(this);
        biner_128.setOnClickListener(this);
        q16.setOnClickListener(this);
        q4.setOnClickListener(this);
        q2.setOnClickListener(this);
        contrast_btn.setOnClickListener(this);
        brightness_btn.setOnClickListener(this);
        invers_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Bitmap tmp;
        switch (id){
            case R.id.fab:
                animateFAB();
                break;
            case R.id.fab1:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
                break;
            case R.id.fab2:
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.balikH_btn:
                try{
                    drawable = (BitmapDrawable) imageView.getDrawable();
                    drawable = Toolsku.flipH(drawable);
                    tmp=drawable.getBitmap();
                    Toolsku.draw(imageView, tmp);
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.balikV_btn:
                try{
                    drawable = (BitmapDrawable) imageView.getDrawable();
                    drawable = Toolsku.flipV(drawable);
                    tmp=drawable.getBitmap();
                    Toolsku.draw(imageView, tmp);
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.red_btn:
                try {
                    /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                    Toolsku.draw(imageView,Toolsku.setColorFilter(gambar,1,0,0));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.gren_btn:
                try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                    Toolsku.draw(imageView, Toolsku.setColorFilter(gambar, 0, 1, 0));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.blue_btn:
                try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                    Toolsku.draw(imageView, Toolsku.setColorFilter(gambar, 0, 0, 1));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.reset_btn:
                try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                    Toolsku.draw(imageView, gambar);
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.gray_btn:
                try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                    Toolsku.draw(imageView, Toolsku.toGray(gambar));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.rotate90_btn:
                try {
                    tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    Toolsku.draw(imageView, Toolsku.rotateImage(tmp, 90));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.rotate180_btn:
                try {
                tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                    Toolsku.draw(imageView, Toolsku.rotateImage(tmp,180));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.Biner1_btn:
                //biner rata rata
                try {
                    Toolsku.draw(imageView, Toolsku.toBinerRata(gambar));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.Biner2_btn:
                //biner 128
                try {
                    Toolsku.draw(imageView, Toolsku.toBiner128(gambar));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.q16_btn:
                try {
                    Toolsku.draw(imageView, Toolsku.kuantisasi(gambar, 16));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.q4_btn:
                try {
                    Toolsku.draw(imageView, Toolsku.kuantisasi(gambar, 4));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.q2_btn:
                try {
                    Toolsku.draw(imageView, Toolsku.kuantisasi(gambar,2));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.contrast_btn:
                try {
                    Toolsku.draw(imageView, Toolsku.contrast(gambar, 2));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.brightness_btn:
                try {
                    Toolsku.draw(imageView, Toolsku.brightness(gambar, 50));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
            case R.id.invers_btn:
                try {
                    Toolsku.draw(imageView, Toolsku.invers(gambar));
                }catch (Exception e){
                    Snackbar.make(view, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }

    public void animateFAB(){
        if(isFabOpen){
            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if (requestCode == RESULT_LOAD_IMAGE){
                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    gambar=BitmapFactory.decodeFile(picturePath);
                    Toolsku.draw(imageView,gambar);
                }catch (Exception e){
                    Log.d("Load Gallery",""+e.getMessage());
                }
            }
            else if(requestCode == REQUEST_IMAGE_CAPTURE){
                try{
                    Bundle extras = data.getExtras();
                    gambar = (Bitmap) extras.get("data");
                    Toolsku.draw(imageView,gambar);
                }catch (Exception e){
                    Log.d("Kamera",""+e.getMessage());
                }
            }
        }
    }
}

/*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/