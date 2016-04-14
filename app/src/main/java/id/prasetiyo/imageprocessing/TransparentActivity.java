package id.prasetiyo.imageprocessing;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

public class TransparentActivity extends AppCompatActivity implements View.OnClickListener{

    Button load1,load2,generate;
    LinearLayout view1,view2;
    ImageView img1,img2,img_final;
    Bitmap gambar1,gambar2;
    int op1,op2;
    EditText txt_op1,txt_op2;
    private Image_tools Toolsku;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        load1 = (Button) findViewById(R.id.transparent_load_1);
        load2 = (Button) findViewById(R.id.transparent_load_2);
        generate = (Button) findViewById(R.id.transparent_generate);
        Toolsku = new Image_tools();
        load1.setOnClickListener(this);
        load2.setOnClickListener(this);
        txt_op1=(EditText)findViewById(R.id.transparent_img_1_opacity);
        txt_op2=(EditText)findViewById(R.id.transparent_img_2_opacity);
        generate.setOnClickListener(this);
        view1 = (LinearLayout) findViewById(R.id.linearView1);
        view2 = (LinearLayout) findViewById(R.id.linearView2);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img_final = (ImageView) findViewById(R.id.img_final);
        view1.setVisibility(View.GONE);
        view2.setVisibility(View.GONE);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        Intent i;
        switch (id){
            case R.id.transparent_load_1:
                i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 101);
                break;
            case R.id.transparent_load_2:
                i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 102);
                break;
            case R.id.transparent_generate:
                try{
                    op1 = Integer.parseInt(txt_op1.getText().toString());
                    op2 = Integer.parseInt(txt_op2.getText().toString());
                    Toolsku.draw(img_final,Toolsku.TransparentGan(gambar1,op1,gambar2,op2));
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if (requestCode == 101){
                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    gambar1= BitmapFactory.decodeFile(picturePath);
                    simpanGambar(gambar1,101);
                    Toolsku.draw(img1, gambar1);
                    load1.setVisibility(View.GONE);
                    view1.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Log.d("Load Gallery",""+e.getMessage());
                }
            }
            if (requestCode == 102){
                try{
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    gambar2= BitmapFactory.decodeFile(picturePath);
                    simpanGambar(gambar2,102);
                    Toolsku.draw(img2, gambar2);
                    load2.setVisibility(View.GONE);
                    view2.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    Log.d("Load Gallery",""+e.getMessage());
                }
            }
        }
    }

    private void simpanGambar(Bitmap src,int id){
        double pengali;
        int newHeight,newWidth;
        pengali=src.getWidth()/500;
        newHeight=(int)(src.getHeight()/pengali);
        newWidth=(int)(src.getWidth()/pengali);
        if (id==101) gambar1= Bitmap.createScaledBitmap(src,newWidth,newHeight,true);
        if (id==102) gambar2= Bitmap.createScaledBitmap(src,newWidth,newHeight,true);
    }
}
