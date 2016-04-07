package id.prasetiyo.imageprocessing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private final static int RESULT_LOAD_IMAGE = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private Bitmap gambar;
    private Image_tools Toolsku;
    private BitmapDrawable drawable;
    private File imageFile;
    private ImageView image_asli,image_hasil;
    private Uri tempUri;
    private View view;

    //MENU
    private Button blk_vert,blk_horiz,blk_90,blk_180;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //OWN SCRIPT
        Toolsku = new Image_tools();
        image_asli = (ImageView) findViewById(R.id.image_asli);
        image_hasil = (ImageView) findViewById(R.id.image_hasil);
        initializeButton();
    }
    private void initializeButton(){
        fab = (FloatingActionButton)findViewById(R.id.fab_main);
        fab1 = (FloatingActionButton)findViewById(R.id.fab_cam);
        fab2 = (FloatingActionButton)findViewById(R.id.fab_gallery);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;
        final Context context = this;
        Bitmap tmp;
        //BALIK
        if (id == R.id.blk_vert) {
            try{
                drawable = (BitmapDrawable) image_asli.getDrawable();
                drawable = Toolsku.flipV(drawable);
                tmp=drawable.getBitmap();
                Toolsku.draw(image_hasil, tmp);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.blk_horiz) {
            try{
                drawable = (BitmapDrawable) image_asli.getDrawable();
                drawable = Toolsku.flipH(drawable);
                tmp=drawable.getBitmap();
                Toolsku.draw(image_hasil, tmp);
            }catch (Exception e){
//                Snackbar.make(View, ""+e.getMessage(), Snackbar.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.blk_90) {
            try {
                tmp = ((BitmapDrawable)image_asli.getDrawable()).getBitmap();
                Toolsku.draw(image_hasil, Toolsku.rotateImage(tmp, 90));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.blk_180) {
            try {
                tmp = ((BitmapDrawable)image_asli.getDrawable()).getBitmap();
                Toolsku.draw(image_hasil, Toolsku.rotateImage(tmp,180));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //FILTER
        else if (id == R.id.filter_R) {
            try {
                    /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                Toolsku.draw(image_hasil,Toolsku.setColorFilter(gambar,1,0,0));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.filter_G) {
            try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                Toolsku.draw(image_hasil, Toolsku.setColorFilter(gambar, 0, 1, 0));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.filter_B) {
            try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                Toolsku.draw(image_hasil, Toolsku.setColorFilter(gambar, 0, 0, 1));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //GRAYSCALE
        else if (id == R.id.gray_Grayscale) {
            try {
                /*tmp = ((BitmapDrawable)imageView.getDrawable()).getBitmap();*/
                Toolsku.draw(image_hasil, Toolsku.toGray(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.gray_q16) {
            try {
                Toolsku.draw(image_hasil, Toolsku.kuantisasi(gambar, 16));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.gray_q8) {
            try {
                Toolsku.draw(image_hasil, Toolsku.kuantisasi(gambar, 8));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.gray_q4) {
            try {
                Toolsku.draw(image_hasil, Toolsku.kuantisasi(gambar, 4));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.gray_q2) {
            try {
                Toolsku.draw(image_hasil, Toolsku.kuantisasi(gambar, 2));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //Biner
        else if (id == R.id.biner_rata) {
            try {
                Toolsku.draw(image_hasil, Toolsku.toBinerRata(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.biner_tengah) {
            try {
                Toolsku.draw(image_hasil, Toolsku.toBiner128(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //Enhancement
        else if (id == R.id.enhancement_C) {
            try {
                Toolsku.draw(image_hasil, Toolsku.contrast(gambar, 2));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.enhancement_B) {
            try {
                Toolsku.draw(image_hasil, Toolsku.brightness(gambar, 50));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.enhancement_auto) {
            try {
                Toolsku.draw(image_hasil, Toolsku.autoLevel(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        //Transformasi
        else if (id == R.id.transform_negative) {
            try {
                Toolsku.draw(image_hasil, Toolsku.invers(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.transform_log) {
            try {
                Toolsku.draw(image_hasil, Toolsku.TransformLog(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
        else if (id == R.id.transform_inverse_log) {
            try {
                Toolsku.draw(image_hasil, Toolsku.autoLevelRGB(gambar));
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.transform_nth_pwr) {
            //TO DO
        }
        else if (id == R.id.transform_nth_root) {
            //TO DO
        }
        //LAIN2
        else if (id == R.id.btn_histogram) {
            try {
                Bitmap kirim;
                kirim = (Bitmap) ((BitmapDrawable)image_hasil.getDrawable()).getBitmap();
                i = new Intent(context, HistogramActivity.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                kirim.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                i.putExtra("image", byteArray);
                startActivity(i);
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        else if (id == R.id.btn_transparent) {
            //TO DO
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent i;
        switch (id){
            case R.id.fab_main:
                animateFAB();
                break;
            case R.id.fab_cam:
                i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"testsdgdh.jpg");
                tempUri=Uri.fromFile(imageFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                i.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
                startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
                break;
            case R.id.fab_gallery:
                i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
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
                    gambar= BitmapFactory.decodeFile(picturePath);
                    simpanGambar(gambar);
                    //gambar = Bitmap.createScaledBitmap(gambar, (int) (gambar.getWidth() * 0.5), (int) (gambar.getHeight() * 0.5), true);
                    Toolsku.draw(image_asli, gambar);
                    Toolsku.draw(image_hasil,gambar);
                }catch (Exception e){
                    Log.d("Load Gallery",""+e.getMessage());
                }
            }
            else if(requestCode == REQUEST_IMAGE_CAPTURE){
                try{
                    gambar=BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(tempUri));
                    simpanGambar(gambar);
                    imageFile.delete();
                    Toolsku.draw(image_asli, gambar);
                    Toolsku.draw(image_hasil, gambar);

                }catch (Exception e){
                    Log.d("Kamera",""+e.getMessage());
                }
            }
        }
    }

    private void simpanGambar(Bitmap src){
        double pengali;
        int newHeight,newWidth;
        pengali=src.getWidth()/500;
        //Log.d("Hasil asli"," Widht="+src.getWidth()+" Height="+src.getHeight());
        newHeight=(int)(gambar.getHeight()/pengali);
        newWidth=(int)(gambar.getWidth()/pengali);
        //Log.d("Hasil","Pengali="+pengali+" Widht="+newWidth+" Height="+newHeight);
        gambar = Bitmap.createScaledBitmap(gambar,newWidth,newHeight,true);
    }
}
