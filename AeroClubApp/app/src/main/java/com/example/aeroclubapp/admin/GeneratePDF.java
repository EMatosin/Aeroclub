package com.example.aeroclubapp.admin;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;

public class GeneratePDF {

    public static void generateAndDownloadPDF(Context context) {
        DatabaseReference aeroclubUserRef = FirebaseDatabase.getInstance().getReference("AeroClubUser");

        aeroclubUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String allUserInfo = "";
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userInfo = getUserInfoAsString(userSnapshot);
                    allUserInfo += userInfo + "\n\n";
                }
                generatePDFForAllUsers(context, allUserInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Erreur lors de la récupération des données", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static String getUserInfoAsString(DataSnapshot userSnapshot) {
        String userInfo = "Mail: " + userSnapshot.child("email").getValue(String.class) + "\n";
        userInfo += "Date de naissance: " + userSnapshot.child("dateNaissance").getValue(String.class) + "\n";
        userInfo += "Nom: " + userSnapshot.child("nom").getValue(String.class) + "\n";
        userInfo += "Email secondaire: " + userSnapshot.child("emailSecond").getValue(String.class) + "\n";
        userInfo += "Surface de stationnement: " + userSnapshot.child("surfaceStationnement").getValue(String.class) + "\n";
        userInfo += "Tarif d'abri: " + userSnapshot.child("tarifAbri").getValue(String.class) + "\n";
        userInfo += "Catégorie d'abri: " + userSnapshot.child("categorieAbri").getValue(String.class) + "\n";
        userInfo += "Date UML Piper: " + userSnapshot.child("datePiper").getValue(String.class) + "\n";
        userInfo += "Date UML Robin: " + userSnapshot.child("dateRobin").getValue(String.class) + "\n";
        userInfo += "Date pour le baptême de l'air: " + userSnapshot.child("dateBaptemeAir").getValue(String.class) + "\n";
        userInfo += "Type de brevet: " + userSnapshot.child("typeBrevet").getValue(String.class) + "\n";
        userInfo += "Type d'avion d'atterrissage: " + userSnapshot.child("typeAvionAtterissage").getValue(String.class) + "\n";
        userInfo += "Période d'atterrissage: " + userSnapshot.child("periodeAtterissage").getValue(String.class) + "\n";
        userInfo += "Groupe acoustique: " + userSnapshot.child("groupeAcoustique").getValue(String.class) + "\n";
        userInfo += "Heure d'atterrissage: " + userSnapshot.child("heureAtterissage").getValue(String.class) + "\n";
        userInfo += "Type de ravitaillement: " + userSnapshot.child("typeRavitaillement").getValue(String.class) + "\n";
        userInfo += "Litre: " + userSnapshot.child("litre").getValue(String.class) + "\n";
        userInfo += "Date de parachutisme: " + userSnapshot.child("dateParachutisme").getValue(String.class) + "\n";
        userInfo += "Forfait de parachutisme: " + userSnapshot.child("forfaitParachutisme").getValue(String.class) + "\n";
        userInfo += "Option de parachutisme: " + userSnapshot.child("optionParachutisme").getValue(String.class) + "\n";


        return userInfo;
    }

    private static void generatePDFForAllUsers(Context context, String content) {
        try {
            String pdfFileName = "Rapport d'activité inscrits AéroClub" + System.currentTimeMillis() + ".pdf";
            File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), pdfFileName);

            PdfWriter writer = new PdfWriter(new FileOutputStream(pdfFile));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Rapport de l'ensemble des utilisateurs"));
            document.add(new Paragraph(content));

            document.close();

            Toast.makeText(context, "Rapport PDF généré : " + pdfFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur lors de la génération du PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
