package com.example.android.JSONManipulator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText uneseniText;
    TextView rezultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.jsonBtn);
        uneseniText = (EditText) findViewById(R.id.uneseniJSON);
        rezultat = (TextView) findViewById(R.id.rezultat);

        btn.setOnClickListener(getJSON);

    }

    View.OnClickListener getJSON = new View.OnClickListener() {
        public void onClick(View v) {
            String jSon = uneseniText.getText().toString();
            JSONArray jArray = null;
            TreeMap<String, TreeMap<String, Integer>> mapaVanjska = new TreeMap<>();
            try {
                JSONObject jObject = new JSONObject(jSon);
                Iterator x = jObject.keys();
                while (x.hasNext()) {
                    String key = (String) x.next();
                    JSONObject objekt = jObject.getJSONObject(key);
                    Iterator y = objekt.keys();
                    while (y.hasNext()) {
                        String key1 = (String) y.next();
                        int item = Integer.parseInt(objekt.getString(key1));
                        if (mapaVanjska.containsKey(key)) {
                            if (mapaVanjska.get(key).containsKey(key1)) {
                                int unutarnjibrojevi = mapaVanjska.get(key).get(key1);
                                unutarnjibrojevi = item;
                            } else {
                                int unutarnjiBrojevi = item;
                                mapaVanjska.get(key).put(key1, unutarnjiBrojevi);
                            }
                        } else {
                            int dodaniBrojevi = item;
                            mapaVanjska.put(key, new TreeMap<String, Integer>());
                            mapaVanjska.get(key).put(key1, dodaniBrojevi);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            int[][] dvaD = new int[mapaVanjska.size()][mapaVanjska.keySet().size()];
            int i = 0;
            for (String kljucc : mapaVanjska.keySet()) {
                int j = 0;
                for (String kljuc : mapaVanjska.get(kljucc).keySet()) {
                    dvaD[i][j] = mapaVanjska.get(kljucc).get(kljuc);
                    j++;
                }
                i++;
            }

            for (int r = 0; r < dvaD.length; r++) {
                for (int z = r + 1; z < dvaD[r].length; z++) {
                    if (dvaD[0][r] > dvaD[0][z]) {
                        for(int f = 0; f < dvaD.length; f++) {
                            swap(dvaD, f, r, z);
                        }
                    }
                }
            }

            int maxValue = 0;
            int suma = 0;
            int count = 1;
            for (int q = 0; q < dvaD.length; q++) {
                for (int w = 0; w < dvaD[q].length; w++) {
                    if (!(q == 0) && maxValue == dvaD[q][w]) {
                        //count++;
                        suma = suma + (q + 1) + (w + 1);
                    }
                    if (!(q == 0) && dvaD[q][w] > maxValue) {
                        maxValue = dvaD[q][w];
                        suma = (q + 1) + (w + 1);
                    }

                }
            }
            rezultat.setText("Suma koordinata je: " + suma);
        }
    };

        private void swap(int[][] values,int row,int x, int y) {
            int temp = values[row][x];
            values[row][x] = values[row][y];
            values[row][y] = temp;
        }
    };

