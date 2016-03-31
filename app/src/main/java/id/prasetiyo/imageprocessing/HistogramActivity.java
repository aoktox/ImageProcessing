package id.prasetiyo.imageprocessing;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

public class HistogramActivity extends AppCompatActivity {

    private final static int RESULT_LOAD_IMAGE = 1;
    private final static int REQUEST_IMAGE_CAPTURE = 2;
    private ImageView gambarView;
    private Bitmap gambar;
    private Image_tools Toolsku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);
        Toolsku = new Image_tools();
        gambarView = (ImageView) findViewById(R.id.gambar_asli);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.take_act, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.load_img:
                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                return true;
            case R.id.capture_img:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                return true;
        }
        return false;
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
                    Toolsku.draw(gambarView,gambar);
                }catch (Exception e){
                    Log.d("Load Gallery", "" + e.getMessage());
                }
            }
            else if(requestCode == REQUEST_IMAGE_CAPTURE){
                try{
                    Bundle extras = data.getExtras();
                    gambar = (Bitmap) extras.get("data");
                    Toolsku.draw(gambarView,gambar);
                }catch (Exception e){
                    Log.d("Kamera",""+e.getMessage());
                }
            }
        }
    }
}
