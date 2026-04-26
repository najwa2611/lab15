package com.example.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app.adapter.EtudiantAdapter;
import com.example.app.classes.Etudiant;
import com.example.app.service.EtudiantService;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nom, prenom, id;
    private Button add, rechercher, supprimer;
    private TextView res;
    private RecyclerView recyclerView;
    private EtudiantService etudiantService;
    private EtudiantAdapter adapter;

    void clear() {
        nom.setText("");
        prenom.setText("");
    }

    void loadData() {
        List<Etudiant> etudiants = etudiantService.findAll();
        adapter.updateList(etudiants);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etudiantService = new EtudiantService(this);

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        add = findViewById(R.id.bn);
        id = findViewById(R.id.id);
        rechercher = findViewById(R.id.load);
        supprimer = findViewById(R.id.delete);
        res = findViewById(R.id.res);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EtudiantAdapter(etudiantService.findAll(), etudiant -> {
            id.setText(String.valueOf(etudiant.getId()));
            res.setText(etudiant.getNom() + " " + etudiant.getPrenom());
        });
        recyclerView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nom.getText().toString().isEmpty() || prenom.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                etudiantService.create(new Etudiant(nom.getText().toString(), prenom.getText().toString()));
                clear();
                loadData();
                Toast.makeText(MainActivity.this, "Étudiant ajouté", Toast.LENGTH_SHORT).show();
            }
        });

        rechercher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    res.setText("");
                    Toast.makeText(MainActivity.this, "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Etudiant e = etudiantService.findById(Integer.parseInt(txt));
                if (e == null) {
                    res.setText("");
                    Toast.makeText(MainActivity.this, "Étudiant introuvable", Toast.LENGTH_SHORT).show();
                    return;
                }
                res.setText(e.getNom() + " " + e.getPrenom());
            }
        });

        supprimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = id.getText().toString().trim();
                if (txt.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Saisir un id", Toast.LENGTH_SHORT).show();
                    return;
                }
                Etudiant e = etudiantService.findById(Integer.parseInt(txt));
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Aucun étudiant à supprimer", Toast.LENGTH_SHORT).show();
                    return;
                }
                etudiantService.delete(e);
                res.setText("");
                id.setText("");
                loadData();
                Toast.makeText(MainActivity.this, "Étudiant supprimé", Toast.LENGTH_SHORT).show();
            }
        });
    }
}