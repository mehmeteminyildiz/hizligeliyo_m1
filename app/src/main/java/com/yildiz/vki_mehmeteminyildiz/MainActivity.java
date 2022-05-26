package com.yildiz.vki_mehmeteminyildiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout tilBoy, tilKilo, tilIdeal, tilDurum;
    private CheckBox checkBay, checkBayan;
    private Button btnHesapla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialDesign();
        setListeners();


    }

    private void setListeners() {
        checkBay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // BAY seçildiyse
                    checkBayan.setChecked(false);
                }
            }
        });


        checkBayan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // BAYAN seçildiyse
                    checkBay.setChecked(false);
                }
            }
        });

        btnHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmptyState()) { // TRUE ise devam
                    vkiHesapla();
                }


            }
        });

        tilBoy.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilBoy.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilKilo.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilKilo.setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkEmptyState() {
        if (tilBoy.getEditText().getText().toString().trim().isEmpty()) {
            tilBoy.setError("Gerekli");
            return false;
        } else if (tilKilo.getEditText().getText().toString().trim().isEmpty()) {
            tilKilo.setError("Gerekli");
            return false;
        } else if (checkBay.isChecked() == false && checkBayan.isChecked() == false) {
            Toast.makeText(MainActivity.this, "Cinsiyet Seçiniz", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void vkiHesapla() {
        double kilo = Double.parseDouble(tilKilo.getEditText().getText().toString().trim());
        double boy = Double.parseDouble(tilBoy.getEditText().getText().toString().trim()); // cm

        boy = boy / 100;
        double vki = kilo / (boy * boy);
        double idealKilo = idealKiloHesapla(boy, kilo);

        if (vki < 18.5 && vki >= 0) { // ZAYIF
            tilDurum.getEditText().setText("Zayıf");
            tilDurum.getEditText().setTextColor(getResources().getColor(R.color.zayif));
        } else if (vki >= 18.5 && vki <= 24.9) { // NORMAL
            tilDurum.getEditText().setText("Normal");
            tilDurum.getEditText().setTextColor(getResources().getColor(R.color.normal));

        } else if (vki >= 25 && vki <= 29.9) { // Fazla Kilolu
            tilDurum.getEditText().setText("Fazla Kilolu");
            tilDurum.getEditText().setTextColor(getResources().getColor(R.color.fazla_kilolu));

        } else if (vki >= 30 && vki <= 39.9) { // Obeez
            tilDurum.getEditText().setText("Obez");
            tilDurum.getEditText().setTextColor(getResources().getColor(R.color.obez));

        } else if (vki >= 40) { // İleri Obez
            tilDurum.getEditText().setText("İleri Derece Obez");
            tilDurum.getEditText().setTextColor(getResources().getColor(R.color.ileri_obez));

        }

        String idealKiloYazisi = new DecimalFormat("0.0").format(idealKilo);
        tilIdeal.getEditText().setText(idealKiloYazisi);

    }

    private double idealKiloHesapla(double boy, double kilo) { // boy: metre olarak geldi
        double idealKilo = 0;

        if (checkBay.isChecked()) {
            idealKilo = 50 + (2.3 * ((boy * 100 / 2.54) - 60));

        } else if (checkBayan.isChecked()) {
            idealKilo = 45.5 + (2.3 * ((boy * 100 / 2.54) - 60));

        }
        return idealKilo;


    }

    private void initialDesign() {
        tilBoy = findViewById(R.id.tilBoy);
        tilKilo = findViewById(R.id.tilKilo);
        tilIdeal = findViewById(R.id.tilIdeal);
        tilDurum = findViewById(R.id.tilDurum);
        checkBay = findViewById(R.id.checkBay);
        checkBayan = findViewById(R.id.checkBayan);
        btnHesapla = findViewById(R.id.btnHesapla);
    }
}