package com.example.galleryapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int CATEGORY_REQUEST_CODE = 1;
    private RecyclerView recyclerView;
    private PhotoAdapter adapter;
    private ArrayList<Photo> photoList;
    private List<String> categoryList;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        photoList = new ArrayList<>();
        categoryList = new ArrayList<>();

        adapter = new PhotoAdapter(this, photoList);
        recyclerView.setAdapter(adapter);

        Button addPhotoButton = findViewById(R.id.add_photo_button);
        Button addCategoryButton = findViewById(R.id.add_category_button);
        spinner = findViewById(R.id.spinner);

        addPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CATEGORY_REQUEST_CODE);
            }
        });

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kategori ekleme işlemi
                addCategory();
            }
        });

        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CATEGORY_REQUEST_CODE && resultCode == RESULT_OK) {
            String category = data.getStringExtra("category");
            String photoUrl = data.getData().toString();
            addPhoto(category, photoUrl);
        }
    }

    private void addPhoto(String category, String photoUrl) {
        Photo photo = new Photo("Fotoğraf", category, photoUrl);
        photoList.add(photo);
        adapter.notifyDataSetChanged();
    }


    private void addCategory() {
        // Kategori ekleme işlemi
        // Kullanıcı tarafından girilen yeni kategori, kategoriListesine eklenir.
        // Spinner yeniden yüklenir ve yeni kategori de dahil edilir.
    }

    private void loadSpinnerData() {
        // Spinner verilerinin yüklenmesi işlemi
        // Önceden kaydedilmiş kategori verileri, kategoriListesi kullanılarak spinner'a yüklenir.
        // Spinner'a yeni bir kategori eklendiğinde, bu kategori de spinner'a dahil edilir.
        categoryList.add("Kategori 1");
        categoryList.add("Kategori 2");
        categoryList.add("Kategori 3");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Spinner'da seçilen kategoriye göre fotoğraf listesi yenilenir ve RecyclerView güncellenir.
        String selectedCategory = parent.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(), "Seçilen kategori: " + selectedCategory, Toast.LENGTH_SHORT).show();

        List<Photo> filteredPhotos = new ArrayList<>();
        for (Photo photo : photoList) {
            if (photo.getCategory().equals(selectedCategory)) {
                filteredPhotos.add(photo);
            }
        }

        // RecyclerView güncellenir.
        adapter.filterList(filteredPhotos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Bir şey yapılmaz.
    }
}