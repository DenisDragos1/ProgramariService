package com.example.proiectservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FloatingActionButton add = findViewById(R.id.addNote);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note_dialog, null);
                TextInputLayout numeClientLayout, masinaLayout, constatareLayout, dataProgramareLayout, pretLayout;
                numeClientLayout = view1.findViewById(R.id.numeClientLayout);
                masinaLayout = view1.findViewById(R.id.masinaLayout);
                constatareLayout = view1.findViewById(R.id.constatareLayout);
                dataProgramareLayout = view1.findViewById(R.id.dataProgramareLayout);
                pretLayout = view1.findViewById(R.id.pretLayout);

                TextInputEditText numeClientET, masinaET, constatareET, dataProgramareET, pretET;
                numeClientET = view1.findViewById(R.id.numeClientET);
                masinaET = view1.findViewById(R.id.masinaET);
                constatareET = view1.findViewById(R.id.constatareET);
                dataProgramareET = view1.findViewById(R.id.dataProgramareET);
                pretET = view1.findViewById(R.id.pretET);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add")
                        .setView(view1)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (Objects.requireNonNull(numeClientET.getText()).toString().isEmpty()) {
                                    numeClientLayout.setError("This field is required!");
                                } else if (Objects.requireNonNull(masinaET.getText()).toString().isEmpty()) {
                                    masinaLayout.setError("This field is required!");
                                } else if (Objects.requireNonNull(constatareET.getText()).toString().isEmpty()) {
                                    constatareLayout.setError("This field is required!");
                                } else if (Objects.requireNonNull(dataProgramareET.getText()).toString().isEmpty()) {
                                    dataProgramareLayout.setError("This field is required!");
                                } else if (Objects.requireNonNull(pretET.getText()).toString().isEmpty()) {
                                    pretLayout.setError("This field is required!");
                                } else {
                                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();

                                    Note note = new Note();
                                    note.setNumeClient(numeClientET.getText().toString());
                                    note.setMasina(masinaET.getText().toString());
                                    note.setConstatare(constatareET.getText().toString());
                                    note.setDataProgramare(dataProgramareET.getText().toString());
                                    note.setPret(Double.parseDouble(Objects.requireNonNull(pretET.getText()).toString()));

                                    database.getReference().child("notes").push().setValue(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        TextView empty = findViewById(R.id.empty);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        database.getReference().child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Note> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Note note = dataSnapshot.getValue(Note.class);
                    Objects.requireNonNull(note).setKey(dataSnapshot.getKey());
                    arrayList.add(note);
                }

                if (arrayList.isEmpty()) {
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                NoteAdapter adapter = new NoteAdapter(MainActivity.this, arrayList);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Note note) {
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_note_dialog, null);
                        TextInputLayout numeClientLayout, masinaLayout, constatareLayout, dataProgramareLayout, pretLayout;
                        TextInputEditText numeClientET, masinaET, constatareET, dataProgramareET, pretET;

                        numeClientET = view.findViewById(R.id.numeClientET);
                        masinaET = view.findViewById(R.id.masinaET);
                        constatareET = view.findViewById(R.id.constatareET);
                        dataProgramareET = view.findViewById(R.id.dataProgramareET);
                        pretET = view.findViewById(R.id.pretET);

                        numeClientLayout = view.findViewById(R.id.numeClientLayout);
                        masinaLayout = view.findViewById(R.id.masinaLayout);
                        constatareLayout = view.findViewById(R.id.constatareLayout);
                        dataProgramareLayout = view.findViewById(R.id.dataProgramareLayout);
                        pretLayout = view.findViewById(R.id.pretLayout);

                        numeClientET.setText(note.getNumeClient());
                        masinaET.setText(note.getMasina());
                        constatareET.setText(note.getConstatare());
                        dataProgramareET.setText(note.getDataProgramare());
                        pretET.setText(String.valueOf(note.getPret()));

                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Edit")
                                .setView(view)
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Objects.requireNonNull(numeClientET.getText()).toString().isEmpty()) {
                                            numeClientLayout.setError("This field is required!");
                                        } else if (Objects.requireNonNull(masinaET.getText()).toString().isEmpty()) {
                                            masinaLayout.setError("This field is required!");
                                        } else if (Objects.requireNonNull(constatareET.getText()).toString().isEmpty()) {
                                            constatareLayout.setError("This field is required!");
                                        } else if (Objects.requireNonNull(dataProgramareET.getText()).toString().isEmpty()) {
                                            dataProgramareLayout.setError("This field is required!");
                                        } else if (Objects.requireNonNull(pretET.getText()).toString().isEmpty()) {
                                            pretLayout.setError("This field is required!");
                                        } else {
                                            progressDialog.setMessage("Saving...");
                                            progressDialog.show();

                                            Note note1 = new Note();
                                            note1.setNumeClient(numeClientET.getText().toString());
                                            note1.setMasina(masinaET.getText().toString());
                                            note1.setConstatare(constatareET.getText().toString());
                                            note1.setDataProgramare(dataProgramareET.getText().toString());
                                            note1.setPret(Double.parseDouble(Objects.requireNonNull(pretET.getText()).toString()));

                                            database.getReference().child("notes").child(note.getKey()).setValue(note1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    dialogInterface.dismiss();
                                                    Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                })
                                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Deleting...");
                                        progressDialog.show();
                                        database.getReference().child("notes").child(note.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
