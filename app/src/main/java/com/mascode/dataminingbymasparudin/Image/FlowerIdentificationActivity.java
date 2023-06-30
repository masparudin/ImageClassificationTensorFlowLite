package com.mascode.dataminingbymasparudin.Image;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.common.model.LocalModel;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions;

import java.util.List;

public class FlowerIdentificationActivity extends ImageClassificationActivity {
    private String[] butterfly_labels = {"Adonis", "African Giant Swallowtail", "American Snoot", "AN 88", "Appollo"};
    private String[] flower_labels = {"Rose", "Dandelion", "Iris", "Sunflower", "Tulip"};
    private String[] hewan_laut_labels = { "Hiu","Lumba-Lumba", "Paus", "Buntal", "Pari", "Belut"};
    private String[] treenut_labels = {"Almonds", "Brazil Nuts", "Cashews", "Hazelnuts", "Macadamia", "Walnuts"};
    private String[] fruit_labels = {"Jeruk", "Pisang", "Apel", "Cherry", "Sawo", "Anggur", "Kiwi", "Strawberry"};
    private String[] makanan_labels = {"Daging Rendang", "Sate", "Bakso", "Gado-Gado", "Gudeg"};
    private String[] gemstone_labels = {"Diamond", "Aquamarine", "Cats Eye", "Emerald", "Jade", "Lapis Lazuli", "Opal", "Blue Saphire", "Ruby"};
    private String[] kematangan_pepaya_labels = {"Matang", "Separuh Matang", "Muda"};
    private String[] jenis_apel_labels = {"Fuji", "Lady", "Golden Delicious", "Granny Smith", "Red Delicious"};
    private String[] apel_or_not_labels = {"Apel", "Bukan Apel"};

    private ImageLabeler imageLabeler;
    private String dataset;
    private String[] kategori;
    LocalModel localModel;
    String label_kelas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String data = intent.getStringExtra("Dataset");

        String bunga = "1. Jenis Bunga";
        String hewan_laut = "2. Jenis Hewan Laut";
        String kupu_kupu = "3. Jenis Kupu-Kupu";
        String buah = "4. Jenis Buah";
        String makanan = "5. Jenis Makanan";
        String tree_nut = "6. Jenis Tree Nut";
        String gemstone = "7. Jenis Gemstone";
        String kem_pepaya = "8. Kematangan Pepaya";
        String jen_apel = "9. Jenis Apel";
        String apel_or_not = "10. Apple or Not";

        if (data.contentEquals(bunga)){
            localModel = new LocalModel.Builder().setAssetFilePath("model_flowers.tflite").build();
            kategori = flower_labels;
        }else if (data.contentEquals(hewan_laut)){
            localModel = new LocalModel.Builder().setAssetFilePath("hewan_laut_model.tflite").build();
            kategori = hewan_laut_labels;
        }else if (data.contentEquals(tree_nut)){
            localModel = new LocalModel.Builder().setAssetFilePath("treenut_model.tflite").build();
            kategori = treenut_labels;
        }else if (data.contentEquals(kupu_kupu)){
            localModel = new LocalModel.Builder().setAssetFilePath("butterfly_model.tflite").build();
            kategori = butterfly_labels;
        }else if (data.contentEquals(buah)){
            localModel = new LocalModel.Builder().setAssetFilePath("fruits_model.tflite").build();
            kategori = fruit_labels;
        }else if (data.contentEquals(makanan)){
            localModel = new LocalModel.Builder().setAssetFilePath("makanan_model.tflite").build();
            kategori = makanan_labels;
        }else if (data.contentEquals(gemstone)){
            localModel = new LocalModel.Builder().setAssetFilePath("gemstone_model.tflite").build();
            kategori = gemstone_labels;
        }else if (data.contentEquals(kem_pepaya)){
            localModel = new LocalModel.Builder().setAssetFilePath("kematangan_pepaya_model.tflite").build();
            kategori = kematangan_pepaya_labels;
        }else if (data.contentEquals(jen_apel)){
            localModel = new LocalModel.Builder().setAssetFilePath("300epoch_model.tflite").build();
            kategori = jenis_apel_labels;
        }else if (data.contentEquals(apel_or_not)){
            localModel = new LocalModel.Builder().setAssetFilePath("apel_or_not.tflite").build();
            kategori = apel_or_not_labels;
        }


        CustomImageLabelerOptions options = new CustomImageLabelerOptions.Builder(localModel)
                .setConfidenceThreshold(0.5f)
                .setMaxResultCount(5).build();
        imageLabeler = ImageLabeling.getClient(options);

    }

    @Override
    protected void runClassification(Bitmap bitmap) {
        InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
        imageLabeler.process(inputImage).addOnSuccessListener(new OnSuccessListener<List<ImageLabel>>() {
            @Override
            public void onSuccess(@NonNull List<ImageLabel> imageLabels) {
                if (imageLabels.size() > 0) {
                    StringBuilder builder = new StringBuilder();
                    for(ImageLabel label : imageLabels) {
                        builder.append(label.getText())
                                .append(label.getIndex()+" : ")
                                .append(label.getConfidence())
                                .append("\n");
                        label_kelas = kategori[label.getIndex()];
                    }
                    getOutputTextView().setText(label_kelas+ " "+builder.toString());
                }else {
                    getOutputTextView().setText("Tidak bisa diklasifikasikan");
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }
}
