package htin.linnzaw.pickgalleryimage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.content.Intent.CATEGORY_BROWSABLE;

public class MainActivity extends AppCompatActivity
{
    final int GALLERY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    public void onActivityResult(int requestCode,int resultCode,Intent intent)
    {
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode)
            {
                case GALLERY_REQUEST_CODE:
                    //intent.getData returns the content URI for the selected Image
                    Uri uri = intent.getData();
                    //viewImage1(uri);
                    viewImage2(uri);
                    break;
            }
    }
    void initialize()
    {
        button1Task();
        button2Task();
        button3Task();
    }
    void button1Task()
    {
        Button button = findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pickFromGallery();
            }
        });
    }
    void button2Task()
    {
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                variousIntentAction();
            }
        });
    }
    void button3Task()
    {
        Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                variousIntentAction();
            }
        });

    }
    private void pickFromGallery()
    {
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.

        //Uri uri = Uri.fromFile(file);
        //intent.setDataAndType(uri, "image/*");


        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(Intent.createChooser(intent,"Select Pictures"),GALLERY_REQUEST_CODE);
    }
    private void viewImage1(Uri uri)
    {
        ImageView imageview = findViewById(R.id.imageview);
        imageview.setImageURI(uri);
        Log.e("Content URI", String.valueOf(uri));
    }
    private void viewImage2(Uri uri)
    {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        // Get the cursor
        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
        // Move to first row
        cursor.moveToFirst();

        //Get the column index of MediaStore.Images.Media.DATA
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        //Gets the String value in the column
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        // Set the Image in ImageView after decoding the String
        ImageView imageview = findViewById(R.id.imageview);
        imageview.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
    }
    private void variousIntentAction()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK_ACTIVITY);
        //intent.putExtra(SearchManager.QUERY,"text_to_search");
        startActivity(intent);
    }
    private void variousIntentCategory()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com/reference/android/content/Intent"));
        intent.addCategory(CATEGORY_BROWSABLE);
        startActivity(intent);
    }
    private void variousIntentFlag()
    {
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, "text_to_search");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
